package com.eyder.worldwide.controlador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;

import com.eyder.worldwide.R;
import com.eyder.worldwide.db.Firebase;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class PerfilUsuarioActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Firebase mAuth;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    private GoogleSignInAccount gAccount;
    private static final String TAG = "PERFIL";
    private TextView nombre,email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);

        //Suprimir la barra de acciones
        Objects.requireNonNull(getSupportActionBar()).hide();

        bottomNavigationView = findViewById(R.id.bottonNavigationView4);
        bottomNavigationView.setBackground(null);
        nombre = findViewById(R.id.textNombre);
        email = findViewById(R.id.textCorreoEl);
        mAuth = new Firebase();
        FirebaseUser user = mAuth.getFirebaseAuth().getCurrentUser();
        mAuth.getFirebaseFirestore().collection("Usuarios").whereEqualTo("correo", user.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                email.setText(user.getEmail());
                                nombre.setText(Objects.requireNonNull(document.getData().get("nombre")).toString());
                            }
                        } else {
                            email.setText(user.getEmail());
                            nombre.setText("Sin Nombre");
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home1) {
                irHome();
                return true;
            } else if (item.getItemId() == R.id.exit) {
                cerrarSesion();
                return true;
            } else if (item.getItemId() == R.id.account) {
                irPerfil();
                return true;
            }
            return false;
        });

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);
    }

    //Metodos para los botones de la barra de navegacion
    private void irHome() {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);

    }

    private void irPerfil(){
        Intent i = new Intent(this, PerfilUsuarioActivity.class);
        startActivity(i);
        finish();
    }

    private void cerrarSesion() {
        if (mAuth. getFirebaseAuth().getCurrentUser() != null) {
            mAuth.getFirebaseAuth().signOut();
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