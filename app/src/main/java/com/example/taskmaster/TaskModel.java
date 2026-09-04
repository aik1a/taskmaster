package com.example.taskmaster;

public class TaskModel {
    private String nombre;
    private String categoria;
    private String prioridad;
    private float dificultad;
    private boolean completada;

    public TaskModel(String nombre, String categoria, String prioridad, float dificultad) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.prioridad = prioridad;
        this.dificultad = dificultad;
        this.completada = false;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public float getDificultad() {
        return dificultad;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }
}
