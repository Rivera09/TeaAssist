package com.example.ragna.taassistant.gestiones;


import com.example.ragna.taassistant.clases.Asistencia;

import java.io.Serializable;
import java.util.Date;

import fechas.Fechas;

/**
 * Created by Ragna on 28/09/2018.
 */

public class gesAsistencia implements Serializable {
    private Asistencia asistencia;
    private Asistencia ultimoDia;

    public void addAsistencia(Asistencia asist){
        if(this.asistencia==null){
            System.out.println("pirmer asis");
            this.asistencia=asist;
            this.ultimoDia=asist;
            return;
        }
        if (asist.getAsistenciaDia().compareTo(ultimoDia.getAsistenciaDia())==0){
            ultimoDia.setAsistio(asist.getAsistio());
            //System.out.println("ltimo deia");
            return;
        }
        Asistencia aux=asistencia;
        while(aux!=null){
            if (asist.getAsistenciaDia().compareTo(aux.getAsistenciaDia())==0){

                aux.setAsistio(asist.getAsistio());
                return;
            }
            //System.out.println("3rd ddd");
            if(aux.getNextDay()==null){
                aux.setNextDay(asist);
                ultimoDia=asist;
                return;
            }
            aux=aux.getNextDay();
        }


    }

    public int getFaltas (){
        Asistencia aux= asistencia;
        if (aux==null)return -1;
        int faltas=0;
        while(aux!=null){
            if(aux.getAsistio()==3){
                faltas++;
            }
            aux=aux.getNextDay();
        }
        return faltas;
    }

    public Asistencia getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(Asistencia asistencia) {
        this.asistencia = asistencia;
    }

    public Asistencia getUltimoDia() {
        return ultimoDia;
    }

    public void setUltimoDia(Asistencia asist) {
        this.ultimoDia = ultimoDia;
    }

    public Asistencia getToday(String date){

        if(asistencia==null){
           ;return asistencia;}
        Asistencia aux=asistencia;
        Date d= Fechas.stringToDate(date);
        int cc=0;
        while(aux!=null){
            if(aux.getAsistenciaDia().compareTo(d)==0){
               return aux;}
            aux=aux.getNextDay();
            cc++;
            if(cc>10)return null;
        }
        return null;
    }
}
