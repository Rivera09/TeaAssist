package com.example.ragna.taassistant.clases;

import com.example.ragna.taassistant.gestiones.gesNota;

import java.io.Serializable;

public class Estudiante extends Persona implements Serializable {



    private Estudiante nextEstudiante;
    private gesNota notas;
    boolean fotoDeCamara;
    String nombreE;
    String emailE;
    String teleE;

    public String getNombreE() {
        return nombreE;
    }

    public String getEmailE() {
        return emailE;
    }

    public void setNombreE(String nombreE) {
        this.nombreE = nombreE;
    }

    public void setEmailE(String emailE) {
        this.emailE = emailE;
    }

    public void setTeleE(String teleE) {
        this.teleE = teleE;
    }

    public String getTeleE() {
        return teleE;

    }

    public void setFotoDeCamara(boolean fotoDeCamara) {
        this.fotoDeCamara = fotoDeCamara;
    }

    public boolean isFotoDeCamara() {
        return fotoDeCamara;
    }

    public Estudiante(String id, String n1, String n2, String n3, String n4, int index){
        super(id,n1,n2,n3,n4);
        setNota(index);
    }

    public void setNota(int index){
        notas=new gesNota();
        for(int i=0;i<index;i++){
            notas.setNotas(new Nota());
        }
    }
    public void setNextEstudiante(Estudiante estudiante){
        this.nextEstudiante=estudiante;
    }

    public Estudiante getNextEstudiante(){
   return this.nextEstudiante;
 }
    public gesNota getNota(){
        return this.notas;
    }
    public Nota getNotas(int index){
       return notas.getNota(index);
    }



}
