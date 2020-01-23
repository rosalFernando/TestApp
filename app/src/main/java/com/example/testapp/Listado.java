package com.example.testapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Clase Java de la actividad Listado la cual muestra una lista con las
 * preguntas almacenadas en la base de datos.
 */

public class Listado extends AppCompatActivity {

    private String TAG= "Listado";
    private ArrayList<Question> ques;
    private Context context;

    /**
     * Metodo que se llama cuando se crea la actividad e inicia los componentes de esta.
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarApp);

        if(toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(getResources().getText(R.string.title_activity_listado));
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }else{
            MyLog.d(TAG,"Error de carga");
        }


        FloatingActionButton fab = findViewById(R.id.botonAniade);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              startActivity(new Intent(Listado.this, CrearEditar.class));
            }
        });


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
     *Metodo que mantiene la actividad activa mientras este recibiendo informacion, en este caso
     * recibira informacion de la base de datos en tiempo real para mostrar las nuevas preguntas
     * que se vayan añadiendo.
     */
    protected void onResume() {
        MyLog.d(TAG, "iniciado OnResume");
        super.onResume();


        TextView eliminarText = (TextView) findViewById(R.id.textViewListado);
        eliminarText.setVisibility(View.VISIBLE);


        if(PreguntasRepositorio.obtenerBBDD(context) != null){
            eliminarText.setVisibility(View.GONE);


            ques = PreguntasRepositorio.obtenerBBDD(context);
            final RecyclerView rwListado = (RecyclerView) findViewById(R.id.RVListado);
            QuestionAdapter qa = new QuestionAdapter(ques);

            qa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=rwListado.getChildAdapterPosition(v);

                    Intent intent= new Intent(Listado.this, CrearEditar.class);
                    intent.putExtra("id", ques.get(pos).getId());
                    startActivity(intent);
                }
            });

            rwListado.setAdapter(qa);
            rwListado.setLayoutManager(new LinearLayoutManager(context));

            MyLog.d(TAG, "cerrando onResumen");

        }
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
