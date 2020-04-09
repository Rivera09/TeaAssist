package com.example.ragna.taassistant.clases;


import java.io.Serializable;
import java.util.Date;

import fechas.Fechas;

/**
 * Created by Ragna on 28/09/2018.
 */

public class Asistencia implements Serializable {
    private final Date asistenciaDia;
    private int asistio=-1;
    private Asistencia nextDay;

    public Asistencia(String date,int image){
        this.asistenciaDia= Fechas.stringToDate(date);
        this.asistio=image;
    }

    public Date getAsistenciaDia() {
        return asistenciaDia;
    }

    public int getAsistio() {
        return asistio;
    }

    public void setAsistio(int asistio) {
        this.asistio = asistio;
    }

    public Asistencia getNextDay() {
        return nextDay;
    }

    public void setNextDay(Asistencia nextDay) {
        this.nextDay = nextDay;
    }
}
