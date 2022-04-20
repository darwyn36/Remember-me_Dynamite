package com.cdp.agenda.entidades;

public class Contactos {

    private int id;
    private String titulo;
    private String hora;
    private String fecha;
    private String direccion;
    private String descripcion;

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


    public String getHora(){

        return hora;
    }
    public void setHora(String hora) {
        this.hora =hora;
    }

    public String getFecha(){

        return fecha;
    }
    public void setFecha(String fecha){

        this.fecha = fecha;
    }

    public String getDireccion(){
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDescripcion() {

        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


}
