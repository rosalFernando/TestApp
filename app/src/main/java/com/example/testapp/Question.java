package com.example.testapp;

public class Question {
    private int id;
    private String titulo;
    private String categoria;
    private String correcta;
    private String incorrecta1;
    private String incorrecta2;
    private String incorrecta3;
    private String img64;

    public Question(int id, String titulo, String categoria, String correcta, String incorrecta1, String incorrecta2, String incorrecta3, String img64) {
        this.id = id;
        this.titulo = titulo;
        this.categoria = categoria;
        this.correcta = correcta;
        this.incorrecta1 = incorrecta1;
        this.incorrecta2 = incorrecta2;
        this.incorrecta3 = incorrecta3;
        this.img64 = img64;
    }

    public Question(String titulo, String categoria, String correcta, String incorrecta1, String incorrecta2, String incorrecta3, String img64) {
        this.titulo = titulo;
        this.categoria = categoria;
        this.correcta = correcta;
        this.incorrecta1 = incorrecta1;
        this.incorrecta2 = incorrecta2;
        this.incorrecta3 = incorrecta3;
        this.img64 = img64;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCorrecta() {
        return correcta;
    }

    public void setCorrecta(String correcta) {
        this.correcta = correcta;
    }

    public String getIncorrecta1() {
        return incorrecta1;
    }

    public void setIncorrecta1(String incorrecta1) {
        this.incorrecta1 = incorrecta1;
    }

    public String getIncorrecta2() {
        return incorrecta2;
    }

    public void setIncorrecta2(String incorrecta2) {
        this.incorrecta2 = incorrecta2;
    }

    public String getIncorrecta3() {
        return incorrecta3;
    }

    public void setIncorrecta3(String incorrecta3) {
        this.incorrecta3 = incorrecta3;
    }

    public String getImg64() {
        return img64;
    }

    public void setImg64(String img64) {
        this.img64 = img64;
    }
}
