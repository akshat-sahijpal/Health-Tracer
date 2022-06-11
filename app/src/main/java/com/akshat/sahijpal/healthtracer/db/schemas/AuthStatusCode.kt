package com.akshat.sahijpal.healthtracer.db.schemas


data class AuthStatusCode(
    var code: Int? = 0,
    var googleAccount: String? = null,
    var phoneAccount: String? = null
)