package com.example.testapp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

/**
 * Clase java de repositorio.
 */

public class PreguntasRepositorio {

    private static SQLiteHelper sqlh;
    private static SQLiteDatabase sqldb;
    private static Cursor c;
    private static String TAG="Repositorio";


    /**
     * Meotodo para insertar una pregunta en la base de datos.
     * @param q
     * @param context
     * @return
     */
    public static boolean insertQuestion (Question q, Context context){


    sqlh = new SQLiteHelper(context, Constantes.BBDD,null,1);
    sqldb = sqlh.getWritableDatabase();

    if(sqldb !=null){
        try {
            ContentValues val = new ContentValues();
            val.put(Constantes.titulo, q.getTitulo());
            val.put(Constantes.categoria, q.getCategoria());
            val.put(Constantes.correcta, q.getCorrecta());
            val.put(Constantes.incorrecta1, q.getIncorrecta1());
            val.put(Constantes.incorrecta2, q.getIncorrecta2());
            val.put(Constantes.incorrecta3, q.getIncorrecta3());
            val.put(Constantes.img64, q.getImg64());

            sqldb.insert(Constantes.tabla, null, val);

        }catch (SQLException e){
            MyLog.d(TAG, "no insert");
            return false;
        }

        sqldb.close();
        MyLog.d(TAG, "si insert");
        return true;

    }else{
        return false;
    }

}

    /**
     * MEtodo para obtener la base de datos completa
     * @param context
     * @return
     */
    public static ArrayList<Question> obtenerBBDD(Context context){
    ArrayList<Question> questions= new ArrayList<>();
    sqlh = new SQLiteHelper(context, Constantes.BBDD,null,1);
    sqldb = sqlh.getWritableDatabase();

     c= sqldb.rawQuery(Constantes.selectTable, null);
    if(c != null){
        if(c.moveToFirst()){
            do{
                int id = c.getInt(c.getColumnIndex(Constantes.id));
                String titulo = c.getString(c.getColumnIndex(Constantes.titulo));
                String categoria = c.getString(c.getColumnIndex(Constantes.categoria));
                String correcta = c.getString(c.getColumnIndex(Constantes.correcta));
                String incorrecta1 = c.getString(c.getColumnIndex(Constantes.incorrecta1));
                String incorrecta2 = c.getString(c.getColumnIndex(Constantes.incorrecta2));
                String incorrecta3 = c.getString(c.getColumnIndex(Constantes.incorrecta3));
                String img64 = c.getString(c.getColumnIndex(Constantes.img64));

                questions.add(new Question(id, titulo, categoria, correcta, incorrecta1, incorrecta2, incorrecta3, img64));
            }while(c.moveToNext());
        }
    }
    c.close();
    sqldb.close();

    return questions;
}

    /**
     * Metodo que nos devuelve una pregunta a traces de su id.
     * @param idQuestion
     * @param context
     * @return
     */
    public static Question selectQuestion(int idQuestion, Context context){
    sqlh = new SQLiteHelper(context, Constantes.BBDD,null,1);
    sqldb = sqlh.getWritableDatabase();

    String[] args= new String[]{
            Integer.toString(idQuestion)
    };

     c= sqldb.rawQuery(Constantes.selectToModify, args);

    if (c != null) {
        if (c.moveToFirst()) {

            int id = c.getInt(c.getColumnIndex(Constantes.id));
            String titulo = c.getString(c.getColumnIndex(Constantes.titulo));
            String categoria = c.getString(c.getColumnIndex(Constantes.categoria));
            String correcta = c.getString(c.getColumnIndex(Constantes.correcta));
            String incorrecta1 = c.getString(c.getColumnIndex(Constantes.incorrecta1));
            String incorrecta2 = c.getString(c.getColumnIndex(Constantes.incorrecta2));
            String incorrecta3 = c.getString(c.getColumnIndex(Constantes.incorrecta3));
            String img64 = c.getString(c.getColumnIndex(Constantes.img64));

            Question q = new Question(id, titulo, categoria, correcta, incorrecta1, incorrecta2, incorrecta3, img64);

            return q;
        }
    }
    c.close();
    sqldb.close();
    return null;

}

    /**
     * Metodo que nos devuelve la categoria.
     * @param context
     * @return
     */
    public static ArrayList<String> selectCategoria( Context context){
    ArrayList<String> categorias = new ArrayList<>();
    sqlh = new SQLiteHelper(context, Constantes.BBDD,null,1);
    sqldb = sqlh.getWritableDatabase();

    c= sqldb.rawQuery(Constantes.selectCat, null);

    if(c !=null){
        if(c.moveToFirst()){
            do {
                String categoria = c.getString(c.getColumnIndex(Constantes.categoria));
                categorias.add(categoria);
            }while (c.moveToNext());
        }
    }
    c.close();
    sqldb.close();
    return categorias;

}

    /**
     * Metodo para acualizar una pregunta de la base de datos.
     * @param q
     * @param context
     * @param id
     * @return
     */
    public static boolean updateQuestion(Question q, Context context, int id){
    sqlh = new SQLiteHelper(context, Constantes.BBDD,null,1);
    sqldb = sqlh.getWritableDatabase();

    if(sqldb != null){
        try {
            ContentValues val = new ContentValues();
            val.put(Constantes.titulo, q.getTitulo());
            val.put(Constantes.categoria, q.getCategoria());
            val.put(Constantes.correcta, q.getCorrecta());
            val.put(Constantes.incorrecta1, q.getIncorrecta1());
            val.put(Constantes.incorrecta2, q.getIncorrecta2());
            val.put(Constantes.incorrecta3, q.getIncorrecta3());
            val.put(Constantes.img64, q.getImg64());

            String[] args = new String[]{
                    Integer.toString(id)
            };
            sqldb.update(Constantes.tabla, val, "id=?", args);
        }catch(SQLException e){
            MyLog.d(TAG, "no modifica");
            return false;
        }

        sqldb.close();
        MyLog.d(TAG,"modificado");
        return true;
    }else{
        return false;
    }

}

    /**
     * Metodo que nos dice el numero de categorias que hay en la base de datos.
     * @param context
     * @return
     */
    public static int numCat(Context context){
    sqlh = new SQLiteHelper(context, Constantes.BBDD,null,1);
    sqldb = sqlh.getWritableDatabase();

    int contador=0;

    if(sqldb != null){
        c= sqldb.rawQuery(Constantes.numCat, null);
        if(c.moveToFirst()){
            do{
                contador=c.getInt(0);
            }while (c.moveToNext());
        }
    }
    return contador;

}

    /**
     * Metodo que cuenta el numero de preguntas que hay en la base de datos.
     * @param context
     * @return
     */

public static int contQues(Context context){
    sqlh = new SQLiteHelper(context, Constantes.BBDD,null,1);
    sqldb = sqlh.getWritableDatabase();

    int contador=0;

    if(sqldb !=null){
        c= sqldb.rawQuery(Constantes.numQues, null);
        if(c.moveToFirst()){
            do{
                contador=c.getInt(0);
            }while(c.moveToNext());
        }
    }
    return contador;
}

    /**
     * Metodo para eliminar una preguntas de la base de datos.
     * @param context
     * @param id
     * @return
     */
    public static boolean deleleteQuestion(Context context, int id){
    sqlh = new SQLiteHelper(context, Constantes.BBDD,null,1);
    sqldb = sqlh.getWritableDatabase();

    if(sqldb !=null){

        MyLog.d(TAG, "conextado eliminar");
        try{

            String[] args = new String[]{
                    Integer.toString(id)
            };

            sqldb.delete(Constantes.tabla,"id = ?", args);


        }catch(SQLException e){
            MyLog.d(TAG, "no borrado");
            return false;

        }

        sqldb.close();
        MyLog.d(TAG, "borrado");
        return true;

    }else{
        MyLog.d(TAG, "sin conexion eliminar");
        return false;
    }

}

    /**
     * Metodo que crea un esquema de xml para añadirlo al fichero.
     * @param context
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalStateException
     * @throws IOException
     */
    public static String CreateXMLString(Context context) throws IllegalArgumentException, IllegalStateException, IOException
    {
        ArrayList<Question> preguntasXML = new ArrayList<Question>();
        preguntasXML= obtenerBBDD(context);


        XmlSerializer xmlSerializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();

        xmlSerializer.setOutput(writer);

        //Start Document
        xmlSerializer.startDocument("UTF-8", true);
        xmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

        //Open Tag <file>
        xmlSerializer.startTag("", "TestApp");

        for (Question q: preguntasXML) {

            xmlSerializer.startTag("", "pregunta");
            xmlSerializer.attribute("", "type", "multichoice");
            xmlSerializer.startTag("", "titulo");
            xmlSerializer.text(q.getTitulo());
            xmlSerializer.endTag("", "titulo");
            xmlSerializer.startTag("", "category");
            xmlSerializer.text(q.getCategoria());
            xmlSerializer.endTag("", "category");
            xmlSerializer.startTag("","correcta");

            xmlSerializer.text(q.getCorrecta());
            xmlSerializer.endTag("", "correcta");
            xmlSerializer.startTag("","incorrecta1");

            xmlSerializer.text(q.getIncorrecta1());
            xmlSerializer.endTag("", "incorrecta1");
            xmlSerializer.startTag("","incorrecta2");

            xmlSerializer.text(q.getIncorrecta2());
            xmlSerializer.endTag("", "incorrecta2");
            xmlSerializer.startTag("","incorrecta3");

            xmlSerializer.text(q.getIncorrecta3());
            xmlSerializer.endTag("", "incorrecta3");
            xmlSerializer.startTag("","imagen");
            xmlSerializer.attribute("", "path", "/");
            xmlSerializer.attribute("", "encoding", "base64");
            xmlSerializer.text(q.getImg64());
            xmlSerializer.endTag("", "imagen");
            xmlSerializer.endTag("","pregunta");
        }

        xmlSerializer.endTag("","TestApp");
        xmlSerializer.endDocument();
        return writer.toString();


    }

}
