package com.example.assignment

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Correctly initializing and setting click listeners for each button
        val imageButton1 = findViewById<ImageButton>(R.id.button1)
        imageButton1.setOnClickListener {
            val intentToLogin = Intent(this, application3::class.java)
            startActivity(intentToLogin)
        }

        val imageButton2 = findViewById<ImageButton>(R.id.button3)
        imageButton2.setOnClickListener {
            val intentToLogin = Intent(this, Application::class.java)
            startActivity(intentToLogin)
        }

        val imageButton3 = findViewById<ImageButton>(R.id.button2)
        imageButton3.setOnClickListener {
            val intentToLogin = Intent(this, CommunityApplication::class.java)
            startActivity(intentToLogin)
        }

        val imageButton4 = findViewById<ImageButton>(R.id.button4)
        imageButton4.setOnClickListener {
            val intentToLogin = Intent(this, Delete::class.java)
            startActivity(intentToLogin)
        }
    }
}
