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
public class IncidenciaComparadorFecha implements Comparator<Incidencia> {
    @Override
    public int compare (Incidencia i1, Incidencia i2) {
        return i1.getFechaSuceso().compareTo(i2.getFechaSuceso());
    }   
}
