package com.example.ragna.taassistant.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ragna.taassistant.R;
import com.example.ragna.taassistant.Serializador;
import com.example.ragna.taassistant.clases.Maestro;
import com.example.ragna.taassistant.gestiones.gestionMaestro;
import com.example.ragna.taassistant.var;


public class Registro_Maestro extends AppCompatActivity {
    Serializador serializador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro__maestro);
        serializador = new Serializador();
    }

    /*@Override
    protected void onResume() {
        Serializador serializador = new Serializador();
        if(!serializador.Existe()){
            Intent intent = new Intent(this,LoginActivity.class);
            SharedPreferences preferences= getSharedPreferences("keepme",Context.MODE_PRIVATE);
            SharedPreferences.Editor saver = preferences.edit();
            saver.putInt("kept",0);
            saver.commit();
            startActivity(intent);
            finish();
        }
        super.onResume();
    }*/

    public void Registrar(View Vista){
        boolean valido = true;
        String nombreRE = "[a-zA-Záéíóú]+(\\s[a-zA-Záéíóú]+)?(\\s[a-zA-Záéíóú]+)?(\\s[a-zA-Záéíóú]*)?\\s*";
        String nombre_field = ((EditText)(findViewById(R.id.nombre_et))).getText().toString();
        String pass_field = ((EditText)(findViewById(R.id.pass_et))).getText().toString();
        String user_field = ((EditText)(findViewById(R.id.user_et))).getText().toString();
        String id_field = ((EditText)(findViewById(R.id.id_et))).getText().toString();
        if(nombre_field.replaceAll(" ","").equals("")
                ||pass_field.replaceAll(" ","").equals("")
                ||user_field.replaceAll(" ","").equals("")
                ||id_field.replaceAll(" ","").equals("")){
            Toast.makeText(this, "No se pueden dejar campos vacios", Toast.LENGTH_SHORT).show();
        }else{
            if(nombre_field.matches(nombreRE)){
                if(var.gmaestro.BusMaestro(id_field) == null){
                    Maestro temp = var.gmaestro.getMaestros();
                    while(temp!=null){
                        if(user_field.equals(temp.getUser())){
                            Toast.makeText(this, "Nombre de usuario registrado anteriormente", Toast.LENGTH_SHORT).show();
                            valido = false;
                            break;
                        }else{
                            temp=temp.getNextMaestro();
                        }
                    }
                }else{
                    Toast.makeText(this, "ID registrado anteriormente", Toast.LENGTH_SHORT).show();
                    valido = false;
                }
                if(valido){

                    String[] nombres = nombre_field.split(" ");
                    String[] rn=new String[4];
                    rn[0]=nombres[0];
                    rn[1]=nombres.length>1?nombres[1]:null;
                    rn[2]=nombres.length>2?nombres[2]:null;
                    rn[3]=nombres.length>3?nombres[3]:null;


                    Maestro nuevo_user = new Maestro(id_field,rn[0],rn[1],rn[2],rn[3]);
                    nuevo_user.setCredential(user_field,pass_field);
                    var.gmaestro.addMaestro(nuevo_user);
                    Toast.makeText(this, serializador.GuardarEnSd(var.gmaestro), Toast.LENGTH_LONG).show();
                    SharedPreferences preferences= getSharedPreferences("keepme",Context.MODE_PRIVATE);
                    SharedPreferences.Editor saver = preferences.edit();
                    saver.putInt("kept",0);
                    saver.commit();
                    finish();
                }
            }else{
                Toast.makeText(this, "Nombre no válido", Toast.LENGTH_SHORT).show();
            }
        }
    }
}