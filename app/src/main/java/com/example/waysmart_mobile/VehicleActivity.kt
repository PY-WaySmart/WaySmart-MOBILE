package com.example.waysmart_mobile

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class VehicleActivity : AppCompatActivity() {

    private lateinit var tvVehicleType: TextView
    private lateinit var tvVehicleBrandModel: TextView
    private lateinit var tvVehiclePlate: TextView
    private lateinit var tvVehicleStatus: TextView
    private lateinit var tvOdometer: TextView
    private lateinit var tvFuelLevel: TextView
    private lateinit var tvModelYear: TextView
    private lateinit var tvLastMaintenance: TextView

    private lateinit var ivVehicleIllustration: ImageView
    private lateinit var heroIllustrationContainer: View
    private lateinit var heroCardContainer: View
    private lateinit var gridMetrics: View
    private lateinit var cardDocumentation: View
    private lateinit var chipGpsStatus: View
    private lateinit var progressFuel: ProgressBar
    private lateinit var btnEditVehicleData: Button
    private lateinit var btnBack: ImageButton

    private var floatingAnimator: ObjectAnimator? = null
    private var haloPulseAnimator: ObjectAnimator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_vehicle)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainVehicleLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        loadVehicleData()
        setupListeners()
        startAnimations()
    }

    private fun initViews() {
        tvVehicleType = findViewById(R.id.tvVehicleType)
        tvVehicleBrandModel = findViewById(R.id.tvVehicleBrandModel)
        tvVehiclePlate = findViewById(R.id.tvVehiclePlate)
        tvVehicleStatus = findViewById(R.id.tvVehicleStatus)
        tvOdometer = findViewById(R.id.tvOdometer)
        tvFuelLevel = findViewById(R.id.tvFuelLevel)
        tvModelYear = findViewById(R.id.tvModelYear)
        tvLastMaintenance = findViewById(R.id.tvLastMaintenance)

        ivVehicleIllustration = findViewById(R.id.ivVehicleIllustration)
        heroIllustrationContainer = findViewById(R.id.heroIllustrationContainer)
        heroCardContainer = findViewById(R.id.heroCardContainer)
        gridMetrics = findViewById(R.id.gridMetrics)
        cardDocumentation = findViewById(R.id.cardDocumentation)
        chipGpsStatus = findViewById(R.id.chipGpsStatus)
        progressFuel = findViewById(R.id.progressFuel)
        btnEditVehicleData = findViewById(R.id.btnEditVehicleData)
        btnBack = findViewById(R.id.btnBack)
    }

    /**
     * Carga los datos iniciales de prueba del vehículo.
     */
    private fun loadVehicleData() {
        tvVehicleType.text = "Motocicleta"
        tvVehicleBrandModel.text = "Honda Cargo 150"
        tvVehiclePlate.text = "XYZ-123"
        tvVehicleStatus.text = getString(R.string.status_active)
        tvOdometer.text = "14,280 km"
        tvFuelLevel.text = "85%"
        tvModelYear.text = "2023"
        tvLastMaintenance.text = "15 Feb 2025"
    }

    private fun setupListeners() {
        btnBack.setOnClickListener {
            it.animate()
                .scaleX(0.9f)
                .scaleY(0.9f)
                .setDuration(100)
                .withEndAction {
                    it.scaleX = 1.0f
                    it.scaleY = 1.0f
                    finish()
                }
                .start()
        }

        btnEditVehicleData.setOnClickListener { view ->
            view.animate()
                .scaleX(0.95f)
                .scaleY(0.95f)
                .setDuration(100)
                .withEndAction {
                    view.scaleX = 1.0f
                    view.scaleY = 1.0f
                    showEditVehicleDialog()
                }
                .start()
        }
    }

    /**
     * Inicia las animaciones de entrada, pulso, barra de combustible y levitación continua.
     */
    private fun startAnimations() {
        // 1. Flotación continua para la motocicleta
        floatingAnimator = ObjectAnimator.ofFloat(ivVehicleIllustration, "translationY", -7f, 7f).apply {
            duration = 1800
            repeatMode = ValueAnimator.REVERSE
            repeatCount = ValueAnimator.INFINITE
            start()
        }

        // 2. Respiración suave en el halo de luz detrás de la moto ✨
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, 1.06f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, 1.06f)
        haloPulseAnimator = ObjectAnimator.ofPropertyValuesHolder(heroIllustrationContainer, scaleX, scaleY).apply {
            duration = 1600
            repeatMode = ValueAnimator.REVERSE
            repeatCount = ValueAnimator.INFINITE
            start()
        }

        // 3. Pulso para el badge de GPS Activo
        val pulseAnim = AnimationUtils.loadAnimation(this, R.anim.pulse_glow)
        chipGpsStatus.startAnimation(pulseAnim)

        // 4. Llenado animado de la barra de combustible (0% -> 85%) ⛽
        ObjectAnimator.ofInt(progressFuel, "progress", 0, 85).apply {
            duration = 1000
            startDelay = 300
            interpolator = DecelerateInterpolator()
            start()
        }

        // 5. Entradas escalonadas con Slide Up Fade
        val slideUpHero = AnimationUtils.loadAnimation(this, R.anim.slide_up_fade)
        val slideUpGrid = AnimationUtils.loadAnimation(this, R.anim.slide_up_fade)
        val slideUpDocs = AnimationUtils.loadAnimation(this, R.anim.slide_up_fade)

        heroCardContainer.startAnimation(slideUpHero)

        gridMetrics.postDelayed({
            gridMetrics.visibility = View.VISIBLE
            gridMetrics.startAnimation(slideUpGrid)
        }, 150)

        cardDocumentation.postDelayed({
            cardDocumentation.visibility = View.VISIBLE
            cardDocumentation.startAnimation(slideUpDocs)
        }, 300)
    }

    /**
     * Muestra el cuadro de diálogo para editar la información del vehículo.
     */
    private fun showEditVehicleDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_vehicle, null)
        val etType = dialogView.findViewById<EditText>(R.id.etVehicleType)
        val etBrandModel = dialogView.findViewById<EditText>(R.id.etVehicleBrandModel)
        val etPlate = dialogView.findViewById<EditText>(R.id.etVehiclePlate)
        val etOdometer = dialogView.findViewById<EditText>(R.id.etVehicleOdometer)
        val btnSave = dialogView.findViewById<Button>(R.id.btnDialogSave)
        val btnCancel = dialogView.findViewById<Button>(R.id.btnDialogCancel)

        etType.setText(tvVehicleType.text)
        etBrandModel.setText(tvVehicleBrandModel.text)
        etPlate.setText(tvVehiclePlate.text)
        etOdometer.setText(tvOdometer.text)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        btnSave.setOnClickListener {
            val newType = etType.text.toString().trim()
            val newBrandModel = etBrandModel.text.toString().trim()
            val newPlate = etPlate.text.toString().trim()
            val newOdometer = etOdometer.text.toString().trim()

            if (newType.isEmpty() || newBrandModel.isEmpty() || newPlate.isEmpty() || newOdometer.isEmpty()) {
                Toast.makeText(this, getString(R.string.error_empty_fields), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Actualizar datos en pantalla
            tvVehicleType.text = newType
            tvVehicleBrandModel.text = newBrandModel
            tvVehiclePlate.text = newPlate.uppercase()
            tvOdometer.text = newOdometer

            // Efecto rebote en la tarjeta principal al guardar
            heroCardContainer.animate()
                .scaleX(1.02f)
                .scaleY(1.02f)
                .setDuration(120)
                .withEndAction {
                    heroCardContainer.animate().scaleX(1.0f).scaleY(1.0f).setDuration(120).start()
                }
                .start()

            Toast.makeText(this, getString(R.string.toast_updated_success), Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        floatingAnimator?.cancel()
        haloPulseAnimator?.cancel()
    }
}