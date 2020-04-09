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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.ragna.taassistant.R;
import com.example.ragna.taassistant.Serializador;
import com.example.ragna.taassistant.clases.Estudiante;
import com.example.ragna.taassistant.var;
import com.squareup.picasso.Picasso;


import java.io.File;

/***
 * Author Cyborg
 */
public class Registro_Estudiantes extends AppCompatActivity {
    Uri capturador;
    String path;
    File pFile;
    boolean guardado;
    boolean fotoDeCamara;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro__estudiantes);
        fotoDeCamara = false;
        guardado = false;
        pFile = null;
        path = "";
        String[] opciones = {"Masculino","Femenino"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.custom_spinner,opciones);
        ((Spinner)(findViewById(R.id.spinner))).setAdapter(adapter);
        setResult(RESULT_FIRST_USER);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!guardado){
            File fotoNoUsada = new File(path);
            if(fotoNoUsada.exists()){
                fotoNoUsada.delete();
            }
        }
    }

    //Implementar codigo de abajo en version final
    /*@Override
    protected void onResume() {
        Serializador serializador = new Serializador();
        if(!serializador.Existe()){
            Intent intent = new Intent(this,LoginActivity.class);
            SharedPreferences preferences= getSharedPreferences("keepme",Context.MODE_PRIVATE);
            SharedPreferences.Editor saver = preferences.edit();
            saver.putInt("kept",0);
            saver.commit();
            startActivity(intent);
            finish();
        }
        super.onResume();
    }*/

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
        Picasso.with(this).load(new File(path)).resize(400,400).centerInside().into((ImageView)(findViewById(R.id.foto_Vista)));
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


    public void Registrar(View vista){
        String nombreRE = "[a-zA-ZáéñÑíóú]+(\\s[a-zA-ZáéÑñíóú]+)?(\\s[a-zA-ZáéíñÑóú]+)?(\\s[a-zA-ZáéñÑíóú]+)?\\s?";
        String emailRE = "\\S+@((gmail)|(hotmail)|(outlook)|(yahoo))((.com)|(.net)|(.es))";
        String numeroRE = "\\d{8}";
        String nombre = ((EditText)(findViewById(R.id.nombreE_et))).getText().toString();
        String id = ((EditText)(findViewById(R.id.idE_et))).getText().toString();
        String numero = ((EditText)(findViewById(R.id.numE_et))).getText().toString();
        String email = ((EditText)(findViewById(R.id.emailE_et))).getText().toString();
        String numP = ((EditText)findViewById(R.id.numP_et)).getText().toString();
        String nombreP = ((EditText)findViewById(R.id.nombreP_et)).getText().toString();
        String emailP = ((EditText)findViewById(R.id.emailP_et)).getText().toString();
        boolean valido = true;
        int sexo = ((Spinner)(findViewById(R.id.spinner))).getSelectedItemPosition();
        if(nombre.matches(nombreRE)){
            if(noEmptyFields(nombre,id,numero,email,((Spinner)(findViewById(R.id.spinner))).getSelectedItem().toString())){
                Estudiante temp = var.asignatura.getGesEstudiante().getEstudiante();
                while(temp!=null){
                    if(temp.getId().equals(id)){
                        valido = false;
                        break;
                    }else{
                        temp = temp.getNextEstudiante();
                    }
                }
                if(valido){
                    if(!email.matches(emailRE)){
                        valido = false;
                        Toast.makeText(this, "Email no valido", Toast.LENGTH_SHORT).show();
                    }
                    if(!numero.matches(numeroRE)){
                        valido=false;
                        Toast.makeText(this, "Número no valido", Toast.LENGTH_SHORT).show();
                    }
                }
                if(valido){
                    Serializador serializador = new Serializador();
                    File foto = null;
                    String nombres[] = nombre.split(" ");
                    String[] rn=new String[4];
                    rn[0]=nombres[0];
                    rn[1]=nombres.length>1?nombres[1]:null;
                    rn[2]=nombres.length>2?nombres[2]:null;
                    rn[3]=nombres.length>3?nombres[3]:null;
                    Estudiante nuevoEstudiante = new Estudiante(id,rn[0],rn[1],rn[2],rn[3], var.asignatura.getParcial());
                    nuevoEstudiante.setCorreo(email);
                    nuevoEstudiante.setNumeroContacto(numero);
                    nuevoEstudiante.setGenero(sexo);
                    nuevoEstudiante.setNombreE(nombreP);
                    nuevoEstudiante.setEmailE(emailP);
                    nuevoEstudiante.setTeleE(numP);
                    if(path!="") foto = new File(path);
                    nuevoEstudiante.setFoto(foto);
                    nuevoEstudiante.setFotoDeCamara(fotoDeCamara);
                    var.asignatura.getGesEstudiante().addEstudiante(nuevoEstudiante);
                    //var.asignatura.addEstudiante(nuevoEstudiante);
                    Toast.makeText(this,  serializador.GuardarEnSd(var.gmaestro), Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    guardado = true;
                    finish();
                }else {
                    Toast.makeText(this, "Ya hay otro estudiante con ese ID", Toast.LENGTH_SHORT).show();
                }
            }
        }else{
            Toast.makeText(this, "Nombre no valido", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean noEmptyFields(String nombre,String id,String numero,String email,String spinnerItem){
        if(nombre.replaceAll(" ","").equals("")
                ||id.replaceAll(" ","").equals("")
                ||numero.replaceAll(" ","").equals("")
                ||email.replaceAll(" ","").equals("")
                ||spinnerItem.replaceAll(" ","").equals("")){
            return false;
        }
        return true;
    }
}


