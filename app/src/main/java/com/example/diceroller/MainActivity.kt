package com.example.diceroller

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

/**
 * Cette activité permet à l'utilisateur de lancer un dé et d'afficher le résultat sur l'écran
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val rollButton: Button = findViewById(R.id.bton)
        rollButton.setOnClickListener {
            rollDice1()

        }
    }
    /**
     * Lance le dé et affiche le résultat dans le textview
     */
    private fun rollDice1() {
        //Créer l'objet
        val dice1 = Dice(6)
        val dice1Roll = dice1.roll()
        val dice2 = Dice(6)
        val dice2Roll = dice2.roll()

        //met à jour le texte
        val resultTextView1: TextView = findViewById(R.id.textView1)
        resultTextView1.text = dice1Roll.toString()
        val resultTextView2: TextView = findViewById(R.id.textView2)
        resultTextView2.text = dice2Roll.toString()

        if(dice1Roll.toString() == dice2Roll.toString()) {
            val winText: TextView = findViewById(R.id.winText)
            winText.text = "You win !"
        }
    }


}