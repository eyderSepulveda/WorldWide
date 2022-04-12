package com.eyder.worldwide.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.eyder.worldwide.R;

public class HomeActivity extends AppCompatActivity {

    Button buscarDestino;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        buscarDestino = findViewById(R.id.btnBuscarDestino);

        buscarDestino.setOnClickListener(view -> irContinente());
    }

    private void irContinente(){
        Intent i = new Intent(this, ContinenteActivity.class);
        startActivity(i);
    }

}