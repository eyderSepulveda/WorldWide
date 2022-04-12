package com.eyder.worldwide.db;

import com.google.firebase.firestore.FirebaseFirestore;

public class ConexionBd {


    FirebaseFirestore db;

    //Retorna la instancia de la BBDD
    public FirebaseFirestore getInstancia(){
        return FirebaseFirestore.getInstance();
    }



}
