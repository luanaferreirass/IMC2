package com.example.imc

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etPeso: EditText
    private lateinit var etAltura: EditText
    private lateinit var btnCalcular: Button
    private lateinit var tvResultado: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializando os componentes
        etPeso = findViewById(R.id.etPeso)
        etAltura = findViewById(R.id.etAltura)
        btnCalcular = findViewById(R.id.btnCalcular)
        tvResultado = findViewById(R.id.tvResultado)

        // Ação do botão de cálculo
        btnCalcular.setOnClickListener {
            val pesoStr = etPeso.text.toString()
            val alturaStr = etAltura.text.toString()

            // Verifica se os campos estão vazios
            if (pesoStr.isEmpty() || alturaStr.isEmpty()) {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
                if (pesoStr.isEmpty()) etPeso.requestFocus()
                else etAltura.requestFocus()
                return@setOnClickListener
            }

            val peso = pesoStr.toFloatOrNull()
            val altura = alturaStr.toFloatOrNull()

            if (peso == null || altura == null || peso <= 0f || altura <= 0f) {
                Toast.makeText(this, "Por favor, insira valores válidos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Valida altura irrealista (menor que 0.5m ou maior que 3m)
            if (altura !in 0.5f..3.0f) {
                Toast.makeText(this, "Por favor, insira uma altura realista (0.5 a 3 metros).", Toast.LENGTH_SHORT).show()
                etAltura.requestFocus()
                return@setOnClickListener
            }

            // Calcular o IMC
            val imc = peso / (altura * altura)
            val classificacao = when {
                imc < 18.5 -> "Abaixo do peso"
                imc in 18.5..24.9 -> "Peso normal"
                imc in 25.0..29.9 -> "Sobrepeso"
                else -> "Obesidade"
            }

            // Exibir o resultado
            tvResultado.text = "IMC: %.2f\n\nClassificação: $classificacao".format(imc)
        }
    }
}
