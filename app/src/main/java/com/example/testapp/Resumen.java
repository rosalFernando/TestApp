package com.example.testapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Clase Java de la actividad Resumen que mostrara informacion
 * sobre la aplicacion en cuanto a preguntas, categorias, etc.
 */

public class Resumen extends AppCompatActivity {
    private String TAG="Resumen";
    private Context context;
    private ConstraintLayout CLREsumen;

    /**
     * Metodo que carga la informacion y el toolbar al inicio de la
     * actividad.
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyLog.d(TAG, "iniciando onCreate");
        setContentView(R.layout.activity_resumen);
        Toolbar toolbar = findViewById(R.id.toolbarApp);
        setSupportActionBar(toolbar);
        context=this;

        FloatingActionButton fab =(FloatingActionButton) findViewById(R.id.botonAniade);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(Resumen.this, CrearEditar.class);
                startActivity(it);



            }
        });


        MyLog.d(TAG,"cerrando onCreate");

    }

    /**
     * Metodo que crea el menu de opciones cuando es llamado por el usuario.
     * @param menu
     * @return true
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_reseumen, menu);
        return true;
    }

    /**
     * Metodo que recibe la accion click del menu y realiza la operacion que
     * se lleve a cabo en cada item.
     *
     * @param item
     * @return item seleccionado
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.acercaDeMenu:
                MyLog.i(TAG, "iniciando acerca de");
                startActivity(new Intent(Resumen.this, AcercaDe.class));
                return true;

            case R.id.ListadoMenu:
                MyLog.i(TAG, "iniciando Listadp");
                startActivity(new Intent(Resumen.this, Listado.class));
                return true;

            case R.id.configMenu:
                MyLog.i(TAG, "iniciando configuracion");
                return true;

            case R.id.salirMenu:
                MyLog.i(TAG, "Saliendo");
                finish();
                System.exit(0);
                return true;

            case R.id.exportarMenu:
                int WriteExternalStoragePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                MyLog.d(TAG, "WRITE_EXTERNAL_STORAGE Permission: " + WriteExternalStoragePermission);


                if (WriteExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ActivityCompat.requestPermissions(Resumen.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WriteExternalStoragePermission);

                    } else {
                        Snackbar.make(CLREsumen, getResources().getString(R.string.permiso_escritura_denegado), Snackbar.LENGTH_LONG)
                                .show();
                    }
                } else {

                        Intent emailIntent = Metodos.exportarXML(context);
                        startActivity(Intent.createChooser(emailIntent, "Exportar Preguntas"));

                }
                return true;
                default:
                    return super.onOptionsItemSelected(item);




        }

    }

    /**
     *Metodo que mantiene la actividad activa mientras este recibiendo informacion,
     */

    @Override
    protected void onResume() {
        MyLog.d(TAG, "iniciando onResume");
        super.onResume();
        TextView NumCategorias = findViewById(R.id.textViewNumCat);
        TextView NumPreguntas = findViewById(R.id.textViewNumPreguntas);

        NumPreguntas.setText(String.valueOf(PreguntasRepositorio.contQues(context)));
        NumCategorias.setText(String.valueOf(PreguntasRepositorio.numCat(context)));


        MyLog.d(TAG, "cerrando onResume");
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
