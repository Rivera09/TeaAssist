package com.example.ragna.taassistant.tabs;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.ragna.taassistant.Activities.verEstudiante;
import com.example.ragna.taassistant.clases.Estudiante;
import com.example.ragna.taassistant.popups.ImportarEstudiantes;
import com.github.clans.fab.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ragna.taassistant.Activities.Registro_Estudiantes;
import com.example.ragna.taassistant.R;
import com.example.ragna.taassistant.adapters.estudianteAdapter;
import com.example.ragna.taassistant.var;
import com.github.clans.fab.FloatingActionMenu;

import java.io.File;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 *
 * Created by Ragna on 29/09/2018.
 */

public class Estudiantes extends Fragment{
    ListView listView;
    EditText text;
    TextView status;
    View view;
    estudianteAdapter estudianteadapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle saved){
        view=inflater.inflate(R.layout.estudiantes_contain,viewGroup,false);
        FloatingActionMenu fam = view.findViewById(R.id.fam1);
        FloatingActionButton fab1 = view.findViewById(R.id.fab1);
        FloatingActionButton fab2 = view.findViewById(R.id.fab2);
        fam.setClosedOnTouchOutside(true);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Registro_Estudiantes.class);
                startActivityForResult(intent,1);
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(var.maestro.getGesAsignatura().getCounter()>1){
                    Intent intent = new Intent(getActivity(),ImportarEstudiantes.class);
                    startActivityForResult(intent,3);
                }else{
                    Toast.makeText(getActivity(), "Sólo tiene una asignatura registrada", Toast.LENGTH_SHORT).show();
                }
            }
        });
        listView= view.findViewById(R.id.est);
        text=view.findViewById(R.id.bus);
        status=view.findViewById(R.id.stds);
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                new Handler().post(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        status.setText("");
                        estudianteadapter=new estudianteAdapter(view.getContext(),R.layout.listvw_estudiantes, var.asignatura.getGesEstudiante().filtrarEstud(text.getText().toString()));
                        listView.setAdapter(estudianteadapter);
                        if(listView.getAdapter().getCount()==0) status.setText("Nada por aqui");
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //estudianteadapter.getFilter().filter(text.getText().toString());
            }
        });
        new Handler().post(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                status.setText("");
           estudianteadapter=new estudianteAdapter(view.getContext(),R.layout.listvw_estudiantes, var.estudiantes);
           listView.setAdapter(estudianteadapter);
                if(listView.getAdapter().getCount()==0) status.setText("Nada por aqui");
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Estudiante estudiante= (Estudiante) listView.getItemAtPosition(i);
                Intent intent = new Intent(getActivity(),verEstudiante.class);
                intent.putExtra("idEstu",estudiante.getId());
                startActivityForResult(intent,2);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int index=i;
                Estudiante estudiante= (Estudiante) listView.getItemAtPosition(index);
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setMessage("Desea Eliminar al Estudiante\n"+estudiante.toString());
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Estudiante estudiante= (Estudiante) listView.getItemAtPosition(index);
                                var.asignatura.getGesEstudiante().delEst(estudiante.getId());
                                getActivity().recreate();
                                dialog.cancel();
                            }
                        });
                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
                return true;
            }
        });
        return view;
    }


    @Override
    public void onActivityResult(int Rcode,int Recived,Intent temp){
        super.onActivityResult(Rcode,Recived,temp);
        if(Recived==RESULT_OK){
            getActivity().recreate();
            getActivity().setResult(RESULT_OK);
        }else if(Recived==RESULT_CANCELED && (Rcode==1 || Rcode==2)){
            String path = temp.getStringExtra("ruta");
            File fotoNoUsada = new File(path);
            if(fotoNoUsada.exists()){
                fotoNoUsada.delete();
            }
        }
    }
}
