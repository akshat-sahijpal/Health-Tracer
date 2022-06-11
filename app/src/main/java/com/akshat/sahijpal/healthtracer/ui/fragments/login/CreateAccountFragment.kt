package com.akshat.sahijpal.healthtracer.ui.fragments.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.akshat.sahijpal.healthtracer.R
import com.akshat.sahijpal.healthtracer.db.schemas.UserTable
import com.akshat.sahijpal.healthtracer.ui.fragments.login.viewModels.AccWithGoogleViewModel
import com.akshat.sahijpal.healthtracer.utils.Constants
import com.akshat.sahijpal.healthtracer.utils.Constants.RC_SIGN_IN
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import java.security.MessageDigest

class CreateAccountFragment : Fragment(R.layout.fragment_create_acc_frag) {
    private lateinit var navController: NavController
    var db = FirebaseFirestore.getInstance()
    private lateinit var passwordED: TextInputLayout
    private lateinit var userNameED: TextInputLayout

    private var flag = false
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val googleModel: AccWithGoogleViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        navController = Navigation.findNavController(view)
        passwordED = view.findViewById(R.id.password_eml_for_email2324)
        userNameED = view.findViewById(R.id.userName_pass_email_ed2323)
        view.findViewById<Button>(R.id.go_with_google_btn).setOnClickListener {
            val signInIntent: Intent = googleModel.optionBuilder(view, this)
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
        view.findViewById<Button>(R.id.go_with_phone_btn)
            .setOnClickListener {
                navController.navigate(R.id.action_create_acc_frag_to_phone_verif_number)
            }
        view.findViewById<TextView>(R.id.forgotPasswordBtn).setOnClickListener {
            navController.navigate(R.id.action_create_acc_frag_to_forgotPassword)
        }
        view.findViewById<TextView>(R.id.signInBtn21).setOnClickListener {
            val userName = userNameED.editText?.text.toString()
            val password = sha256(passwordED.editText?.text.toString())
            if (checkNull(userName, password)) {
                db.collection(Constants.userInformation_c).whereEqualTo("username", userName)
                    .get()
                    .addOnSuccessListener {
                        if (!it.isEmpty) {
                            for (i in it) {
                                val fd = i.toObject(UserTable::class.java)
                                if (fd.Username == userName && fd.password == password) {
                                    flag = true
                                    Toast.makeText(
                                        context,
                                        "Account $userName found !!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    if (fd.authStatus?.code == Constants.GOOGLE_AUTH) {
                                        val credential09 = fd.authStatus?.googleAccount
                                        val credential = GoogleAuthProvider.getCredential(credential09, null)
                                        auth.signInWithCredential(credential)
                                            .addOnCompleteListener {
                                                it.addOnSuccessListener {
                                                    // Sign in success, update UI with the signed-in user's information
                                                    val user = auth.currentUser
                                                    Toast.makeText(
                                                        context,
                                                        "Done for $user",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                    navController.navigate(R.id.action_create_acc_frag_to_userFragment)
                                                }
                                            }.addOnFailureListener {
                                                Toast.makeText(
                                                    context,
                                                    "Error...... ${it.message}",
                                                    Toast.LENGTH_SHORT

                                                ).show()
                                                Log.d("RESULT", "${it.message}")
                                            }
                                    } /*else if (fd.authStatus?.code == Constants.PHONE_AUTH) {
                                        val credential = fd.authStatus?.phoneAccount
                                        auth.signInWithCredential(credential)
                                            .addOnCompleteListener {
                                                it.addOnSuccessListener {
                                                    // Sign in success, update UI with the signed-in user's information
                                                    val user = auth.currentUser
                                                    Toast.makeText(
                                                        context,
                                                        "Done for $user",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                    navController.navigate(R.id.action_create_acc_frag_to_userFragment)
                                                }
                                            }.addOnFailureListener {
                                                Toast.makeText(
                                                    context,
                                                    "Error........... ${it.message}",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                    }*/
                                } else if (fd.password != password) {
                                    Toast.makeText(context, "Invalid Password", Toast.LENGTH_SHORT)
                                        .show()
                                    passwordED.error = "Invalid Password"
                                } else {
                                    Toast.makeText(context, "Invalid Entry", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                        } else {
                            flag = false
                            Toast.makeText(context, "No Account Found !!!", Toast.LENGTH_LONG)
                                .show()
                         }
                    }
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            googleModel.registerWithGoogle(data, 1)
            googleModel.getUserData().observe(viewLifecycleOwner) {
                Toast.makeText(context, "Data => $it", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkNull(name: String, pass: String): Boolean {
        var cont1 = true
        if (name.isEmpty()) {
            userNameED.error = "Please Enter Your UserName"
            cont1 = false
        } else {
            cont1 = true
            userNameED.error = null
        }
        if (pass.isEmpty()) {
            passwordED.error = "Please Enter Your Password"
            cont1 = false
        } else {
            cont1 = true
            passwordED.error = null
        }
        return cont1
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