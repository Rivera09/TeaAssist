package com.example.ragna.taassistant.tabs;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ragna.taassistant.R;
import com.example.ragna.taassistant.adapters.notasTableAdapter;
import com.example.ragna.taassistant.popups.ExportNotas;
import com.example.ragna.taassistant.var;
import android.support.design.widget.FloatingActionButton;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Ragna on 05/10/2018.
 */

public class Notas extends Fragment {
    View view;
    TextView parcial;
    TableRow row;
    notasTableAdapter tableAdapter;
    String[] temp={"Nombre","Acumulativo","Asistencia","Tareas","Otros","Examen","Total","Nota Final","Recuperacion","Observacion"};
    TableLayout tableLayout;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle saved){
         view=inflater.inflate(R.layout.notas_contain,viewGroup,false);
         tableLayout=view.findViewById(R.id.notas);
         parcial=view.findViewById(R.id.par);
         parcial.setText("Parcial "+var.chose);
         tableUpdate();
         //row=(TableRow) ;
       // System.out.println("akjdf' as;jas'dfa;sdkf "+tableLayout);
        return view;
    }
    private void tableUpdate(){
        new Handler().post(new Runnable() {
            @Override
            public void run() {

                tableAdapter=new notasTableAdapter(view,tableLayout, var.chose, var.estudiantes,temp, parcial.getInputType());
            /*
                 System.out.println("imorinaso ahd "+((TextView)tableAdapter.rows.get(1).getChildAt(0)).getText().toString());
                ((EditText)tableAdapter.rows.get(1).getChildAt(2)).setText("234");*/
            }
        });
    }
    public void onPause(){
                tableAdapter.onChanged();
        super.onPause();
    }

}
