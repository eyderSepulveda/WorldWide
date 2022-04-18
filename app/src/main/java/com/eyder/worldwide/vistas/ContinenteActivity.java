package com.eyder.worldwide.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.eyder.worldwide.R;

public class ContinenteActivity extends AppCompatActivity {

    Button btnAfrica, btnAmerica, btnAsia, btnEuropa, btnOceania;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continente);

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
    }

    private void irTipoTurismo(String continente){
        Bundle continentes = new Bundle();
        continentes.putString("continente", continente);
        Intent i = new Intent(this, TipoTurismoActivity.class);
        i.putExtras(continentes);
        startActivity(i);
    }


}