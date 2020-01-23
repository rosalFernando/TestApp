package com.example.testapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {
    public SQLiteHelper( Context context, String name,CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * Metodo que crea la base de datos.
     * @param db
     */

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constantes.createTable);

    }

    /**
     * Metodo para actualizar la base de datos.
     * @param db
     * @param oldVersion
     * @param newVersion
     */

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(Constantes.delIfExist);
        db.execSQL(Constantes.createTable);
    }
}
