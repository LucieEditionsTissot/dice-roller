package com.example.diceroller

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextSwitcher
import android.widget.TextView

/**
 * Cette activité permet à l'utilisateur de lancer un dé et d'afficher le résultat sur l'écran
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addGuess()

    }
    /**
     * Lance le dé et affiche le résultat dans le textview
     */
    private fun rollDice(guess : String) {
        //Créer l'objet
        val dice1 = Dice(6)
        val dice1Roll = dice1.roll()
        val dice2 = Dice(6)
        val dice2Roll = dice2.roll()
        val winText: TextView = findViewById(R.id.winText)

        //met à jour le texte
        val resultTextView1: TextView = findViewById(R.id.textView1)
        resultTextView1.text = dice1Roll.toString()
        val resultTextView2: TextView = findViewById(R.id.textView2)
        resultTextView2.text = dice2Roll.toString()
        val result = dice1Roll + dice2Roll

        if(result.toString() == guess) {
            winText.text = "Bravo tu as deviné"
        }
    }

    private fun addGuess() {

        val numberSpinner: Spinner = findViewById(R.id.guess)

        val numbersList = (2..12).toList()

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, numbersList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


        numberSpinner.adapter = adapter


        numberSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedNumber = parent.getItemAtPosition(position) as Int
                val rollButton: Button = findViewById(R.id.bton)
                rollButton.isEnabled = true
                rollButton.setOnClickListener {
                    rollDice(selectedNumber.toString())
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }




    }


}