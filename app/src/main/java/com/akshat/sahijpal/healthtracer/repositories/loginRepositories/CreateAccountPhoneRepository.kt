package com.example.footsetmove.repositories.loginRepositories

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.footsetmove.R
import com.akshat.sahijpal.healthtracer.utils.Constants.SHARED_PREF_FOR_LOG
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class CreateAccountPhoneRepository {
    private var userData: MutableLiveData<FirebaseUser> = MutableLiveData()
    private var _instance: CreateAccountPhoneRepository? = null
    private lateinit var sharedPref: SharedPreferences
    private var tokenOtpID: String? = null
    private var dataPhoneAuth: MutableLiveData<PhoneAuthCredential> = MutableLiveData()
    private var _view: View? = null
    private lateinit var Frag: Fragment
    private lateinit var navController: NavController
    private lateinit var otpNum: EditText
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    fun getInstanceOfCreateAccountPhoneRepository(): CreateAccountPhoneRepository {
        if (_instance == null) {
            _instance = CreateAccountPhoneRepository()
        }
        return _instance as CreateAccountPhoneRepository
    }
    fun makeOtp(
        view: View, phoneNumberTH: String, activity: FragmentActivity?, context: Context, vi: Int,
        frag:Fragment
    ): String? {
        this.Frag = frag
        _view = view
        navController = Navigation.findNavController(_view!!)
        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                if (vi == 1) {
                    signInWithPhoneAuthCredential(credential, context)
                } else if (vi == 2) {
                    signInWithPhoneAuthCredential404(credential, context)
                }
            }
            override fun onVerificationFailed(e: FirebaseException) {
                Log.d("TAG", e.toString())
                Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show()
                if (e is FirebaseAuthInvalidCredentialsException) {
                    Log.d("TAG", e.toString())
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    Log.d("TAG", e.toString())
                }
            }
            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                tokenOtpID = verificationId
            }
        }
        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(phoneNumberTH) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        return tokenOtpID
    }

    @SuppressLint("CommitPrefEdits")
    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential, context: Context) {
        mAuth.signInWithCredential(credential)
            .addOnSuccessListener { result ->
                Log.d("Result -> ", result?.user.toString())
                dataPhoneAuth.value = credential
                this.Frag.setFragmentResult(SHARED_PREF_FOR_LOG, bundleOf("phone-auth" to credential))
                navController.navigate(R.id.action_phone_verif_otp_to_final_screen)
            }
            .addOnFailureListener { result ->
                Log.e("Result -> ", result.toString())
                Toast.makeText(context, result.toString(), Toast.LENGTH_LONG).show()
            }
        userData.postValue(mAuth.currentUser)
    }
    fun signInWithPhoneAuthCredential404(credential: PhoneAuthCredential, context: Context) {
        mAuth.signInWithCredential(credential)
            .addOnSuccessListener { result ->
                Log.d("Result -> ", result?.user.toString())
                dataPhoneAuth.value = credential
                this.Frag.setFragmentResult(SHARED_PREF_FOR_LOG, bundleOf("phone-auth" to credential))
                navController.navigate(R.id.action_signInWithPhone_to_passwordForSignInPhone)
            }
            .addOnFailureListener { result ->
                Log.e("Result -> ", result.toString())
                Toast.makeText(context, result.toString(), Toast.LENGTH_LONG).show()
            }
    }
    fun getPhoneAuthData(): MutableLiveData<PhoneAuthCredential> {
        return this.dataPhoneAuth
    }
    fun getUserData(): MutableLiveData<FirebaseUser> {
        return this.userData
    }
}