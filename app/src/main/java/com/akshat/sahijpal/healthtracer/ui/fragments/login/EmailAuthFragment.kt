package com.akshat.sahijpal.healthtracer.ui.fragments.login

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.akshat.sahijpal.healthtracer.R
import com.akshat.sahijpal.healthtracer.ui.fragments.login.viewModels.AccWithEmailViewModel
import com.akshat.sahijpal.healthtracer.utils.Constants.EMAIL_REQUEST_KEY
import com.akshat.sahijpal.healthtracer.utils.Constants.PASS_REQUEST_KEY
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_final_screen.*

class EmailAuthFragment : Fragment(R.layout.fragment_email_acc) {
    private lateinit var emailFT: TextInputLayout
    private lateinit var navController: NavController
    private val model: AccWithEmailViewModel by viewModels()
    private lateinit var btnC: Button
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emailFT = view.findViewById(R.id.email_id_ed_133)
        val pass = view.findViewById<TextInputLayout>(R.id.password_for_email333)
        navController = Navigation.findNavController(view)
        btnC = view.findViewById(R.id.verifyBtnEmail212)
        btnC.setOnClickListener {
            var flag: Boolean
            var flag2: Boolean
            val email = emailFT.editText?.text.toString().let {
                if (it.isEmpty()) {
                    emailFT.error = "Please Enter Valid Email Id"
                    flag = false
                    return@let it
                }
                flag = true
                return@let it
            }
            val passKey = pass.editText?.text.toString().let {
                if (it.isEmpty()) {
                    pass.error = "Please Create A Pass"
                    flag2 = false
                    return@let it
                } else if (it.length < 6) {
                    pass.error = "Password Length Must be greater Than 6!!"
                }
                flag2 = true
                return@let it
            }
            if (flag && flag2) {
                setFragmentResult(EMAIL_REQUEST_KEY, bundleOf("email" to email))
                setFragmentResult(PASS_REQUEST_KEY, bundleOf("pass" to passKey))
                model.addUserToDb(view, email, pass = passKey)
            }
        }
    }
}