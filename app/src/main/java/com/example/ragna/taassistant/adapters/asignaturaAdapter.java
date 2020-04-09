package com.example.ragna.taassistant.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ragna.taassistant.R;
import com.example.ragna.taassistant.clases.Asignatura;

/**
 * Created by Ragna on 27/09/2018.
 */

public class asignaturaAdapter extends ArrayAdapter<Asignatura> {
    Context context;
    int Rlayout;
    Asignatura[] asignaturas=null;

    public asignaturaAdapter(@NonNull Context context, int resource, Asignatura[] asignaturas) {
        super(context, resource,asignaturas);
        this.asignaturas=asignaturas;
        this.context=context;
        this.Rlayout=resource;
    }

    public View getView(int pos, View contentView, ViewGroup padre){
        View Rl=contentView;
        AsigHolder asigHolder;
        if(Rl==null){
            LayoutInflater layoutInflater=((Activity)context).getLayoutInflater();
            Rl=layoutInflater.inflate(Rlayout,padre,false);
            asigHolder=new AsigHolder();
            asigHolder.name= Rl.findViewById(R.id.nameasig);
            asigHolder.parcial= Rl.findViewById(R.id.parcial);
            asigHolder.alumnos= Rl.findViewById(R.id.alumnos);
            asigHolder.tipoEva= Rl.findViewById(R.id.tipoeva);
            asigHolder.id= Rl.findViewById(R.id.ccid);

            Rl.setTag(asigHolder);
        }else{
            asigHolder=(AsigHolder)Rl.getTag();
        }
        Asignatura asg=asignaturas[pos];

        asigHolder.name.setText(asg.getName());
        asigHolder.id.setText(asg.getId());
        asigHolder.id.setVisibility(View.INVISIBLE);
        asigHolder.tipoEva.setText(String.valueOf(asg.getTipoEvaluacion()));
        asigHolder.parcial.setText(String.valueOf(asg.getParcial()));
        asigHolder.alumnos.setText((asg.getEstcount()==0?"No hay Alumnos ":("Alumnos Matriculados "+String.valueOf(asg.getEstcount()))));

        return Rl;
    }



    static class AsigHolder{
        TextView name,alumnos,parcial,tipoEva,id;
    }
}
