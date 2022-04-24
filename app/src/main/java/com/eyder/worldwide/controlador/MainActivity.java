package com.eyder.worldwide.controlador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.eyder.worldwide.R;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN=5000;

    //vairables
    Animation topAnimation,buttonAnimation;

    ImageView imagen;
    TextView logo,slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        //Suprimir la barra de acciones
        Objects.requireNonNull(getSupportActionBar()).hide();
        ///Animaciones
        topAnimation= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        buttonAnimation= AnimationUtils.loadAnimation(this,R.anim.buttom_animation);

        //Enlazar con la animacion
        imagen=findViewById(R.id.imagen);
        logo=findViewById(R.id.logo);
        slogan=findViewById(R.id.slogan);

        imagen.setAnimation(topAnimation);
        logo.setAnimation(buttonAnimation);
        slogan.setAnimation(buttonAnimation);

        new Handler().postDelayed(() -> {
            Intent i = new Intent(MainActivity.this, IniciarSesionRegistroActivity.class);
            startActivity(i);
            finish();
        },SPLASH_SCREEN);
    }


}