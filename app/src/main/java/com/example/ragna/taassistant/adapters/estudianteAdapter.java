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
import com.example.ragna.taassistant.clases.Estudiante;
import com.example.ragna.taassistant.var;
import com.squareup.picasso.Picasso;

import java.io.IOException;


/**
 * Created by Ragna on 28/09/2018.
 */

public class estudianteAdapter extends ArrayAdapter<Estudiante>{
    Context context;
    int Rlayout;
    Estudiante[] estudiante=null;

    public estudianteAdapter(@NonNull Context context, int resource, Estudiante[] estudiante) {
        super(context, resource,estudiante);
        this.estudiante=estudiante;
        this.context=context;
        this.Rlayout=resource;
    }

    public View getView(int pos, View contentView, ViewGroup padre){
        View Rl=contentView;
        EstudHolder estudHolder;
        if(Rl==null){
            LayoutInflater layoutInflater=((Activity)context).getLayoutInflater();
            Rl=layoutInflater.inflate(Rlayout,padre,false);
            estudHolder=new EstudHolder();
            estudHolder.imagen= Rl.findViewById(R.id.eimg);
            estudHolder.nombre= Rl.findViewById(R.id.enombre);
            estudHolder.faltas= Rl.findViewById(R.id.faltas);
            Rl.setTag(estudHolder);
        }else{
            estudHolder=(EstudHolder) Rl.getTag();
        }
        Estudiante std=estudiante[pos];
        try {
            estudHolder.nombre.setText(std.toString());
            int faltas=std.getNota().getNota(var.chose).getAsistencia().getFaltas();
            estudHolder.faltas.setText(faltas==-1?"No se ha registrado asistencia":"Faltas del parcial "+faltas);
            if(std.getFoto() == null) {
                estudHolder.imagen.setImageResource(R.drawable.ic_user_placeholder);
            }else {
                Picasso.with(getContext()).load(std.getFoto()).resize(200,200).centerInside().into(estudHolder.imagen);
            }
        }catch (Exception e){
            System.out.println(e+"  este " );
        }


        return Rl;
    }
    static class EstudHolder{
        TextView nombre,id,faltas;
        ImageView imagen;
    }
}