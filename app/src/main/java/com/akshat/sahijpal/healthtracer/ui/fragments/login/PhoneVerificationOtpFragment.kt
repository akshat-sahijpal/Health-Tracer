package com.akshat.sahijpal.healthtracer.ui.fragments.login

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.akshat.sahijpal.healthtracer.R
import com.akshat.sahijpal.healthtracer.ui.fragments.login.viewModels.AccWithPhoneViewModel
import com.akshat.sahijpal.healthtracer.utils.Constants.Phone_exchange_bundle_key
import com.akshat.sahijpal.healthtracer.utils.Constants.Phone_exchange_key
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.PhoneAuthProvider

class PhoneVerificationOtpFragment : Fragment(R.layout.fragment_phone_verif_otp) {
      var tokenOtpID: String? = null
    lateinit var otpNum: TextInputLayout
    private val phoneModel: AccWithPhoneViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener(Phone_exchange_key) { _, bundle ->
            phoneModel.generateOtpRequest(
                view,
                bundle.get(Phone_exchange_bundle_key).toString(),
                activity,
                1,
                this
            )
        }
        otpNum = view.findViewById(R.id.otp_id_sent)
        view.findViewById<Button>(R.id.verifyBtn).setOnClickListener {
            when {
                otpNum.editText?.text.toString().isEmpty() -> Toast.makeText(
                    view.context,
                    "Enter the otp!!!",
                    Toast.LENGTH_SHORT
                ).show()
                otpNum.editText?.text.toString().length != 6 -> Toast.makeText(
                    view.context,
                    "Otp Length is 6!!!",
                    Toast.LENGTH_SHORT
                ).show()
                else -> phoneModel.signIn(
                    PhoneAuthProvider.getCredential(
                        tokenOtpID,
                        otpNum.toString()
                    )
                )
            }
        }
    }
}