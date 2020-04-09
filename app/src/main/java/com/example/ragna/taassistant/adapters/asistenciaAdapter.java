package com.example.ragna.taassistant.adapters;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.ragna.taassistant.R;
import com.example.ragna.taassistant.clases.Asistencia;
import com.example.ragna.taassistant.clases.Estudiante;
import com.example.ragna.taassistant.var;

/**
 * Created by Ragna on 02/10/2018.
 */

public class asistenciaAdapter extends ArrayAdapter<Estudiante>{

    Context context;
    int Rlayout ;
    Estudiante[] estudiante=null;
    String date;

    public asistenciaAdapter(@NonNull Context context, int resource, Estudiante[] estudiante,String date) {
        super(context, resource,estudiante);
        this.estudiante=estudiante;
        this.context=context;
        this.Rlayout=resource;
        this.date=date;
    }

    public View getView(int pos, View contentView, ViewGroup padre){
        View Rl=contentView;
        EstudHolder estudHolder;
        if(Rl==null){
            LayoutInflater layoutInflater=((Activity)context).getLayoutInflater();
            Rl=layoutInflater.inflate(Rlayout,padre,false);
            estudHolder=new EstudHolder();
            estudHolder.imagen= Rl.findViewById(R.id.asis);
            estudHolder.nombre= Rl.findViewById(R.id.nameasis);
            estudHolder.id= Rl.findViewById(R.id.asisint);

            Rl.setTag(estudHolder);
        }else{
            estudHolder=(EstudHolder) Rl.getTag();
        }
        Estudiante std=estudiante[pos];
        try {
            Asistencia ast=std.getNotas(var.chose).getAsistencia().getToday(date);
            int index=ast==null?1:ast.getAsistio();
            estudHolder.nombre.setText(std.toString());
            estudHolder.imagen.setImageResource(var.nextImg(index));
            estudHolder.id.setText(String.valueOf(index));
            estudHolder.id.setVisibility(View.INVISIBLE);
        }catch (Exception e){
            System.out.println("errro "+e);
        }

        return Rl;
    }



    static class EstudHolder{
        TextView nombre,id;
        ImageView imagen;
    }
}