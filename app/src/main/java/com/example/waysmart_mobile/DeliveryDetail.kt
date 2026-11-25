package com.example.waysmart_mobile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DeliveryDetailActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled", "ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_detail)

        // Referencias a vistas
        val btnBack = findViewById<ImageButton>(R.id.btn_delivery_back)
        val tvEstado = findViewById<TextView>(R.id.tv_delivery_estado)
        val tvOrigen = findViewById<TextView>(R.id.tv_delivery_origen)
        val tvDestino = findViewById<TextView>(R.id.tv_delivery_destino)
        val tvCliente = findViewById<TextView>(R.id.tv_delivery_cliente)
        val tvMonto = findViewById<TextView>(R.id.tv_delivery_monto)

        val btnAceptar = findViewById<Button>(R.id.btn_delivery_aceptar)
        val btnIniciar = findViewById<Button>(R.id.btn_delivery_iniciar)
        val btnEntregado = findViewById<Button>(R.id.btn_delivery_entregado)

        // Configurar WebView con mapa interactivo Leaflet + ruta real OSRM
        val webViewMapa = findViewById<WebView>(R.id.webview_delivery_map)
        webViewMapa.settings.javaScriptEnabled = true
        webViewMapa.settings.domStorageEnabled = true
        webViewMapa.webViewClient = WebViewClient()

        // Evitar que el ScrollView robe el gesto al mover o hacer zoom en el mapa
        webViewMapa.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE ->
                    view.parent.requestDisallowInterceptTouchEvent(true)
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL ->
                    view.parent.requestDisallowInterceptTouchEvent(false)
            }
            false
        }

        val mapaHtml = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
                <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css" />
                <script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script>
                <style>
                    html, body, #map { height: 100%; width: 100%; margin: 0; padding: 0; }
                </style>
            </head>
            <body>
                <div id="map"></div>
                <script>
                    var origen = [2.4419, -76.6063];
                    var destino = [2.4535, -76.5982];

                    var map = L.map('map').setView(origen, 14);
                    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                        attribution: 'OSM'
                    }).addTo(map);

                    L.marker(origen).addTo(map).bindPopup('Origen: Calle 10 # 5-20');
                    L.marker(destino).addTo(map).bindPopup('Destino: Av. Panamericana # 30-15');

                    // Ruta real respetando sentidos viales y contravías
                    var url = 'https://router.project-osrm.org/route/v1/driving/' +
                              origen[1] + ',' + origen[0] + ';' +
                              destino[1] + ',' + destino[0] +
                              '?overview=full&geometries=geojson';

                    fetch(url)
                        .then(res => res.json())
                        .then(data => {
                            if (data.routes && data.routes.length > 0) {
                                var coords = data.routes[0].geometry.coordinates.map(c => [c[1], c[0]]);
                                var linea = L.polyline(coords, { color: '#1967D2', weight: 5 }).addTo(map);
                                map.fitBounds(linea.getBounds(), { padding: [25, 25] });
                            }
                        });
                </script>
            </body>
            </html>
        """.trimIndent()

        webViewMapa.loadDataWithBaseURL("https://localhost", mapaHtml, "text/html", "UTF-8", null)

        // Recibir datos opcionales por Intent
        intent.getStringExtra("EXTRA_ORIGEN")?.let { tvOrigen.text = "Origen: $it" }
        intent.getStringExtra("EXTRA_DESTINO")?.let { tvDestino.text = "Destino: $it" }
        intent.getStringExtra("EXTRA_CLIENTE")?.let { tvCliente.text = "Cliente: $it" }
        intent.getStringExtra("EXTRA_MONTO")?.let { tvMonto.text = "Monto: $it" }

        // Botón regresar
        btnBack.setOnClickListener { finish() }

        // Flujo de estados
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