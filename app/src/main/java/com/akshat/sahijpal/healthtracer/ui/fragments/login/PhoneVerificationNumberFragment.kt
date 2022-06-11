package com.example.footsetmove.ui.fragments.login

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.footsetmove.R
import com.example.footsetmove.db.schemas.userTableForPhone
import com.akshat.sahijpal.healthtracer.utils.Constants
import com.akshat.sahijpal.healthtracer.utils.Constants.Phone_exchange_bundle_key
import com.akshat.sahijpal.healthtracer.utils.Constants.Phone_exchange_key
import com.google.firebase.firestore.FirebaseFirestore
import com.hbb20.CountryCodePicker

class PhoneVerificationNumberFragment : Fragment(R.layout.fragment_phone_verif_number) {
    private lateinit var countryCodePicker: CountryCodePicker
    private lateinit var number: String
    private lateinit var phoneNumber: EditText
    private lateinit var navController: NavController
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        countryCodePicker = view.findViewById(R.id.CodePicker)
        phoneNumber = view.findViewById(R.id.phoneNumbED)
        navController = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.proceedBtn).setOnClickListener {
            countryCodePicker.registerCarrierNumberEditText(phoneNumber)
            number = countryCodePicker.fullNumberWithPlus
            var resd = true
            db.collection(Constants.userInformation_c).whereEqualTo("mobile", number)
                .addSnapshotListener { it, e ->
                    Toast.makeText(context, "Running...", Toast.LENGTH_SHORT).show()
                     if (it != null) {
                        var dfr=0
                        for (i in it) {
                            val dk = i.toObject(userTableForPhone::class.java)
                            if (dk.mobile != number) {
                                //      setFragmentResult(Phone_exchange_key, bundleOf(Phone_exchange_bundle_key to number))
                                //      navController.navigate(R.id.action_phone_verif_number_to_phone_verif_otp2)
                            } else {
                                resd = false
                                Toast.makeText(
                                    context,
                                    "Mobile Number Is Already Is In Use ",
                                    Toast.LENGTH_SHORT
                                ).show()
                                Log.d("run", "On Crash Point")
                                navController.navigate(R.id.action_phone_verif_number_to_signInWithPhone)
                            }
                        }
                    }
                    if (resd || it == null) {
                        setFragmentResult(
                            Phone_exchange_key,
                            bundleOf(Phone_exchange_bundle_key to number)
                        )
                        navController.navigate(R.id.action_phone_verif_number_to_phone_verif_otp)
                    }
                    if (e != null) {
                        Log.d(ContentValues.TAG, e.toString())
                        Toast.makeText(context, "error -> $e", Toast.LENGTH_LONG)
                            .show()
                    }
                }
        }
    }
}