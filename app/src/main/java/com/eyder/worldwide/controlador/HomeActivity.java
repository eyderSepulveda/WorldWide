package com.eyder.worldwide.controlador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Suprimir la barra de acciones
        Objects.requireNonNull(getSupportActionBar()).hide();

        bottomNavigationView = findViewById(R.id.bottonNavigationView);
        bottomNavigationView.setBackground(null);
        mAuth = FirebaseAuth.getInstance();

        card1 = (CardView) findViewById(R.id.cardLugar1);
        card2 = (CardView) findViewById(R.id.cardLugar2);
        card3 = (CardView) findViewById(R.id.cardLugar3);
        card4 = (CardView) findViewById(R.id.cardLugar4);

        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);


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


    @Override
    public void onClick(View view) {
        Intent i;
        int id = view.getId();
        if (id == R.id.cardLugar1) {
            irResultadoLugar();
        } else if (id == R.id.cardLugar2) {
            irResultadoLugar();
        } else if (id == R.id.cardLugar3) {
            irResultadoLugar();
        } else {
            irResultadoLugar();
        }

    }

    private void irResultadoLugar(){
        //Intent i = new Intent(this, ResultadoLugarActivity.class);
       // startActivity(i);

        String stringSol =  (card1.isClickable() ? "sol" : "No");
        String stringNaturaleza =  (card2.isClickable() ? "naturaleza" : "No");
        String stringGastronomico =  (card3.isClickable() ? "gastronomico" : "No");
        String stringCultural =  (card4.isClickable() ? "cultural" : "No");
        Bundle continentes = new Bundle();

        Intent i = new Intent(this, ResultadoLugarActivity.class);
        i.putExtras(continentes);
        i.putExtra("sol", stringSol);
        i.putExtra("naturaleza", stringNaturaleza);
        i.putExtra("cultural", stringCultural);
        i.putExtra("gastronomico", stringGastronomico);
        startActivity(i);
        finish();



    }
}