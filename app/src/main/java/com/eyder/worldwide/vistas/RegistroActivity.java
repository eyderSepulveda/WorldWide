package com.eyder.worldwide.vistas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eyder.worldwide.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class RegistroActivity extends AppCompatActivity {


    //Crear una cuenta con contrasenia
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    private static final String ERROREMAILUSO = "The email address is already in use by another account.";
    Button registrar;

    private EditText email, password, passwordConfi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editTextCorreo);
        password = findViewById(R.id.editTextContrasena1);
        passwordConfi = findViewById(R.id.editTextContrasena2);
        registrar = findViewById(R.id.buttonResgistrar);

        registrar.setOnClickListener(view -> registrarUsuario(email.getText().toString(), password.getText().toString(), passwordConfi.getText().toString()));

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            reload(); //Ojo verificar si genera error
        }
    }

    private void registrarUsuario(String correo, String password1, String pasworrd2) {

        if (password1.equals(pasworrd2)) {

            Task<AuthResult> authResultTask = mAuth.createUserWithEmailAndPassword(correo, password1)
                    .addOnCompleteListener(this, task -> {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "USUARIO CREADO CORRECTAMENTE");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "USUARIO NO SE HA CREADO", task.getException());
                            Toast.makeText(RegistroActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //Log.w(TAG, task.getException().getMessage());
                            if (ERROREMAILUSO.equals(Objects.requireNonNull(task.getException()).getMessage())) {
                                Toast.makeText(RegistroActivity.this, "El correo electronico ya se encuentra registrado",
                                        Toast.LENGTH_SHORT).show();
                            }
                            updateUI(null);
                        }
                    });
        } else {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }


    }

    private void reload() {
    }

    private void updateUI(FirebaseUser user) {
        Intent i = new Intent(getApplicationContext(), IniciarSesionRegistroActivity.class);
        startActivity(i);

    }
}