
package com.example.waysmart_mobile

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val cardSolicitudes = findViewById<LinearLayout>(R.id.cardSolicitudes)
        val cardVehiculo = findViewById<LinearLayout>(R.id.cardVehiculo)
        val cardPerfil = findViewById<LinearLayout>(R.id.cardPerfil)

        cardSolicitudes.setOnClickListener {
            startActivity(Intent(this, ServicesActivity::class.java))
        }

        cardVehiculo.setOnClickListener {
            startActivity(Intent(this, VehicleActivity::class.java))
        }

        cardPerfil.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}

