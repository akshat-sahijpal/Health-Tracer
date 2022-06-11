package com.akshat.sahijpal.healthtracer.db.remote.Firebase.FirebaseConnection

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.akshat.sahijpal.healthtracer.R
import com.google.firebase.auth.*
import java.security.MessageDigest


class FirebaseEmailRConnection {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var view: View
    private lateinit var navController: NavController
    fun createUser(view: View, email: String, pass: String, context: Context): FirebaseUser? {
        navController = Navigation.findNavController(view)
        this.view = view
        auth.createUserWithEmailAndPassword(email,  sha256(pass))
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    try {
                        throw task.exception!!
                    } // if user enters wrong email.
                    catch (weakPassword: FirebaseAuthWeakPasswordException) {
                        Log.d(TAG, "onComplete: weak_password")
                        Toast.makeText(context, "onComplete: weak_password", Toast.LENGTH_SHORT)
                            .show()

                    } // if user enters wrong password.
                    catch (malformedEmail: FirebaseAuthInvalidCredentialsException) {
                        Log.d(TAG, "onComplete: malformed_email")
                        Toast.makeText(context, "onComplete: malformed_email", Toast.LENGTH_SHORT)
                            .show()

                    } catch (existEmail: FirebaseAuthUserCollisionException) {
                        Log.d(TAG, "onComplete: exist_email")
                        Toast.makeText(context, "onComplete: exist_email", Toast.LENGTH_SHORT)
                            .show()
                    } catch (e: Exception) {
                        Log.d(TAG, "onComplete: " + e.message)
                        Toast.makeText(context, "onComplete: " + e.message, Toast.LENGTH_SHORT)
                            .show()

                    }
                }
            }
        return registerEmail(email, pass, context)
    }

    private fun registerEmail(email: String, pass: String, context: Context): FirebaseUser? {
        auth.signInWithEmailAndPassword(email, sha256(pass))
            .addOnSuccessListener { task ->
                if (task != null) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    Toast.makeText(context, "Done for $user", Toast.LENGTH_SHORT).show()
                 } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        context,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            if(auth.currentUser != null){
                if (!auth.currentUser.isEmailVerified) {
                    auth.currentUser.sendEmailVerification()
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(view.context, "Check Your Email", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                }
            }
        return auth.currentUser
    }
    private fun sha256(base: String): String {
        return try {
            val digest = MessageDigest.getInstance("SHA-256")
            val hash = digest.digest(base.toByteArray(charset("UTF-8")))
            val hexString = StringBuilder()
            for (i in hash.indices) {
                val hex = Integer.toHexString(0xff and hash[i].toInt())
                if (hex.length == 1) hexString.append('0')
                hexString.append(hex)
            }
            hexString.toString()
        } catch (ex: Exception) {
            throw RuntimeException(ex)
        }

    }
}