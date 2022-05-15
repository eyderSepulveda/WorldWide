package com.eyder.worldwide.controlador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    private GoogleSignInAccount gAccount;
    private BottomNavigationView bottomNavigationView;
    private CardView card1, card2, card3, card4;


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Suprimir la barra de acciones
        Objects.requireNonNull(getSupportActionBar()).hide();

        bottomNavigationView = findViewById(R.id.bottonNavigationView);
        bottomNavigationView.setBackground(null);
        mAuth = FirebaseAuth.getInstance();

        card1 = findViewById(R.id.cardLugar1);
        card2 = findViewById(R.id.cardLugar2);
        card3 = findViewById(R.id.cardLugar3);
        card4 = findViewById(R.id.cardLugar4);

        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);


        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home1) {
                irHome();
                return true;
            } else if (item.getItemId() == R.id.exit) {
                cerrarSesion();
                return true;
            } else if (item.getItemId() == R.id.account) {
                irPerfil();
                return true;
            }
            return false;
        });

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);

    }

    //Metodos para los cardView
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.cardLugar1) {
            irContinente("sol");
        } else if (id == R.id.cardLugar2) {
            irContinente("naturaleza");
        } else if (id == R.id.cardLugar3) {
            irContinente("gastronomico");
        } else {
            irContinente("cultural");
        }

    }

    private void irContinente(String tourism) {
        Bundle tipoTurismo = new Bundle();
        tipoTurismo.putString("tourism", tourism);
        Intent i = new Intent(this, ContinenteActivity.class);
        i.putExtras(tipoTurismo);
        startActivity(i);
    }

    //Metodos para los botones de la barra de navegacion
    private void irHome() {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);

    }

    private void irPerfil(){

    }

    private void cerrarSesion() {
        if (mAuth.getCurrentUser() != null) {
            mAuth.signOut();
            irInicioSesion();
        }
        gAccount = GoogleSignIn.getLastSignedInAccount(this);

        if (gAccount != null) {
            gsc.signOut();  //Deslogar para Google
            irInicioSesion();
        }
    }

    private void irInicioSesion() {
        Intent i = new Intent(this, IniciarSesionRegistroActivity.class);
        startActivity(i);
        finish();
    }


}