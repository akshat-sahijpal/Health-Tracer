package com.akshat.sahijpal.healthtracer.db.schemas

data class TransactionTable(
        var status: Boolean,
        var decription:String,
        var request_to:String,// must be set to user_data
        var request_from:String// must be set to user_data
) 