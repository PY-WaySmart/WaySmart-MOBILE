package com.example.waysmart_mobile

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnVolver = findViewById<ImageButton>(R.id.btnVolver)
        val etNombreCompleto = findViewById<EditText>(R.id.etNombreCompleto)
        val etCorreo = findViewById<EditText>(R.id.etCorreo)
        val etTelefono = findViewById<EditText>(R.id.etTelefono)
        val spinnerTipoVehiculo = findViewById<Spinner>(R.id.spinnerTipoVehiculo)
        val etPlaca = findViewById<EditText>(R.id.etPlaca)
        val btnRegistrarse = findViewById<Button>(R.id.btnRegistrarse)
        val tvYaTienesCuenta = findViewById<TextView>(R.id.tvYaTienesCuenta)

        btnVolver.setOnClickListener {
            finish()
        }

        val tiposVehiculo = listOf("Tipo de vehículo", "Carro", "Moto")
        val adapter = object : ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            tiposVehiculo
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent) as TextView
                view.setTextColor(if (position == 0) Color.parseColor("#64748B") else Color.BLACK)
                view.textSize = 15f
                return view
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent) as TextView
                view.setTextColor(Color.BLACK)
                view.textSize = 15f
                return view
            }
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTipoVehiculo.adapter = adapter

        btnRegistrarse.setOnClickListener {
            val nombre = etNombreCompleto.text.toString().trim()
            val correo = etCorreo.text.toString().trim()
            val telefono = etTelefono.text.toString().trim()
            val vehiculoPos = spinnerTipoVehiculo.selectedItemPosition
            val placa = etPlaca.text.toString().trim().uppercase()

            if (nombre.isEmpty()) {
                etNombreCompleto.error = "Ingresa tu nombre completo"
                etNombreCompleto.requestFocus()
                return@setOnClickListener
            }

            if (correo.isEmpty()) {
                etCorreo.error = "Ingresa tu correo electrónico"
                etCorreo.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                etCorreo.error = "Ingresa un correo electrónico válido"
                etCorreo.requestFocus()
                return@setOnClickListener
            }

            if (telefono.isEmpty()) {
                etTelefono.error = "Ingresa tu número de teléfono"
                etTelefono.requestFocus()
                return@setOnClickListener
            }

            if ((!Patterns.PHONE.matcher(telefono).matches()) || (telefono.length < 7)) {
                etTelefono.error = "Ingresa un número de teléfono válido"
                etTelefono.requestFocus()
                return@setOnClickListener
            }

            if (vehiculoPos == 0) {
                Toast.makeText(this, "Selecciona el tipo de vehículo", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (placa.isEmpty()) {
                etPlaca.error = "Ingresa la placa del vehículo"
                etPlaca.requestFocus()
                return@setOnClickListener
            }

            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        tvYaTienesCuenta.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}