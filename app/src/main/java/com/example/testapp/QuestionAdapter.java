package com.example.testapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Clase Adaptador la cual sera utilizada para
 * mostrar el titulo y la categoria en el listado de preguntas
 */

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.questionViewHolder> implements View.OnClickListener{
    private ArrayList<Question> items;
    private View.OnClickListener listener;

    /**
     *Clase interna que implementa el viewHolder
     *y almacena la vista y los datos
     */

    public static class questionViewHolder extends RecyclerView.ViewHolder{
        private TextView TextViewTit;
        private TextView TextViewCat;
        public questionViewHolder( View itemView) {
            super(itemView);
            TextViewTit=(TextView) itemView.findViewById(R.id.textViewTituloRow);
            TextViewCat=(TextView) itemView.findViewById(R.id.textViewCategoriaRow);

        }

        /**
         * Metodo que rellena los campos del view holder
         * @param ques
         */

        public void questionBind(Question ques){
            TextViewTit.setText(ques.getTitulo());
            TextViewCat.setText(ques.getCategoria());
        }
    }

    /**
     *constructor
     * @param question
     */
    public QuestionAdapter(@NonNull ArrayList<Question> question){
        this.items =question;

    }

    /**
     * Metodo que crea el view Holder.
     * @param parent
     * @param viewType
     * @return
     */

    @Override
    public questionViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
       View row = LayoutInflater.from(parent.getContext())
       .inflate(R.layout.row, parent, false);
        row.setOnClickListener(this);
       questionViewHolder qvh = new questionViewHolder(row);
       return qvh;
    }

    /**
     * Metodo que enlaza cada elemento y su posicion con el viewHolder.
     * @param viewHolder
     * @param position
     */

    @Override
    public void onBindViewHolder(questionViewHolder viewHolder, int position) {
        Question ques = items.get(position);
        viewHolder.questionBind(ques);
    }

    /**
     *Devuelve el numero de items del apadtador
     * @return
     */

    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * Asigna listener a cada elemento de la lista.
     * @param listener
     */

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    /**
     * Accion para cada elemento de la lista.
     * @param v
     */

    @Override
    public void onClick(View v) {

        if(listener !=null)listener.onClick(v);

    }


}
