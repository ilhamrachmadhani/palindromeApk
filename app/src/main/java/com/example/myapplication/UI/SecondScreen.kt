package com.example.myapplication.UI


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.myapplication.R


class SecondScreen : AppCompatActivity() {

    private lateinit var textUsername: TextView
    private lateinit var textSelectedUser: TextView
    private lateinit var buttonChooseUser: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_screen)


        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Second Screen"


        textUsername = findViewById(R.id.textUsername)
        textSelectedUser = findViewById(R.id.textSelectedUser)
        buttonChooseUser = findViewById(R.id.buttonChooseUser)


        val username = intent.getStringExtra("username")
        textUsername.text = username ?: "Guest"


        buttonChooseUser.setOnClickListener {
            val intent = Intent(this, ThirdScreen::class.java)
            resultLauncher.launch(intent)
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedUser = result.data?.getStringExtra("selectedUserName")
            textSelectedUser.text = "Selected User: $selectedUser"
        }
    }
}
