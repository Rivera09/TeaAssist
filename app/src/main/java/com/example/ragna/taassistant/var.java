package com.example.ragna.taassistant;


import android.widget.TableRow;

import com.example.ragna.taassistant.clases.*;
import com.example.ragna.taassistant.gestiones.gestionMaestro;

import java.util.ArrayList;

/**
 * Created by Ragna on 24/09/2018.
 */

public class var {
   public static gestionMaestro gmaestro;
   public static Maestro maestro;
   public static Asignatura asignatura;
   public static Estudiante[] estudiantes;
   public static int chose;

   public static int nextImg(int index){
      switch (index){
         case 2:{
            return R.mipmap.checkbox2;
         }
         case 3:{
            return R.mipmap.checkbox3;
         }
         case 4:{
            return R.mipmap.checkbox4;
         }
         case 5:{
            return R.mipmap.checkbox5;
         }
         default: return R.mipmap.checkbox1;
      }
   }
}
