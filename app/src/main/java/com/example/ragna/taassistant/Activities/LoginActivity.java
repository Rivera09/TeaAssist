package com.example.ragna.taassistant.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ragna.taassistant.R;
import com.example.ragna.taassistant.Serializador;
import com.example.ragna.taassistant.clases.Maestro;
import com.example.ragna.taassistant.gestiones.gestionMaestro;
import com.example.ragna.taassistant.var;

import java.io.File;


/**
 * Author Cybrog
 * */
public class LoginActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1){
            String[] permissions = {
                    "android.permission.WRITE_EXTERNAL_STORAGE",
                    "android.permission.READ_EXTERNAL_STORAGE",
                    "android.permission.CAMERA"
            };
            requestPermissions(permissions,200);
        }
        if(var.gmaestro == null)recuperarUsuarios();
    }


    /**Método que manda al usuario a la ventana de registro.*/
    public void Registrar(View vista){
        Intent intent = new Intent(this,Registro_Maestro.class);
        startActivity(intent);
    }

    /**Método para el inicio de sesión.*/
    public void Login(View vista){
        String userField = ((EditText)(findViewById(R.id.user_et))).getText().toString();
        String passField = ((EditText)(findViewById(R.id.clave_et))).getText().toString();
        boolean validUser = false;
        try{
        Maestro userTemp = var.gmaestro.getMaestros();
        while(userTemp != null){
            if(userTemp.accesGrant(userField,passField)){
                validUser = true;
                var.maestro = userTemp;
                if(userTemp.getId().equals("-1")){

                    Intent intent = new Intent(this,Asignatuas.class);
                    startActivity(intent);
                    return;
                }
                if(((CheckBox)(findViewById(R.id.keepBox))).isChecked()){
                    SharedPreferences preferences= getSharedPreferences("keepme",Context.MODE_PRIVATE);
                    SharedPreferences.Editor saver = preferences.edit();
                    saver.putInt("kept",1);
                    saver.putString("idkept",userTemp.getId());
                    saver.commit();
                }
                break;
            }else{
                userTemp = userTemp.getNextMaestro();
            }
        }}catch (Exception e){
            Toast.makeText(this, "Base de datos formateada debido a cambios internos", Toast.LENGTH_SHORT).show();
            File archivoCorrupto = new File(Environment.getExternalStorageDirectory(),"Android/data/com.jjsd.teaassist/TeaAssist.dat");
            if(archivoCorrupto !=null && archivoCorrupto.exists()){
                archivoCorrupto.delete();
            }
            var.gmaestro = new gestionMaestro();
        }
        if(validUser){
            Intent intent = new Intent(this,Asignaturas.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this, "Los datos no coiciden.", Toast.LENGTH_SHORT).show();
        }
    }

    public void recuperarUsuarios(){
        Serializador serializador = new Serializador();
        if(serializador.Existe()){
            var.gmaestro = (gestionMaestro) serializador.Recuperar();
        }else{
            var.gmaestro = new gestionMaestro();
            try {
                File carpeta = Environment.getExternalStorageDirectory();
                carpeta = new File(carpeta.getPath(),"Android/data/com.jjsd.teaassist");
                carpeta.mkdir();
            }catch (Exception e){
            }
        }
        SharedPreferences preferences= getSharedPreferences("keepme",Context.MODE_PRIVATE);
        int kept = preferences.getInt("kept",0);
        if(kept==1){
            try {
                var.maestro = var.gmaestro.BusMaestro(preferences.getString("idkept","0000"));
            }catch (Exception e){
                File archivoCorrupto = new File(Environment.getExternalStorageDirectory(),"Android/data/com.jjsd.teaassist/TeaAssist.dat");
                if(archivoCorrupto !=null && archivoCorrupto.exists()){
                    archivoCorrupto.delete();
                    SharedPreferences.Editor saver = preferences.edit();
                    saver.putInt("kept",0);
                    saver.commit();
                }
            }
            if(var.maestro != null){
                Intent intent = new Intent(this,Asignaturas.class);
                startActivity(intent);
                finish();
            }else{
                SharedPreferences.Editor saver = preferences.edit();
                saver.putInt("kept",0);
                saver.commit();
            }
        }
    }

}