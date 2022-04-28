package com.cdp.agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    TextInputLayout user,contrasenia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user=(TextInputLayout) findViewById(R.id.usuario);
        contrasenia=(TextInputLayout) findViewById(R.id.contrasenia);
    }
    public void ir(View view){
        Intent intent= new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
    }
}