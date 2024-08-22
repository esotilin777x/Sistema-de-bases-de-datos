/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.mycompany.sbd_proyecto.modelo;

/**
 *
 * @author LENOVO
 */
public enum Rol {
    GERENTE("Gerente"),
    CAJERO("Cajero"),
    PROVEEDOR("Proveedor");

    private final String displayName;

    Rol(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}