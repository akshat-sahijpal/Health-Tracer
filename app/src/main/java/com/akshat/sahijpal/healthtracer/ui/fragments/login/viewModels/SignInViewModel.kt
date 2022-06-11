package com.example.footsetmove.ui.fragments.login.viewModels

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.footsetmove.repositories.loginRepositories.SignInRepository

class SignInViewModel(context: Application): AndroidViewModel(context) {
    @SuppressLint("StaticFieldLeak")
    val cont: Context = context
    var userExistenceStatus: MutableLiveData<Boolean> = MutableLiveData()
    var repository: SignInRepository = SignInRepository().getInstanceOfSignInRepository()
    fun checkUsage(user:String){
        var ti = false
          repository.isAUser(user, context = cont)
          this.userExistenceStatus = repository.getExistenceForUser()
    }
    fun getExistenceForUser() :  MutableLiveData<Boolean>{
        return this.userExistenceStatus
    }
}