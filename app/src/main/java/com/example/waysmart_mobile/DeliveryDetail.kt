package com.example.waysmart_mobile

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.waysmart_mobile.R

class DeliveryDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_detail)

        // Referencias a vistas (IDs únicos de esta pantalla)
        val btnBack = findViewById<ImageButton>(R.id.btn_delivery_back)
        val tvEstado = findViewById<TextView>(R.id.tv_delivery_estado)
        val tvOrigen = findViewById<TextView>(R.id.tv_delivery_origen)
        val tvDestino = findViewById<TextView>(R.id.tv_delivery_destino)
        val tvCliente = findViewById<TextView>(R.id.tv_delivery_cliente)
        val tvMonto = findViewById<TextView>(R.id.tv_delivery_monto)

        val btnAceptar = findViewById<Button>(R.id.btn_delivery_aceptar)
        val btnIniciar = findViewById<Button>(R.id.btn_delivery_iniciar)
        val btnEntregado = findViewById<Button>(R.id.btn_delivery_entregado)

        // Recibir datos opcionales desde ServicesActivity mediante Intent
        val origenExtra = intent.getStringExtra("EXTRA_ORIGEN")
        val destinoExtra = intent.getStringExtra("EXTRA_DESTINO")
        val clienteExtra = intent.getStringExtra("EXTRA_CLIENTE")
        val montoExtra = intent.getStringExtra("EXTRA_MONTO")

        if (!origenExtra.isNullOrEmpty()) tvOrigen.text = "Origen: $origenExtra"
        if (!destinoExtra.isNullOrEmpty()) tvDestino.text = "Destino: $destinoExtra"
        if (!clienteExtra.isNullOrEmpty()) tvCliente.text = "Cliente: $clienteExtra"
        if (!montoExtra.isNullOrEmpty()) tvMonto.text = "Monto: $montoExtra"

        // Botón regresar a la pantalla anterior (ServicesActivity)
        btnBack.setOnClickListener {
            finish()
        }

        // Flujo de estados del pedido
        btnAceptar.setOnClickListener {
            tvEstado.text = "Estado: Solicitud aceptada"
            btnAceptar.isEnabled = false
            btnIniciar.isEnabled = true
            Toast.makeText(this, "Has aceptado la entrega", Toast.LENGTH_SHORT).show()
        }

        btnIniciar.setOnClickListener {
            tvEstado.text = "Estado: En camino al destino"
            btnIniciar.isEnabled = false
            btnEntregado.isEnabled = true
            Toast.makeText(this, "Recorrido iniciado", Toast.LENGTH_SHORT).show()
        }

        btnEntregado.setOnClickListener {
            tvEstado.text = "Estado: Pedido entregado"
            btnEntregado.isEnabled = false
            Toast.makeText(this, "¡Entrega completada con éxito!", Toast.LENGTH_LONG).show()
        }
    }
}