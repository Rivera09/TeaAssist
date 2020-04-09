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
import com.example.ragna.taassistant.clases.Maestro;

/**
 * Created by Ragna on 27/09/2018.
 */

public class mastAdapter extends ArrayAdapter<Maestro> {
    Context context;
    int Rlayout;
    Maestro[] m=null;

    public mastAdapter(@NonNull Context context, int resource, Maestro[] m) {
        super(context, resource,m);
        this.m=m;
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
            asigHolder.tipoEva= Rl.findViewById(R.id.tipoeva);
            asigHolder.id= Rl.findViewById(R.id.ccid);

            Rl.setTag(asigHolder);
        }else{
            asigHolder=(AsigHolder)Rl.getTag();
        }
        Maestro asg=m[pos];
        if(asg!=null) {
            asigHolder.name.setText(asg.toString());
            asigHolder.id.setText(asg.getId());
            asigHolder.id.setVisibility(View.INVISIBLE);
            asigHolder.tipoEva.setText(String.valueOf(asg.getUser()));
            asigHolder.parcial.setText(String.valueOf(asg.getPass()));
        }
        return Rl;
    }



    static class AsigHolder{
        TextView name,parcial,tipoEva,id;
    }
}
