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
import com.example.ragna.taassistant.Serializador;
import com.example.ragna.taassistant.adapters.asignaturaAdapter;
import com.example.ragna.taassistant.clases.Asignatura;
import com.example.ragna.taassistant.var;


public class Asignaturas extends AppCompatActivity {
    ListView lw;
    com.example.ragna.taassistant.adapters.asignaturaAdapter asignaturaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignaturas);

        {
            /*Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            toolbar.setTitle(var.maestro.getN1()+""+var.maestro.getN3());*/
            ((TextView) (findViewById(R.id.placeHolder))).setText(var.maestro.getN1() + " " + var.maestro.getN3());
        }
        /*{
           new Thread(new Runnable() {
                @Override
                public void run() {
                    Asignatura a1=new Asignatura("Espanol");
                    a1.setParcial(3);
                    var.maestro.addAsignatura(a1);
                    a1=new Asignatura("mate");
                    a1.setParcial(3);
                    var.maestro.addAsignatura(a1);
                    a1=new Asignatura("Actividades");
                    a1.setParcial(1);
                    var.maestro.addAsignatura(a1);
                    a1=new Asignatura("mates");
                    a1.setParcial(4);
                    var.maestro.addAsignatura(a1);
                    a1=new Asignatura("qiomia");
                    a1.setParcial(4);
                    a1.setParcialActivo(4);
                    var.maestro.addAsignatura(a1);
                }

            }).start();
        }*/

        lw = findViewById(R.id.lwasg);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                asignaturaAdapter = new asignaturaAdapter(Asignaturas.this, R.layout.list_vw_asignatura, var.maestro.getGesAsignatura().asignaturas());
                lw.setAdapter(asignaturaAdapter);
            }
        });

        lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int item, long id) {
                TextView t = v.findViewById(R.id.ccid);
                var.asignatura = var.maestro.getGesAsignatura().BusAsg(t.getText().toString().trim());
                var.chose = var.asignatura.getParcialActivo();
                startActivityForResult((new Intent(Asignaturas.this, AsignaturaMenu.class)), 2);

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
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
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

