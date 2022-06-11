package com.akshat.sahijpal.healthtracer.db.schemas

data class RefAccTable(
    var user_name:String?,
    var email_id: String?,
    var acc_details: String?,
    var phone_number:String?,
){
  var date_of_joining:String? = null
}
