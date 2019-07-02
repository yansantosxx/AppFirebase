package com.example.yansa.appfirebase.DAO;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfiguracaoFirebase {

    private static DatabaseReference referenceFirebase;
    private static FirebaseAuth autenticacao;

    public static DatabaseReference getFirebase () {
        if (referenceFirebase == null) {
            referenceFirebase = FirebaseDatabase.getInstance().getReference();
    }

        return referenceFirebase;
    }

public static FirebaseAuth getFirebaseAuth() {

        if(autenticacao == null) {
            autenticacao = FirebaseAuth.getInstance();

        }

        return autenticacao;
    }

}