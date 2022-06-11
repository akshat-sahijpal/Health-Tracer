package com.akshat.sahijpal.healthtracer.ui.fragments.login

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.akshat.sahijpal.healthtracer.R
import com.akshat.sahijpal.healthtracer.db.schemas.AuthStatusCode
import com.akshat.sahijpal.healthtracer.db.schemas.UserTable
import com.akshat.sahijpal.healthtracer.ui.fragments.login.viewModels.FinalScreenViewModel
import com.akshat.sahijpal.healthtracer.utils.Constants
import com.akshat.sahijpal.healthtracer.utils.Constants.EMAIL_REQUEST_KEY
import com.akshat.sahijpal.healthtracer.utils.Constants.PASS_REQUEST_KEY
import com.akshat.sahijpal.healthtracer.utils.Constants.SHARED_PREF_FOR_GOOGLE
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_final_screen.*
import java.security.MessageDigest


class FinalScreenFragment : Fragment(R.layout.fragment_final_screen) {
    private lateinit var navController: NavController
    private val model: FinalScreenViewModel by viewModels()
    private lateinit var userNameED: TextInputLayout
    private lateinit var authStateForSignIn: AuthStatusCode
    private lateinit var nameED: TextInputLayout
    private var checkCall = false
    private lateinit var email: TextInputLayout
    private var flag = false
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var mobileNo: TextInputLayout
    private lateinit var genderED: TextInputLayout
    private lateinit var passwordED: TextInputLayout

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        userNameED = view.findViewById(R.id.userName)
        nameED = view.findViewById(R.id.Name_id_ed)
        email = view.findViewById(R.id.email_id_ed_1)
        mobileNo = view.findViewById(R.id.mob_id_ed)
        genderED = view.findViewById(R.id.gender_ED)
        passwordED = view.findViewById(R.id.password_ED)
        when {
            auth.currentUser?.email != null -> {
                email.editText?.setText(auth.currentUser?.email)
                email.editText?.isFocusable = false
            }
            auth.currentUser?.displayName != null -> {
                nameED.editText?.setText(auth.currentUser?.displayName)
                nameED.editText?.isFocusable = false
            }
            auth.currentUser?.phoneNumber != null -> {
                mobileNo.editText?.setText(auth.currentUser?.phoneNumber)
                mobileNo.editText?.isFocusable = false
            }
        }
        setFragmentResultListener(EMAIL_REQUEST_KEY) { _, bundle ->
            email.editText?.setText(bundle.getString("email"))
            email.editText?.isFocusable = false
        }
        setFragmentResultListener(PASS_REQUEST_KEY) { _, bundle ->
            passwordED.editText?.setText(bundle.getString("pass"))
            passwordED.editText?.isFocusable = false
        }

        view.findViewById<Button>(R.id.bnt_for_creation)
            .setOnClickListener {
                val userName = userNameED.editText?.text.toString().let {
                    if (it.isEmpty()) {
                        userNameED.error = "Username Cannot Be Null"
                    }
                    return@let it
                }
                val name = nameED.editText?.text.toString().let {
                    if (it.isEmpty()) {
                        nameED.error = "Name Cannot Be Null"
                    }
                    return@let it
                }
                val mobile = mobileNo.editText?.text.toString()
                val emailE = email.editText?.text.toString()
                val password = passwordED.editText?.text.toString().let {
                    if (it.isEmpty()) {
                        passwordED.error = "Password Cannot Be Null"
                    } else if (it.length < 6) {
                        passwordED.error = "Password Length More Than 6 words"
                    } else {
                        return@let sha256(it)
                    }
                    return@let it
                }
                setFragmentResultListener(Constants.SHARED_PREF_FOR_LOG) { _, bundle ->
                    val phoneAuthCred: PhoneAuthCredential =
                        bundle.get("phone-auth") as PhoneAuthCredential
                    checkCall = true
                    authStateForSignIn = AuthStatusCode(Constants.PHONE_AUTH, null, null)
                }
                setFragmentResultListener(SHARED_PREF_FOR_GOOGLE) { _, bundle ->
                    val acc: GoogleSignInAccount = bundle.get("google-auth") as GoogleSignInAccount
                    authStateForSignIn = AuthStatusCode(Constants.GOOGLE_AUTH, acc.idToken, null)
                }
                db.collection(Constants.userInformation_c).whereEqualTo("username", userName)
                    .get()
                    .addOnSuccessListener {
                        if (!it.isEmpty) {
                            for (i in it) {
                                val fd = i.toObject(UserTable::class.java)
                                if (fd.Username == userName) {
                                    flag = true
                                    userNameED.error = "UserName Already Taken"
                                    Toast.makeText(
                                        context,
                                        "UserName $userName is Already Taken",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } else {
                            if (userName.isNotEmpty() && name.isNotEmpty() && password != null) {
                                model.setData(
                                    view,
                                    auth,
                                    userName,
                                    name,
                                    20,
                                    when {
                                        mobile.toList()[0].toString() == "+" -> mobile
                                        else -> "+91$mobile"
                                    },
                                    emailE,
                                    password,
                                    genderED.editText?.text.toString(),
                                    authStateForSignIn
                                )
                            }
                            if (userName.isNotEmpty() && name.isNotEmpty() && password != null && checkCall) {
                                model.setDataForPhone(
                                    view,
                                    auth,
                                    userName,
                                    name,
                                    20,
                                    when {
                                        mobile.toList()[0].toString() == "+" -> mobile
                                        else -> "+91$mobile"
                                    },
                                    emailE,
                                    password,
                                    genderED.editText?.text.toString()
                                )
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