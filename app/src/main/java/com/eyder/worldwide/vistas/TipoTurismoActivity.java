package com.eyder.worldwide.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.eyder.worldwide.R;

public class TipoTurismoActivity extends AppCompatActivity {

    Button btnBuscarTurismo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_turismo);

        btnBuscarTurismo = findViewById(R.id.btnBuscarTur);
        btnBuscarTurismo.setOnClickListener(view -> irResultadoLugar());
    }


    private void irResultadoLugar(){
        Intent i = new Intent(this, ResultadoLugarActivity.class);
        startActivity(i);
    }

}