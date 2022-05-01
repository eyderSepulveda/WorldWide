package com.eyder.worldwide.controlador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.eyder.worldwide.R;
import com.eyder.worldwide.db.Firebase;
import com.eyder.worldwide.entidades.Lugar;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
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
    private String continente,stringSol,stringNaturaleza,stringCultural,stringGastronomico;
    private RecyclerView ListaLugares;
    private ArrayList<Lugar> lista;
    private BottomNavigationView bottomNavigationView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_lugar);

        mAuth = FirebaseAuth.getInstance();
        bottomNavigationView2 = findViewById(R.id.bottonNavigationView2);
        bottomNavigationView2.setBackground(null);
        db = new Firebase();
        continente = getIntent().getStringExtra("continente");
        stringSol= getIntent().getStringExtra("sol");
        stringNaturaleza=getIntent().getStringExtra("naturaleza");
        stringCultural=getIntent().getStringExtra("cultural");
        stringGastronomico=getIntent().getStringExtra("gastronomico");
        buscarLugares();

        bottomNavigationView2.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
               case R.id.search:
                    irContinente();
                    return true;
                case R.id.home1:
                    irhome();
                    return true;
                case R.id.account:
                    //irPerfil();
                    return true;
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
                whereArrayContainsAny("tipoTurismo", Arrays.asList(stringSol, stringNaturaleza, stringCultural, stringGastronomico))
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        int con = 0;
                        lista= new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Lugar lugar=new Lugar(Integer.parseInt(document.getId()), Objects.requireNonNull(document.getData().get("nombre")).toString(), Objects.requireNonNull(document.getData().get("descripcion")).toString());
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

    private void irContinente(){
        Intent i = new Intent(this, ContinenteActivity.class);
        startActivity(i);

    }
    private void irhome(){
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);

    }

}