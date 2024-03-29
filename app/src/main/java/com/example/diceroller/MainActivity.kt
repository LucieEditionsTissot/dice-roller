package com.example.diceroller

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import nl.dionsegijn.konfetti.KonfettiView

class MainActivity : AppCompatActivity() {
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private var isRolling = false
    private lateinit var rollButton: Button
    private lateinit var numberSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handler = Handler(Looper.getMainLooper())

        numberSpinner = findViewById(R.id.guess)
        rollButton = findViewById(R.id.bton)
        rollButton.isEnabled = false

        addGuess()

        runnable = Runnable {
            if (isRolling) {
                rollDice(numberSpinner.selectedItem.toString())
            }
        }

        rollButton.setOnClickListener {
            if (isRolling) {
                stopRolling()
            } else {
                startRolling()
            }
        }

        val resultTextView1: TextView = findViewById(R.id.textView1)
        val resultTextView2: TextView = findViewById(R.id.textView2)
        val stopRollingListener = View.OnClickListener {
            if (isRolling) {
                stopRolling()
            }
        }
        resultTextView1.setOnClickListener(stopRollingListener)
        resultTextView2.setOnClickListener(stopRollingListener)
    }
    private fun rollDice(guess: String) {
        if (!isRolling) return
        // Création des dés
        val dice1 = Dice(6)
        val dice2 = Dice(6)

        // Lancer les dés
        val dice1Roll = dice1.roll()
        val dice2Roll = dice2.roll()

        // Mise à jour des résultats
        val resultTextView1: TextView = findViewById(R.id.textView1)
        val resultTextView2: TextView = findViewById(R.id.textView2)
        resultTextView1.text = dice1Roll.toString()
        resultTextView2.text = dice2Roll.toString()

        val result = dice1Roll + dice2Roll
        val winText: TextView = findViewById(R.id.winText)
        val rollButton: Button = findViewById(R.id.bton)

        if (result.toString() == guess) {
            isRolling = false
            winText.text = getString(R.string.win)
            rollButton.text = getString(R.string.lancer_les_d_s)
            celebrateWin()
        } else {
            winText.text = ""
        }

        handler.postDelayed(runnable, 100)
    }

    private fun celebrateWin() {
        val konfettiView = findViewById<KonfettiView>(R.id.viewKonfetti)
        konfettiView.build()
            .addColors(
                android.graphics.Color.YELLOW,
                android.graphics.Color.GREEN,
                android.graphics.Color.MAGENTA
            )
            .setDirection(0.0, 359.0)
            .setSpeed(1f, 5f)
            .setFadeOutEnabled(true)
            .setTimeToLive(1000L)
            .addShapes(
                nl.dionsegijn.konfetti.models.Shape.Square,
                nl.dionsegijn.konfetti.models.Shape.Circle
            )
            .addSizes(nl.dionsegijn.konfetti.models.Size(12))
            .setPosition(-50f, konfettiView.width + 50f, -50f, -50f)
            .streamFor(300, 2000L)
    }

    private fun addGuess() {
        val initialList = listOf("0")
        val numbersList = initialList + (2..12).map { it.toString() }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, numbersList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        numberSpinner.adapter = adapter

        numberSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedNumber = parent.getItemAtPosition(position).toString()
                rollButton.isEnabled = selectedNumber != "0"
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun startRolling() {
        isRolling = true
        handler.postDelayed(runnable, 100)
        rollButton.text = getString(R.string.arr_ter_les_d_s)
    }

    private fun stopRolling() {
        handler.removeCallbacks(runnable)
        isRolling = false
        rollButton.text = getString(R.string.lancer_les_d_s)
        rollButton.isEnabled = true
    }
}
