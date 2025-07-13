package com.example.myapplication.UI

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class MainActivity : AppCompatActivity() {

    private lateinit var inputName : EditText
    private lateinit var inputSentence : EditText
    private lateinit var buttonCheck : Button
    private lateinit var buttonNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputName = findViewById(R.id.editTextUsername)
        inputSentence = findViewById(R.id.editTextPalindrome)
        buttonCheck = findViewById(R.id.buttonCheck)
        buttonNext = findViewById(R.id.buttonLogin)


        buttonCheck.setOnClickListener{
            val sentence = inputSentence.text.toString()
            if (isPalindrome(sentence)){
                showDialog("isPalindrome")
            }else{
                showDialog("not Palindrome")
            }
        }

        buttonNext.setOnClickListener{
            val name = inputName.text.toString()
            val intent = Intent(this, SecondScreen::class.java)
            intent.putExtra("username", name)
            startActivity(intent)
        }

    }

    private fun isPalindrome(input: String): Boolean {
        val cleanInput = input.replace("\\s".toRegex(), "").lowercase()
        return cleanInput == cleanInput.reversed()
    }

    private fun showDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Result")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss()}
            .show()
    }

}