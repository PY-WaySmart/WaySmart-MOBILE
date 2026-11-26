package com.example.waysmart_mobile

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {

    companion object {
        private const val VALID_EMAIL = "waysmart.business@gmail.com"
        private const val VALID_PASSWORD = "waysmart12345"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login)

        val mainView = findViewById<View>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupUI()
    }

    private fun setupUI() {
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvRegister = findViewById<TextView>(R.id.tvRegister)
        val btnBack = findViewById<ImageButton>(R.id.btnBack)

        btnBack.setOnClickListener {
            finish()
        }

        val fullText = getString(R.string.no_account_register)
        val boldPart = getString(R.string.register_bold_part)
        val spannable = SpannableString(fullText)
        val startIndex = fullText.indexOf(boldPart)
        if (startIndex >= 0) {
            spannable.setSpan(
                StyleSpan(Typeface.BOLD),
                startIndex,
                startIndex + boldPart.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE,
            )
        }
        tvRegister.text = spannable

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty()) {
                etEmail.error = getString(R.string.error_email_required)
                etEmail.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.error = "Ingresa un correo electrónico válido"
                etEmail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                etPassword.error = getString(R.string.error_password_required)
                etPassword.requestFocus()
                return@setOnClickListener
            }

            if ((email != VALID_EMAIL) || (password != VALID_PASSWORD)) {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show()
                etEmail.error = "Usuario no encontrado o credenciales incorrectas"
                etEmail.requestFocus()
                return@setOnClickListener
            }

            Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}