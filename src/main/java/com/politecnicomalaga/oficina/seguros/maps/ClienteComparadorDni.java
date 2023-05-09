/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.politecnicomalaga.oficina.seguros.maps;

import java.util.Comparator;

/**
 *
 * @author noelia
 */
public class ClienteComparadorDni implements Comparator<Cliente> {
    @Override
    public int compare (Cliente c1, Cliente c2) {
        return c1.getDni().compareTo(c2.getDni());
    }   
}
