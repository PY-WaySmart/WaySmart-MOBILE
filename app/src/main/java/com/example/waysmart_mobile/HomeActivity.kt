package com.example.waysmart_mobile

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        findViewById<LinearLayout>(R.id.cardSolicitudes).setOnClickListener {
            startActivity(Intent(this, ServicesActivity::class.java))
        }

        // TODO: integrar cuando exista VehicleActivity
        // findViewById<LinearLayout>(R.id.cardVehiculo).setOnClickListener {
        //     startActivity(Intent(this, VehicleActivity::class.java))
        // }

        // TODO: integrar cuando exista ProfileActivity
        // findViewById<LinearLayout>(R.id.cardPerfil).setOnClickListener {
        //     startActivity(Intent(this, ProfileActivity::class.java))
        // }
    }
}