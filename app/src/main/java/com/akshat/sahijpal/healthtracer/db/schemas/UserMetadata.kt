package com.akshat.sahijpal.healthtracer.db.schemas

data class UserMetadata(
        var current_coins:Int,
        var current_cash:Int,
        var current_level:Int,
        var total_coins:Int,
        var total_cash:Int
)
