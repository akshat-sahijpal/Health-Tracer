package com.akshat.sahijpal.healthtracer.ui.fragments.login.viewModels

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import com.akshat.sahijpal.healthtracer.db.schemas.AuthStatusCode
import com.akshat.sahijpal.healthtracer.repositories.loginRepositories.FinalScreenRepository
import com.google.firebase.auth.FirebaseAuth

class FinalScreenViewModel(context: Application): AndroidViewModel(context){
   private var repository: FinalScreenRepository = FinalScreenRepository().getInstanceOfFinalScreenRepository()
    fun setData(
        view: View,
        auth: FirebaseAuth,
        userName:String,
        fullName: String,
        age: Int,
        mobileNo: String?,
        email:String,
        password: String,
        genderV: String?,
        authStatusCode: AuthStatusCode
    ){
          repository.uploadDataToFirebase(
            view,
            auth,
            userName,
            fullName,
            age,
            mobileNo,
            email,
            password,
            genderV,
              authStatusCode)
    }
    fun setDataForPhone(
        view: View,
        auth: FirebaseAuth,
        userName:String,
        fullName: String,
        age: Int,
        mobileNo: String?,
        email:String,
        password: String,
        genderV: String?,
    ){
        repository.uploadDataToFirebaseForPhone(
            view,
            auth,
            userName,
            fullName,
            age,
            mobileNo,
            email,
            password,
            genderV)
    }

}