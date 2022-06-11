package com.example.footsetmove.db.schemas


data class AuthStatusCode(
    var code: Int? = 0,
    var googleAccount: String? = null,
    var phoneAccount: String? = null
)