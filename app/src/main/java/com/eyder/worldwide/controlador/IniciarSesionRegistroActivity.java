package com.eyder.worldwide.controlador;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eyder.worldwide.R;
import com.eyder.worldwide.db.Firebase;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;
import java.util.regex.Pattern;

public class IniciarSesionRegistroActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    private Firebase mAuth;
    private TextInputLayout correo, contrasena;
    private Button iniciarSesion, registro;
    private SignInButton btnGoogle;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    private GoogleSignInAccount gAccount;
    private TextView error;
    private CheckedTextView olvidoContrasena;
    // Publicidad
   // private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_iniciar_sesion);
        //Suprimir la barra de acciones
        Objects.requireNonNull(getSupportActionBar()).hide();
        mAuth = new Firebase();
        correo = findViewById(R.id.editTextCorreo);
        contrasena = findViewById(R.id.editTextTextContrasena);
        iniciarSesion = findViewById(R.id.iniciarSesionBtn);
        registro = findViewById(R.id.btnRegistro);
        btnGoogle = findViewById(R.id.signGoogle);
        olvidoContrasena = findViewById(R.id.olvidoContrasen);

        error = findViewById(R.id.mensajeError);
        error.setVisibility(View.INVISIBLE);

        iniciarSesion.setOnClickListener(view -> {
            iniciarSesion(Objects.requireNonNull(correo.getEditText()).getText().toString(), Objects.requireNonNull(contrasena.getEditText()).getText().toString());

        });

        registro.setOnClickListener(view -> irRegistrarse1());

        /*LOGUEAR GOOGLE*/
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);
        gAccount = GoogleSignIn.getLastSignedInAccount(this); //Obtiene el ultimo login google

        //Valida si el usuario ya se ha logueado
        if (gAccount != null) {
            //Abre la pagina inicial
            Log.d(TAG, "USUARIO YA LOGUEADO ANTERIORMENTE");
            Log.d(TAG, "fullName: " + gAccount.getDisplayName());
            Log.d(TAG, "Email: " + gAccount.getEmail());
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(i);
            finish();
        }

        //Captura la respuesta del inicio sesion google
        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Log.d(TAG, "INICIO: " + result.getResultCode());
                    //Obtieniendo una cuenta despues que el usuario selecione una cuenta del dialogo de cuentas de google
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                    manejarGoogleLogueo(task);
                });

        btnGoogle.setOnClickListener(view -> {
            Intent signInGoogle = gsc.getSignInIntent();
            someActivityResultLauncher.launch(signInGoogle);
        });


        //Olvido contrase??a
        olvidoContrasena.setOnClickListener(view -> {
                    Intent j = new Intent(this, OlvidoContrasenaActivity.class);
                    startActivity(j);
                }
        );

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getFirebaseAuth().getCurrentUser() != null) {
            updateUI(mAuth.getFirebaseAuth().getCurrentUser());
        }
    }

    @SuppressLint("SetTextI18n")
    private void iniciarSesion(String email, String password) {
        // [START sign_in_with_email]
        try {
            boolean emailValido = false;
            //Valida que email no sea nulo
            if (email == null || email.length() == 0) {
                correo.setError("Usuario requerido");
            } else {
                emailValido = validarEmail(email);
            }
            //Valida que contrase??a no sea nulo
            if (password == null || password.length() == 0) {
                contrasena.setError("Contrase??a requerida");
            } else {
                contrasena.setError(null);
            }

            if (emailValido && password.length() > 0) {
                mAuth.getFirebaseAuth().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getFirebaseAuth().getCurrentUser();
                                if (user.isEmailVerified()) {
                                    updateUI(user);
                                } else {
                                    error.setText("Debe confirmar el correo el??ctronico de registro.");
                                    error.setVisibility(View.VISIBLE);
                                    mAuth.getFirebaseAuth().signOut();
                                }
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                String mensaje = task.getException().getMessage();
                                if (mensaje.contains("The password is invalid")) {
                                    error.setVisibility(View.INVISIBLE);
                                    contrasena.setError("La contrase??a no es v??lida");

                                } else if (mensaje.contains("There is no user record")) {
                                    error.setText("Usuario no registrado");
                                    error.setVisibility(View.VISIBLE);
                                }
                            }
                        });
            }
        } catch (Exception e) {
            Toast.makeText(IniciarSesionRegistroActivity.this, "Se ha producido un error",
                    Toast.LENGTH_SHORT).show();
        }

        // [END sign_in_with_email]
    }


    private void reload() {
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(i);
        }
    }

    private void irRegistrarse1() {
        Intent i = new Intent(this, RegistroActivity.class);
        startActivity(i);
    }


    private void manejarGoogleLogueo(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            //Se obtienen datos de cuenta
            final String getFullName = account.getDisplayName();
            final String email = account.getEmail();
            final Uri photoUrl = account.getPhotoUrl();
            Log.d(TAG, "fullName: " + getFullName);
            Log.d(TAG, "Email: " + email);
            //Iniciar Home
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(i);
            finish();
        } catch (ApiException e) {
            e.printStackTrace();
            Toast.makeText(this, "No ha sido posible iniciar sesi??n con tu cuenta Google", Toast.LENGTH_SHORT).show();
        }

    }

    //Validadar email este escrito correctamente
    private boolean validarEmail(String correo1) {
        if (!Patterns.EMAIL_ADDRESS.matcher(correo1).matches()) {
            correo.setError("Correo electr??nico inv??lido");
            return false;
        } else {
            correo.setError(null);
        }

        return true;
    }


}