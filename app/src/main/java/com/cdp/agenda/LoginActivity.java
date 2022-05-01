package com.cdp.agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    TextInputLayout user,contrasenia;
    RequestQueue requestQueue;
    Spinner spiRol;
    String URL;
    boolean res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        requestQueue= Volley.newRequestQueue(this);
        user=(TextInputLayout) findViewById(R.id.usuario);
        contrasenia=(TextInputLayout) findViewById(R.id.contrasenia);
        spiRol = findViewById(R.id.spirol);
    }
    public void registrar(View view){
        Intent intent= new Intent(LoginActivity.this,RegistroActivity.class);
        startActivity(intent);
    }
    public void validarDatos(View view){
        String usuario,contra;
        usuario=user.getEditText().getText().toString().trim();
        contra=contrasenia.getEditText().getText().toString().trim();

        String r = String.valueOf(spiRol.getSelectedItem());
        if(!user.getEditText().getText().toString().equals("") && !contrasenia.getEditText().getText().toString().equals("")){

            if(r.equals("Adulto")){
                URL = "https://bdconandroidstudio.000webhostapp.com/verificarUserAdulto.php?nombre_a="+usuario+"&contrasenia="+contra;

                    verificarLogin(URL,contra,"adulto");


            }
            if(r.equals("Responsable")){
                URL = "https://bdconandroidstudio.000webhostapp.com/verificarUserResp.php?nombre_r="+usuario+"&contrasenia="+contra;

                verificarLogin(URL,contra,"responsable");

            }
        }else{
            Toast.makeText(LoginActivity.this, "Usuario o contraseña vacíos", Toast.LENGTH_LONG).show();
        }
    }


    private void verificarLogin(String URL,String contra,String rol){
        // Toast.makeText(getApplicationContext(), "se hizo la consulta", Toast.LENGTH_SHORT).show();

        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Toast.makeText(getApplicationContext(), "se hizo la consulta", Toast.LENGTH_SHORT).show();
                        String contrasenia="";
                        try {
                            contrasenia= response.getString("contrasenia").toString();






                            if (contrasenia.equals(contra)) {
                                if(rol.equals("adulto")) {
                                    Intent intent = new Intent(LoginActivity.this, mainAdulto2.class);
                                    startActivity(intent);
                                }
                                if(rol.equals("responsable")){
                                    Intent intent= new Intent(LoginActivity.this,MainActivity.class);//ventana del responsable
                                    startActivity(intent);
                                    Toast.makeText(LoginActivity.this, "Se ingreso a responsable", Toast.LENGTH_LONG).show();
                                }
                            }else{
                                Toast.makeText(LoginActivity.this, "Usuario o contraseña  incorrecto", Toast.LENGTH_LONG).show();
                            }






                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
                    }
                }
        );


        requestQueue.add(jsonObjectRequest);

    }


}