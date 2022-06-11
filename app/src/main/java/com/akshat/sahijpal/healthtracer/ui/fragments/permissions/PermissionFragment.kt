package com.akshat.sahijpal.healthtracer.ui.fragments.permissions

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.akshat.sahijpal.healthtracer.R
import com.akshat.sahijpal.healthtracer.utils.Constants
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.RecordingClient
import com.google.android.gms.fitness.data.DataType

class PermissionFragment : Fragment(R.layout.fragment_permission) {

    private var isPermissionGranted = false
    private lateinit var googleButton: Button
    private lateinit var activityButton: Button
    private lateinit var errorTy: TextView
    private lateinit var fitnessOptions: FitnessOptions
    private lateinit var acc: GoogleSignInAccount
    private lateinit var client: RecordingClient


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        googleButton = view.findViewById(R.id.googleButton)
        activityButton = view.findViewById(R.id.activityButton)
        errorTy = view.findViewById(R.id.errorTy)
        errorTy.visibility = View.INVISIBLE
        googleButton.setOnClickListener { googlePermission() }
        activityButton.setOnClickListener { permission(view.context) }
    }

    fun googlePermission() {
        fitnessOptions = FitnessOptions.builder()
            .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
            .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
            .build()
        acc = GoogleSignIn.getAccountForExtension(requireContext(), fitnessOptions)
        client = Fitness.getRecordingClient(requireContext(), acc)
        if (!GoogleSignIn.hasPermissions(acc, fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                requireActivity(), // your activity
                Constants.GOOGLE_FIT_PERMISSIONS_REQUEST_CODE, // e.g. 1
                acc,
                fitnessOptions
            )
        } else {
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun permission(context: Context) {
        when {
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACTIVITY_RECOGNITION
            ) == PackageManager.PERMISSION_GRANTED -> isPermissionGranted = true
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACTIVITY_RECOGNITION
            ) == PackageManager.PERMISSION_DENIED -> {
                requestPermissions(
                    arrayOf(android.Manifest.permission.ACTIVITY_RECOGNITION),
                    Constants.fragmentActivityPermissionRecognitionRequestCodeForHomeScreen
                )
            }
            else -> isPermissionGranted = false
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.fragmentActivityPermissionRecognitionRequestCodeForHomeScreen
            && permissions.contentEquals(arrayOf(android.Manifest.permission.ACTIVITY_RECOGNITION))
        ) {
            when {
                grantResults[0] == PackageManager.PERMISSION_GRANTED -> isPermissionGranted = true
                grantResults[0] == PackageManager.PERMISSION_DENIED -> isPermissionGranted = false
            }
        }
    }

}