package com.example.testapp;


import android.os.Environment;

public class Constantes {


    public static final String REST_URL="http://www.testapp.com/api";
    public static final String BBDD = "testapp";
    public static final String tabla= "preguntas";
    public static final String id= "id";
    public static final String titulo= "titulo";
    public static final String categoria= "categoria";
    public static final String correcta = "correcta";
    public static final String incorrecta1 = "incorrecta1";
    public static final String incorrecta2 = "incorrecta2";
    public static final String incorrecta3 = "incorrecta3";
    public static final String img64 = "img64";

    public static final String createTable= "CREATE TABLE "+tabla+
            "("+id+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +titulo+ " TEXT, "+categoria+" TEXT, "+correcta+" TEXT, "+incorrecta1+" TEXT, "
            +incorrecta2+" TEXT, "+incorrecta3+" TEXT, "+img64+" TEXT)";
    public static final String selectTable= " SELECT * FROM " + tabla + " ORDER BY "+titulo;
    public static final String selectCat = " SELECT DISTINCT "+categoria+" FROM Preguntas ORDER BY "+categoria;
    public static final String selectToModify = "SELECT * FROM " + tabla + " WHERE " + id + " = ?";
    public static final String numCat = "SELECT COUNT (DISTINCT "+categoria+") FROM "+tabla;
    public static final String numQues = "SELECT COUNT (DISTINCT "+id+") FROM "+tabla;
    public static final String delIfExist= "DROP TABLE IF EXISTS "+tabla;

    public static final String pathFotos= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/cameraImages";
    public static final int REQUEST_CAPTURE_IMAGE = 200;
    public static final int REQUEST_SELECT_IMAGE = 201;
    public static final int CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 123;
    public static final int CODE_WRITE_EXTERNAL_STORAGE_PERMISSION_CAMERA = 456;
    public static final int CODE_WRITE_EXTERNAL_STORAGE_PERMISSION_GALERY = 789;






}
