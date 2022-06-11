package com.example.footsetmove.db.remote.Firebase.FirebaseConnection

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.footsetmove.R
import com.akshat.sahijpal.healthtracer.utils.Constants
import com.akshat.sahijpal.healthtracer.utils.Constants.SHARED_PREF_FOR_GOOGLE
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class FirebaseGoogleRConnection {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var navController: NavController
    private lateinit var googleClient: GoogleSignInClient
    private lateinit var Frag:Fragment
    private var authDataForGoogle: MutableLiveData<GoogleSignInAccount> = MutableLiveData()
    fun googleSignInOptionsBuilder(view: View, context: Context, frag:Fragment): Intent {
        navController = Navigation.findNavController(view)
        this.Frag = frag
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(Constants.googleAuthClientID)
            .requestEmail()
            .build()
        googleClient = GoogleSignIn.getClient(context, gso)
        return googleClient.signInIntent
    }

    fun logWithGoogle(context: Context, data: Intent?, ti: Int): FirebaseUser? {
        var user: FirebaseUser? = null
        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            if (ti == 1) {
                user = task.getResult(ApiException::class.java)?.let { authWithGoogle(it, context) }
            } else if (ti == 2) {
                user = task.getResult(ApiException::class.java)
                    ?.let { signInWithGoogleSI(it, context) }
            }
        } catch (e: Exception) {
            Toast.makeText(context, "${e.printStackTrace()}", Toast.LENGTH_SHORT).show()
        }
        return user
    }
   private fun authWithGoogle(acc: GoogleSignInAccount, context: Context): FirebaseUser {
        val credential = GoogleAuthProvider.getCredential(acc.idToken, null)
        auth.fetchSignInMethodsForEmail(acc.email)
            .addOnCompleteListener { task ->
                val isNewUser: Boolean? = task.result?.signInMethods?.isEmpty()
                if (isNewUser == true) {
                    Toast.makeText(context, "Mail Added", Toast.LENGTH_SHORT).show()
                    auth.signInWithCredential(credential)
                        .addOnCompleteListener {
                            it.addOnSuccessListener {
                                Log.d("Result For Auth ", it.user.displayName)
                                authDataForGoogle.postValue(acc)
                                this.Frag.setFragmentResult(SHARED_PREF_FOR_GOOGLE, bundleOf("google-auth" to acc))
                                navController.navigate(R.id.action_create_acc_frag_to_final_screen)
                            }
                        }.addOnFailureListener {
                            Log.d("Result For Auth", "Failed....")
                        }
                } else {
                    Toast.makeText(context, "Mail Already Exists", Toast.LENGTH_SHORT).show()
                    auth.signInWithCredential(credential)
                        .addOnCompleteListener {
                            it.addOnSuccessListener {
                                Log.d("Result For Auth ", it.user.displayName)
                                navController.navigate(R.id.action_create_acc_frag_to_passwordForSignInGoogle)
                            }
                        }.addOnFailureListener {
                            Log.d("Result For Auth", "Failed....")
                        }
                 }
            }
        return auth.currentUser
    }

    private fun signInWithGoogleSI(acc: GoogleSignInAccount, context: Context): FirebaseUser {
        val credential = GoogleAuthProvider.getCredential(acc.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener {
                it.addOnSuccessListener {
                    Toast.makeText(
                        context,
                        "Result For Auth ${it.user.displayName} done !!!",
                        Toast.LENGTH_SHORT
                    ).show()
                    authDataForGoogle.postValue(acc)
                    this.Frag.setFragmentResult(SHARED_PREF_FOR_GOOGLE, bundleOf("google-auth" to acc))
                    navController.navigate(R.id.action_sign_in_frag_to_passwordForSignInGoogle)
                }
            }.addOnFailureListener {
                Log.d("Result For Auth", "Failed....")
            }
        return auth.currentUser
    }
    fun getAuthDataForGoogle() : MutableLiveData<GoogleSignInAccount>{
        return this.authDataForGoogle
    }
}