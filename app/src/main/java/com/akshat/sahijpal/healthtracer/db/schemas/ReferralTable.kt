package com.example.footsetmove.db.schemas

import java.time.format.DateTimeFormatter
import java.util.*

data class ReferralTable(
        var date: Date,
        var referred_by:String,
        var reffered_to:String
)
