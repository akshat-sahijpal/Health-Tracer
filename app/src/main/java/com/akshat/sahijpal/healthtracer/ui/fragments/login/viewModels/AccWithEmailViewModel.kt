package com.akshat.sahijpal.healthtracer.ui.fragments.login.viewModels

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akshat.sahijpal.healthtracer.repositories.loginRepositories.CreateAccountEmailRepository
import com.google.firebase.auth.FirebaseUser

class AccWithEmailViewModel(context: Application) : AndroidViewModel(context) {

    private val cont = context
    private var mailRepository: CreateAccountEmailRepository = CreateAccountEmailRepository()
        .getInstanceOfCreateAccountEmailRepository(context)
    private var userLiveData: MutableLiveData<FirebaseUser> = MutableLiveData()

    fun addUserToDb(view: View, email: String, pass: String) {
        mailRepository.registerUserWithEmail(view, email, pass, this.cont)
        userLiveData = mailRepository.getUserMutableLiveData()
    }

    fun getUserData(): LiveData<FirebaseUser> {
        return this.userLiveData
    }
}