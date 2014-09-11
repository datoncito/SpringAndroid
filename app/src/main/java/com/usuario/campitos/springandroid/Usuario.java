/*
 * Copyright (c) 2014. ESte código es libre de usarlo como se te de tu rechingada gana, siempre y cuando ponas que usaste coo plantilla mis clases. Va que va Culeross!!
 */

package com.usuario.campitos.springandroid;

/**
 * Created by campitos on 6/08/14.
 */
public class Usuario {
    String nombre;
    int edad;

    public Usuario() {
    }

    public Usuario(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
