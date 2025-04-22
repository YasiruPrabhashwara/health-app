package com.example.assignment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class Register : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        // Register button click listener
        binding.registerBtn.setOnClickListener {
            val username = binding.usernameInput.text.toString()
            val email = binding.EmailInput.text.toString()
            val password = binding.passwordInput.text.toString()
            val repassword = binding.repasswordInput.text.toString()

            // Validate input fields
            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || repassword.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Password matching validation
            if (password != repassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create user with Firebase Authentication
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        Log.d("Register", "User registered: ${user?.uid}")
                        saveAdditionalUserInfo(user, username, email)
                    } else {
                        // Log error message if registration failed
                        Log.e("Register", "Registration failed: ${task.exception?.message}")
                        Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    // Save additional user information to Firebase Realtime Database
    private fun saveAdditionalUserInfo(user: FirebaseUser?, username: String, email: String) {
        val uid = user?.uid ?: return
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("users").child(uid)

        val userData = mapOf(
            "username" to username,
            "email" to email
        )

        // Write user data to Firebase
        ref.setValue(userData).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Successfully saved user data
                Log.d("Register", "User data saved successfully for UID: $uid")
                Toast.makeText(this, "Registered successfully!", Toast.LENGTH_SHORT).show()

                // Navigate to Login activity
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()
            } else {
                // Log error if data saving failed
                Log.e("Register", "Failed to save user data: ${task.exception?.message}")
                Toast.makeText(this, "Failed to save user data", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
