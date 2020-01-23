package com.example.testapp;

import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Clase con metodos que se usara en la aplicacion.
 */

public class Metodos {


    /**
     * Metodo que coge un fichero xml del sistema y lo añade a un correo para mandarlo por email
     * @param contexto
     * @return
     */

    @SuppressWarnings("null")

    public static Intent exportarXML(Context contexto){
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/preguntasExportar");
        String fname = "preguntas.xml";
        File file = new File (myDir, fname);
        try
        {
            if (!myDir.exists()) {
                myDir.mkdirs();
            }
            if (file.exists ())
                file.delete ();
            FileWriter fw=new FileWriter(file);
            //Escribimos en el fichero un String
            fw.write(PreguntasRepositorio.CreateXMLString(contexto));
            //Cierro el stream
            fw.close();
        }
        catch (Exception ex)
        {
            MyLog.e("Ficheros", "Error. fichero no insertado en memoria interna");
        }
        String cadena = myDir.getAbsolutePath()+"/"+fname;
        Uri path = Uri.parse("file://"+cadena);

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","afdelrosal@iesfranciscodelosrios.es", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Preguntas Exportadas");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Relacion de preguntas de la aplicacion TestApp");
        emailIntent .putExtra(Intent.EXTRA_STREAM, path);
        return emailIntent;
    }

    /**
     * Meetodo que crea un codigo de identificando para una imagen mediante la palara "pic" y la fecha y hora del momento
     * en el que se almacena.
     *
     * @return "pic_" + date
     */

    public static String getFileCode() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss", java.util.Locale.getDefault());
        String date = dateFormat.format(new Date());

        return "pic_" + date;
    }





}
