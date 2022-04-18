package com.eyder.worldwide.vistas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.eyder.worldwide.R;
import com.eyder.worldwide.entidades.Lugar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

public class ResultadoLugarActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private final String TAG = "busqueda";
    private String continente,stringSol,stringNaturaleza,stringCultural,stringGastronomico;
    private RecyclerView ListaLugares;
    private ArrayList<Lugar> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_lugar);

        db = FirebaseFirestore.getInstance();
        continente = getIntent().getStringExtra("continente");
        stringSol= getIntent().getStringExtra("sol");
        stringNaturaleza=getIntent().getStringExtra("naturaleza");
        stringCultural=getIntent().getStringExtra("cultural");
        stringGastronomico=getIntent().getStringExtra("gastronomico");
        buscarLugares();

    }

    private void buscarLugares(){
        db.collection("Lugares").whereEqualTo("continente", continente).
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
}