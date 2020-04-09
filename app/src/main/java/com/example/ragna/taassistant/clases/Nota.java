package com.example.ragna.taassistant.clases;

import com.example.ragna.taassistant.gestiones.gesAsistencia;

import java.io.Serializable;

/**
 * Created by Ragna on 27/09/2018.
 */

public class Nota implements Serializable {

    private float tareas=-1;
    private float acumulativo=-1;
    private float examen=-1;
    private float asistenciaN=-1;
    private float otros=-1;

    private boolean active;
    private gesAsistencia asistencia=new gesAsistencia();
    private Nota nextNota;

    public Nota getNextNota() {
        return this.nextNota;
    }
    public float getNotaFinal(){
        float notaFial=0;
        if(tareas!=-1){ notaFial+=tareas;}
        if(acumulativo!=-1){notaFial+=acumulativo;}
        if(examen!=-1){notaFial+=examen;}
        if(asistenciaN!=-1){notaFial+=asistenciaN;}
        if(otros!=-1){notaFial+=otros;}
        return notaFial;
    }

    public void setNextNota(Nota notas) {
        this.nextNota=notas;
    }

    public float getTareas() {
        return tareas;
    }

    public void setTareas(float tareas) {
        this.tareas = (float)((int)(tareas*100))/100;
    }

    public float getAcumulativo() {
        return acumulativo;
    }

    public void setAcumulativo(float acumulatico) {
        this.acumulativo = (float)((int)(acumulatico*100))/100;;
    }

    public float getExamen() {
        return examen;
    }

    public void setExamen(float examen) {
        this.examen = (float)((int)(examen*100))/100;;
    }

    public float getOtros() {
        return otros;
    }

    public void setOtros(float otros) {
        this.otros = (float)((int)(otros*100))/100;;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public gesAsistencia getAsistencia() {
        return asistencia;
}

    public void setAsistencia(gesAsistencia asistencia) {
        this.asistencia = asistencia;
    }

    public float getAsistenciaN() {
        return asistenciaN;
    }

    public void setAsistenciaN(float asistenciaN) {
        this.asistenciaN = asistenciaN;
    }

}
