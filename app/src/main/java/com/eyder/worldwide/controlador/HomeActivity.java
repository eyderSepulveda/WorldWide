package com.eyder.worldwide.controlador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.eyder.worldwide.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private Button buscarDestino, cerrarSesion;
    private FirebaseAuth mAuth;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    private GoogleSignInAccount gAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        buscarDestino = findViewById(R.id.btnBuscarDestino);
        cerrarSesion = findViewById(R.id.btnCerrarSesion);
        mAuth = FirebaseAuth.getInstance();
        buscarDestino.setOnClickListener(view -> irContinente());

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);


        cerrarSesion.setOnClickListener(view -> cerrarSesion());
    }

    private void irContinente(){
        Intent i = new Intent(this, ContinenteActivity.class);
        startActivity(i);

    }

    private void irInicioSesion(){
        Intent i = new Intent(this, IniciarSesionRegistroActivity.class);
        startActivity(i);
        finish();
    }

    private void cerrarSesion(){
        if (mAuth.getCurrentUser() != null){
            mAuth.signOut();
            irInicioSesion();
        }
        gAccount=GoogleSignIn.getLastSignedInAccount(this);

        if(gAccount!=null){
            gsc.signOut();  //Deslogar para Google
            irInicioSesion();
        }
    }

}