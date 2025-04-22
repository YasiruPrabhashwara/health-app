package com.example.assignment

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.assignment.databinding.ActivityDeleteBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Delete : AppCompatActivity() {

    private lateinit var binding: ActivityDeleteBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var userRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Firebase reference
        database = FirebaseDatabase.getInstance()
        userRef = database.getReference("healthData") // Your root node

        // Delete button functionality
        binding.seeBtn.setOnClickListener {
            val username = binding.usernameInput.text.toString().trim()
            val id = binding.idNumber.text.toString().trim() // ID entered by user

            if (username.isNotEmpty() && id.isNotEmpty()) {
                val recordRef = userRef.child(id) // Go to healthData → id

                recordRef.get().addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {
                        val storedUsername = snapshot.child("username").value?.toString()?.trim()


                        if (storedUsername.equals(username, ignoreCase = true)) {
                            recordRef.removeValue().addOnSuccessListener {
                                Toast.makeText(this, "Health data deleted successfully", Toast.LENGTH_SHORT).show()
                                binding.usernameInput.text.clear()
                                binding.idNumber.text.clear()
                            }.addOnFailureListener {
                                Toast.makeText(this, "Failed to delete data", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this, "Username does not match the record", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "No record found for given ID", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, "Error fetching record", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter both Username and ID", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
