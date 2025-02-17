package com.example.assignment

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Interface2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_interface2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val logginButton = findViewById<Button>(R.id.button)
        logginButton.setOnClickListener {
            val intentToLogin = Intent(this, Login::class.java)
            startActivity(intentToLogin)
        }

        val registerButton = findViewById<Button>(R.id.button3)
        registerButton.setOnClickListener {
            val intentToRegister = Intent(this, Register::class.java)
            startActivity(intentToRegister)
        }
    }
}