package com.akshat.sahijpal.healthtracer.ui.fragments.login.viewModels

import android.app.Application
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akshat.sahijpal.healthtracer.repositories.loginRepositories.CreateAccountPhoneRepository
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential

class AccWithPhoneViewModel(context: Application) : AndroidViewModel(context) {
    private val cont = context
    private var userLiveData: MutableLiveData<FirebaseUser> = MutableLiveData()
    private val repository: CreateAccountPhoneRepository =
        CreateAccountPhoneRepository().getInstanceOfCreateAccountPhoneRepository()

    fun generateOtpRequest(
        view: View,
        phoneNumberTH: String,
        activity: FragmentActivity?,
        vi: Int,
        frag: Fragment
    ): String? {
        return repository.makeOtp(view, phoneNumberTH, activity, cont, vi, frag)
    }

    fun signIn(credential: PhoneAuthCredential) {
        repository.signInWithPhoneAuthCredential(credential, cont)
        userLiveData = repository.getUserData()
    }
    fun getAuthDataForPhone() : MutableLiveData<PhoneAuthCredential>{
        return repository.getPhoneAuthData()
    }
    fun getUserData(): LiveData<FirebaseUser> {
        return this.userLiveData
    }
}