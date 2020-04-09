package com.example.ragna.taassistant.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ragna.taassistant.R;
import com.example.ragna.taassistant.Serializador;
import com.example.ragna.taassistant.adapters.ExportAsis;
import com.example.ragna.taassistant.adapters.tabAdapter;
import com.example.ragna.taassistant.popups.ExportNotas;
import com.example.ragna.taassistant.tabs.Asistencias;
import com.example.ragna.taassistant.tabs.Estudiantes;
import com.example.ragna.taassistant.tabs.Notas;
import com.example.ragna.taassistant.var;

public class AsignaturaMenu extends AppCompatActivity  {
    tabAdapter tabAdapters;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignatura_menu);
       /* Estudiante estudiante=new Estudiante("00000","alex","chubaca","asd","Carboncillo",var.asignatura.getParcial());
        estudiante.setImage(R.mipmap.goku);
        var.asignatura.addEstudiante(estudiante);
        estudiante=new Estudiante("00asd000","aleasd","chubaadasca","asdasd","aasdsd",var.asignatura.getParcial());
        estudiante.setImage(R.mipmap.m2);
        var.asignatura.addEstudiante(estudiante);*/
        var.estudiantes=var.asignatura.getGesEstudiante().getEstudiantes();
        {
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            toolbar.setTitle(" "+var.asignatura.getName()+"     Parcial "+var.chose);
        }

        TabLayout tabLayout = findViewById(R.id.tab);
        ViewPager viewPager = findViewById(R.id.view);
        tabLayout.setupWithViewPager(viewPager);
        setUpViewPager(viewPager);

    }

    private void setUpViewPager(ViewPager viewPager){
        tabAdapters=new tabAdapter(getSupportFragmentManager());
        tabAdapters.addFragment(new Estudiantes(),"Estudiantes");
        tabAdapters.addFragment(new Asistencias(),"Asistencia");
        tabAdapters.addFragment(new Notas(),"Notas");
        viewPager.setAdapter(tabAdapters);
        //viewPager.setCurrentItem(2);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.item_asignatura, menu);

        for(int i =0;i<var.asignatura.getParcial();i++){
            SpannableString s=new SpannableString("Parcial "+(i+1));
            if((i+1)==var.asignatura.getParcialActivo()){
                s.setSpan(new ForegroundColorSpan(Color.BLUE),0,s.length(),0);
            }
            menu.add(0,i,Menu.NONE,s);



        }
        menu.add(0,var.asignatura.getParcial()+1,Menu.NONE,"Finalizar Parcial");
        menu.add(0,var.asignatura.getParcial()+1,Menu.NONE,"Exportar Notas");
        menu.add(0,var.asignatura.getParcial()+1,Menu.NONE,"Exportar Asistencia Parcial "+var.chose);
        return true;
    }

    @Override
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String indice = item.toString();
        if(indice.toLowerCase().contains("Finalizar Parcial".toLowerCase())){

            var.asignatura.setParcialActivo();
            var.chose= var.asignatura.getParcialActivo();
            System.out.println("parciandole s");
            recreate();
            return true;
        }
        if(indice.toLowerCase().contains("Notas".toLowerCase())){
            Intent intent = new Intent(this,ExportNotas.class);
            startActivityForResult(intent,2);
            return true;
        }
        if(indice.toLowerCase().contains("Asistencia".toLowerCase())){
            System.out.println("MPAD ADA SD");
            Intent intent = new Intent(this, com.example.ragna.taassistant.popups.ExportAsis.class);
            startActivityForResult(intent,3);
            return true;
        }

        indice = indice.replace("Parcial ","");

        var.chose= Integer.parseInt(indice);
        recreate();
        //tabAdapters.destroyItem();
        return true;
        //return super.onOptionsItemSelected(item);
    }

    public void cambiarParcial(MenuItem item) {

       // startActivityForResult(new Intent(this,SelectParcial.class),1);
     //Toast.makeText(getApplicationContext(),"e ",Toast.LENGTH_LONG).show();
    }


    @Override
    public void onActivityResult(int Rcode, int Recived, Intent temp){
        super.onActivityResult(Rcode,Recived,temp);
        if (Rcode==2&&Recived==RESULT_OK){
            Toast.makeText(this,"Exportado Notas ",Toast.LENGTH_LONG).show();

        }if (Rcode==3&&Recived==RESULT_OK){
            Toast.makeText(this,"Exportado Asistencia "+var.chose+" Parcial",Toast.LENGTH_LONG).show();

        }
    }
}
