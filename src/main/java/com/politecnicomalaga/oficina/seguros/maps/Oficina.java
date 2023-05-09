package com.politecnicomalaga.oficina.seguros.maps;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author noelia
 */
public class Oficina {

    private String codOfi;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;
    private int orden;

    Map<String, Cliente> misClientes = new HashMap<>();

    public Oficina() {
    }

    public Oficina(String codOfi, String nombre, String direccion, String telefono, String email) {
        this.codOfi = codOfi;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }

    public Oficina(String sCSV) {
        String[] lineas = sCSV.split("\n");
        //Me vendrá una línea mínimo para clinica
        String[] columnas = lineas[0].split(";");
        if (columnas[0].equals("Oficina")) {
            this.codOfi = columnas[1];
            this.nombre = columnas[2];
            this.direccion = columnas[3];
            this.telefono = columnas[4];
            this.email = columnas[5];
        } else {
            return;
        }
        //Después de 0 a n tratamientos
        this.misClientes = new HashMap<>();

        String[] clientesPosibles = sCSV.split("Cliente");
        String miClienteCSV;

        for (int i = 1; i < clientesPosibles.length; i++) {
            miClienteCSV = "Cliente" + clientesPosibles[i];
            Cliente c = new Cliente(miClienteCSV);
            misClientes.put(c.getDni(), c);
        }
    }

    public Cliente mostrarClientePorDNI(String dni) {
        int i = 0;
        boolean found = false;
        while (!found && i < misClientes.size()) {
            Cliente cliente = this.misClientes.get(i);
            if (cliente.getDni().equals(dni)) {
                found = true;
                return cliente;
            }
        }
        return null;
    }

    public boolean addCliente(Cliente cliente) {
        if (cliente.getDni() == null
                || cliente.getCodPoliza() == null
                || cliente.getNombre() == null
                || cliente.getApellidos().isEmpty()) {
            return false;
        }

        for (Cliente c : misClientes.values()) {
            // Si el dni introducido ya pertenece a un cliente, da error y no se crea el cliente.
            if (c.getDni().equals(cliente.getDni())) {
                return false;
            }
        }
        misClientes.put(cliente.getDni(), cliente);
        return true;
    }

    public boolean eliminaCliente(String dni) {
        boolean found = false;
        int i = 0;

        Cliente c = this.mostrarClientePorDNI(dni);

        while (!found && i < misClientes.size()) {
            Cliente cliente = misClientes.get(i);
            if (cliente.getDni().equals(dni)) {
                if (c.getMisIncidencias().isEmpty()) {
                    found = true;
                    misClientes.remove(cliente);
                    return true;
                }
            }
            i++;

        }
        return false;
    }

    public boolean actualizaCliente(String dni, String codPoliza, String nombre, String apellidos, String direccion, String email, String telefono) {
        boolean found = false;
        int i = 0;
        while (!found && i < misClientes.size()) {
            Cliente cliente = misClientes.get(i);
            if (cliente.getDni().equals(dni)) {
                found = true;
                cliente.setDni(dni);
                cliente.setNombre(nombre);
                cliente.setApellidos(apellidos);
                cliente.setDireccion(direccion);
                cliente.setEmail(email);
                cliente.setTelefono(telefono);
            }
        }
        i++;
        return false;
    }

    public Cliente buscaUnCliente(String dni) {
        for (Cliente p : misClientes.values()) {
            if (p.compara(dni, Cliente.AtributosCliente.DNI)) {
                return p;
            }
        }
        return null;
    }

    public Cliente[] buscaClientes(String campoBusqueda, Cliente.AtributosCliente atributoBusqueda) {
        ArrayList<Cliente> resultado = new ArrayList<>();
        for (Cliente p : misClientes.values()) {
            if (p.compara(campoBusqueda, atributoBusqueda)) {
                resultado.add(p);
            }
        }
        if (resultado.size() > 0) {
            Cliente[] listaP = new Cliente[resultado.size()];
            return resultado.toArray(listaP);
        }
        return null;
    }

    public Cliente[] buscarClientesApellidos(String apellidos) {
        List<Cliente> clientesEncontrados = new ArrayList<>();
        for (Cliente cliente : misClientes.values()) {
            if (cliente.getApellidos().equals(apellidos)) {
                clientesEncontrados.add(cliente);
            }
        }
        return clientesEncontrados.toArray(new Cliente[0]);
    }

    //Listar todos los clientes
    /* public Cliente[] todosClientes() {
        Cliente[] arrayClientes = new Cliente[misClientes.size()];
        if (misClientes.size() == 0) {
            return null;
        }
        Cliente[] listaCli = new Cliente[misClientes.size()];
        return misClientes.toArray(listaCli);
    }*/
    public Cliente[] todosClientes() {

        if (misClientes.size() == 0) {
            return null;
        }

        switch (orden) {

            case 0:
                ArrayList<Cliente> clientesOrdenadosNombre = new ArrayList<>(misClientes.values());
                Collections.sort(clientesOrdenadosNombre, new ClienteComparadorApellNom());
                return clientesOrdenadosNombre.toArray(new Cliente[clientesOrdenadosNombre.size()]);

            case 1:
                ArrayList<Cliente> clientesOrdenadosDNI = new ArrayList<>(misClientes.values());
                Collections.sort(clientesOrdenadosDNI, new ClienteComparadorDni());
                return clientesOrdenadosDNI.toArray(new Cliente[clientesOrdenadosDNI.size()]);

            case 2:
                ArrayList<Cliente> clientesOrdenadosApellNom = new ArrayList<>(misClientes.values());
                Collections.sort(clientesOrdenadosApellNom, new ClienteComparadorApellNom());
                return clientesOrdenadosApellNom.toArray(new Cliente[clientesOrdenadosApellNom.size()]);

            default:
                // orden desconocido, devolver todos los clientes sin ordenar
                return misClientes.values().toArray(new Cliente[misClientes.size()]);

        }
    }

    public boolean actualizaCliente(String dni, String campo, Cliente.AtributosCliente atrActualizar) {
        Cliente p = this.buscaUnCliente(dni);
        if (p != null) {
            p.setValor(campo, atrActualizar);
            return true;
        }
        return false;
    }

    public String getCodOfi() {
        return codOfi;
    }

    public void setCodOfi(String codOfi) {
        this.codOfi = codOfi;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, Cliente> getMisClientes() {
        return misClientes;
    }

    public void setMisClientes(Map<String, Cliente> misClientes) {
        this.misClientes = misClientes;
    }

    public String toCSV() {
        String cadena = String.format("Oficina;%s;%s;%s;%s;%s\n", codOfi, nombre, direccion, telefono, email);
        if (misClientes.isEmpty()) {
            return cadena;
        }
        for (Cliente c : misClientes.values()) {
            cadena += c.toCSV();
        }
        return cadena;
    }

}
