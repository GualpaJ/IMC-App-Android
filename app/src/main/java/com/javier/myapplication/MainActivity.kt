package com.javier.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.pow
import com.google.android.material.slider.Slider
import java.util.Locale
import android.view.View

class MainActivity : AppCompatActivity() {

    var weight: Int = 70
    var height: Int = 160

    lateinit var heightSlider: Slider
    lateinit var heightTextView: TextView
    lateinit var weightTextView: TextView
    lateinit var addWeightButton: Button
    lateinit var removeWeightButton: Button

    lateinit var calcularButton: Button
    lateinit var resultTextView: TextView

    lateinit var resultCardView: CardView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Height
        heightSlider = findViewById(R.id.heightSlider)
        heightTextView = findViewById(R.id.heightTextView)

        heightSlider.addOnChangeListener { slider, f, bool ->
            height = heightSlider.value.toInt()
            heightTextView.text = "$height cm"
        }

        //Weight
        weightTextView = findViewById(R.id.weightTextView)
        addWeightButton = findViewById(R.id.addWeightButton)
        removeWeightButton = findViewById(R.id.removeWeightButton)

        addWeightButton.setOnClickListener {
            weight++
            weightTextView.text = "$weight kg"
        }

        removeWeightButton.setOnClickListener {
            weight--
            weightTextView.text = "$weight kg"
        }

        //Result
        calcularButton = findViewById(R.id.calcularButton)
        resultTextView = findViewById(R.id.resultTextView)
        resultCardView = findViewById(R.id.resultCardView)

        val resultCardText = findViewById<TextView>(R.id.resultCardText)

        calcularButton.setOnClickListener {
            // Calcula el IMC y actualiza el TextView con el valor
            val heighInMeter = height / 100f
            val result = weight / heighInMeter.pow(2)
            resultTextView.text = String.format(Locale.getDefault(), "%.2f", result) // valor numérico

            // Muestra el CardView
            resultCardView.visibility = View.VISIBLE

            // Cambia el color y el texto de la frase según el rango
            when {
                result < 18.5 -> {
                    resultCardView.setCardBackgroundColor(getColor(R.color.blue))
                    resultCardText.text = "Bajo peso"
                }
                result in 18.5..24.9 -> {
                    resultCardView.setCardBackgroundColor(getColor(R.color.green))
                    resultCardText.text = "Normal"
                }
                result in 25.0..29.9 -> {
                    resultCardView.setCardBackgroundColor(getColor(R.color.yellow))
                    resultCardText.text = "Sobrepeso"
                }
                else -> {
                    resultCardView.setCardBackgroundColor(getColor(R.color.red))
                    resultCardText.text = "Sobrepeso Extremo"
                }
            }
        }


    }

    private fun calcularBMI() {
        val heighInMeter = height / 100f
        val result = weight / heighInMeter.pow(2)
        resultTextView.text = String.format(Locale.getDefault(), "%.2f", result)
    }
}