package com.example.ragna.taassistant.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ragna.taassistant.R;
import com.example.ragna.taassistant.Serializador;
import com.example.ragna.taassistant.clases.Estudiante;
import com.example.ragna.taassistant.var;
import com.squareup.picasso.Picasso;

import java.io.File;

public class verEstudiante extends AppCompatActivity {
    Uri capturador;
    EditText nombreP;
    EditText emailP;
    EditText numP;
    TextView textHolder;
    Estudiante estudiante;
    EditText id_et;
    Spinner spinner;
    EditText nombre_et;
    EditText email_et;
    EditText num_et;
    ImageView holder;
    CardView cv_camara;
    CardView cv_galeria;
    ViewGroup.LayoutParams p1;
    Button top_Button;
    Button cancel_btn;
    String id;
    String path;
    File pFile;
    boolean fotoDeCamara;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_estudiante);
        //-------------------Instancias
        fotoDeCamara = false;
        path = "";
        nombreP = findViewById(R.id.eNombreE_et);
        emailP = findViewById(R.id.vEmailP_et);
        numP = findViewById(R.id.eNum_et);
        spinner = findViewById(R.id.sSpinner);
        textHolder = findViewById(R.id.SPlaceHolder);
        id_et = findViewById(R.id.id_et1);
        nombre_et = findViewById(R.id.nombre_et1);
        email_et = findViewById(R.id.email_et1);
        num_et = findViewById(R.id.tel_et);
        top_Button = findViewById(R.id.btn_editar);
        cancel_btn = findViewById(R.id.cancel_btn);
        cv_camara = findViewById(R.id.cv_btn_camara);
        cv_galeria = findViewById(R.id.cv_btn_galeria);
        holder = findViewById(R.id.imageHolder);
        id = getIntent().getStringExtra("idEstu");
        estudiante = var.asignatura.getGesEstudiante().BusEstudianteId(id);
        //------------------No visible o no disponible
        id_et.setEnabled(false);
        nombre_et.setEnabled(false);
        email_et.setEnabled(false);
        num_et.setEnabled(false);
        textHolder.setEnabled(false);
        nombreP.setEnabled(false);
        emailP.setEnabled(false);
        numP.setEnabled(false);
        spinner.setVisibility(View.INVISIBLE);
        cancel_btn.setVisibility(View.INVISIBLE);
        cv_camara.setVisibility(View.INVISIBLE);
        cv_galeria.setVisibility(View.INVISIBLE);
        //-------------------Llenando campos
        id_et.setText(estudiante.getId());
        nombre_et.setText(estudiante.getNombres()+" "+estudiante.getApellidos());
        email_et.setText(estudiante.getCorreo());
        num_et.setText(estudiante.getNumeroContacto());
        numP.setText(estudiante.getTeleE());
        nombreP.setText(estudiante.getNombreE());
        emailP.setText(estudiante.getEmailE());
        if(estudiante.getFoto() == null) {
            holder.setImageResource(R.drawable.ic_user_placeholder);
        }else {
            Picasso.with(this).load(estudiante.getFoto()).resize(400,400).centerInside().into(holder);
        }
        if(estudiante.getGenero()==0){
            textHolder.setText("Sexo: Másculino");
        }else{
            textHolder.setText("Sexo: Femenino");
        }
        String[] opciones = {"Másculino","Femenino"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.custom_spinner,opciones);
        spinner.setAdapter(adapter);
        spinner.setSelection(estudiante.getGenero());
        //-----------------------------------------------
        p1 = holder.getLayoutParams();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,p1.height);
        holder.setLayoutParams(params);
        setResult(RESULT_FIRST_USER);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,@Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=RESULT_OK) return;
        if(requestCode == 2){
            capturador = data.getData();
            path = obtenerPath(capturador);
            if(pFile!=null && pFile.exists()){
                pFile.delete();
            }
            if(path==null){
                path = obtenerPath(data);
            }
            fotoDeCamara = false;
        }else {
            if(pFile!=null && pFile.exists()){
                pFile.delete();
            }
            path = capturador.getPath();
            pFile = new File(path);
            fotoDeCamara = true;
        }
        Picasso.with(this).load(new File(path)).resize(400,400).centerInside().into(holder);
    }

    public void loadFromCamera(View vista){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory(),"Android/data/com.jjsd.teaassist/Estudiante"+String.valueOf(System.currentTimeMillis()));
        capturador = Uri.fromFile(file);
        try {
            String autoridad = getApplicationContext().getPackageName() + ".fileprovider";
            Uri imageUri = FileProvider.getUriForFile(this,autoridad,file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
            intent.putExtra("return data",true);
            startActivityForResult(intent,1);
            Intent respuestaTemp = new Intent();
            respuestaTemp.putExtra("ruta",file.getAbsolutePath());
            setResult(RESULT_CANCELED,respuestaTemp);
        }catch (Exception e){
        }
    }

    public void loadFromGallery(View vista){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Completar acción con:"),2);
    }

    public String obtenerPath(Uri contenidoUri){
        String[] proj={MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contenidoUri,proj,null,null,null);
        if(cursor==null) return "";
        int colum_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(colum_index);
    }

    public String obtenerPath(Intent data){
        Uri originalUri = data.getData();
        String id = originalUri.getLastPathSegment().split(":")[1];
        final String[] imageColumns = {MediaStore.Images.Media.DATA};
        final String imageOrderBy = null;

        Uri uri = getUri();
        String filePath = "path";

        Cursor imageCursor = getContentResolver().query(uri, imageColumns,
                MediaStore.Images.Media._ID + "=" + id, null, imageOrderBy);

        if(imageCursor.moveToFirst()) {
            filePath = imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA));
        }
        return  filePath;
    }

    private Uri getUri() {
        String state = Environment.getExternalStorageState();
        if(!state.equalsIgnoreCase(Environment.MEDIA_MOUNTED))
            return MediaStore.Images.Media.INTERNAL_CONTENT_URI;

        return MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    }

    public void toolButton(View vista){
        if(top_Button.getText().equals("Editar")){
            editar();
        }else{
            guardar();
        }
    }

    private void editar(){
        top_Button.setText("Guardar");
        getIntent().putExtra("margen",20);
        holder.setLayoutParams(p1);
        cancel_btn.setVisibility(View.VISIBLE);
        cv_galeria.setVisibility(View.VISIBLE);
        cv_camara.setVisibility(View.VISIBLE);
        spinner.setVisibility(View.VISIBLE);
        textHolder.setVisibility(View.INVISIBLE);
        //--------------------Editable
        id_et.setEnabled(true);
        nombreP.setEnabled(true);
        emailP.setEnabled(true);
        numP.setEnabled(true);
        nombre_et.setEnabled(true);
        email_et.setEnabled(true);
        num_et.setEnabled(true);
        //-----------------------------
    }

    public void cancelar(View vista){
        //------------------No visible
        id_et.setEnabled(false);
        nombre_et.setEnabled(false);
        email_et.setEnabled(false);
        num_et.setEnabled(false);
        nombreP.setEnabled(false);
        numP.setEnabled(false);
        emailP.setEnabled(false);
        cancel_btn.setVisibility(View.INVISIBLE);
        cv_camara.setVisibility(View.INVISIBLE);
        cv_galeria.setVisibility(View.INVISIBLE);
        spinner.setVisibility(View.INVISIBLE);
        textHolder.setVisibility(View.VISIBLE);
        //-------------------Llenando campos
        id_et.setText(estudiante.getId());
        nombre_et.setText(estudiante.getNombres()+" "+estudiante.getApellidos());
        email_et.setText(estudiante.getCorreo());
        num_et.setText(estudiante.getNumeroContacto());
        if(estudiante.getFoto() == null) {
            holder.setImageResource(R.drawable.ic_user_placeholder);
        }else {
            Picasso.with(this).load(estudiante.getFoto()).resize(400,400).centerInside().into(holder);
        }
        //-----------------------------------------------
        top_Button.setText("Editar");
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,p1.height);
        holder.setLayoutParams(params);
    }

    private void guardar(){
        //------------------------------
        String nombreRE = "[a-zA-ZáéñÑíóú]+(\\s+[a-zA-ZáéÑñíóú]+)?(\\s+[a-zA-ZáéíñÑóú]+)?(\\s+[a-zA-ZáéñÑíóú]+)?\\s*";
        String emailRE = "\\S+@((gmail)|(hotmail)|(outlook)|(yahoo))((.com)|(.net))";
        String numeroRE = "\\d{8}";
        String nombre = nombre_et.getText().toString();
        String email = email_et.getText().toString();
        String num = num_et.getText().toString();
        String id = id_et.getText().toString();
        //------------------------------
        Serializador serializador = new Serializador();
        File pFoto = estudiante.getFoto();
        if(!(path.equals(""))){
            estudiante.setFoto(new File(path));
            if(estudiante.isFotoDeCamara()) {
                if(pFoto!=null && pFoto.exists()) pFoto.delete();
            }
        }
        String nombres[] = nombre.split(" ");
        String[] rn=new String[4];
        rn[0]=nombres[0];
        rn[1]=nombres.length>1?nombres[1]:null;
        rn[2]=nombres.length>2?nombres[2]:null;
        rn[3]=nombres.length>3?nombres[3]:null;
        boolean valido = true;
        if(noEmptyFields(nombre,id,num,email)){
            if(nombre.matches(nombreRE)){
                Estudiante temp = var.asignatura.getGesEstudiante().getEstudiante();
                while(temp!=null){
                    if(temp.getId().equals(id) && temp.getId()!=estudiante.getId()){
                        valido = false;
                        break;
                    }else{
                        temp = temp.getNextEstudiante();
                    }
                }
                if(valido){
                    if(!num.matches(numeroRE)){
                        valido=false;
                        Toast.makeText(this, "Número no valido", Toast.LENGTH_SHORT).show();
                    }
                    if(!email.matches(emailRE)){
                        valido = false;
                        Toast.makeText(this, "Email no valido", Toast.LENGTH_SHORT).show();
                    }
                }
            }else{
                valido = false;
                Toast.makeText(this, "Nombre no valido", Toast.LENGTH_SHORT).show();
            }
            if(valido){
                estudiante.setGenero(spinner.getSelectedItemPosition());
                estudiante.setN1(rn[0]);
                estudiante.setN2(rn[1]);
                estudiante.setN3(rn[2]);
                estudiante.setN4(rn[3]);
                estudiante.setId(id);
                estudiante.setCorreo(email);
                estudiante.setNumeroContacto(num);
                estudiante.setTeleE(numP.getText().toString());
                estudiante.setEmailE(emailP.getText().toString());
                estudiante.setNombreE(nombreP.getText().toString());
                estudiante.setFotoDeCamara(fotoDeCamara);
                setResult(RESULT_OK);
                Toast.makeText(this,serializador.GuardarEnSd(var.gmaestro), Toast.LENGTH_SHORT).show();
                finish();
            }
        }else{
            Toast.makeText(this, "No se pueden dejar campos vacios", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean noEmptyFields(String nombre,String id,String numero,String email){
        if(nombre.replaceAll(" ","").equals("")
                ||id.replaceAll(" ","").equals("")
                ||numero.replaceAll(" ","").equals("")
                ||email.replaceAll(" ","").equals("")){
            return false;
        }
        return true;
    }
}