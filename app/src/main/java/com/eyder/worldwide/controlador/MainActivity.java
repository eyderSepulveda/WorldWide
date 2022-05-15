package com.eyder.worldwide.controlador;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.eyder.worldwide.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN=3000;

    //vairables
    private Animation topAnimation,buttonAnimation;

    private ImageView imagen;
    private TextView logo,slogan;
    private GoogleSignInOptions gso;
    private GoogleSignInAccount gAccount;

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

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gAccount = GoogleSignIn.getLastSignedInAccount(this);

        new Handler().postDelayed(() -> {
            Intent i;
            Pair[] pairs=null;
            if(gAccount!=null|| FirebaseAuth.getInstance().getCurrentUser()!=null){
                i = new Intent(MainActivity.this, HomeActivity.class);
                pairs= new Pair[2];
                pairs[0]= new Pair<View,String>(imagen,"logo_image");
                pairs[1]= new Pair<View,String>(logo,"logo_name");
            }else{
                i = new Intent(MainActivity.this, IniciarSesionRegistroActivity.class);
                pairs= new Pair[3];
                pairs[0]= new Pair<View,String>(imagen,"logo_image");
                pairs[1]= new Pair<View,String>(logo,"logo_name");
                pairs[2]= new Pair<View,String>(slogan,"Slogan_name");
            }

            ActivityOptions option=ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
            startActivity(i,option.toBundle());

        },SPLASH_SCREEN);

    }


}