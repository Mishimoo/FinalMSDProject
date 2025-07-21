package com.example.finalmsdproject.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalmsdproject.MainActivity
import com.example.finalmsdproject.R
import com.example.finalmsdproject.models.User
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

//handles login
class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // skips login if user is already signed in
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            FirebaseFirestore.getInstance().collection("users")
                .document(currentUser.uid)
                .get()
                .addOnSuccessListener { document ->
                    val user = document.toObject(User::class.java)

                    if (user != null) {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("uid", user.uid)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Could not load user profile", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                }
            return
        }

        setContentView(R.layout.activity_login) // Set layout file

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Reference UI elements from layout
        val emailInput = findViewById<EditText>(R.id.emailInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val loginBtn = findViewById<Button>(R.id.loginBtn)
        val registerRedirect = findViewById<TextView>(R.id.registerRedirect)

        // Handle login button click
        loginBtn.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            // Check for empty input fields
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Attempt to sign in using Firebase Authentication
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    val uid = auth.currentUser?.uid ?: return@addOnSuccessListener

                    FirebaseFirestore.getInstance().collection("users")
                        .document(uid)
                        .get()
                        .addOnSuccessListener { document ->
                            val user = document.toObject(User::class.java)

                            if (user != null) {
                                val intent = Intent(this, MainActivity::class.java)
                                intent.putExtra("uid", user.uid)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(
                                    this,
                                    "Failed to load user profile",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                        }
                }
        }
            // Redirect to RegisterActivity when user taps "Register"
            registerRedirect.setOnClickListener {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        }
    }
