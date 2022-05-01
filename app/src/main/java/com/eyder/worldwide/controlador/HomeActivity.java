package com.eyder.worldwide.controlador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.eyder.worldwide.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    private GoogleSignInAccount gAccount;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Suprimir la barra de acciones
        Objects.requireNonNull(getSupportActionBar()).hide();

        bottomNavigationView = findViewById(R.id.bottonNavigationView);
        bottomNavigationView.setBackground(null);
        mAuth = FirebaseAuth.getInstance();


        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.exit:
                    cerrarSesion();
                    return true;
                case R.id.search:
                    irContinente();
                    return true;
                case R.id.home1:
                    irHome();
                    return true;
                case R.id.account:
                    //irPerfil();
                    return true;
            }
            return false;
        });


       gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);
    }


    private void irContinente(){
        Intent i = new Intent(this, ContinenteActivity.class);
        startActivity(i);

    }
    private void irHome(){
        Intent i = new Intent(this, HomeActivity.class);
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