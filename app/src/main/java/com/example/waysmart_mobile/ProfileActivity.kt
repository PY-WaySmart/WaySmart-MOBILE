package com.example.waysmart_mobile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.waysmart_mobile.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    companion object {
        const val PREFS_NAME = "WaySmartUserPrefs"
        const val KEY_NOMBRE = "user_nombre"
        const val KEY_CORREO = "user_correo"
        const val KEY_TELEFONO = "user_telefono"
        const val KEY_CALIFICACION = "user_calificacion"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cargarDatosUsuario()
        configurarEventos()
    }

    private fun cargarDatosUsuario() {
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        val nombre = intent.getStringExtra(KEY_NOMBRE)
            ?: prefs.getString(KEY_NOMBRE, "Juan Pérez")

        val correo = intent.getStringExtra(KEY_CORREO)
            ?: prefs.getString(KEY_CORREO, "juan.perez@email.com")

        val telefono = intent.getStringExtra(KEY_TELEFONO)
            ?: prefs.getString(KEY_TELEFONO, "+57 300 123 4567")

        val calificacion = intent.getStringExtra(KEY_CALIFICACION)
            ?: prefs.getString(KEY_CALIFICACION, "4.8 ★")

        binding.tvProfileNombre.text = nombre
        binding.tvProfileCorreo.text = correo
        binding.tvProfileTelefono.text = telefono
        binding.tvProfileCalificacion.text = calificacion
    }

    private fun configurarEventos() {
        // 1. Botón Volver (flecha superior izquierda -> HomeActivity)
        binding.btnProfileVolver.setOnClickListener {
            Toast.makeText(this, "Volviendo a Pantalla Principal...", Toast.LENGTH_SHORT).show()

            // TODO: INTEGRACIÓN - Descomentar cuando HomeActivity exista en el proyecto:
            /*
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
            */
        }

        // 2. Botón Cerrar Sesión (con confirmación Sí / No -> LoginActivity)
        binding.btnProfileCerrarSesion.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Cerrar Sesión")
                .setMessage("¿Estás seguro de que deseas cerrar sesión?")
                .setPositiveButton("Sí") { _, _ ->
                    // Limpia los datos de sesión guardados en el dispositivo
                    val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                    prefs.edit().clear().apply()

                    // Feedback visual mientras se integra LoginActivity
                    binding.tvProfileNombre.text = "Sesión cerrada"
                    binding.tvProfileCorreo.text = "---"
                    binding.tvProfileTelefono.text = "---"

                    Toast.makeText(this, "Sesión cerrada. Redirigiendo a Login...", Toast.LENGTH_SHORT).show()

                    // TODO: INTEGRACIÓN - Descomentar cuando LoginActivity exista en el proyecto:
                    /*
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                    */
                }
                .setNegativeButton("No", null)
                .show()
        }
    }
}