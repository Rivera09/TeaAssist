package com.example.ragna.taassistant.popups;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ragna.taassistant.R;
import com.example.ragna.taassistant.clases.Asignatura;
import com.example.ragna.taassistant.clases.Estudiante;
import com.example.ragna.taassistant.var;

import java.util.ArrayList;
import java.util.List;

public class ImportarEstudiantes extends Activity {


    public class Item {
        boolean checked;
        Estudiante estu;

        Item(Estudiante estudiante) {
            estu = estudiante;
            checked = false;
        }

        public boolean isChecked() {
            return checked;
        }
    }

    static class ViewHolder {
        CheckBox checkBox;
        TextView text;
    }

    public class ItemsListAdapter extends BaseAdapter {

        private Context context;
        private List<Item> list;

        ItemsListAdapter(Context c, List<Item> l) {
            context = c;
            list = l;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public boolean isChecked(int position) {
            return list.get(position).checked;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            // reuse views
            ViewHolder viewHolder = new ViewHolder();
            if (rowView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                rowView = inflater.inflate(R.layout.row, null);

                viewHolder.checkBox = rowView.findViewById(R.id.rowCheckBox);
                viewHolder.text = rowView.findViewById(R.id.rowTextView);
                rowView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) rowView.getTag();
            }
            viewHolder.checkBox.setChecked(list.get(position).checked);
            final String itemStr = list.get(position).estu.getNombres() + " " + list.get(position).estu.getApellidos();
            viewHolder.text.setText(itemStr);
            viewHolder.checkBox.setTag(position);
            viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean newState = !list.get(position).isChecked();
                    list.get(position).checked = newState;
                }
            });
            viewHolder.checkBox.setChecked(isChecked(position));

            return rowView;
        }
    }

    List<Item> items;
    ListView listView;
    ItemsListAdapter myItemsListAdapter;
    Asignatura[] asignaturas;
    int cantAsignaturas;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_importar_estudiantes);
        //----------------------------------
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int largo = dm.widthPixels;
        int alto = dm.heightPixels;
        getWindow().setLayout((int)(largo*.9),(int)(alto*.9));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x=0;
        params.y=-20;
        getWindow().setAttributes(params);
        //----------------------------------
        setResult(RESULT_FIRST_USER);
        spinner = findViewById(R.id.spinner_imp);
        cantAsignaturas = var.maestro.getGesAsignatura().getCounter();
        asignaturas = var.maestro.getGesAsignatura().asignaturas();
        listView = findViewById(R.id.listview);
        String[] clases = new String[cantAsignaturas - 1];
        int j = 0;
        for (int i = 0; i < cantAsignaturas; i++) {
            if (asignaturas[i] != var.asignatura) {
                clases[j] = asignaturas[i].getName();
                j++;
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.custom_spinner, clases);
        spinner.setAdapter(adapter);
        initItems();
        myItemsListAdapter = new ItemsListAdapter(this, items);
        listView.setAdapter(myItemsListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                initItems();
                myItemsListAdapter = new ItemsListAdapter(ImportarEstudiantes.this, items);
                listView.setAdapter(myItemsListAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void importar(View vista) {
        Estudiante nuevo;
        for (int i = 0; i < items.size(); i++) {
            nuevo = items.get(i).estu;
            if (items.get(i).isChecked()) {
                Estudiante nEs=new Estudiante(nuevo.getId(),nuevo.getN1(), nuevo.getN2(),nuevo.getN3(),nuevo.getN4(),var.asignatura.getParcial());
                nEs.setCorreo(nuevo.getCorreo());
                nEs.setNumeroContacto(nuevo.getNumeroContacto());
                nEs.setGenero(nuevo.getGenero());
                nEs.setFoto(nuevo.getFoto());
                nEs.setFotoDeCamara(nuevo.isFotoDeCamara());
                nEs.setNombreE(nuevo.getNombreE());
                nEs.setEmailE(nuevo.getEmailE());
                nEs.setTeleE(nuevo.getTeleE());
                var.asignatura.getGesEstudiante().addEstudiante(nEs);
            }
        }
        setResult(RESULT_OK);
        finish();
    }

    private void initItems() {
        items = new ArrayList<>();
        Asignatura asignatura = var.maestro.getGesAsignatura().asignaturaPorNombre(spinner.getSelectedItem().toString());
        Estudiante estuTemp;
        Estudiante[] estudiantes = asignatura.getGesEstudiante().getEstudiantes();
        Estudiante[] estudiantes1 = var.asignatura.getGesEstudiante().getEstudiantes();
        boolean registrado;
        for (int i = 0; i < estudiantes.length; i++) {
            registrado = false;
            estuTemp = estudiantes[i];
            for(int j=0;j<estudiantes1.length;j++){
                if(estuTemp.getId()==estudiantes1[j].getId()){
                    registrado = true;
                    break;
                }
            }
            if(!registrado){
                Item item = new Item(estuTemp);
                items.add(item);
            }
        }
    }
}