package com.example.footsetmove.repositories.loginRepositories
import android.util.Log
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.footsetmove.R
import com.example.footsetmove.db.schemas.AuthStatusCode
import com.example.footsetmove.db.schemas.UserTable
import com.example.footsetmove.db.schemas.userTableForPhone
import com.akshat.sahijpal.healthtracer.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class FinalScreenRepository {
    private lateinit var navController: NavController

    private var _instance: FinalScreenRepository? = null
    fun getInstanceOfFinalScreenRepository(): FinalScreenRepository {
        if (_instance == null) {
            _instance = FinalScreenRepository()
        }
        return _instance as FinalScreenRepository
    }

    fun uploadDataToFirebase(
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
        navController = Navigation.findNavController(view)
        val userInstance = UserTable(Username = userName, Name = fullName, Age = age, mobile = mobileNo, email=email,
            null, null, password = password, gender = genderV,date_of_joining = Calendar.getInstance().time.toString(),
            null, true, authStatus = authStatusCode)
        val dbRef = Constants.storeDataPath.document(auth.currentUser.uid)
        dbRef.set(userInstance)
            .addOnSuccessListener {
                Log.d("ResultForConnection", "Data Uploaded!!")
                navController.navigate(R.id.action_final_screen_to_userFragment)
            }.addOnFailureListener{
                Log.d("ResultForConnection", "Failed To Upload!!")
            }
    }
    fun uploadDataToFirebaseForPhone(
        view: View,
        auth: FirebaseAuth,
        userName:String,
        fullName: String,
        age: Int,
        mobileNo: String?,
        email:String,
        password: String,
        genderV: String?
    ){
        navController = Navigation.findNavController(view)
        val userInstance = userTableForPhone(Username = userName, Name = fullName, Age = age, mobile = mobileNo, email=email,
            null, null, password = password, gender = genderV,date_of_joining = Calendar.getInstance().time.toString(),
            null, true)
        val dbRef = Constants.storeDataPath.document(auth.currentUser.uid)
        dbRef.set(userInstance)
            .addOnSuccessListener {
                Log.d("ResultForConnection", "Data Uploaded!!")
                navController.navigate(R.id.action_final_screen_to_userFragment)
            }.addOnFailureListener{
                Log.d("ResultForConnection", "Failed To Upload!!")
            }
    }
}