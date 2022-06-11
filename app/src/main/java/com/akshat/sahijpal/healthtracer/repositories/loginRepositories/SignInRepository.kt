package com.akshat.sahijpal.healthtracer.repositories.loginRepositories

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.akshat.sahijpal.healthtracer.db.schemas.UserTable
import com.akshat.sahijpal.healthtracer.utils.Constants
import com.google.firebase.firestore.FirebaseFirestore

class SignInRepository {
    private var db:FirebaseFirestore = FirebaseFirestore.getInstance()
    var userExistenceStatus: MutableLiveData<Boolean> = MutableLiveData()
    private var _instance: SignInRepository? = null
    fun getInstanceOfSignInRepository():SignInRepository {
        if (_instance == null) {
            _instance = SignInRepository()
        }
        return _instance as SignInRepository
    }
    fun isAUser(value: String?, context: Context){
        db.collection(Constants.userInformation_c).whereEqualTo("username", value)
            .addSnapshotListener { s, e ->
                if (s != null) {
                    for(i in s){
                        val fd = i.toObject(UserTable::class.java)
                        if(fd.Username == value){
                            userExistenceStatus.postValue(true)
                            Toast.makeText(context, "UserName Exist", Toast.LENGTH_LONG).show()
                        }
                    }
                }
                if (e != null) {
                    Log.d(TAG, e.toString())
                }
            }
    }
    fun getExistenceForUser() :  MutableLiveData<Boolean>{
       return this.userExistenceStatus
    }
}