package com.example.finalmsdproject.models

import com.example.finalmsdproject.R


data class User(
    val uid: String = "",
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val avatarResId: Int = R.drawable.default_profile
)