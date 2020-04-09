package com.example.ragna.taassistant.adapters;

/**
 *
 * Created by Ragna on 03/11/2018.
 */
import android.graphics.Color;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import jxl.*;
import jxl.format.*;
import jxl.format.Colour;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;

public class Export {

        public static void generarExcel(ArrayList<ArrayList<String>> datos, String header, String ruta,String col1,String col2) throws IOException {
            //System.out.println("control 2");
            try {
                //System.out.println("control 3");
                WorkbookSettings setting=new WorkbookSettings();
                setting.setEncoding("ISO-8859-1");
                File dir = Environment.getExternalStorageDirectory();
                dir= new File(dir.getPath(),"TeaAssist");
                System.out.println(dir.getPath());
                dir.mkdirs();
               // System.out.println("control 4");
                WritableWorkbook book=Workbook.createWorkbook(new File(dir.getPath(),ruta+".xls"),setting);

                WritableSheet sheet=book.createSheet("Notas",0);
                sheet.mergeCells(0,0,2,0);
                sheet.mergeCells(5,0,7,0);
                sheet.mergeCells(10,0,12,0);
               // new WritableFont(WritableFont.ARIAL,16,WritableFont.NO_BOLD);
                WritableCellFormat cellFormat=new WritableCellFormat(new WritableFont(WritableFont.ARIAL,16,WritableFont.NO_BOLD));

                WritableCellFormat A=new WritableCellFormat(new WritableFont(WritableFont.ARIAL,16,WritableFont.NO_BOLD));
                A.setShrinkToFit(false);
                A.setBackground(Colour.GREEN);

                WritableCellFormat R=new WritableCellFormat(new WritableFont(WritableFont.ARIAL,16,WritableFont.NO_BOLD));
                R.setShrinkToFit(false);
                R.setBackground(Colour.RED);
                for(int i=0;i<1;i++){
                    //System.out.println("control 5."+i);
                    for(int j=0;j<datos.get(i).size();j++){
                        sheet.addCell(new jxl.write.Label(j,i,(datos.get(i)).get(j),(datos.get(i)).get(j).equals(col1)?A:((datos.get(i)).get(j).equals(col2)?R:cellFormat)));
                    }
                }
                /*for(int i=1;i<2;i++){
                    sheet.mergeCells(0,i,1,i);
                    for(int j=0;j<datos.get(i).size();j++){
                        sheet.addCell(new jxl.write.Label(j,i,(datos.get(i)).get(j),(datos.get(i)).get(j).equals(col1)?A:((datos.get(i)).get(j).equals(col2)?R:cellFormat)));
                    }
                }*/
                for(int i=1;i<datos.size();i++){
                   // sheet.mergeCells(0,i,1,i);
                    //System.out.println("control 5."+i);
                    for(int j=0;j<datos.get(i).size();j++){
                            sheet.addCell(new jxl.write.Label(j,i,(datos.get(i)).get(j),(datos.get(i)).get(j).equals(col1)?A:((datos.get(i)).get(j).equals(col2)?R:cellFormat)));
                    }
                }
                CellView cell;
                for(int i=0;i<sheet.getColumns();i++){
                    cell=sheet.getColumnView(i);
                    cell.setAutosize(true);
                    sheet.setColumnView(i,cell);

                }
                    book.write();
                    book.close();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }

        }

}
