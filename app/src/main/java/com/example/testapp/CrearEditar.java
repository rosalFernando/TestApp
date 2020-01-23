package com.example.testapp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Clase Java de la actividad CrearEditar la cual permite insertar, eliminar y
 * modificar preguntas.
 */

public class CrearEditar extends AppCompatActivity {



    private Context context;
    private ConstraintLayout CLCrearEditar;
    private ArrayAdapter<String> adapter;

    private EditText titulo;
    private EditText correcta;
    private EditText incorrecta1;
    private EditText incorrecta2;
    private EditText incorrecta3;
    private Spinner categoria;
    private ImageView img;
    private FloatingActionButton guardar;
    private FloatingActionButton eliminar;
    private Button addCat;
    private Button borrarFoto;
    private Button hacerFoto;
    private Button cogerFoto;
    private String imagen64;
    private Uri uri;
     private String TAG = "CrearEditar";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_editar);

        context=this;

        CLCrearEditar=findViewById(R.id.ConstraintLayaoutCE);
        titulo=(EditText) findViewById(R.id.editTextTitulo);
        correcta=(EditText) findViewById(R.id.editTextCor);
        incorrecta1=(EditText) findViewById(R.id.editTextInc1);
        incorrecta2=(EditText) findViewById(R.id.editTextInc2);
        incorrecta3=(EditText) findViewById(R.id.editTextInc3);
        categoria=(Spinner) findViewById(R.id.spinnerCategoria);
        guardar=(FloatingActionButton) findViewById(R.id.floatingActionButtonGuardar);
        eliminar=(FloatingActionButton) findViewById(R.id.floatingActionButtonElimina);
        addCat=(Button) findViewById(R.id.buttonCategoria);
        hacerFoto =(Button) findViewById(R.id.buttonFoto);
        cogerFoto =(Button) findViewById(R.id.buttonGaleria);
        borrarFoto=(Button) findViewById(R.id.buttonDelFoto);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbarApp);
        img = (ImageView) findViewById(R.id.imageViewFoto);



        if(toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_crear_editar));
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

        }

        /**
         * Accion del boton para añadir categorias.
         */

        final ArrayList<String> categorias= PreguntasRepositorio.selectCategoria(context);

        addCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                LayoutInflater layoutActivity = LayoutInflater.from(context);
                View viewAlertDialog = layoutActivity.inflate(R.layout.alert_dialog_categoria, null);

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                alertDialog.setView(viewAlertDialog);

                final EditText dialogIn = (EditText) viewAlertDialog.findViewById(R.id.editDialogTextCat);

                alertDialog
                        .setCancelable(false)
                        .setPositiveButton(getResources().getString(R.string.aniade),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        if (dialogIn.getText().toString().trim().isEmpty()) {
                                            Snackbar.make(view, getResources().getText(R.string.cat_vacia), Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                        } else {
                                            adapter.add(dialogIn.getText().toString());
                                            categoria.setSelection(adapter.getPosition(dialogIn.getText().toString()));
                                        }

                                    }
                                })
                        .setNegativeButton(getResources().getString(R.string.cancelar),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                })
                        .create()
                        .show();
            }


            });

        adapter=new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, categorias);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        categoria=(Spinner) findViewById(R.id.spinnerCategoria);
        categoria.setAdapter(adapter);

        final Intent startingIntent=getIntent();

        /**
         * If para rellenar los campos cuando venimos desde listado con las opciones de la pregunta.
         */

        if(startingIntent.hasExtra("id")){
            int idRec=getIntent().getExtras().getInt("id");
            Question quesRec= PreguntasRepositorio.selectQuestion(idRec, context);

            titulo.setText(quesRec.getTitulo());
            categoria.setSelection(adapter.getPosition(quesRec.getCategoria()));
            correcta.setText(quesRec.getCorrecta());
            incorrecta1.setText(quesRec.getIncorrecta1());
            incorrecta2.setText(quesRec.getIncorrecta2());
            incorrecta3.setText(quesRec.getIncorrecta3());
            imagen64=quesRec.getImg64();

            byte [] codImagen= Base64.decode(imagen64, Base64.DEFAULT);
            Bitmap imgDecoded= BitmapFactory.decodeByteArray(codImagen, 0, codImagen.length);

            img.setImageBitmap(imgDecoded);

        }




        /**
         * Accion del boton para realizar una foto desde camara.
         */

        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            hacerFoto.setEnabled(false);
        } else {
            hacerFoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {



                    int WriteExternalStoragePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    MyLog.d(TAG, "WRITE_EXTERNAL_STORAGE Permission: " + WriteExternalStoragePermission);

                    /**
                     *
                     * PackageManager es una clase para recuperar diversos tipos de información relacionada con los paquetes
                     de aplicaciones que están actualmente instalados en el dispositivo.
                     */

                    if (WriteExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
                        /**
                         * Comprobacion de la version del sistema Android, ya que antes de la version 6
                         * no era necesario aceptar los permisos en tiempo de ejecucion, desde la 6 si es necesario.
                         */

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            ActivityCompat.requestPermissions(CrearEditar.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constantes.CODE_WRITE_EXTERNAL_STORAGE_PERMISSION_CAMERA);

                        } else {
                            Snackbar.make(CLCrearEditar, getResources().getString(R.string.permiso_escritura_garantizado), Snackbar.LENGTH_LONG)
                                    .show();
                        }
                    } else {


                            takeImagen();

                    }
                }
            });
        }

        /**
         * Accion del boton para seleccionar una imagen
         * desde galeria.
         */

            cogerFoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

                    int WriteExternalStoragePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    MyLog.d(TAG, "WRITE_EXTERNAL_STORAGE Permission: " + WriteExternalStoragePermission);


                    if (WriteExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {

                        /**
                         * Comprobacion de la version del sistema Android, ya que antes de la version 6
                         * no era necesario aceptar los permisos en tiempo de ejecucion, desde la 6 si es necesario.
                         */

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            ActivityCompat.requestPermissions(CrearEditar.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constantes.CODE_WRITE_EXTERNAL_STORAGE_PERMISSION_GALERY);

                        } else {
                            Snackbar.make(CLCrearEditar, getResources().getString(R.string.permiso_escritura_garantizado), Snackbar.LENGTH_LONG)
                                    .show();
                        }
                    } else {


                            selectPicture();

                    }
                }
            });



        /**
         * Accion del FavBoton eliminar.
         */

        eliminar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {

                LayoutInflater layoutActivity = LayoutInflater.from(context);
                final View viewAlertDialog = layoutActivity.inflate(R.layout.alert_dialog_borrar, null);

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                alertDialog.setView(viewAlertDialog);

                final EditText dialogInput = (EditText) viewAlertDialog.findViewById(R.id.editDialogTextCat);

                alertDialog.setCancelable(false)

                        .setPositiveButton(getResources().getString(R.string.aceptar),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        if (startingIntent.hasExtra("id")) {
                                            int idRecogido = getIntent().getExtras().getInt("id");

                                            PreguntasRepositorio.deleleteQuestion(context, idRecogido);
                                            Toast.makeText(context, getResources().getString(R.string.pregunta_eliminada), Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            Toast.makeText(context, getResources().getString(R.string.pregunta_no_creada), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                })

                        .setNegativeButton(getResources().getString(R.string.cancelar),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                })
                        .create()
                        .show();
            }

        });

        /**
         * Accion del Favboton Guardar.
         */

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /**
                 * Comprobacion de que los elementos de la pregunta se encuentran rellenos
                 * y en caso de no estar rellenos se muestra un mensaje de error y se impide guardar.
                 */
                if (titulo.getText().toString().isEmpty()) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(titulo.getWindowToken(), 0);

                    Snackbar.make(view, getResources().getText(R.string.snackBar_enunciado_vacio), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } else if (categorias.isEmpty()) {

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(guardar.getWindowToken(), 0);

                    Snackbar.make(view, getResources().getText(R.string.snackBar_categoria_vacio), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else if (correcta.getText().toString().isEmpty()) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(correcta.getWindowToken(), 0);
                    Snackbar.make(view, getResources().getText(R.string.snackBar_respuesta_correcta_vacio), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else if (incorrecta1.getText().toString().isEmpty()) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(incorrecta1.getWindowToken(), 0);
                    Snackbar.make(view, getResources().getText(R.string.snackBar_respuesta_incorrecta1_vacio), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else if (incorrecta2.getText().toString().isEmpty()) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(incorrecta2.getWindowToken(), 0);
                    Snackbar.make(view, getResources().getText(R.string.snackBar_respuesta_incorrecta2_vacio), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else if (incorrecta3.getText().toString().isEmpty()) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(incorrecta3.getWindowToken(), 0);
                    Snackbar.make(view, getResources().getText(R.string.snackBar_respuesta_incorrecta3_vacio), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } else {

                    Intent startingIntent = getIntent();
                    if (startingIntent.hasExtra("id")) {

                        int idRec = getIntent().getExtras().getInt("id");

                        turnBase64(img);

                        Question quesRec = new Question(titulo.getText().toString(), categoria.getSelectedItem().toString(), correcta.getText().toString(), incorrecta1.getText().toString(), incorrecta2.getText().toString(), incorrecta3.getText().toString(), imagen64);
                        PreguntasRepositorio.updateQuestion(quesRec, context, idRec);
                        finish();

                    } else {

                        String imagen64 = turnBase64(img);

                        Question q = new Question(titulo.getText().toString(), categoria.getSelectedItem().toString(), correcta.getText().toString(), incorrecta1.getText().toString(), incorrecta2.getText().toString(), incorrecta3.getText().toString(), imagen64);
                        if (PreguntasRepositorio.insertQuestion(q, context)) {
                            Toast.makeText(context, getResources().getString(R.string.snackBar_pregunta_creada), Toast.LENGTH_SHORT).show();
                            finish();

                        } else {
                            Toast.makeText(context, getResources().getString(R.string.pregunta_no_creada), Toast.LENGTH_SHORT).show();
                        }
                    }

                }

            }
        });

        /**
         * Accion del boton para eliminar la imagen.
         */

        borrarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                img.setImageDrawable(null);
            }
        });




    }



    /**
     * Metodo que permite tomar una imagen desde
     * la camara del dispositivo.
     */

    private void takeImagen() {

        try {
            // Se crea el directorio para las fotografías
            File dirFotos = new File(Constantes.pathFotos);
            dirFotos.mkdirs();

            // Se crea el archivo para almacenar la fotografía
            File fileFoto = File.createTempFile(Metodos.getFileCode(), ".jpg", dirFotos);

            // Se crea el objeto Uri a partir del archivo
            // A partir de la API 24 se debe utilizar FileProvider para proteger
            // con permisos los archivos creados
            // Con estas funciones podemos evitarlo
            // https://stackoverflow.com/questions/42251634/android-os-fileuriexposedexception-file-jpg-exposed-beyond-app-through-clipdata
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            uri = Uri.fromFile(fileFoto);
            Log.d(TAG, uri.getPath().toString());

            // Se crea la comunicación con la cámara
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Se le indica dónde almacenar la fotografía
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            // Se lanza la cámara y se espera su resultado
            startActivityForResult(cameraIntent, Constantes.REQUEST_CAPTURE_IMAGE);

        } catch (IOException ex) {

            Log.d(TAG, "Error: " + ex);
            CoordinatorLayout coordinatorLayout = findViewById(R.id.ConstraintLayaoutCE);
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, getResources().getString(R.string.error_imagen), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    /**
     * Metodo que nos permite seleccionar una imagen desde nuestro dispositivo
     * y prepara la actividad para recivir el resultado de la imagen y mosrtrarla.
     */

    private void selectPicture(){
        // Se le pide al sistema una imagen del dispositivo
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, getResources().getString(R.string.elige_imagen)),
                Constantes.REQUEST_SELECT_IMAGE);
    }



    /**
     * Metodo que recibe una imagen desde el ImageView y la combierte a base 64
     * para poder almacenarla en la base de datos como cadena de caracteres.
     *
     * @param imgv
     * @return imagen64
     */

    private String turnBase64(ImageView imgv){

        if(img.getDrawable() != null){
            Bitmap imagenBitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();
            Bitmap resized = Bitmap.createScaledBitmap(imagenBitmap, 200, 200, true);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            resized.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            imagen64 = Base64.encodeToString(byteArray, 0);
            return imagen64;
        } else {
            imagen64 = "";
            return imagen64;
        }

    }

    /**
     * Metodo que envia el resultado de la actividad al seleccionar una imagen o
     * tomarla desde la camara.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case (Constantes.REQUEST_CAPTURE_IMAGE):
                if(resultCode == CrearEditar.RESULT_OK){
                    // Se carga la imagen desde un objeto URI al imageView
                    ImageView imageView = findViewById(R.id.imageViewFoto);
                    imageView.setImageURI(uri);

                    // Se le envía un broadcast a la Galería para que se actualice
                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    mediaScanIntent.setData(uri);
                    sendBroadcast(mediaScanIntent);

                } else if (resultCode == CrearEditar.RESULT_CANCELED) {
                    // Se borra el archivo temporal
                    File file = new File(uri.getPath());
                    file.delete();
                }
                break;

            case (Constantes.REQUEST_SELECT_IMAGE):
                if (resultCode == CrearEditar.RESULT_OK) {
                    // Se carga la imagen desde un objeto Bitmap
                    Uri selectedImage = data.getData();
                    String selectedPath = selectedImage.getPath();

                    if (selectedPath != null) {
                        // Se leen los bytes de la imagen
                        InputStream imageStream = null;
                        try {
                            imageStream = getContentResolver().openInputStream(selectedImage);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        // Se transformam los bytes de la imagen a un Bitmap
                        Bitmap bmp = BitmapFactory.decodeStream(imageStream);

                        // Se carga el Bitmap en el ImageView
                        ImageView imageView = findViewById(R.id.imageViewFoto);
                        imageView.setImageBitmap(bmp);
                    }
                }
                break;
        }
    }






    /**
     * Metodo que comprueba si se han aceptado los permisos para camara y galeria y muestra
     * mensaje en caso de que no se acepten
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Constantes.CODE_WRITE_EXTERNAL_STORAGE_PERMISSION_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takeImagen();
                } else {
                    Snackbar.make(CLCrearEditar, getResources().getString(R.string.permiso_escritura_no_aceptado), Snackbar.LENGTH_LONG)
                            .show();
                }
                break;
            case Constantes.CODE_WRITE_EXTERNAL_STORAGE_PERMISSION_GALERY:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    selectPicture();
                } else {
                    Snackbar.make(CLCrearEditar, getResources().getString(R.string.permiso_escritura_no_aceptado), Snackbar.LENGTH_LONG)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    /**
     * Metodo que nos devuelve a la actividad anterior y mantiene esta creada.
     * @return true.
     */
    @Override
    public boolean onNavigateUp(){
        return true;
    }

    /**
     * Metodo que mata la actividad y devuelve a la anterior.
     */
    @Override
    public void onBackPressed(){
        finish();
    }

    @Override
    /**
     * Metodo que se llama cuando la actividad ha sido detenida y vuelta a llamar por el usuario.
     */
    protected void onStart() {
        MyLog.d(TAG, "iniciando onStart");
        super.onStart();
        MyLog.d(TAG, "cerrando onStart");
    }

    @Override
    /**
     *Metodo que mantiene la actividad activa mientras este recibiendo informacion,
     */
    protected void onResume() {
        MyLog.d(TAG, "iniciando onResume");
        super.onResume();
        MyLog.d(TAG, "cerrando onResume");
    }

    /**
     *Metodo que se llama cuando el usuario ya no interactua con la actividad pero
     * aun se muestras en la pantalla.
     */

    @Override
    protected void onPause() {
        MyLog.d(TAG, "iniciando onPause");
        super.onPause();
        MyLog.d(TAG, "cerrando onPause");
    }

    /**
     *Metodo que se llama cuando la actividad no es visible para el usuario.
     */

    @Override
    protected void onStop() {
        MyLog.d(TAG, "iniciando onStop");
        super.onStop();
        MyLog.d(TAG, "cerrando onStop");
    }

    /**
     *Metodo que se llama cuando la actividad es destruida y limpia
     *todo lo que haya en ella.
     */

    @Override
    protected void onDestroy() {
        MyLog.d(TAG, "iniciando onDestroy");
        super.onDestroy();
        MyLog.d(TAG, "cerrando onDestroy");

    }

    /**
     *Metodo al que se llama cuando el usuario ha vuelto otra vez a la actividad.
     */


    @Override
    protected void onRestart() {
        MyLog.d(TAG, "iniciando onRestart");
        super.onRestart();
        MyLog.d(TAG, "cerrando onRestart");
    }



}
