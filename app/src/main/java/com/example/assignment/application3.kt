package com.example.assignment

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment.databinding.ActivityApplication3Binding
import com.google.firebase.database.FirebaseDatabase

class application3 : AppCompatActivity() {

    private lateinit var binding: ActivityApplication3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApplication3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = FirebaseDatabase.getInstance()
        val healthRef = database.getReference("healthData")

        binding.loginBtn.setOnClickListener {
            val username = binding.usernameInput.text.toString().trim()
            val waterIntake = binding.ageInput.text.toString().trim()
            val exerciseDuration = binding.exerciseDuration.text.toString().trim()
            val id = binding.idNumber.text.toString().trim()

            if (username.isNotEmpty() && waterIntake.isNotEmpty() && exerciseDuration.isNotEmpty() && id.isNotEmpty()) {
                // Check if the ID already exists in the database
                healthRef.child(id).get().addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {
                        // ID exists, show an error message
                        Toast.makeText(this, "ID already exists. Please enter a different ID.", Toast.LENGTH_SHORT).show()
                    } else {
                        // ID doesn't exist, save data
                        val data = mapOf(
                            "username" to username,
                            "waterIntake" to waterIntake,
                            "exerciseDuration" to exerciseDuration
                        )

                        healthRef.child(id).setValue(data).addOnSuccessListener {
                            Toast.makeText(this, "Data added successfully!", Toast.LENGTH_SHORT).show()

                            // Clear all input fields
                            binding.usernameInput.text.clear()
                            binding.ageInput.text.clear()
                            binding.exerciseDuration.text.clear()
                            binding.idNumber.text.clear() // ID field cleared
                        }.addOnFailureListener {
                            Toast.makeText(this, "Failed to add data", Toast.LENGTH_SHORT).show()
                        }
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, "Error checking ID", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
