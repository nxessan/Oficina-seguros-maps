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
public class ClienteComparadorApellNom implements Comparator<Cliente>{
    @Override
    public int compare(Cliente c1, Cliente c2) {
            int resultado = c1.getApellidos().compareTo(c2.getApellidos());
            if (resultado == 0) {
                resultado = c1.getNombre().compareTo(c2.getNombre());
            }
            return resultado;
        }
}
