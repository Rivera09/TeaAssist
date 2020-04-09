package com.example.ragna.taassistant.gestiones;

import com.example.ragna.taassistant.clases.Maestro;

import java.io.Serializable;

/**
 * Created by Ragna on 27/09/2018.
 */

public class gestionMaestro implements Serializable {

    private Maestro maestro;
    private int counter=1;

    public void addMaestro(Maestro nmaestro){

        if(!this.maestro.getId().equals("-1")){
            return;
        }
        Maestro aux=maestro;
        while(aux!=null){
            if(aux.getNextMaestro()==null){
                aux.setNextMaestro(nmaestro);
                counter+=1;
                break;
            }
            aux = aux.getNextMaestro();
        }

    }

    public Maestro[] arrayMaest(){
        Maestro[] arrayMaestro=new Maestro[counter-1];
        Maestro aux=maestro.getNextMaestro();

        if(aux==null)return  arrayMaestro;
        for(int i=1;i<counter;i++){
            arrayMaestro[i-1]=aux;
            if(aux==null)return arrayMaestro;
            aux=aux.getNextMaestro();
        }
        return  arrayMaestro;
    }

    public Maestro BusMaestro(String id){
        Maestro aux=maestro;
        while (aux!=null){
            if(aux.getId().equals(id)){
                return aux;
            }
            aux=aux.getNextMaestro();
        }
        return null;
    }
    public Maestro getMaestros(){
    return maestro;
    }

    public gestionMaestro(){
        Maestro master=new Maestro("-1","Zew","Xcx",null,null);
        master.setCredential("Zew Xcx","MaximusVExtreme");
        maestro=master;
    }
}
