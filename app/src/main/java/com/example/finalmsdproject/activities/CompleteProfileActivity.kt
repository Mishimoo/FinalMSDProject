package com.example.finalmsdproject.activities

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.finalmsdproject.R
import com.example.finalmsdproject.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CompleteProfileActivity : AppCompatActivity() {

    private lateinit var profileImageView: ImageView
    private lateinit var firstNameInput: EditText
    private lateinit var lastNameInput: EditText
    private lateinit var completeBtn: Button

    private var selectedAvatarResId: Int = R.drawable.default_profile
    private var selectedAvatarView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete_profile)

        profileImageView = findViewById(R.id.profileImageView)
        firstNameInput = findViewById(R.id.firstNameInput)
        lastNameInput = findViewById(R.id.lastNameInput)
        completeBtn = findViewById(R.id.completeProfileBtn)

        // Set default preview
        profileImageView.setImageResource(R.drawable.default_profile)

        setupAvatarSelection()

        completeBtn.setOnClickListener {
            val firstName = firstNameInput.text.toString().trim()
            val lastName = lastNameInput.text.toString().trim()

            if (firstName.isEmpty() || lastName.isEmpty()) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return@setOnClickListener
            val email = FirebaseAuth.getInstance().currentUser?.email ?: ""

            val user = User(
                uid = uid,
                email = email,
                firstName = firstName,
                lastName = lastName,
                avatarResId = selectedAvatarResId
            )

            FirebaseFirestore.getInstance()
                .collection("users")
                .document(uid)
                .set(user)
                .addOnSuccessListener {
                    Toast.makeText(this, "Profile saved!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error saving profile: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun setupAvatarSelection() {
        val avatarViews = listOf(
            R.id.avatar1 to R.drawable.avatar1,
            R.id.avatar2 to R.drawable.avatar2,
            R.id.avatar3 to R.drawable.avatar3,
            R.id.avatar4 to R.drawable.avatar4,
            R.id.avatar5 to R.drawable.avatar5,
            R.id.avatar6 to R.drawable.avatar6
        )

        for ((viewId, resId) in avatarViews) {
            val avatarView = findViewById<ImageView>(viewId)

            avatarView.setOnClickListener {
                if (selectedAvatarResId == resId) {
                    // Unselect
                    avatarView.setBackgroundResource(R.drawable.avatar_border_unselected)
                    profileImageView.setImageResource(R.drawable.default_profile)
                    selectedAvatarResId = R.drawable.default_profile
                    selectedAvatarView = null
                } else {
                    // Deselect previous
                    selectedAvatarView?.setBackgroundResource(R.drawable.avatar_border_unselected)

                    // Select new
                    avatarView.setBackgroundResource(R.drawable.avatar_border_selected)
                    profileImageView.setImageResource(resId)

                    selectedAvatarResId = resId
                    selectedAvatarView = avatarView
                }
            }
        }

        // Start with no selection
        selectedAvatarResId = R.drawable.default_profile
        profileImageView.setImageResource(selectedAvatarResId)
    }
}
