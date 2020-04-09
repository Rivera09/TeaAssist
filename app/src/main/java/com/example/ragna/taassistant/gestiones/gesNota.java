package com.example.ragna.taassistant.gestiones;

import com.example.ragna.taassistant.clases.Nota;
import com.example.ragna.taassistant.var;

import java.io.Serializable;

/**
 *
 * Created by Ragna on 02/10/2018.
 */

public class gesNota implements Serializable {
    private Nota nota;
    private float recuperacion=-1;

    public void setNotas(Nota notas) {
    Nota aux=nota;
    if(aux==null){
        nota=notas;
        return;
    }
    while(aux!=null){
        if(aux.getNextNota()==null){
            aux.setNextNota(notas);
            return;
        }
        aux=aux.getNextNota();
    }
    }

    public Nota getNota(int index) {
        index--;
        if(index==0)return nota;
        Nota aux=nota;
        for(int i=0;i<index;i++){
            aux=aux.getNextNota();
        }
        return aux;
    }
    public Nota getNota() {
        return  nota;
    }

    public float notaFinal(){
        float notafial=0;
        Nota aux=nota;
        while(aux!=null){
            notafial+=aux.getNotaFinal();
            aux=aux.getNextNota();
        }
        return ((notafial !=0) ? (float) ((int)(((notafial / var.asignatura.getParcial())*100)))/100 : 0);
    }

    public float getRecuperacion() {
        return recuperacion;
    }

    public void setRecuperacion(float recuperacion) {
        this.recuperacion = (float)((int)(recuperacion*100))/100;
    }
}
