package com.example.ragna.taassistant;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/***
 * Author Cyborg
 */
public class Serializador {
    //private final String ruta = Environment.getExternalStorageDirectory().getPath() + "//Android//data//TeaAsssist";
    public String GuardarEnSd(Object objeto){
        String mensaje = "Guardado correctamente";
        File datos = Environment.getExternalStorageDirectory();
        try {
            //String ruta_Final = ruta+"//"+nombre+".obb";
            datos = new File(datos.getPath(),"Android/data/com.jjsd.teaassist/TeaAssist.dat");
            if(!Existe()){
                datos.createNewFile();
            }
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(datos));
            objectOutputStream.writeObject(objeto);
            objectOutputStream.close();
        }catch (Exception e){
            mensaje="Error al guardar en sd: "+e.getMessage();
        }
        return mensaje;
    }
    public boolean Existe(){
        File datos = Environment.getExternalStorageDirectory();
        //String ruta_Final = ruta+"//"+nombre+".obb";
        datos = new File(datos.getPath(),"Android/data/com.jjsd.teaassist/TeaAssist.dat");
        if(datos.exists()){
            return true;
        }
        return false;
    }
    public Object Recuperar(){
        File datos =Environment.getExternalStorageDirectory();
        Object object = null;
        //String ruta_Final = ruta+"//"+nombre+".obb";
        try {
            datos = new File(datos.getPath(),"Android/data/com.jjsd.teaassist/TeaAssist.dat");
            ObjectInput objectInput = new ObjectInputStream(new FileInputStream(datos));
            object = objectInput.readObject();
            objectInput.close();
        }catch (Exception e){

        }
        return  object;
    }
}
