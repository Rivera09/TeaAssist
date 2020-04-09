package com.example.ragna.taassistant.tabs;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ragna.taassistant.R;
import com.example.ragna.taassistant.Serializador;
import com.example.ragna.taassistant.adapters.asistenciaAdapter;
import com.example.ragna.taassistant.clases.Asistencia;
import com.example.ragna.taassistant.var;

import java.util.Calendar;

/**
 * Created by Ragna on 28/09/2018.
 */

public class Asistencias extends Fragment  {
    Serializador serializador;
    ListView listView;
    asistenciaAdapter asAdapter;
    TextView calendar;
    int dia,mes,ano;
    String dat;
    View v,vi;
    boolean changed=false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle saved){
        final View view=inflater.inflate(R.layout.content_asistencias,viewGroup,false);
        vi=view;
        serializador = new Serializador();
        calendar= view.findViewById(R.id.cal);
        Calendar calendar1=Calendar.getInstance();
        dia=calendar1.get(Calendar.DAY_OF_MONTH);
        mes=calendar1.get(Calendar.MONTH);
        ano=calendar1.get(Calendar.YEAR);
        dat=dia+"-"+(mes+1)+"-"+ano;
        calendar.setText(dat);
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vi) {
                v=vi;
                DatePickerDialog pickerDialog=new DatePickerDialog(vi.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int yy, int mm, int dd) {
                        dia=dd;
                        mes=mm;
                        ano=yy;
                        String datt=dd+"-"+(mm+1)+"-"+yy;

                        if (!((datt).equals(calendar.getText().toString()))){
                            onChanged();
                            calendar.setText(datt);
                            listUpdate(datt);

                        }

                    }
                },ano,mes,dia);
                pickerDialog.show();
            }
        });
        listView= view.findViewById(R.id.assis);
        listUpdate(dat);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                changed=true;
                ImageView iView= view.findViewById(R.id.asis);
                int index=Integer.parseInt(((TextView)view.findViewById(R.id.asisint)).getText().toString());
                index =((index==5)?1:index+1);
                iView.setImageResource(var.nextImg((index)));
                ((TextView)view.findViewById(R.id.asisint)).setText(String.valueOf(index));
            }
        });

        return view;
    }

    public void onChanged(){
        if (changed){

            Thread th=new Thread(new Runnable() {
                @Override
                public void run() {
                    int img;
                    ViewGroup row;
                    Asistencia ast;
                    String cal=dat;

                    for(int i=0;i<listView.getAdapter().getCount();i++){
                        row=(ViewGroup) listView.getChildAt(i);
                        img=Integer.parseInt(((TextView)row.findViewById(R.id.asisint)).getText().toString());
                        ast=new Asistencia(cal,img);
                        var.estudiantes[i].getNotas(var.asignatura.getParcialActivo()).getAsistencia().addAsistencia(ast);
                    }
                }
            });
            th.start();

           dat=calendar.getText().toString();
            changed=false;}
    }
    public void onPause() {
        serializador.GuardarEnSd(var.gmaestro);
        onChanged();
        super.onPause();
    }

    @Override
    public void onStop() {
        serializador.GuardarEnSd(var.gmaestro);
        super.onStop();
    }

    public void listUpdate(final String datt){
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                asAdapter =new asistenciaAdapter(vi.getContext(),R.layout.list_lw_asistencias, var.estudiantes,datt);
                listView.setAdapter(asAdapter);
            }
        });
    }

}