package com.eyder.worldwide.controlador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.eyder.worldwide.R;

public class TransicionActivity extends AppCompatActivity {

    private String tipoTurismo,continente;
    private static int SPLASH_SCREEN=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transicion);
        tipoTurismo = getIntent().getStringExtra("tipoTurismo");
        continente = getIntent().getStringExtra("continente");

        new Handler().postDelayed(() -> {
            Intent i = new Intent(this, ResultadoLugarActivity.class);
            i.putExtra("tipoTurismo", tipoTurismo);
            i.putExtra("continente", continente);

            startActivity(i);

        },SPLASH_SCREEN);

    }
}