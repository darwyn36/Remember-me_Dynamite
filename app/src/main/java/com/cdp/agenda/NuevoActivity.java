package com.cdp.agenda;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cdp.agenda.db.DbContactos;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class NuevoActivity extends AppCompatActivity {

    EditText txtTitulo, txtDireccion, txtDescripcion;
    TextView eHora,eFecha;
    Button btnGuarda, bFecha, bHora;
    Activity actividad;
    private int dia, mes, anio, hora, minutos;
    private String titulo,time,fecha,direccion,descripcion;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);
        requestQueue= Volley.newRequestQueue(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        bFecha = (Button) findViewById(R.id.bFecha);
        bHora = (Button) findViewById(R.id.bHora);
        eFecha = (TextView) findViewById(R.id.eFecha);
        eHora = (TextView) findViewById(R.id.eHora);
        //bFecha.setOnClickListener((View.OnClickListener) this);
        //bHora.setOnClickListener((View.OnClickListener) this);

        txtTitulo = findViewById(R.id.txtTitulo);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        btnGuarda = findViewById(R.id.btnGuarda);
        actividad=this;
        btnGuarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titulo=txtTitulo.getText().toString().trim();
                fecha=eFecha.getText().toString().trim();
                time=eHora.getText().toString().trim();
                descripcion=txtDescripcion.getText().toString().trim();
                direccion=txtDireccion.getText().toString().trim();

                registrarActivity("https://bdconandroidstudio.000webhostapp.com/registrar.php");

                if (!txtTitulo.getText().toString().equals("")
                        && !eFecha.getText().toString().equals("")&& !eHora.getText().toString().equals("")) {

                    String[] parts = txtTitulo.getText().toString().split("");
                    String primero  =parts[0];
                    if (primero.equals(" ")){
                        Toast.makeText(NuevoActivity.this, "Llenar campo de Tìtulo", Toast.LENGTH_LONG).show();
                        return;
                    }

                    DbContactos dbContactos = new DbContactos(NuevoActivity.this);
                    long id = dbContactos.insertarContacto(titulo,time,fecha,direccion,descripcion);

                    if (id > 0) {
                        //Toast.makeText(NuevoActivity.this, "REGISTRO GUARDADO", Toast.LENGTH_LONG).show();
                        limpiar();
                        Intent intent = new Intent(actividad, mainAdulto2.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(NuevoActivity.this, "ERROR AL GUARDAR REGISTRO", Toast.LENGTH_LONG).show();
                    }
                } else {
                    eHora.setError("");
                    eFecha.setError("");
                    txtTitulo.setError("");
                    Toast.makeText(NuevoActivity.this, "Error complete los datos obligatorios", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void limpiar() {
        txtTitulo.setText("");
        eHora.setText("");
        eFecha.setText("");
        txtDireccion.setText("");
        txtDescripcion.setText("");

    }

  //  @Override
    public void onClick(View v) {
        if (v == bFecha) {
            final Calendar c = Calendar.getInstance();
            dia = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH);
            anio = c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    String mes = (monthOfYear+1)+ "";
                    String dia= dayOfMonth+"";
                    if((monthOfYear+1)<10){
                        mes = "0"+(monthOfYear+1);

                    }
                    if(dayOfMonth<10){
                        dia = "0"+(dayOfMonth);

                    }



                    eFecha.setText(year + "-" + mes + "-" + dia);

                }
            }, 2022, mes, dia);
            datePickerDialog.show();
        }
        if (v == bHora) {
            final Calendar c = Calendar.getInstance();
            hora = c.get(Calendar.HOUR_OF_DAY);
            minutos = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    eHora.setText(String.format("%02d:%02d", hourOfDay, minute));
                }
            }, hora, minutos, false);
            timePickerDialog.show();
        }
    }


    public void registrarActivity(String URL){
        StringRequest stringRequest= new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "OPERACION EXITOSA", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros=new HashMap<String,String>();
                parametros.put("titulo",titulo);
                parametros.put("hora",time);
                parametros.put("fecha",fecha);
                parametros.put("descripcion",descripcion);
                parametros.put("direccion",direccion);

                return parametros;
            }
        };

        requestQueue.add(stringRequest);

    }

}