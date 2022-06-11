package com.example.footsetmove.db.remote.Firebase.FirebaseConnection

import com.example.footsetmove.db.schemas.*
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
class FirebaseInit()  {
    // this is for dummy data
        private var firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    init{
        /*  firebaseFirestore.collection(userInformation_c)
                    .add(UserTable("Anmol", " Sahi",20, "982020222",
                        "ofaks","7812791vgyv","787182712",
                        "7138123b3","M", Calendar.getInstance().time.toString(),"22",true))
                    .addOnSuccessListener(OnSuccessListener<DocumentReference> {  })
                    .addOnFailureListener(OnFailureListener {   })
            firebaseFirestore.collection(stepsInformation_c)
                    .add(StepTable(2000,333,212, 23, StepTable.Milestone(20,29,22)))                    .addOnSuccessListener(OnSuccessListener<DocumentReference> {  })
                    .addOnFailureListener(OnFailureListener {   })
            firebaseFirestore.collection(transactionInformation_c)
                    .add(TransactionTable(false,"333","212", "23"))                    .addOnSuccessListener(OnSuccessListener<DocumentReference> {  })
                    .addOnFailureListener(OnFailureListener {   })
            firebaseFirestore.collection(userMetaInformation_c)
                    .add(UserMetadata(2222,333,212, 23,22))
                    .addOnSuccessListener(OnSuccessListener<DocumentReference> {  })
                    .addOnFailureListener(OnFailureListener {   })
            firebaseFirestore.collection(referralInformation_c)
                    .add(ReferralTable(Calendar.getInstance().time, "@akshat","@nam"))                    .addOnSuccessListener(OnSuccessListener<DocumentReference> {  })
                    .addOnFailureListener(OnFailureListener {   })*/
    }
}