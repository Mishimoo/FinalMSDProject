package com.example.finalmsdproject.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

object AuthHelper {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun getCurrentUid(): String? {
        return auth.currentUser?.uid
    }

    fun getCurrentEmail(): String? {
        return auth.currentUser?.email
    }

    fun signOut() {
        auth.signOut()
    }
}