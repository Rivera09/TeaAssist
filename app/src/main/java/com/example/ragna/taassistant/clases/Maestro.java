package com.example.ragna.taassistant.clases;


import com.example.ragna.taassistant.gestiones.gestionAsignatura;

import java.io.Serializable;

public class Maestro extends Persona implements Serializable {
 private String user;
 private String pass;
 private gestionAsignatura gesAsignatura=new gestionAsignatura();
 private Maestro nextMaestro;

 public Maestro(String id,String n1,String n2,String n3,String n4){
     super(id,n1,n2,n3,n4);
 }
 public void setCredential(String user,String password){
   this.user=user;
   this.setPass(password);
 }
 
 public boolean changeCredential(String npass,String oldpass){
   if(oldpass.equals(getPass())){
     this.setPass(npass);
     return true;
   }
   return false;
 }

 public String getUser(){
     return user;
 }

 public boolean accesGrant(String user,String pass){
  if(this.user.equals(user)&& this.getPass().equals(pass)){
    return true;
  }
  return false;
 }
 
     public void setNextMaestro(Maestro maestro){
     this.nextMaestro=maestro;
 }
 
     public Maestro getNextMaestro(){
   return this.nextMaestro;
 }
 
    public void addAsignatura(Asignatura asignatura){
        gesAsignatura.addAsignatura(asignatura);
    }

    public gestionAsignatura getGesAsignatura() {
        return gesAsignatura;
    }

    public void setGesAsignatura(gestionAsignatura gesAsignatura) {
        this.gesAsignatura = gesAsignatura;
    }


    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
