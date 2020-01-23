package com.example.testapp;

import android.content.Context;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Clase Java de la actividad Acerca De la cual muestras informacion
 * sobre la aplicacion.
 */

public class AcercaDe extends AppCompatActivity {

    private String TAG="AcercaDe";
    private Context context;

    /**
     * Metodo que inicia los componentes de la actividad, en este caso el toolbar
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_de);



        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbarApp);


        if(toolbar !=null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(getResources().getText(R.string.title_activity_acerca_de));
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }else{
            MyLog.d(TAG,"Error, no carga Toolbar");
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
