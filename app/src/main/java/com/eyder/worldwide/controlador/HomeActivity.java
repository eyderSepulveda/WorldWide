package com.eyder.worldwide.controlador;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.eyder.worldwide.R;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    private GoogleSignInAccount gAccount;
    private BottomNavigationView bottomNavigationView;
    private CardView card1, card2, card3, card4;
    private InterstitialAd mInterstitialAd;

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

        // Publicidad

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i("ANUNCIO", "CARGADO");
                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when fullscreen content is dismissed.
                                Log.d("TAG", "HA PASADO ALGO BUENO");
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when fullscreen content failed to show.
                                Log.d("TAG", "NO SE MOSTROS");
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when fullscreen content is shown.
                                // Make sure to set your reference to null so you don't
                                // show it a second time.
                                mInterstitialAd = null;
                                Log.d("TAG", "SE MOSTRO ALGP");
                            }
                        });

                        if (mInterstitialAd != null) {
                            mInterstitialAd.show(HomeActivity.this);
                        } else {
                            Log.d("TAG", "FALLO AL MOSTRARSE");
                        }
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i("ANUNCIO", loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });


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