package com.eyder.worldwide.controlador;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eyder.worldwide.R;
import com.eyder.worldwide.db.Firebase;
import com.eyder.worldwide.entidades.Usuario;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class RegistroActivity extends AppCompatActivity {


    //Crear una cuenta con contrasenia
    private Firebase mAuth;
    private static final String TAG = "EmailPassword";
    private static final String ERROREMAILUSO = "The email address is already in use by another account.";
    private Button registrar;
    private TextInputLayout email, password, passwordConfi;
    private TextView error;

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mAuth = new Firebase();
        email = findViewById(R.id.editTextCorreo);
        password = findViewById(R.id.editTextContrasena1);
        passwordConfi = findViewById(R.id.editTextContrasena2);
        registrar = findViewById(R.id.buttonResgistrar);

        error = findViewById(R.id.errorRegistro);
        error.setVisibility(View.INVISIBLE);
        registrar.setOnClickListener(view -> {
            registrarUsuario(Objects.requireNonNull(email.getEditText()).getText().toString(), Objects.requireNonNull(password.getEditText()).getText().toString(), Objects.requireNonNull(passwordConfi.getEditText()).getText().toString());
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getFirebaseAuth().getCurrentUser();
        if (currentUser != null) {
            reload(); //Ojo verificar si genera error
        }
    }

    private void registrarUsuario(String correo, String password1, String pasworrd2) {
        try{
            boolean emailvalido=false;
            boolean pwCorrecto=false,pwCorrecto2=false;
            /*Validamos el correo no sea vacio*/
            if(correo.length()==0){
                email.setError("Correo eléctronico requerido");
            }else{
                emailvalido=validarEmail(correo);
            }
            /*Validamos el password1 no sea vacio*/
            if(password1.length()==0){
                password.setError("Contraseña requerida");
            }else if(!password1.equals(pasworrd2)){
                password.setError("Las contraseñas no coinciden");
            }else if(password1.length()<6){
                password.setError("Debe ser de al menos 6 caracteres");
            }else{
                password.setError(null);
                pwCorrecto=true;
            }
            /*Validamos el pasworrd2 no sea vacio*/
            if(pasworrd2.length()==0){
                passwordConfi.setError("Confirmar contraseña requerida");
            }else if(!password1.equals(pasworrd2)){
                passwordConfi.setError("Las contraseñas no coinciden");
            }else if(pasworrd2.length()<6){
                passwordConfi.setError("Debe ser de al menos 6 caracteres");
            }else{
                passwordConfi.setError(null);
                pwCorrecto2=true;
            }

            if (emailvalido&&pwCorrecto&&pwCorrecto2) {

                @SuppressLint("SetTextI18n") Task<AuthResult> authResultTask = mAuth.getFirebaseAuth().createUserWithEmailAndPassword(correo, password1)
                        .addOnCompleteListener(this, task -> {

                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "USUARIO CREADO CORRECTAMENTE");
                                FirebaseUser user = mAuth.getFirebaseAuth().getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "USUARIO NO SE HA CREADO", task.getException());

                                //Log.w(TAG, task.getException().getMessage());
                                String mensaje=task.getException().getMessage();
                                if (ERROREMAILUSO.contains(mensaje)) {
                                    error.setText("El correo eléctronico ya se encuentra registrado");
                                    error.setError("El correo eléctronico ya se encuentra registrado");
                                    error.setVisibility(View.VISIBLE);
                                }else{
                                    error.setText("No se ha podido crear la cuenta");
                                    error.setError("No se ha podido crear la cuenta");
                                    error.setVisibility(View.VISIBLE);
                                }
                                //updateUI(null);
                            }
                        });
            }
        }catch (Exception e){
            Toast.makeText(RegistroActivity.this, "Se ha producido un error",
                    Toast.LENGTH_SHORT).show();
        }
    }








    private void reload() {
    }

    private void updateUI(FirebaseUser user) {
        Intent i = new Intent(getApplicationContext(), IniciarSesionRegistroActivity.class);
        startActivity(i);

    }

    //Validadar email este escrito correctamente
    private boolean validarEmail(String correo) {
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            email.setError("Correo electrónico inválido");
            return false;
        } else {
            email.setError(null);
        }

        return true;
    }
}