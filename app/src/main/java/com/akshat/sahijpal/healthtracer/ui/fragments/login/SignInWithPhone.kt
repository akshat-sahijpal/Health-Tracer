package com.akshat.sahijpal.healthtracer.ui.fragments.login

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.akshat.sahijpal.healthtracer.R
import com.akshat.sahijpal.healthtracer.db.schemas.userTableForPhone
import com.akshat.sahijpal.healthtracer.ui.fragments.login.viewModels.AccWithPhoneViewModel
import com.akshat.sahijpal.healthtracer.utils.Constants
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.FirebaseFirestore

class SignInWithPhone : Fragment(R.layout.fragment_sign_in_with_phone) {
    private val phoneModel: AccWithPhoneViewModel by viewModels()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val phone: TextInputLayout = view.findViewById(R.id.mob_id_ed_signIn3212)
        view.findViewById<Button>(R.id.verifyBtn3232).setOnClickListener {
            if (phone.editText?.text.toString().isNotEmpty()) {
                val phoneNumber = "+91" + phone.editText?.text.toString()
                db.collection(Constants.userInformation_c).whereEqualTo("mobile", phoneNumber)
                    .get()
                    .addOnSuccessListener {
                        if (!it.isEmpty) {
                            for (i in it) {
                                val fd = i.toObject(userTableForPhone::class.java)
                                if (fd.mobile == phoneNumber) {
                                    Toast.makeText(
                                        context,
                                        "phoneNumber: $phoneNumber Found!!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    phoneModel.generateOtpRequest(
                                        view,
                                        phoneNumber,
                                        activity,
                                        2,
                                        this
                                    )
                                }
                            }
                        } else {
                            Toast.makeText(
                                context,
                                "phoneNumber: $phoneNumber Not Found!!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else if (phone.editText?.text.toString().isEmpty()) {
                phone.editText?.error = "Please Enter Your Number"
            } else if (phone.editText?.text.toString().length < 10) {
                phone.editText?.error = "Invalid Number Format"
            }
        }
    }
}