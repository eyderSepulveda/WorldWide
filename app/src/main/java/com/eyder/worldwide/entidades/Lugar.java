package com.eyder.worldwide.entidades;

public class Lugar {
    private int id;
    private String nombre;
    private String descripcion;

    public Lugar(int id, String nombre, String descripcion){
        this.id=id;
        this.nombre=nombre;
        this.descripcion=descripcion;
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getNombre() {
        return nombre;
    }
}
