package com.example.ragna.taassistant.clases;

import com.example.ragna.taassistant.gestiones.gestionEstudiante;

import java.io.Serializable;
import java.util.Date;

public class Asignatura implements Serializable {

  private int tipoEvaluacion=0;
  private int parcial=0;
  private int parcialActivo=1;
  private String name;
  //private String drescripcion;
  private String id;
  private int aprobIndice=70;
  private boolean activa=true;
  private Asignatura nextAsignatura;
  private Date fechaDeInicio;
  private Date getFechaDeFinalizacion;
  private gestionEstudiante gesEstudiante=new gestionEstudiante();

  public Asignatura(String name){
    this.name=toUpper(name);
  }
  public void addEstudiante(Estudiante estudiante){
    this.gesEstudiante.addEstudiante(estudiante);
  }
  public int getParcial() {
        return parcial;
  }
  public void setParcial(int parcial) {
        this.parcial = parcial;
  }
  public int getTipoEvaluacion() {
    return tipoEvaluacion;
  }

  public void setTipoEvaluacion(int tipoEvaluacion) {
    this.tipoEvaluacion = tipoEvaluacion;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /*public String getDrescripcion() {
    return drescripcion;
  }*/

  /*public void setDrescripcion(String drescripcion) {
    this.drescripcion = drescripcion;
  }*/

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public boolean isActiva() {
    return activa;
  }

  public void setActiva(boolean activa) {
    this.activa = activa;
  }

  public Asignatura getNextAsignatura() {
    return nextAsignatura;
  }

  public void setNextAsignatura(Asignatura nextAsignatura) {
    this.nextAsignatura = nextAsignatura;
  }

  public Date getFechaDeInicio() {
    return fechaDeInicio;
  }

  public void setFechaDeInicio(Date fechaDeInicio) {
    this.fechaDeInicio = fechaDeInicio;
  }

  public Date getGetFechaDeFinalizacion() {
    return getFechaDeFinalizacion;
  }

  public void setGetFechaDeFinalizacion(Date getFechaDeFinalizacion) {
    this.getFechaDeFinalizacion = getFechaDeFinalizacion;
  }

  public gestionEstudiante getGesEstudiante() {
    return gesEstudiante;
  }

  public void setGesEstudiante(gestionEstudiante gesEstudiante) {
    this.gesEstudiante = gesEstudiante;
  }

  public int getEstcount(){
    return gesEstudiante.getCount();
  }

  public String toUpper(String s){
    String a=s;
    String a1=String.valueOf(a.charAt(0));
    a=a.substring(1,a.length());
    a1=a1.toUpperCase();
    a=a1+a;
    return a;
  }

    public int getParcialActivo() {
        return parcialActivo;
    }

    public void setParcialActivo() {
      if(parcialActivo<parcial){
      this.parcialActivo++;}
    }

    public int getAprobIndice() {
        return aprobIndice;
    }

    public void setAprobIndice(int aprobIndice) {
        this.aprobIndice = aprobIndice;
    }
}
