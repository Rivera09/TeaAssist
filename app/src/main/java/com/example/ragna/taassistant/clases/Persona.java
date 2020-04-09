package com.example.ragna.taassistant.clases;

import java.io.Serializable;
import java.util.Date;
import java.io.File;

public class Persona implements Serializable {

  private String id;
  private String n1;
  private String n2;
  private String n3;
  private String n4;
  private File foto;
  private Date fechaNacimiento;
  private String dir;
  private String numeroContacto;
  private int image;
  String correo;
  private int genero;//0=masculino , 1=femenino
  
  
  public Persona(String id,String n1,String n2,String n3,String n4){
   this.id=id;
   this.n1=toUpper(n1);
   this.n2=toUpper(n2);
   this.n3=toUpper(n3);
   this.n4=toUpper(n4);
  }


  
  public String toString(){
    String name="";
    if(n1!=null)name=n1;
    if(n2!=null)name+=(" "+n2);
    if(n3!=null)name+=(" "+n3);
    if(n4!=null)name+=(" "+n4);
    return name;

  }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCorreo() {
        return correo;
    }

    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getN1() {
    return n1;
  }

  public void setN1(String n1) {
    this.n1 = n1;
  }

  public String getN2() {
    return n2;
  }

  public void setN2(String n2) {
    this.n2 = n2;
  }

  public String getN3() {
    return n3;
  }

  public void setN3(String n3) {
    this.n3 = n3;
  }

  public String getN4() {
    return n4;
  }

  public void setN4(String n4) {
    this.n4 = n4;
  }

  public Date getFechaNacimiento() {
    return fechaNacimiento;
  }

  public void setFechaNacimiento(Date fechaNacimiento) {
    this.fechaNacimiento = fechaNacimiento;
  }

  public String getDir() {
    return dir;
  }

  public void setDir(String dir) {
    this.dir = dir;
  }

  public String getNumeroContacto() {
    return numeroContacto;
  }

  public void setNumeroContacto(String numeroContacto) {
    this.numeroContacto = numeroContacto;
  }

  public int getImage() {
    return image;
  }

  public void setImage(int image) {
    this.image = image;
  }
  public String toUpper(String s){

    if(s==null)return "";
    if(s.equals("")|s.equals(" "))return "";
    String a=s;
    String a1=String.valueOf(a.charAt(0));
    a=a.substring(1,a.length());
    a1=a1.toUpperCase();
    a=a1+a;
    return a;
  }
  public void setFoto(File foto) {
    this.foto = foto;
  }

  public File getFoto() {
    return foto;
  }
  public int isGenero() {
    return genero;
  }

  public void setGenero(int genero) {
    this.genero = genero;
  }

  public int getGenero() {
    return genero;
  }

  public String getNombres(){
    String name="";
    if(n1!=null)name=n1;
    if(n2!=null)name+=(" "+n2);
    return name;
  }public String getApellidos(){
    String name="";
    if(n3!=null)name+=(n3);
    if(n4!=null)name+=(" "+n4);
    return name;
  }
}
