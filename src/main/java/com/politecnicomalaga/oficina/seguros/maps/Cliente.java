/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.politecnicomalaga.oficina.seguros.maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author noelia
 */
public class Cliente {

    public enum AtributosCliente {
        DNI, CODPOLIZA, NOMBRE, APELLIDOS, DIRECCION, EMAIL, TELEFONO
    };

    private String dni;
    private String codPoliza;
    private String nombre;
    private String apellidos;
    private String direccion;
    private String email;
    private String telefono;
    private int numIncidencias = 1;

    private Map<String, Incidencia> misIncidencias;
    List<Incidencia> misIncidenciasCSV = new ArrayList<Incidencia>();


    public Cliente(String dni, String codPoliza, String nombre, String apellidos, String direccion, String email, String telefono) {
        this.dni = dni;
        this.codPoliza = codPoliza;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        numIncidencias = 1;
        
        this.misIncidencias = new HashMap<>();

    }

    //Constructor para pasar de csv a string 
    public Cliente(String sCSV) {
        String[] lineas = sCSV.split("\n");
        String[] columnasCliente = lineas[0].split(";");
        if (columnasCliente[0].equals("Cliente")) {
            this.dni = columnasCliente[1];
            this.codPoliza = columnasCliente[2];
            this.nombre = columnasCliente[3];
            this.apellidos = columnasCliente[4];
            this.direccion = columnasCliente[5];
            this.email = columnasCliente[6];
            this.telefono = columnasCliente[7];
        } else {
            return;
        }

        misIncidencias = new HashMap<>();

        for (int i = 1; i < lineas.length; i++) {
            String[] columnasIncidencia = lineas[i].split(";");

            if (columnasIncidencia[0].equals("Incidencia")) {
                if (columnasIncidencia.length > 8 && columnasIncidencia[8].length() >= 1 && columnasIncidencia[8].length() <= 2) {
                    Incidencia in = new Incidencia_urgente(lineas[i]);
                    misIncidencias.put(in.getCodIncidencia(), in);
                } else if (columnasIncidencia.length > 8 && columnasIncidencia[8].length() > 2) {
                    Incidencia in = new Incidencia_ajena(lineas[i]);
                    misIncidencias.put(in.getCodIncidencia(), in);
                } else {
                    Incidencia in = new Incidencia(lineas[i]);
                    misIncidencias.put(in.getCodIncidencia(), in);
                }
            }
        }
    }
    
    //número de póliza del cliente, un guión y un id autonumérico generado por la aplicación    
    public String generarCodIncidencia() {
        String cadena = codPoliza + "-" + numIncidencias;
        numIncidencias++;
        return cadena;
    }

    public boolean nuevaIncidencia(String fechaSuceso, String hora, String matPropia, String matAjena, String descripcion) {

    Incidencia i = new Incidencia(fechaSuceso, hora, matPropia, matAjena, descripcion, generarCodIncidencia());
            Incidencia antigua = misIncidencias.put(i.getCodIncidencia(), i);
            if (antigua != null) {
                //Volvemos a poner el objeto que nos ha devuelto donde estaba, y devolvemos null porque no se ha podido añadir el nuevo objeto.
                misIncidencias.put(antigua.getCodIncidencia(), antigua);
                return false;
            }
                    return true;

        }
    

    public boolean nuevaIncidenciaAjena(String fechaSuceso, String hora, String matPropia, String matAjena, String descripcion, String dniAjeno) {

            Incidencia_ajena iAjena = new Incidencia_ajena(fechaSuceso, hora, matPropia, matAjena, descripcion, generarCodIncidencia(), dniAjeno);
            //El put devuelve un objeto si ya había uno con esa Key. Si no, devuelve null.      
            Incidencia antigua = misIncidencias.put(iAjena.getCodIncidencia(), iAjena);
            //Si ha devuelto un objeto...
            if (antigua != null) {
                //Volvemos a poner el objeto que nos ha devuelto donde estaba, y devolvemos null porque no se ha podido añadir el nuevo objeto.
                misIncidencias.put(antigua.getCodIncidencia(), antigua);
                return false;
            } 
            //antigua tiene null, es decir, no había una Incidencia en esa Key. Devuelvo true porque ya se ha añadido la nueva.
            return true;
    }

    public boolean nuevaIncidenciaUrgente(String fechaSuceso, String hora, String matPropia, String matAjena, String descripcion, String diasMax) {

        Incidencia_urgente iUrgente = new Incidencia_urgente(fechaSuceso, hora, matPropia, matAjena, descripcion, generarCodIncidencia(), diasMax);
            Incidencia antigua = misIncidencias.put(iUrgente.getCodIncidencia(), iUrgente);
            if (antigua != null) {
                //Volvemos a poner el objeto que nos ha devuelto donde estaba, y devolvemos null porque no se ha podido añadir el nuevo objeto.
                misIncidencias.put(antigua.getCodIncidencia(), antigua);
                return false;
            }
            //antigua tiene null, es decir, no había una Incidencia en esa Key. Devuelvo true porque ya se ha añadido la nueva.
            return true;
    }

    public boolean cerrarIncidencia(String codIncidencia) {
        if (misIncidencias.containsKey(codIncidencia)) {
            Incidencia incidencia = misIncidencias.get(codIncidencia);
            incidencia.setAbierta(false);
            return true;
        }
        return false;
    }
    
public Incidencia mostrarIncidenciaPorCodigo(String codIncidencia) {
        return misIncidencias.get(codIncidencia);
    }

    public Incidencia[] buscaIncidencias(String campoBusqueda, Incidencia.AtributosIncidencias atributoBusqueda) {
        List<Incidencia> resultado = new ArrayList<>();

        for (Incidencia incidencia : misIncidencias.values()) {
            if (incidencia.compara(campoBusqueda, atributoBusqueda)) {
                resultado.add(incidencia);
            }
        }

        if (resultado.size() > 0) {
            Incidencia[] listaIncidencias = new Incidencia[resultado.size()];
            return resultado.toArray(listaIncidencias);
        }
        return null;
    }

    public Incidencia[] todasIncidencias() {
        if (misIncidencias.size() == 0) {
            return null;
        }
        Incidencia[] listaIncidencias = new Incidencia[misIncidencias.size()];
        return misIncidencias.values().toArray(listaIncidencias);
    }

    public List<Incidencia> listarIncidencias() {
        return new ArrayList<>(misIncidencias.values());
    }

    public Incidencia_urgente[] listarIncidenciasUrgentes() {
        List<Incidencia_urgente> resultado = new ArrayList<>();
        for (Incidencia incidencia : misIncidencias.values()) {
            if (incidencia instanceof Incidencia_urgente) {
                resultado.add((Incidencia_urgente) incidencia);
            }
        }
        if (resultado.size() > 0) {
            Incidencia_urgente[] listaIncidencias = new Incidencia_urgente[resultado.size()];
            return resultado.toArray(listaIncidencias);
        }
        return null;
    }

    public boolean compara(String campo, AtributosCliente at) {
        switch (at) {
            case DNI:
                return this.dni.contains(campo);
            case CODPOLIZA:
                return this.codPoliza.contains(campo);
            case NOMBRE:
                return this.nombre.contains(campo);
            case APELLIDOS:
                return this.apellidos.contains(campo);
            case DIRECCION:
                return this.direccion.contains(campo);
            case EMAIL:
                return this.email.contains(campo);
            case TELEFONO:
                return this.telefono.contains(campo);
        }

        return false;
    }

    public void setValor(String campo, AtributosCliente at) {
        switch (at) {
            case DNI:
                this.setDni(campo);
                break;
            case NOMBRE:
                this.setNombre(campo);
                break;
            case APELLIDOS:
                this.setApellidos(campo);
                break;
            case DIRECCION:
                this.setDireccion(campo);
                break;
            case EMAIL:
                this.setEmail(campo);
                break;
            case TELEFONO:
                this.setTelefono(campo);
                break;
        }
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCodPoliza() {
        return codPoliza;
    }

    public void setCodPoliza(String codPoliza) {
        this.codPoliza = codPoliza;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Map<String, Incidencia> getMisIncidencias() {
        return misIncidencias;
    }

    public void setMisIncidencias(Map<String, Incidencia> misIncidencias) {
        this.misIncidencias = misIncidencias;
    }    

    @Override
    public String toString() {
        return String.format("%10s#%10s#%10s#%10s#%20s#%10s", dni, codPoliza, nombre, apellidos, direccion, email, telefono);
    }

    public String toCSV() {
    String cadena = String.format("Cliente;%s;%s;%s;%s;%s;%s;%s\n", dni, codPoliza, nombre, apellidos, direccion, email, telefono);
        for (Map.Entry<String, Incidencia> entry : this.misIncidencias.entrySet()) {
            cadena += entry.getValue().toCSV();
    }
    return cadena;
}

}
