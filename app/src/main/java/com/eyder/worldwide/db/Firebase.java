package com.eyder.worldwide.db;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Firebase {

    public FirebaseAuth getFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    public FirebaseFirestore getFirebaseFirestore() {
        return FirebaseFirestore.getInstance();
    }
}
