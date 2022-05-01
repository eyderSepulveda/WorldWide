package com.eyder.worldwide.controlador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.eyder.worldwide.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class TipoTurismoActivity extends AppCompatActivity {

    private Button btnBuscarTurismo;
    private String continente;
    private TextView almacenContinente;
    private CheckBox cultural, gastronomico, sol, naturaleza;
    private final String TAG = "tipoTurismo";
    private FirebaseFirestore db;
    private BottomNavigationView bottomNavigationView3;
    private FirebaseAuth mAuth;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    private GoogleSignInAccount gAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_turismo);

        bottomNavigationView3 = findViewById(R.id.bottonNavigationView4);
        bottomNavigationView3.setBackground(null);

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


        bottomNavigationView3.setOnItemSelectedListener(item -> {
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

    private void irhome(){
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);

    }
    private void irContinente(){
        Intent i = new Intent(this, ContinenteActivity.class);
        startActivity(i);

    }


}