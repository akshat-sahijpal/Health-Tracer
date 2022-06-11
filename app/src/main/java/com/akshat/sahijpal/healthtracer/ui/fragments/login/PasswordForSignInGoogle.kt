package com.akshat.sahijpal.healthtracer.ui.fragments.login

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.akshat.sahijpal.healthtracer.R
import com.akshat.sahijpal.healthtracer.db.schemas.UserTable
import com.akshat.sahijpal.healthtracer.utils.Constants
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.FirebaseFirestore
import java.security.MessageDigest

class PasswordForSignInGoogle : Fragment(R.layout.fragment_password_for_sign_in_google) {
    private lateinit var passwordSignIn: TextInputLayout
    private lateinit var navController: NavController
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        passwordSignIn = view.findViewById(R.id.passwordED_for_phoneSignIn3332)
        navController = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.signInBtn2233331).setOnClickListener {
            val passK = passwordSignIn.editText
            if (passK?.text == null) {
                passwordSignIn.error = "Please Enter Your Password"
            } else {
                val p = sha256(passK.text.toString())
                db.collection(Constants.userInformation_c).whereEqualTo("password", p)
                    .addSnapshotListener { it, e ->
                        if (it != null) {
                            if (!it.isEmpty) {
                                var cffd = 0
                                for (i in it) {
                                    val fd = i.toObject(UserTable::class.java)
                                    if (fd.password == p && cffd == 0) {
                                        Toast.makeText(
                                            context,
                                            "Correct Match!!!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        cffd++
                                        navController.navigate(R.id.action_passwordForSignInGoogle_to_userFragment)
                                    }
                                }
                            } else {
                                Toast.makeText(
                                    context,
                                    "Invalid Password!!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                passwordSignIn.error = "Invalid Password!!"
                            }
                        }
                    }
            }
        }
    }

    private fun sha256(base: String): String? {
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