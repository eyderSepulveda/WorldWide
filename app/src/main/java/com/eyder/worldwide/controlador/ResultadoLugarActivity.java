package com.eyder.worldwide.controlador;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eyder.worldwide.R;
import com.eyder.worldwide.db.Firebase;
import com.eyder.worldwide.entidades.Lugar;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class ResultadoLugarActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    private GoogleSignInAccount gAccount;
    private Firebase db;
    private final String TAG = "busqueda";
    private String continente,stringTipoTurismo;
    private RecyclerView ListaLugares;
    private ArrayList<Lugar> lista;
    private BottomNavigationView bottomNavigationView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_lugar);

        //Suprimir la barra de acciones
        Objects.requireNonNull(getSupportActionBar()).hide();


        mAuth = FirebaseAuth.getInstance();
        bottomNavigationView2 = findViewById(R.id.bottonNavigationView2);
        bottomNavigationView2.setBackground(null);
        db = new Firebase();
        continente = getIntent().getStringExtra("continente");
        stringTipoTurismo= getIntent().getStringExtra("tipoTurismo");
        Log.d("continente: " , continente);
        Log.d("stringTipoTurismo: " , stringTipoTurismo);
        buscarLugares();

        bottomNavigationView2.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.home1) {
                irhome();
                return true;
            }else if (item.getItemId() == R.id.exit){
                cerrarSesion();
            }else if (item.getItemId() == R.id.account){
                irPerfil();
            }
            return false;

        });

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);

    }

    private void buscarLugares(){
        db.getFirebaseFirestore().collection("Lugares").whereEqualTo("continente", continente).
                whereArrayContainsAny("tipoTurismo", Arrays.asList(stringTipoTurismo))
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        int con = 0;
                        lista= new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Lugar lugar=new Lugar(Integer.parseInt(document.getId()), Objects.requireNonNull(document.getData().get("nombre")).toString(), Objects.requireNonNull(document.getData().get("descripcion")).toString());
                            lugar.setFechaVisita(Objects.requireNonNull(document.getData().get("fechaVisita")).toString());
                            lugar.setImagen(Objects.requireNonNull(document.getData().get("imagen")).toString());
                            lugar.setDescripcion(Objects.requireNonNull(document.getData().get("descripcion")).toString());
                            lugar.setTransporte(Objects.requireNonNull(document.getData().get("transporte")).toString());

                            lista.add(lugar);
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            con ++;
                        }
                        ListaLugares = findViewById(R.id.lugares);

                        // use this setting to improve performance if you know that changes
                        // in content do not change the layout size of the RecyclerView
                        ListaLugares.setHasFixedSize(true);

                        // use a linear layout manager
                        ListaLugares.setLayoutManager(new LinearLayoutManager(this));

                        // specify an adapter with the list to show
                        LugaresAdapter adapter = new LugaresAdapter(lista);
                        ListaLugares.setAdapter(adapter);
                        Log.d(TAG, "total array " +lista.size());
                        Log.d(TAG, "Se encontraron " + con +" lugares");


                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
    }

    //Metodos para los botones de la barra de navegacion
    private void irPerfil(){

    }
    private void irhome(){
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);

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