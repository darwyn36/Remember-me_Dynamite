package com.cdp.agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlarmManager; //notificaciones
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    Button btnGuarda, bFecha, bHora;
    Activity actividad;
    private int dia, mes, anio, hora, minutos;
    private int alarmID = 1; //para notificaciones
    private SharedPreferences settings; //notificaciones

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        settings = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);//notificaciones

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
                    String finalHour, finalMinute; //notificaciones

                    eHora.setText(String.format("%02d:%02d", hourOfDay, minute));

                    finalHour = "" + hourOfDay;//notificaciones
                    finalMinute = "" + minute; //notificaciones
                    if(hourOfDay < 10) finalHour = "0" + hourOfDay; //notificaciones
                    if(minute < 10) finalMinute = "0" + minute; //notificaciones

                    Calendar today = Calendar.getInstance(); //notificaciones

                    today.set(Calendar.HOUR_OF_DAY, hourOfDay); //notificaciones
                    today.set(Calendar.MINUTE, minute); //notificaciones
                    today.set(Calendar.SECOND, 0); //notificaciones

                    SharedPreferences.Editor edit = settings.edit(); //notificaciones
                    edit.putString("hora", finalHour); //notificaciones
                    edit.putString("minutos", finalMinute); //notificaciones

                    //guarda la notificacion en caso de que se reinicie el dispositivo android
                    edit.putInt("alarmID", alarmID); //notificaciones
                    edit.putLong("alarmTime", today.getTimeInMillis()); //notificaciones

                    edit.commit(); //notificaciones

                    Utils.setAlarm(alarmID, today.getTimeInMillis(), NuevoActivity.this);
                }
            }, hora, minutos, false);
            timePickerDialog.show();
        }
    }
}