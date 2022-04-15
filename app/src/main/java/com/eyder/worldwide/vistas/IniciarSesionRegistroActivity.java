package com.eyder.worldwide.vistas;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eyder.worldwide.R;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.GetSignInIntentRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class IniciarSesionRegistroActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    private EditText correo, contrasena;
    private Button iniciarSesion, registro;
    private int requestCode = 100;
    private Task<GoogleSignInAccount> task;
    private SignInButton btnGoogle;

    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    private GoogleSignInAccount gAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        mAuth = FirebaseAuth.getInstance();
        correo = findViewById(R.id.editTextCorreo);
        contrasena = findViewById(R.id.editTextTextContrasena);
        iniciarSesion = findViewById(R.id.iniciarSesionBtn);
        registro = findViewById(R.id.btnRegistro);
        btnGoogle = findViewById(R.id.signGoogle);
        iniciarSesion.setOnClickListener(view -> iniciarSesion(correo.getText().toString(), contrasena.getText().toString()));
        registro.setOnClickListener(view -> irRegistrarse1());

        /*LOGUEAR GOOGLE*/
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);
        gAccount=GoogleSignIn.getLastSignedInAccount(this); //Obtiene el ultimo login google

        //Valida si el usuario ya se ha logueado
        if(gAccount!=null){
            //Abre la pagina inicial
            Log.d(TAG,"USUARIO YA LOGUEADO ANTERIORMENTE");
            Log.d(TAG, "fullName: "+gAccount.getDisplayName());
            Log.d(TAG, "Email: "+gAccount.getEmail());
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(i);
            finish();
        }

        //Captura la respuesta del inicio sesion google
        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Log.d(TAG, "INICIO: "+result.getResultCode());
                    //Obtieniendo una cuenta despues que el usuario selecione una cuenta del dialogo de cuentas de google
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                    manejarGoogleLogueo(task);
                });

        btnGoogle.setOnClickListener(view -> {
            Intent signInGoogle=gsc.getSignInIntent();
            someActivityResultLauncher.launch(signInGoogle);
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null){
            updateUI(mAuth.getCurrentUser());
        }
    }

    private void iniciarSesion(String email, String password) {
        // [START sign_in_with_email]

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(IniciarSesionRegistroActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                });
        // [END sign_in_with_email]
    }


    private void reload() { }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(i);
        }
    }

    private void irRegistrarse1(){
        Intent i = new Intent(this, RegistroActivity.class);
        startActivity(i);
    }


    private void manejarGoogleLogueo(Task<GoogleSignInAccount> task){
        try {
            GoogleSignInAccount account=task.getResult(ApiException.class);
            //Se obtienen datos de cuenta
            final String getFullName= account.getDisplayName();
            final String email=account.getEmail();
            final Uri photoUrl=account.getPhotoUrl();
            Log.d(TAG, "fullName: "+getFullName);
            Log.d(TAG, "Email: "+email);
            //Iniciar Home
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(i);
            finish();
        } catch (ApiException e) {
            e.printStackTrace();
            Toast.makeText(this,"No ha sido posible iniciar sesión so tu cuenta Google",Toast.LENGTH_SHORT).show();
        }

    }


}