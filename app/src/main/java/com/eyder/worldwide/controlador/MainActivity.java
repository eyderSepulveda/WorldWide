package com.eyder.worldwide.controlador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.eyder.worldwide.R;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private Button btnIniciar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnIniciar = findViewById(R.id.btnIniciarSesion);
        Objects.requireNonNull(getSupportActionBar()).hide();
        btnIniciar.setOnClickListener(view -> iniciar());
    }

    public void iniciar() {
        Intent i = new Intent(this, IniciarSesionRegistroActivity.class);
        startActivity(i);
    }

}