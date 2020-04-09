package com.example.ragna.taassistant.adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ragna.taassistant.clases.Estudiante;
import com.example.ragna.taassistant.clases.Nota;
import com.example.ragna.taassistant.var;


import java.util.ArrayList;

/**
 *
 * Created by Ragna on 05/10/2018.
 */

public class notasTableAdapter {

    private Context context;
    private TableLayout tableLayout;
    private String[] header;
    private int parcial;
    private Estudiante[] data;
    private TableRow row;
    private TextView cell;
    private EditText celedit;
    private int input;
    private TextView head;
    private ArrayList<TableRow> rows=new ArrayList<>();
    private TableRow.LayoutParams params;
    private boolean changed=false;

    public notasTableAdapter(View view, TableLayout tableLayout, int parcial, Estudiante[] estudiantes,String[] header, int input){
        this.context=view.getContext();
        this.tableLayout=tableLayout;
        this.parcial=parcial;
        this.data=estudiantes;
        this.input=input;
        params = newPrams();
        addHeader(header);
        createDataT();

    }


    private void addHeader(String[] header){
        this.header=header;
        createHeder();

    }
    /*
    public void addData(ArrayList<String[]> data){
        this.data=data;
        createDataT();

    }*/
    private void newRow(){
        row=new TableRow(context);
        rows.add(row);

    }
    private void newCell(){
        cell= new TextView(context);
        cell.setGravity(Gravity.CENTER);
        cell.setTextSize(20);
    }
    private void newCellE(){
        celedit=new EditText(context);
        celedit.setGravity(Gravity.CENTER);
        celedit.setInputType(input);
        celedit.setTextSize(20);
        celedit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    if(changed){
                        update();
                    }
                }
            }
        });
        celedit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                changed=true;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    private void newCellHead(){
        head=new TextView(context);
        head.setGravity(Gravity.CENTER);
        head.setTextSize(20);
    }
    private void createHeder(){
        int indexCol = 0;
        newRow();
        while(indexCol <header.length) {
            newCellHead();
            head.setText(header[indexCol++]);
            row.addView(head,params);
        }
        tableLayout.addView(row);
    }

    private void createDataT(){

        int indexR;
        for(indexR =1; indexR <=data.length; indexR++){
            newRow();
            Estudiante std=data[indexR -1];
            //System.out.println("registrando "+std.toString());
            newCell();
            cell.setText(std.toString());
            cell.setTextSize(16);
            cell.setGravity(Gravity.START);
            row.addView(cell,params);
            Nota nota=std.getNotas(parcial);
            newCellE();//celedit.setGravity(Gravity.RIGHT);
            celedit.setText(nota.getAcumulativo()==-1?"":String.valueOf(nota.getAcumulativo()));
            row.addView(celedit,params);
            newCellE();//celedit.setGravity(Gravity.RIGHT);
            celedit.setText(nota.getAsistenciaN()==-1?"":String.valueOf(nota.getAsistenciaN()));
            row.addView(celedit,params);
            newCellE();//celedit.setGravity(Gravity.RIGHT);
            celedit.setText(nota.getTareas()==-1?"":String.valueOf(nota.getTareas()));
            row.addView(celedit,params);
            newCellE();//celedit.setGravity(Gravity.RIGHT);
            celedit.setText(nota.getOtros()==-1?"":String.valueOf(nota.getOtros()));
            row.addView(celedit,params);
            newCellE();//celedit.setGravity(Gravity.RIGHT);
            celedit.setText(nota.getExamen()==-1?"":String.valueOf(nota.getExamen()));

            row.addView(celedit,params);
            float not=nota.getNotaFinal();
            float notr=std.getNota().getRecuperacion();
            newCell();//cell.setGravity(Gravity.RIGHT);
            cell.setText(String.valueOf(not));
            if(not<var.asignatura.getAprobIndice())cell.setTextColor(Color.RED);
            if(not>var.asignatura.getAprobIndice())cell.setTextColor(Color.GREEN);
            row.addView(cell,params);
            newCell();//cell.setGravity(Gravity.RIGHT);
            not=std.getNota().notaFinal();
            cell.setText(String.valueOf(not));
            if(not<var.asignatura.getAprobIndice())cell.setTextColor(Color.RED);
            if(not>var.asignatura.getAprobIndice())cell.setTextColor(Color.GREEN);
            row.addView(cell,params);

            newCellE();//celedit.setGravity(Gravity.RIGHT);
            celedit.setText((std.getNota().getRecuperacion())==-1?"":String.valueOf(std.getNota().getRecuperacion()));
            if(var.asignatura.getParcialActivo()!=var.asignatura.getParcial())
                celedit.setEnabled(false);
            row.addView(celedit,params);

            newCell();//cell.setGravity(Gravity.RIGHT);
            not=std.getNota().notaFinal();

            if(notr>not)not=notr;
            cell.setText(not<var.asignatura.getAprobIndice()?"RPRB":"APRB");
            if(not<var.asignatura.getAprobIndice())cell.setTextColor(Color.RED);
            if(not>var.asignatura.getAprobIndice()+5)cell.setTextColor(Color.GREEN);
            row.addView(cell,params);
            tableLayout.addView(row);
        }
    }

    private TableRow.LayoutParams newPrams(){
        TableRow.LayoutParams params=new TableRow.LayoutParams();
        params.setMargins(11,1,11,1);
        params.weight=1;
        return params;
    }
    public void onChanged(){
        if (changed){
        Thread th=new Thread(new Runnable() {
            @Override
            public void run() {
                Nota not;
                String n;
                for(int i=1;i<rows.size();i++){
                    if(check(i,data[i-1])){
                         not=data[i-1].getNotas(parcial);
                        //Toast.makeText(context,data[i-1].toString(),Toast.LENGTH_SHORT).show();
                        n=((EditText)rows.get(i).getChildAt(1)).getText().toString();
                        not.setAcumulativo(Float.parseFloat(n.equals("")?"-1":n));
                        n=((EditText)rows.get(i).getChildAt(2)).getText().toString();
                        not.setAsistenciaN(Float.parseFloat(n.equals("")?"-1":n));
                        n=((EditText)rows.get(i).getChildAt(3)).getText().toString();
                        not.setTareas(Float.parseFloat(n.equals("")?"-1":n));
                        n=((EditText)rows.get(i).getChildAt(4)).getText().toString();
                        not.setOtros(Float.parseFloat(n.equals("")?"-1":n));
                        n=((EditText)rows.get(i).getChildAt(5)).getText().toString();
                        not.setExamen(Float.parseFloat(n.equals("")?"-1":n));
                        n=((EditText)rows.get(i).getChildAt(8)).getText().toString();
                        data[i-1].getNota().setRecuperacion(Float.parseFloat(n.equals("")?"-1":n));
                }}
            }
        });
        th.start();
        changed=false;

        }


    }
    private void update() {
    /*    if (changed){
            Thread th=new Thread(new Runnable() {
                @Override
                public void run() {
                    Nota not;
                    String n;
                    TextView cella;
                    for(int i=1;i<rows.size();i++){
                        not=data[i-1].getNotas(parcial);
                        n=((EditText)rows.get(i).getChildAt(1)).getText().toString();
                        not.setAcumulativo(Float.parseFloat(n.equals("")?"-1":n));
                        ((EditText)rows.get(i).getChildAt(1)).setText(not.getAcumulativo()==-1?"":String.valueOf(not.getAcumulativo()));
                        n=((EditText)rows.get(i).getChildAt(2)).getText().toString();
                        not.setAsistenciaN(Float.parseFloat(n.equals("")?"-1":n));
                        ((EditText)rows.get(i).getChildAt(2)).setText(not.getAsistenciaN()==-1?"":String.valueOf(not.getAsistenciaN()));
                        n=((EditText)rows.get(i).getChildAt(3)).getText().toString();
                        not.setTareas(Float.parseFloat(n.equals("")?"-1":n));
                        ((EditText)rows.get(i).getChildAt(3)).setText(not.getTareas()==-1?"":String.valueOf(not.getTareas()));
                        n=((EditText)rows.get(i).getChildAt(4)).getText().toString();
                        not.setOtros(Float.parseFloat(n.equals("")?"-1":n));
                        ((EditText)rows.get(i).getChildAt(4)).setText(not.getOtros()==-1?"":String.valueOf(not.getOtros()));
                        n=((EditText)rows.get(i).getChildAt(5)).getText().toString();
                        not.setExamen(Float.parseFloat(n.equals("")?"-1":n));
                        ((EditText)rows.get(i).getChildAt(5)).setText(not.getExamen()==-1?"":String.valueOf(not.getExamen()));
                        float nota=not.getNotaFinal();
                        cella=(TextView)rows.get(i).getChildAt(6);
                        cella.setText(String.valueOf(nota));
                        if(nota<var.asignatura.getAprobIndice())cella.setTextColor(Color.RED);
                        if(nota>var.asignatura.getAprobIndice())cella.setTextColor(Color.GREEN);
                        cella=(TextView)rows.get(i).getChildAt(7);
                        nota=data[i-1].getNota().notaFinal();
                        cella.setText(String.valueOf(not));
                        if(nota<var.asignatura.getAprobIndice())cella.setTextColor(Color.YELLOW);
                        if(nota>var.asignatura.getAprobIndice())cella.setTextColor(Color.GREEN);

                    }
                }
            });
            th.start();changed=false;
        tableLayout.refreshDrawableState();}*/

         Nota not;
         String n;
        System.out.println(rows.size()+"  "+data.length);
        for(int i=1;i<rows.size();i++){
            not=data[i-1].getNotas(parcial);
            if(check(i,data[i-1]))
             {  //Toast.makeText(context,data[i-1].toString(),Toast.LENGTH_SHORT).show();
                n=((EditText)rows.get(i).getChildAt(1)).getText().toString();
                not.setAcumulativo(Float.parseFloat(n.equals("")?"-1":n));
                n=((EditText)rows.get(i).getChildAt(2)).getText().toString();
                not.setAsistenciaN(Float.parseFloat(n.equals("")?"-1":n));
                n=((EditText)rows.get(i).getChildAt(3)).getText().toString();
                not.setTareas(Float.parseFloat(n.equals("")?"-1":n));
                n=((EditText)rows.get(i).getChildAt(4)).getText().toString();
                not.setOtros(Float.parseFloat(n.equals("")?"-1":n));
                n=((EditText)rows.get(i).getChildAt(5)).getText().toString();
                not.setExamen(Float.parseFloat(n.equals("")?"-1":n));
                n=((EditText)rows.get(i).getChildAt(8)).getText().toString();
                data[i-1].getNota().setRecuperacion(Float.parseFloat(n.equals("")?"-1":n));
        }
        }
        rows=new ArrayList<>();
        tableLayout.removeAllViewsInLayout();
        addHeader(header);
        createDataT();
        changed=false;

      }

    private boolean check(int index, Estudiante std){
        double nf=0,rc=0;
        String n;
        n=((EditText)rows.get(index).getChildAt(1)).getText().toString();
        nf+=Float.parseFloat(n.equals("")?"0":n);
        n=((EditText)rows.get(index).getChildAt(2)).getText().toString();
        nf+=Float.parseFloat(n.equals("")?"0":n);
        n=((EditText)rows.get(index).getChildAt(3)).getText().toString();
        nf+=(Float.parseFloat(n.equals("")?"0":n));
        n=((EditText)rows.get(index).getChildAt(4)).getText().toString();
        nf+=Float.parseFloat(n.equals("")?"0":n);
        n=((EditText)rows.get(index).getChildAt(5)).getText().toString();
        nf+=Float.parseFloat(n.equals("")?"0":n);
        n=((EditText)rows.get(index).getChildAt(8)).getText().toString();
        rc+=Float.parseFloat(n.equals("")?"0":n);
        if(nf>100|| rc>100){
            Toast.makeText(context,nf+" No se pudo Guardar la nota de\n"+std.toString(),Toast.LENGTH_LONG).show();
            return false;
        }else return true;
    }
    }

