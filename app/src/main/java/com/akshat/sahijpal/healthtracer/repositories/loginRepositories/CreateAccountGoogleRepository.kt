package com.example.footsetmove.repositories.loginRepositories

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.footsetmove.db.remote.Firebase.FirebaseConnection.FirebaseGoogleRConnection
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser

class CreateAccountGoogleRepository() {
    private lateinit var context: Context
    private var userMutableLiveData: MutableLiveData<FirebaseUser> = MutableLiveData()
    private var _instance: CreateAccountGoogleRepository? = null
    private val googleRConnectionInstance: FirebaseGoogleRConnection = FirebaseGoogleRConnection()
    fun getInstanceOfCreateAccountGoogleRepository(application: Context): CreateAccountGoogleRepository {
        if (_instance == null) {
            _instance = CreateAccountGoogleRepository()
        }
        this.context = application
        return _instance as CreateAccountGoogleRepository
    }

    fun googleOptionBuilder(view: View, context: Context, frag: Fragment): Intent {
        return googleRConnectionInstance.googleSignInOptionsBuilder(view, context, frag )
    }

    fun registerUserWithGoogle(data: Intent?, context: Context, ti: Int) {
        val firebaseUser = googleRConnectionInstance.logWithGoogle(context, data, ti)
        userMutableLiveData.postValue(firebaseUser)
    }

    fun getUserMutableLiveData(): MutableLiveData<FirebaseUser> {
        return this.userMutableLiveData
    }
    fun getAuthDataForGoogle() : MutableLiveData<GoogleSignInAccount> {
        return googleRConnectionInstance.getAuthDataForGoogle()
    }
}