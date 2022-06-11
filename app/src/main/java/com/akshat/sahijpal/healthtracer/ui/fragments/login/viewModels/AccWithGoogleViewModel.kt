package com.akshat.sahijpal.healthtracer.ui.fragments.login.viewModels

import android.app.Application
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akshat.sahijpal.healthtracer.repositories.loginRepositories.CreateAccountGoogleRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser

class AccWithGoogleViewModel(context: Application) : AndroidViewModel(context) {
    private val cont = context
    private var googleRepository: CreateAccountGoogleRepository = CreateAccountGoogleRepository()
        .getInstanceOfCreateAccountGoogleRepository(context)
    private var userLiveData: MutableLiveData<FirebaseUser> = MutableLiveData()
    fun optionBuilder(view: View, frag: Fragment): Intent {
        return googleRepository.googleOptionBuilder(view, context = cont, frag)
    }

    fun registerWithGoogle(data: Intent?, ti: Int) {
        googleRepository.registerUserWithGoogle(data, cont, ti)
        userLiveData = googleRepository.getUserMutableLiveData()
    }

    fun getUserData(): LiveData<FirebaseUser> {
        return this.userLiveData
    }
    fun getAuthDataForGoogle(): MutableLiveData<GoogleSignInAccount>{
        return googleRepository.getAuthDataForGoogle()
    }
}