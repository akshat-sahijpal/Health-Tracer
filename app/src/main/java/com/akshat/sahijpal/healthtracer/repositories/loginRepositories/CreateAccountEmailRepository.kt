package com.example.footsetmove.repositories.loginRepositories

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.footsetmove.db.remote.Firebase.FirebaseConnection.FirebaseEmailRConnection
import com.google.firebase.auth.FirebaseUser

class CreateAccountEmailRepository {
    private lateinit var context: Context
    private var userMutableLiveData: MutableLiveData<FirebaseUser> = MutableLiveData()
    private var _instance: CreateAccountEmailRepository? = null
    private val emailRConnectionInstance: FirebaseEmailRConnection = FirebaseEmailRConnection()
    fun getInstanceOfCreateAccountEmailRepository(application: Context): CreateAccountEmailRepository {
        if (_instance == null) {
            _instance = CreateAccountEmailRepository()
        }
        this.context = application
        return _instance as CreateAccountEmailRepository
    }
    fun registerUserWithEmail(view: View, email: String, pass: String, context: Context) {
        val user: FirebaseUser? = emailRConnectionInstance.createUser(view, email, pass, context)
        userMutableLiveData.postValue(user)
    }
    fun getUserMutableLiveData(): MutableLiveData<FirebaseUser>{
        return this.userMutableLiveData
    }
}