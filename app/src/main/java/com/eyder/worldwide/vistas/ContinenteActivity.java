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

        btnAfrica.setOnClickListener(view -> irTipoTurismo());

        btnAmerica.setOnClickListener(view -> irTipoTurismo());

        btnAsia.setOnClickListener(view -> irTipoTurismo());

        btnEuropa.setOnClickListener(view -> irTipoTurismo());
        btnOceania.setOnClickListener(view -> irTipoTurismo());
    }

    private void irTipoTurismo(){
        Intent i = new Intent(this, TipoTurismoActivity.class);
        startActivity(i);
    }


}