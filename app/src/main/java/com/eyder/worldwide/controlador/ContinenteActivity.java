package com.eyder.worldwide.controlador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eyder.worldwide.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ContinenteActivity extends AppCompatActivity implements View.OnClickListener{

   // Button btnAfrica, btnAmerica, btnAsia, btnEuropa, btnOceania;
    private BottomNavigationView bottomNavigationView4;
    //private FirebaseAuth mAuth;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    private FirebaseAuth mAuth;
    private GoogleSignInAccount gAccount;
    //private GoogleSignInAccount gAccount;
    private CardView cardAsia, cardAfrica, cardAmerica, cardEuropa, cardOceania;
    private String tipoTurismo;
    private TextView almacenarTipoTurismo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continente);
        //Suprimir la barra de acciones
        Objects.requireNonNull(getSupportActionBar()).hide();
        bottomNavigationView4 = findViewById(R.id.bottonNavigationView3);
        bottomNavigationView4.setBackground(null);

        mAuth = FirebaseAuth.getInstance();

        almacenarTipoTurismo = findViewById(R.id.tipoTur);
        tipoTurismo = getIntent().getStringExtra("tourism");
        almacenarTipoTurismo.setText( tipoTurismo);
        Log.d("Valor", String.valueOf(tipoTurismo));// Saber el valor de la variable tipo turismo

        cardAfrica = (CardView) findViewById(R.id.contAfrica);
        cardAsia = (CardView) findViewById(R.id.contAsia);
        cardAmerica = (CardView) findViewById(R.id.contAmerica);
        cardEuropa = (CardView) findViewById(R.id.contEuropa);
        cardOceania = (CardView) findViewById(R.id.contOceania);

        cardAfrica.setOnClickListener(this);
        cardAsia.setOnClickListener(this);
        cardAmerica.setOnClickListener(this);
        cardEuropa.setOnClickListener(this);
        cardOceania.setOnClickListener(this);

        bottomNavigationView4.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home1) {
                irhome();
                return true;
            }else if (item.getItemId() == R.id.exit){
                cerrarSesion();
                return true;
            }else if (item.getItemId() == R.id.account){
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

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.contAfrica) {
            irResultadoLugar("africa");
        } else if (id == R.id.contAmerica) {
            irResultadoLugar("america");
        } else if (id == R.id.contAsia) {
            irResultadoLugar("asia");
        } else if (id == R.id.contEuropa) {
            irResultadoLugar("Europa");
        } else {
            irResultadoLugar("oceania");
        }
    }

    //Leer datos en cloud firestore
    private void irResultadoLugar(String continente){
        Intent i = new Intent(this, ResultadoLugarActivity.class);
        i.putExtra("tipoTurismo", tipoTurismo);
        i.putExtra("continente", continente);
        startActivity(i);
        finish();
    }

    // Metodos para los botones de barra de navegacion
    private void irhome(){
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);

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

    private void irPerfil(){

    }

}