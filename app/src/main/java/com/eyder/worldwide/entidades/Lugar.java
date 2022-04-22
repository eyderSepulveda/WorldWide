package com.eyder.worldwide.entidades;

import java.util.ArrayList;

public class Lugar {
    private int id;
    private String nombre;
    private Continente continente;
    private String fechaVisita;
    private ArrayList<TipoTurismo> tipoTurismo;
    private String transporte;
    private String descripcion;


    public Lugar(int id, String nombre, Continente continente, String fechaVisita, ArrayList<TipoTurismo> tipoTurismo, String transporte, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.continente = continente;
        this.fechaVisita = fechaVisita;
        this.tipoTurismo = tipoTurismo;
        this.transporte = transporte;
        this.descripcion = descripcion;
    }

    public Lugar(int id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Continente getContinente() {
        return continente;
    }

    public void setContinente(Continente continente) {
        this.continente = continente;
    }

    public String getFechaVisita() {
        return fechaVisita;
    }

    public void setFechaVisita(String fechaVisita) {
        this.fechaVisita = fechaVisita;
    }

    public ArrayList<TipoTurismo> getTipoTurismo() {
        return tipoTurismo;
    }

    public void setTipoTurismo(ArrayList<TipoTurismo> tipoTurismo) {
        this.tipoTurismo = tipoTurismo;
    }

    public String getTransporte() {
        return transporte;
    }

    public void setTransporte(String transporte) {
        this.transporte = transporte;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
