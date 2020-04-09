package com.example.ragna.taassistant.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ragna.taassistant.R;
import com.example.ragna.taassistant.Serializador;
import com.example.ragna.taassistant.clases.Asignatura;
import com.example.ragna.taassistant.var;

/***
 *Author Cyborg
 */
public class Registro_Asignatura extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro__asignatura);
    }

    //Implementar codigo de abajo en version final
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

    private boolean NoEmptyFields(){
        String nombre_Asignatura = ((EditText)(findViewById(R.id.nombreA_et))).getText().toString();
        String cant_Parciales = ((EditText)(findViewById(R.id.parciales_et))).getText().toString();
        if(nombre_Asignatura.replaceAll(" ","").equals("")
                ||cant_Parciales.replaceAll(" ","").equals("")){
            return false;
        }
        return true;
    }

    public void Registrar(View vista){
        if(NoEmptyFields()){
            Serializador serializador = new Serializador();
            String nombre_Asignatura = ((EditText)(findViewById(R.id.nombreA_et))).getText().toString();
            int cant_Parciales = Integer.parseInt(((EditText)(findViewById(R.id.parciales_et))).getText().toString());
            Asignatura asignatura = new Asignatura(nombre_Asignatura);
            asignatura.setParcial(cant_Parciales);
            var.maestro.addAsignatura(asignatura);
            serializador.GuardarEnSd(var.gmaestro);
            Toast.makeText(this, "Guardado correctamente", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,Asignaturas.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this, "No se pueden dejar campos vacios.", Toast.LENGTH_SHORT).show();
        }
    }
}
