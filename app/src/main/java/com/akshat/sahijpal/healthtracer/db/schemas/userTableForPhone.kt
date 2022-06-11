package com.example.footsetmove.db.schemas

data class userTableForPhone(
    var Username:String = "",
    var Name:String = "",
    var Age:Int = 0,
    var mobile:String? = "",
    var email:String = "",
    var referral_code:String? = "",// must be set to user_data
    var referred_by:String? = "",// must be set to user_data
    var password:String = "",
    var gender:String? = "",
    var date_of_joining: String = "",//.ofPattern("dd.MM.yyyy. HH:mm:ss")
    var avatar_id:String? = "",
    var active_status:Boolean? = true
    )
