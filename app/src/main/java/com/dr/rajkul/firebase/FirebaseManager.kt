package com.dr.rajkul.firebase

import com.google.firebase.firestore.FirebaseFirestore

object FirebaseManager {

    fun dbInstance() = FirebaseFirestore.getInstance()



}