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

public class ContinenteActivity extends AppCompatActivity {

    Button btnAfrica, btnAmerica, btnAsia, btnEuropa, btnOceania;
    private BottomNavigationView bottomNavigationView4;
    private FirebaseAuth mAuth;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    private GoogleSignInAccount gAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continente);
        bottomNavigationView4 = findViewById(R.id.bottonNavigationView3);
        bottomNavigationView4.setBackground(null);
        btnAfrica = findViewById(R.id.btnAfrica);
        btnAmerica = findViewById(R.id.btnAmerica);
        btnAsia = findViewById(R.id.btnAsia);
        btnEuropa = findViewById(R.id.btnEuropa);
        btnOceania = findViewById(R.id.btnOceania);

        btnAfrica.setOnClickListener(view -> irTipoTurismo("africa"));

        btnAmerica.setOnClickListener(view -> irTipoTurismo("america"));

        btnAsia.setOnClickListener(view -> irTipoTurismo("asia"));

        btnEuropa.setOnClickListener(view -> irTipoTurismo("Europa"));

        btnOceania.setOnClickListener(view -> irTipoTurismo("oceania"));

        bottomNavigationView4.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home1) {
                irhome();
                return true;
            }else if (item.getItemId() == R.id.search){
                irContinente();
            }else if (item.getItemId() == R.id.account){
                //irPerfil();
            }
            return false;
        });

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);

    }

    private void irTipoTurismo(String continente){
        Bundle continentes = new Bundle();
        continentes.putString("continente", continente);
        Intent i = new Intent(this, TipoTurismoActivity.class);
        i.putExtras(continentes);
        startActivity(i);
    }

    private void irhome(){
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);

    }
    private void irContinente(){
        Intent i = new Intent(this, ContinenteActivity.class);
        startActivity(i);

    }

}