package com.akshat.sahijpal.healthtracer.utils

import com.google.firebase.firestore.FirebaseFirestore

object Constants {
    const val userInformation_c = "user_info"
    const val referralInformation_c = "referral_data"
    const val stepsInformation_c = "step_data"
    const val fragmentActivityPermissionRecognitionRequestCodeForHomeScreen = 19283
    const val userMetaInformation_c = "user_data"
    const val transactionInformation_c = "transactions"
    const val googleAuthClientID = "729166993247-g6i3qe3bpt0l6s34h0js07pnq9kbgb6e.apps.googleusercontent.com"
    const val Phone_exchange_key = "com.fatdevs.mainbranch232332"
    const val Phone_exchange_bundle_key = "mainbranch256342"
    const val RC_SIGN_IN = 29292
    private const val dataUploaderPath = userInformation_c
    const val EMAIL_REQUEST_KEY = "com.example.footsetmove.utils.EMAIL_REQUEST_KEY"
    const val PASS_REQUEST_KEY = "com.example.footsetmove.utils.PASS_REQUEST_KEY"
    private var db = FirebaseFirestore.getInstance()
    var storeDataPath = db.collection(dataUploaderPath)
    const val PHONE_AUTH = 1
    const val GOOGLE_AUTH = 2
    const val SHARED_PREF_FOR_LOG = "com.example.footsetmove.utils.SHARED_PREF_FOR_LOG"
    const val GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 246328
    const val SHARED_PREF_FOR_GOOGLE = "com.example.footsetmove.utils.SHARED_PREF_FOR_GOOGLE"
}