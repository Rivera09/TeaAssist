package com.example.ragna.taassistant.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ragna.taassistant.R;
import com.example.ragna.taassistant.adapters.mastAdapter;
import com.example.ragna.taassistant.clases.Maestro;
import com.example.ragna.taassistant.var;


public class Asignatuas extends AppCompatActivity {
    ListView lw;
    mastAdapter mastAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignaturas);
        ((TextView) (findViewById(R.id.placeHolder))).setText(var.maestro.toString());
        lw = findViewById(R.id.lwasg);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mastAdapter = new mastAdapter(Asignatuas.this, R.layout.list_vw_asignatura, var.gmaestro.arrayMaest());
                lw.setAdapter(mastAdapter);
            }
        });

        lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int item, long id) {
                Maestro m= (Maestro) lw.getItemAtPosition(item);
                var.maestro=var.gmaestro.BusMaestro(m.getId());
                startActivityForResult((new Intent(Asignatuas.this, Asignaturas.class)), 2);

            }
        });
        lw.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "mucho ", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        final Intent intent = new Intent(this, Registro_Asignatura.class);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2 && resultCode==RESULT_OK){
            recreate();
        }
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

    public void LogOut(View vista) {
        SharedPreferences preferences = getSharedPreferences("keepme", Context.MODE_PRIVATE);
        SharedPreferences.Editor saver = preferences.edit();
        saver.putInt("kept", 0);
        saver.commit();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}

