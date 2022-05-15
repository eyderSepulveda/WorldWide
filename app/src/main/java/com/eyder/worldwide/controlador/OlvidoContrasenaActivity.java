package com.eyder.worldwide.controlador;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eyder.worldwide.R;
import com.eyder.worldwide.db.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class OlvidoContrasenaActivity extends AppCompatActivity {

    private TextInputLayout correo;
    private Button recuperar;
    private TextView error;

    private String mail;
    private Firebase mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvido_contrasena);
        correo = findViewById(R.id.editTextCorreoRecuperar);
        recuperar = findViewById(R.id.restablecerBtn);
        error = findViewById(R.id.mensajeErrorCont);
        error.setVisibility(View.INVISIBLE);
        mAuth = new Firebase();
        recuperar.setOnClickListener(view -> {
            recuperarCorreo();
        });

    }

    @SuppressLint("SetTextI18n")
    private void recuperarCorreo(){
        mail= Objects.requireNonNull(correo.getEditText()).getText().toString();
        if(!mail.isEmpty()){
            if(validarEmail(mail)){
                mAuth.getFirebaseAuth().sendPasswordResetEmail(mail).addOnCompleteListener(
                        task -> {
                            if(task.isSuccessful()){
                                error.setText("Se ha enviado un correo eléctronico para restablecer contraseña.");
                                error.setTextColor(Color.BLUE);
                            }else{
                                error.setText("No se pudo enviar correo eléctronico para restablecer contraseña.");
                                error.setTextColor(Color.RED);
                            }
                            error.setVisibility(View.VISIBLE);
                        }
                );
            }
        }else{
            correo.setError("Correo eléctronico requerido");
        }
    }

    //Validadar email este escrito correctamente
    private boolean validarEmail(String correo1) {
        if (!Patterns.EMAIL_ADDRESS.matcher(correo1).matches()) {
            correo.setError("Correo electrónico inválido");
            return false;
        } else {
            correo.setError(null);
        }

        return true;
    }
}