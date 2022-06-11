package com.akshat.sahijpal.healthtracer.ui.fragments.login

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.akshat.sahijpal.healthtracer.R
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class ForgotPassword : Fragment(R.layout.fragment_forgot_password) {
    private lateinit var emailOrMobile: EditText
    private lateinit var checkerText:TextView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emailOrMobile = view.findViewById(R.id.mailForVerificationOrMobile)
        checkerText = view.findViewById(R.id.forgotPordBtn32333)
        checkerText.visibility = View.INVISIBLE
        view.findViewById<Button>(R.id.signInBtn221).setOnClickListener {
            val emailOrMob = emailOrMobile.text.toString().let {
                if (it == null) {
                    emailOrMobile.error = "Username/Mobile Cannot Be Null"
                }
                return@let it
            }
            val uRL = "https://footsetmove.page.link/nYJz"
            val actionCodeSettings = ActionCodeSettings.newBuilder()
                .setAndroidPackageName("com.example.footsetmove", true, null)
                .setHandleCodeInApp(true)
                .setUrl(uRL)
                .build()
            Firebase.auth.sendSignInLinkToEmail(emailOrMob, actionCodeSettings)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Email sent", Toast.LENGTH_SHORT).show()
                        checkerText.visibility = View.VISIBLE
                    } else {
                        Toast.makeText(
                            context,
                            "Failed To Send The Mail because ${task.exception}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
             }
        }
    }
}