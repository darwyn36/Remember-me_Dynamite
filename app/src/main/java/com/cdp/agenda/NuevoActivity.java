package com.cdp.agenda;

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

import com.cdp.agenda.db.DbContactos;

import java.util.Calendar;

public class NuevoActivity extends AppCompatActivity {

    EditText txtTitulo, txtDireccion, txtDescripcion;
    TextView eHora,eFecha;
    Button btnGuarda, bFecha, bHora, borrar;
    Activity actividad;
    private int dia, mes, anio, hora, minutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);

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

                if (!txtTitulo.getText().toString().equals("")
                        && !eFecha.getText().toString().equals("")&& !eHora.getText().toString().equals("")) {

                    DbContactos dbContactos = new DbContactos(NuevoActivity.this);
                    long id = dbContactos.insertarContacto(txtTitulo.getText().toString(), eHora.getText().toString(),eFecha.getText().toString(),txtDireccion.getText().toString(), txtDescripcion.getText().toString());

                    if (id > 0) {
                        Toast.makeText(NuevoActivity.this, "REGISTRO GUARDADO", Toast.LENGTH_LONG).show();
                        limpiar();
                        Intent intent = new Intent(actividad, MainActivity.class);
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
        borrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                limpiar();
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
            final Calendar now = Calendar.getInstance();
            int actualDay = now.get(Calendar.DAY_OF_MONTH);
            int actualMonth = now.get(Calendar.MONTH)+1;
            int actualYear = now.get(Calendar.YEAR);

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
                    if(year > actualYear){
                        eFecha.setText(year+ "-" + mes + "-" + dia);
                    }else if(year < actualYear){
                        Toast.makeText(NuevoActivity.this, "NO SE PUEDEN REGISTRAR FECHAS ANTERIORES", Toast.LENGTH_LONG).show();
                    }else if(year == actualYear){
                        if((monthOfYear+1)>actualMonth){
                            eFecha.setText(year+ "-" + mes + "-" + dia);
                        }else if((monthOfYear+1)<actualMonth){
                            Toast.makeText(NuevoActivity.this, "NO SE PUEDEN REGISTRAR FECHAS ANTERIORES", Toast.LENGTH_LONG).show();
                        }else if((monthOfYear+1)==actualMonth){
                            if(dayOfMonth>=actualDay){
                                eFecha.setText(year+ "-" + mes + "-" + dia);
                            }else Toast.makeText(NuevoActivity.this, "NO SE PUEDEN REGISTRAR FECHAS ANTERIORES", Toast.LENGTH_LONG).show();
                        }
                    }

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
}