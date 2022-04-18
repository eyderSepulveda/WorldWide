package com.eyder.worldwide.vistas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.eyder.worldwide.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class TipoTurismoActivity extends AppCompatActivity {

    private Button btnBuscarTurismo;
    private String continente;
    private TextView almacenContinente;
    private CheckBox cultural, gastronomico, sol, naturaleza;
    private final String TAG = "tipoTurismo";
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_turismo);

        btnBuscarTurismo = findViewById(R.id.btnBuscarTur);
        almacenContinente = findViewById(R.id.editTextAlmacenContinente);
        cultural = findViewById(R.id.checkBoxTCultural);
        gastronomico = findViewById(R.id.checkBoxTGastronomico);
        sol = findViewById(R.id.checkBoxTSolPlaya);
        naturaleza = findViewById(R.id.checkBoxTNaturaleza);
        btnBuscarTurismo.setOnClickListener(view -> irResultadoLugar());
        continente = getIntent().getStringExtra("continente");
        almacenContinente.setText( continente);
        db = FirebaseFirestore.getInstance();

    }

    //Leer datos en cloud firestore
    private void irResultadoLugar(){

        String stringSol =  (sol.isChecked() ? "sol" : "No");
        String stringNaturaleza =  (naturaleza.isChecked() ? "naturaleza" : "No");
        String stringGastronomico =  (gastronomico.isChecked() ? "gastronomico" : "No");
        String stringCultural =  (cultural.isChecked() ? "cultural" : "No");
        Bundle continentes = new Bundle();
        continentes.putString("continente", continente);

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