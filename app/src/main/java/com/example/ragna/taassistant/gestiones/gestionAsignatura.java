package com.example.ragna.taassistant.gestiones;

import com.example.ragna.taassistant.clases.Asignatura;

import java.io.Serializable;

/**
 * Created by Ragna on 27/09/2018.
 */

public class gestionAsignatura implements Serializable {

    private Asignatura asignatura;
    private int counter=0;

    public void addAsignatura(Asignatura asignatura){
        counter++;
        asignatura.setId(String.valueOf(counter));
        if(this.asignatura==null){
            this.asignatura=asignatura;
            return;
        }
      
        Asignatura aux=this.asignatura,aux2=aux;
        while(aux!=null){
            if(sortAsing(aux.getName(),asignatura.getName())){
                if(aux.getNextAsignatura()==null){
                    aux.setNextAsignatura(asignatura);
                    break;
                }
                aux2=aux;
                aux=aux.getNextAsignatura();
            }else{
                if(aux2==aux){
                    asignatura.setNextAsignatura(aux);
                    this.asignatura=asignatura;
                    break;
                }
                asignatura.setNextAsignatura(aux);
                aux2.setNextAsignatura(asignatura);
                break;
            }

        }

    }

    public Asignatura asignaturaPorNombre(String nombre){
        Asignatura temp = asignatura;
        while (temp!=null){
            if(temp.getName().equals(nombre)){
                return temp;
            }else {
                temp = temp.getNextAsignatura();
            }
        }
        return null;
    }

    public Asignatura BusAsg(String id){
        Asignatura aux=asignatura;
        while(aux!=null){
            if(aux.getId().equals(id)){
                return aux;
            }
            aux=aux.getNextAsignatura();
        }
        return aux;
    }
    public Asignatura[] asignaturas(){
        Asignatura[] arrayaAsignaturas=new Asignatura[counter];
        Asignatura aux=asignatura;

        for(int i=0;i<counter;i++){
            arrayaAsignaturas[i]=aux;
            aux=aux.getNextAsignatura();
        }
        return  arrayaAsignaturas;
    }

    private boolean sortAsing(String a1,String a2){
        int legth;
        a1=a1.toLowerCase();
        a2=a2.toLowerCase();
        if(a1.length()>a2.length()){
            legth=a2.length();
        }else{
            legth=a1.length();
        }
        for(int i=0;i<legth;i++){
            if(a1.charAt(i)<a2.charAt(i)){
                return true;
            }else if(a1.charAt(i)>a2.charAt(i)){
                return false;
            }

        }
        return !(a1.length()>a2.length());
    }

    public int getCounter() {
        return counter;
    }
}
