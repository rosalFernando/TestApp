package com.example.testapp;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Clase Java que inicia los compenentes de la actividad Principal o main.
 */

public class MainActivity extends AppCompatActivity {
private String TAG="Main Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyLog.d(TAG,"iniciando onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int segCierre=5;


        new Handler().postDelayed(new Runnable() {
            public void run() {
                //En el primer activity hay que poner el nombre de la actividad en la que esta y en el segundo la actividad
                //a la que quieres que te lleve
                startActivity(new Intent(MainActivity.this, Resumen.class));
                finish();
            }
            //Aqui se ponen los milisegundos que tarda
        }, segCierre * 1000);

        MyLog.d(TAG,"cerrando onCreate");






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
