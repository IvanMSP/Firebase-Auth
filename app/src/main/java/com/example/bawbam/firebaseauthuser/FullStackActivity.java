package com.example.bawbam.firebaseauthuser;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FullStackActivity extends AppCompatActivity {

    private static final String TAG = "FullStackActivity";
    private TextView tvUserDetail;
    private Button btnSignOut;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_stack);

        tvUserDetail = (TextView) findViewById(R.id.tvUserDetail);
        btnSignOut = (Button) findViewById(R.id.btnSignOut);
        Inicialize();

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                Intent i = new Intent(FullStackActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private  void signOut(){
        firebaseAuth.signOut();
    }

    private void Inicialize(){

        firebaseAuth = FirebaseAuth.getInstance();
        //este listener detecta los cambios en el inicio de Sesión
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser(); //Aqui cachamos todos los datos del user
                if(firebaseUser != null){
                    tvUserDetail.setText("IDUser:" + firebaseUser.getUid() + "Email:" + firebaseUser.getEmail());
                }else{
                    Log.w(TAG, "onAuthStateChanged - signed_out");
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }
}
