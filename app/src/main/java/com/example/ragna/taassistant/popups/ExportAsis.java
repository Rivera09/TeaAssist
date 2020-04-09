package com.example.ragna.taassistant.popups;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ragna.taassistant.R;
import com.example.ragna.taassistant.adapters.Export;
import com.example.ragna.taassistant.var;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * Created by Ragna on 03/11/2018.
 */

public class ExportAsis extends Activity implements View.OnClickListener{
    EditText path;
    Button acept,cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int ancho = dm.widthPixels;
        final int alto = dm.heightPixels;
        getWindow().setLayout((int) (ancho * .9), (int) (alto * .35));
        setContentView(R.layout.expot);

        path=findViewById(R.id.path);
        acept=findViewById(R.id.acept);
        cancel=findViewById(R.id.calcel);
        acept.setOnClickListener(this);
        cancel.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.acept:{
                export();
                break;
            }
            case R.id.calcel:{
                finish();
                break;
            }
        }
    }


    private void export(){
        File tem = Environment.getExternalStorageDirectory();
//String ruta_Final = ruta+"//"+nombre+".obb";
        tem = new File(tem.getPath(), "TeaAssist/" + path.getText().toString().trim() + ".xls");
        System.out.println(tem.getPath());
        if (!tem.exists()) {
            try {
                //System.out.println("cotrol 1");
                ArrayList<ArrayList<String>> datos;
                datos= var.asignatura.getGesEstudiante().getEstudiantesAsis();
                com.example.ragna.taassistant.adapters.ExportAsis.generarExcel(datos, "Notas", path.getText().toString().trim());
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            setResult(RESULT_OK);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Error ya existe " + tem.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
