package com.example.ragna.taassistant.gestiones;

import android.view.View;
import android.widget.Toast;

import com.example.ragna.taassistant.R;
import com.example.ragna.taassistant.clases.Asistencia;
import com.example.ragna.taassistant.clases.Estudiante;
import com.example.ragna.taassistant.clases.Nota;
import com.example.ragna.taassistant.var;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import fechas.Fechas;

/**
 * Created by Ragna on 27/09/2018.
 */

public class gestionEstudiante implements Serializable  {
    private Estudiante estudiante;
    private int count=0;


    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void addEstudiante(Estudiante estudiante){
        count++;
        if(this.estudiante==null){
            this.estudiante=estudiante;
            return;
        }
        Estudiante aux =this.estudiante,aux2=aux;

        while (aux != null) {
            String a=aux.getN1(),b=estudiante.getN1();
            if(a.equals(b)){
                a=aux.getN2();b=estudiante.getN2();
                if(a.equals(b)){
                    a=aux.getN3();b=estudiante.getN3();
                    if(a.equals(b)){
                        a=aux.getN4();b=aux2.getN4();
                    }
                }
            }
            if(sortEstudiante(a,b)){
                if(aux.getNextEstudiante()==null){
                    aux.setNextEstudiante(estudiante);
                    break;
                }
                aux2=aux;
                aux=aux.getNextEstudiante();
            }else{
                if(aux2==aux){
                    estudiante.setNextEstudiante(aux);
                    this.estudiante=estudiante;
                    break;
                }
                estudiante.setNextEstudiante(aux);
                aux2.setNextEstudiante(estudiante);
                break;
            }
        }
    }

    public int getCount(){
        return count;
    }

    public Estudiante BusEstudianteId(String id){
        Estudiante aux=estudiante;
        while(aux!=null){
            if(aux.getId().equals(id)){
                return aux;
            }
            aux=aux.getNextEstudiante();
        }
        return null;
    }

    public Estudiante[] getEstudiantes(){
        Estudiante[] arrayaEstudiants=new Estudiante[count];
        Estudiante aux=estudiante;

        for(int i=0;i<count;i++){
            arrayaEstudiants[i]=aux;
            aux=aux.getNextEstudiante();
        }
        return  arrayaEstudiants;
    }
    private boolean sortEstudiante(String a1,String a2){
        int legth;
        if(a1==a2)return true;
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

    public boolean delEst(String id){
        if(estudiante==null)return false;
        if(estudiante.getId().equals(id)){
            count--;
            estudiante=estudiante.getNextEstudiante();
            return true;
        }
        if(estudiante.getNextEstudiante()==null)return false;

        Estudiante aux=estudiante;
        while(aux!=null){
            count--;
            if(aux.getNextEstudiante().getId().equals(id)){
                aux.setNextEstudiante(aux.getNextEstudiante().getNextEstudiante());
                return  true;
            }
            aux=aux.getNextEstudiante();
        }
        return false;
    }

    public Estudiante BusEst(String id){
        Estudiante aux=estudiante;
        while(aux!=null){
        if(id.equals(aux.getId())){return aux;}
        aux=aux.getNextEstudiante();
        }
        return null;
    }
    public Estudiante[] filtrarEstud(String filtro){
        int c=0;
        if(filtro.equals(""))return getEstudiantes();
        Estudiante aux=estudiante;
        while(aux!=null){
            if(aux.toString().toLowerCase().contains(filtro.toLowerCase())){
              c++;
            }
            aux=aux.getNextEstudiante();
        }
        Estudiante[] temp=new Estudiante[c];
        int s=0;
        if(c==0)return temp;
        //System.out.println("help "+c);
        aux=estudiante;
        while(aux!=null){
           // System.out.println("kkkkkkkkasd asdas ad");
            if(aux.toString().toLowerCase().contains(filtro.toLowerCase())){
                temp[s]=aux;
                s++;
            }
            aux=aux.getNextEstudiante();
            if(s==c)return temp;
        }
        return temp;
    }

    public ArrayList<ArrayList<String>> getEstudiantesArNot() {
        ArrayList<ArrayList<String>> datos=new ArrayList<ArrayList<String>>();
            ArrayList<String> innerList=new ArrayList<String>();
        Estudiante aux=estudiante;
        Nota not;
        float nota;
        innerList.add("Fecha "+Fechas.getFechaActualStringDiaMesAno(Fechas.getFechaActual()));
        innerList.add("");
        innerList.add("");
        innerList.add("");
        innerList.add("");
        innerList.add(var.maestro.toString());
        innerList.add("");
        innerList.add("");
        innerList.add("");
        innerList.add("");
        innerList.add("Asignatura "+var.asignatura.getName());
        datos.add(innerList);
        //header
        innerList=new ArrayList<String>();
        innerList.add("Nombre");
        for(int i=0;i<var.asignatura.getParcial();i++){
            innerList.add("Acumlativo");
            innerList.add("Asistencia");
            innerList.add("Tareas");
            innerList.add("Otros");
            innerList.add("Examen");
            innerList.add("Nota Parcial "+(i+1));
        }
        innerList.add("Nota Final");
        innerList.add("Recuperacion");
        innerList.add("Observacion");
        datos.add(innerList);

        while(aux!=null){
            innerList=new ArrayList<String>();
            not=aux.getNota().getNota();
            innerList.add(aux.toString());
            while (not!=null){
                innerList.add(not.getAcumulativo()==-1?"":String.valueOf(not.getAcumulativo()));
                innerList.add(not.getAsistenciaN()==-1?"":String.valueOf(not.getAsistenciaN()));
                innerList.add(not.getTareas()==-1?"":String.valueOf(not.getTareas()));
                innerList.add(not.getOtros()==-1?"":String.valueOf(not.getOtros()));
                innerList.add(not.getExamen()==-1?"":String.valueOf(not.getExamen()));
                innerList.add(not.getNotaFinal()==-1?"":String.valueOf(not.getNotaFinal()));
                not=not.getNextNota();
            }
            nota=aux.getNota().notaFinal();
            innerList.add(nota==-1?"":String.valueOf(nota));
            innerList.add(aux.getNota().getRecuperacion()==-1?"":String.valueOf(aux.getNota().getRecuperacion()));
            if(aux.getNota().getRecuperacion()>nota)nota=aux.getNota().getRecuperacion();
            innerList.add((nota>=(float)var.asignatura.getAprobIndice())?"APRB":"RPRB");
            datos.add(innerList);
            aux=aux.getNextEstudiante();
        }


    return datos;
    }

    public ArrayList<ArrayList<String>> getEstudiantesAsis(){
        ArrayList<String> head=new ArrayList<>();
        ArrayList<String> headb=new ArrayList<>();
        ArrayList<ArrayList<String>>data=new ArrayList<>();
        ArrayList <String> innerList=new ArrayList<>();
        Estudiante aux=estudiante;
        Asistencia asis=estudiante.getNota().getNota(var.chose).getAsistencia().getAsistencia();
        head.add(" ");
        headb.add("Nombre");
        innerList.add(aux.toString());
        while(asis!=null){
            head.add(getFechaActualStringDiaF(asis.getAsistenciaDia()));
            headb.add(getFechaActualStringDia(asis.getAsistenciaDia()));
            innerList.add(asis.getAsistio()==1?" ":
                    (asis.getAsistio()==2?"A":
                            (asis.getAsistio()==3?"F":
                                    (asis.getAsistio()==4?"R":"E")))
            );
            asis=asis.getNextDay();
        }
        data.add(head);
        data.add(headb);
        data.add(innerList);
        aux=aux.getNextEstudiante();
        while(aux!=null){
            innerList=new ArrayList<>();
            asis=aux.getNota().getNota(var.chose).getAsistencia().getAsistencia();
            while(asis!=null){
                innerList.add(asis.getAsistio()==1?" ":
                        (asis.getAsistio()==2?"A":
                                (asis.getAsistio()==3?"F":
                                        (asis.getAsistio()==4?"R":"E"))));
                asis=asis.getNextDay();
            }
            data.add(innerList);
            aux=aux.getNextEstudiante();
        }
        return data;
    }
    public  String getFechaActualStringDiaF(Date fecha) {
        SimpleDateFormat format2 = new SimpleDateFormat("dd");
        return format2.format(fecha);
    }
    public  String getFechaActualStringDia(Date fecha) {
        SimpleDateFormat format2 = new SimpleDateFormat("EEE");
        return format2.format(fecha);
    }

}

