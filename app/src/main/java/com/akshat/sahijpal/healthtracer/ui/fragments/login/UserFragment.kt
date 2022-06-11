package com.example.footsetmove.ui.fragments.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.footsetmove.R
import com.example.footsetmove.db.schemas.UserTable
import com.akshat.sahijpal.healthtracer.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class UserFragment : Fragment(R.layout.fragment_user) {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var navController: NavController
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        sendVerificationMailIfNotVerified()
        view.findViewById<Button>(R.id.button040404)
            .setOnClickListener {
                auth.signOut()
                navController.navigate(R.id.action_userFragment_to_permissionFragment)
            }
        navController.navigate(R.id.action_userFragment_to_homeFragment2)
        db.collection(Constants.userInformation_c).whereEqualTo("email", auth.currentUser.email)
            .get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    for (i in it) {
                        val fd = i.toObject(UserTable::class.java)
                        if (fd.email == auth.currentUser.email) {
                            view.findViewById<TextView>(R.id.user222).text =
                                "${fd.Username} \n ${fd.mobile} \n ${fd.Name} "
                        }
                    }
                }
            }
        if(auth.currentUser.phoneNumber != null){
            db.collection(Constants.userInformation_c).whereEqualTo("mobile", auth.currentUser.phoneNumber)
                .get()
                .addOnSuccessListener {
                    if (!it.isEmpty) {
                        for (i in it) {
                            val fd = i.toObject(UserTable::class.java)
                            if (fd.mobile == auth.currentUser.phoneNumber) {
                                view.findViewById<TextView>(R.id.user222).text =
                                    "${fd.Username} \n ${fd.mobile} \n ${fd.Name} "
                            }
                        }
                    }

                }
        }
    }

    private fun sendVerificationMailIfNotVerified() {
        val user = Firebase.auth.currentUser
        if (user != null) {
            if (user.email?.toString() != null && user.isEmailVerified) {
                user.sendEmailVerification()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                context,
                                "Mail Sent!!! check your inbox",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
    }
}