package com.cdp.agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    TextInputLayout user,contrasenia;
    Spinner spiRol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user=(TextInputLayout) findViewById(R.id.usuario);
        contrasenia=(TextInputLayout) findViewById(R.id.contrasenia);
        spiRol = findViewById(R.id.spirol);
    }
    public void registrar(View view){
        Intent intent= new Intent(LoginActivity.this,RegistroActivity.class);
        startActivity(intent);
    }
    public void ir2(View view){
        String r = String.valueOf(spiRol.getSelectedItem());
        Toast.makeText(LoginActivity.this, ""+r, Toast.LENGTH_SHORT).show();
    }
}