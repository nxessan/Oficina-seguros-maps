/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.politecnicomalaga.oficina.seguros.maps;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

    
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Map;


/**
 *
 * @author noelia
 */
public class OficinaSegurosMaps {

    private static Oficina miOficina;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        boolean seguir = true;
        int opcion;

        do {
            //menú principal

            //mostrar menú
            mostrarMenuPrincipal();
            opcion = leerIntTeclado(sc);
            if (opcion > 1 && opcion < 7 && OficinaSegurosMaps.miOficina == null) {
                System.out.println(ProjectStrings.OFICINANULL);
            } else {
                switch (opcion) {
                    case 1:
                        generarOficina(sc);
                        break;

                    case 2:
                        altaCliente(sc);

                        break;
                    case 3:
                        eliminaCliente(sc);

                        break;
                    case 4:
                        modificaCliente(sc);

                        break;
                    case 5:
                        buscaClientes(sc);

                        break;
                    case 6:
                        listaClientes();

                        break;
                    case 7:
                        saveOficina();
                        break;
                    case 8:
                        loadOficina(sc);
                        break;
                    case -1: //Opción de entrada errónea 
                        System.out.println(ProjectStrings.OPCION_NO_VALIDA);
                        break;
                    default:
                        seguir = false;
                }
            } //Else oficina is not null
        } while (seguir);
        System.out.println("Fin de la aplicación");

    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n\n\nMenú Opciones Proyecto Seguro-Incidencias");
        System.out.println("-------------------------------------------------\n");
        System.out.println("1. Dar de alta/modificar datos de la Oficina de Seguros");
        System.out.println("2. Alta de cliente");
        System.out.println("3. Eliminar cliente");
        System.out.println("4. Modificar datos de cliente");
        System.out.println("5. Buscar cliente/s");
        System.out.println("6. Listar cliente/s");
        System.out.println("7. Guardar oficina");
        System.out.println("8. Cargar oficina");
        System.out.println("Cualquier otra opción: Salir");

    }

    //Recogemos un número de teclado, si nos dan algo que no es un número, ponemos -1 para repetir entrada
    private static int leerIntTeclado(Scanner sc) {
        int iOpcion;
        try {
            iOpcion = sc.nextInt();
            sc.nextLine();
        } catch (InputMismatchException e) {
            sc.nextLine();
            return -1;
        }
        return iOpcion;
    }

    //Recogemos un número float de teclado, si nos dan algo que no es un número float, ponemos -1f 
    private static float leerFloatTeclado(Scanner sc) {
        float fOpcion;
        try {
            fOpcion = sc.nextFloat();
            sc.nextLine();
        } catch (InputMismatchException e) {
            sc.nextLine();
            return -1f;
        }
        return fOpcion;
    }

    //Recoger un string de teclado
    private static String leerStringTeclado(Scanner sc) {

        String sEntrada;
        try {
            sEntrada = sc.nextLine();
        } catch (InputMismatchException e) {
            return "";
        }
        return sEntrada;
    }

    private static void generarOficina(Scanner sc) {
        //Recoger datos oficina
        String codOfi, nombre, direccion, telefono, email;

        System.out.println("Código de la oficina:");
        codOfi = leerStringTeclado(sc);
        System.out.println("Nombre de la oficina:");
        nombre = leerStringTeclado(sc);
        System.out.println("Dirección de la oficina:");
        direccion = leerStringTeclado(sc);
        System.out.println("Teléfono de la oficina:");
        telefono = leerStringTeclado(sc);
        System.out.println("Email de la oficina:");
        email = leerStringTeclado(sc);

        //Crear/modificar oficina
        if (miOficina == null) {
            miOficina = new Oficina(codOfi, nombre, direccion, telefono, email);
        } else {
            //Setters
            miOficina.setCodOfi(codOfi);
            miOficina.setNombre(nombre);
            miOficina.setDireccion(direccion);
            miOficina.setTelefono(telefono);
            miOficina.setEmail(email);
        }
    }

    //Submenú / Interfaz texto para gestionar clientes
    private static void altaCliente(Scanner sc) {

        //Mostrar submenú
        System.out.println("SubMenú Proyecto Oficina Seguros");
        System.out.println("-------------------------------------------------\n");
        System.out.println("ALTA CLIENTE\n");
        //Recoger datos cliente
        String dni, codPoliza, nombre, apellidos, direccion, email, telefono;

        System.out.println("DNI");
        dni = leerStringTeclado(sc);
        System.out.println("Código de Póliza");
        codPoliza = leerStringTeclado(sc);
        System.out.println("Nombre");
        nombre = leerStringTeclado(sc);
        System.out.println("Apellidos");
        apellidos = leerStringTeclado(sc);
        System.out.println("Dirección");
        direccion = leerStringTeclado(sc);
        System.out.println("Email");
        email = leerStringTeclado(sc);
        System.out.println("Telefono");
        telefono = leerStringTeclado(sc);

        Cliente c = new Cliente(dni, codPoliza, nombre, apellidos, direccion, email, telefono);

        //Crear cliente
        if (miOficina.addCliente(c)) {
            System.out.println(ProjectStrings.ALTACLIENTE_OK);
        } else {
            System.out.println(ProjectStrings.ALTACLIENTE_ERROR);
        }

    }

    private static void modificaCliente(Scanner sc) {
        String dni, campo;
        Cliente.AtributosCliente atributoAModificar;
        int opcion;

        System.out.println("SubMenú Proyecto Oficina-Seguros");
        System.out.println("-------------------------------------------------\n");
        System.out.println("MODIFICAR CLIENTE\n");
        System.out.println("Escriba el dni del cliente:");
        dni = leerStringTeclado(sc);
        System.out.println("Escoja el campo a modificar:");
        System.out.println("1. Dni");
        System.out.println("2. Código de Póliza");
        System.out.println("3. Nombre");
        System.out.println("4. Apellidos");
        System.out.println("5. Dirección");
        System.out.println("5. Teléfono");
        System.out.println("Cualquier otra cosa. Email");
        opcion = leerIntTeclado(sc);
        switch (opcion) {
            case 1:
                atributoAModificar = Cliente.AtributosCliente.DNI;
                break;
            case 2:
                atributoAModificar = Cliente.AtributosCliente.CODPOLIZA;
                break;
            case 3:
                atributoAModificar = Cliente.AtributosCliente.NOMBRE;
                break;
            case 4:
                atributoAModificar = Cliente.AtributosCliente.APELLIDOS;
                break;
            case 5:
                atributoAModificar = Cliente.AtributosCliente.DIRECCION;
                break;
            case 6:
                atributoAModificar = Cliente.AtributosCliente.TELEFONO;
                break;
            default:
                atributoAModificar = Cliente.AtributosCliente.EMAIL;
        }
        System.out.println("Ahora introduzca el nuevo valor del campo seleccionado:");
        campo = leerStringTeclado(sc);
        if (miOficina.actualizaCliente(dni, campo, campo, campo, dni, dni, campo)) {
            System.out.println(ProjectStrings.UPDATECLIENTE_OK);
        } else {
            System.out.println(ProjectStrings.ALTACLIENTE_ERROR + dni);
        }
    }

    private static void eliminaCliente(Scanner sc) {
        //Mostrar submenú
        System.out.println("SubMenú Proyecto Oficina Seguros");
        System.out.println("-------------------------------------------------\n");
        System.out.println("ELIMINAR CLIENTE\n");
        //Recoger datos Cliente
        String dni;

        System.out.println("Dni");
        dni = leerStringTeclado(sc);

        //Eliminar cliente
        if (miOficina.eliminaCliente(dni)) {
            System.out.println(ProjectStrings.ELIMINACLIENTE_OK);
        } else {
            System.out.println(ProjectStrings.ELIMINACLIENTE_ERROR);
        }
    }

    private static void buscaClientes(Scanner sc) {
        Cliente[] lista;
        String campo;
        Cliente.AtributosCliente atributoBusqueda;
        int opcion;

        System.out.println("SubMenú Proyecto Oficina Seguros");
        System.out.println("-------------------------------------------------\n");
        System.out.println("BUSCAR CLIENTE/S\n");
        System.out.println("Escoja el campo a buscar:");
        System.out.println("1. Dni");
        System.out.println("2. Código de Póliza");
        System.out.println("3. Nombre");
        System.out.println("4. Apellidos");
        System.out.println("5. Dirección");
        System.out.println("5. Teléfono");
        System.out.println("Cualquier otra cosa. Email");
        opcion = leerIntTeclado(sc);
        switch (opcion) {
            case 1:
                atributoBusqueda = Cliente.AtributosCliente.DNI;
                break;
            case 2:
                atributoBusqueda = Cliente.AtributosCliente.CODPOLIZA;
                break;
            case 3:
                atributoBusqueda = Cliente.AtributosCliente.NOMBRE;
                break;
            case 4:
                atributoBusqueda = Cliente.AtributosCliente.APELLIDOS;
                break;
            case 5:
                atributoBusqueda = Cliente.AtributosCliente.DIRECCION;
                break;
            case 6:
                atributoBusqueda = Cliente.AtributosCliente.TELEFONO;
                break;
            default:
                atributoBusqueda = Cliente.AtributosCliente.EMAIL;
        }
        System.out.println("Ahora introduzca el valor a buscar del campo seleccionado:");
        campo = leerStringTeclado(sc);
        lista = miOficina.buscaClientes(campo, atributoBusqueda);
        if (lista != null && lista.length > 0) {
            System.out.println("Pacientes encontrados:");
            mostrarListaClientes(lista);
            System.out.println("Si desea consultar/gestionar inciencias de un Cliente, teclee su dni");
            System.out.println("En otro caso pulse Intro para volver al menú principal");
            String dni = leerStringTeclado(sc);
            if (dni.length() > 0) {
                Cliente[] unPaciente = miOficina.buscaClientes(dni, Cliente.AtributosCliente.DNI);
                if (unPaciente != null && unPaciente.length == 1) {
                    subMenuIncidencias(unPaciente[0], sc);
                } else {
                    System.out.println(ProjectStrings.BUSQUEDA_NO_ENCUENTRA);
                }

            }

        } else {
            System.out.println(ProjectStrings.BUSQUEDA_NO_ENCUENTRA);
        }
    }

    private static void listaClientes() {
        mostrarListaClientes(miOficina.todosClientes());

    }

    private static void mostrarListaClientes(Cliente[] lista) {
        if (lista != null) {
            System.out.println("Lista de clientes:");
            for (Cliente p : lista) {
                System.out.println(p.toString());
            }
        } else {
            System.out.println("0 clientes");
        }
    }

    //Interfaz - Texto para incidencias
    //Submenú para gestión de las incidencias.
    public static void subMenuIncidencias(Cliente p, Scanner sc) {
        Cliente[] lista;
        String campo;
        Incidencia.AtributosIncidencias atributoBusqueda;
        int opcion;
        boolean seguir = true;

        do {
            System.out.println("SubMenú Proyecto Oficina Seguros");
            System.out.println("-------------------------------------------------\n");
            System.out.println("Incidencias del cliente:\n");
            //System.out.println(p.toString());
            System.out.println("Escoja la opción a realizar:");
            System.out.println("1. Abrir incidencia");
            System.out.println("2. Abrir incidencia ajena");
            System.out.println("3. Abrir incidencia urgente");
            System.out.println("4. Cerrar incidencia");
            System.out.println("5. Buscar incidencia");
            System.out.println("6. Listar incidencias");
            System.out.println("Cualquier otra opción: volver al menú principal");
            opcion = leerIntTeclado(sc);
            switch (opcion) {
                case 1:
                    abrirIncidencia(p, sc);
                    break;
                case 2:
                    abrirIncidenciaAjena(p, sc);
                    break;
                case 3:
                    abrirIncidenciaUrgente(p, sc);
                    break;
                case 4:
                    cerrarIncidencia(p, sc);
                    break;
                case 5:
                    buscarIncidencias(p, sc);
                    break;
                case 6:
                    listarIncidencias(p);
                    break;
                default:
                    seguir = false;
            }
        } while (seguir);
        System.out.println("Saliendo del submenú de Incidencias...\n\n\n");

    }

    private static void abrirIncidencia(Cliente p, Scanner sc) {
        //Mostrar submenú
        System.out.println("SubMenú Proyecto Oficina Seguros");
        System.out.println("-------------------------------------------------\n");
        System.out.println("NUEVA INCIDENCIA\n");
        //Recoger datos incidencia
        String fechaSuceso, hora, matPropia, matAjena, descripcion;

        System.out.println("Fecha del suceso");
        fechaSuceso = leerStringTeclado(sc);
        System.out.println("Hora");
        hora = leerStringTeclado(sc);
        System.out.println("Matrícula de su vehículo");
        matPropia = leerStringTeclado(sc);
        System.out.println("Matrícula del vehículo con el que ha tenido el percance");
        matAjena = leerStringTeclado(sc);
        System.out.println("Descripción");
        descripcion = leerStringTeclado(sc);

        //Crear incidencia
        if (p.nuevaIncidencia(fechaSuceso, hora, matPropia, matAjena, descripcion)) {
            System.out.println(ProjectStrings.ALTA_INCIDENCIA_OK);
        } else {
            System.out.println(ProjectStrings.ALTA_INCIDENCIA_ERROR);
        }
    }

    private static void abrirIncidenciaAjena(Cliente p, Scanner sc) {
        //Mostrar submenú
        System.out.println("SubMenú Proyecto Oficina Seguros");
        System.out.println("-------------------------------------------------\n");
        System.out.println("NUEVA INCIDENCIA AJENA\n");
        //Recoger datos incidencia
        String fechaSuceso, hora, matPropia, matAjena, descripcion, dniAjeno;

        System.out.println("Fecha del suceso");
        fechaSuceso = leerStringTeclado(sc);
        System.out.println("Hora");
        hora = leerStringTeclado(sc);
        System.out.println("Matrícula de su vehículo");
        matPropia = leerStringTeclado(sc);
        System.out.println("Matrícula del vehículo con el que ha tenido el percance");
        matAjena = leerStringTeclado(sc);
        System.out.println("Descripción");
        descripcion = leerStringTeclado(sc);
        System.out.println("DNI Ajeno");
        dniAjeno = leerStringTeclado(sc);

        //Crear incidencia ajena
        if (p.nuevaIncidenciaAjena(fechaSuceso, hora, matPropia, matAjena, descripcion, dniAjeno)) {
            System.out.println(ProjectStrings.ALTA_INCIDENCIA_OK);
        } else {
            System.out.println(ProjectStrings.ALTA_INCIDENCIA_ERROR);
        }
    }

    private static void abrirIncidenciaUrgente(Cliente p, Scanner sc) {
        //Mostrar submenú
        System.out.println("SubMenú Proyecto Oficina Seguros");
        System.out.println("-------------------------------------------------\n");
        System.out.println("NUEVA INCIDENCIA URGENTE\n");
        //Recoger datos incidencia
        String fechaSuceso, hora, matPropia, matAjena, descripcion, maxDias;

        System.out.println("Fecha del suceso");
        fechaSuceso = leerStringTeclado(sc);
        System.out.println("Hora");
        hora = leerStringTeclado(sc);
        System.out.println("Matrícula de su vehículo");
        matPropia = leerStringTeclado(sc);
        System.out.println("Matrícula del vehículo con el que ha tenido el percance");
        matAjena = leerStringTeclado(sc);
        System.out.println("Descripción");
        descripcion = leerStringTeclado(sc);
        System.out.println("Máximo de dias");
        maxDias = leerStringTeclado(sc);

        //Crear incidencia
        if (p.nuevaIncidenciaUrgente(fechaSuceso, hora, matPropia, matAjena, descripcion, maxDias)) {
            System.out.println(ProjectStrings.ALTA_INCIDENCIA_OK);
        } else {
            System.out.println(ProjectStrings.ALTA_INCIDENCIA_ERROR);
        }
    }

    private static void cerrarIncidencia(Cliente p, Scanner sc) {
        //Mostrar submenú
        System.out.println("SubMenú Proyecto Oficina-Seguros");
        System.out.println("-------------------------------------------------\n");
        System.out.println("CERRAR INCIDENCIA\n");
        //Recoger datos Paciente
        String codigo;

        System.out.println("Codigo");
        codigo = leerStringTeclado(sc);

        //Crear incidencia
        if (p.cerrarIncidencia(codigo)) {
            System.out.println(ProjectStrings.ELIMINA_INCIDENCIA_OK);
        } else {
            System.out.println(ProjectStrings.ELIMINA_INCIDENCIA_ERROR);
        }
    }

    private static void buscarIncidencias(Cliente p, Scanner sc) {
        Incidencia[] lista;
        String campo;
        Incidencia.AtributosIncidencias atributoBusqueda;
        int opcion;

        System.out.println("SubMenú Proyecto Oficina Seguros");
        System.out.println("-------------------------------------------------\n");
        System.out.println("BUSCAR INCIDENCIA/S\n");
        System.out.println("Escoja el campo a buscar:");
        System.out.println("1. Fecha Suceso");
        System.out.println("2. Hora");
        System.out.println("3. Matrícula Propia");
        System.out.println("4. Matrícula Ajena");
        System.out.println("5. Descripción");
        System.out.println("5. Abierta");
        System.out.println("Cualquier otra cosa. Código de incidencia");
        opcion = leerIntTeclado(sc);
        switch (opcion) {
            case 1:
                atributoBusqueda = Incidencia.AtributosIncidencias.FECHASUCESO;
                break;
            case 2:
                atributoBusqueda = Incidencia.AtributosIncidencias.HORA;
                break;
            case 3:
                atributoBusqueda = Incidencia.AtributosIncidencias.MATPROPIA;
                break;
            case 4:
                atributoBusqueda = Incidencia.AtributosIncidencias.MATPROPIA;
                break;
            case 5:
                atributoBusqueda = Incidencia.AtributosIncidencias.ABIERTA;
                break;
            default:
                atributoBusqueda = Incidencia.AtributosIncidencias.CODINCIDENCIA;
        }
        System.out.println("Ahora introduzca el valor a buscar del campo seleccionado:");
        System.out.println("NOTA: Para abiertas, poner true, para no abiertas, poner false");
        campo = leerStringTeclado(sc);
        lista = p.buscaIncidencias(campo, atributoBusqueda);
        if (lista != null && lista.length > 0) {
            System.out.println("Incidencias encontradas:");
            mostrarIncidencias(lista);
        } else {
            System.out.println("No existen incidencias con ese criterio de búsqueda");
        }
    }

    private static void listarIncidencias(Cliente p) {
        mostrarIncidencias(p.todasIncidencias());

    }

    private static void mostrarIncidencias(Incidencia[] lista) {
        try {
            System.out.println("Lista de incidencias:");
            Arrays.sort(lista); // Ordenar el arreglo utilizando compareTo()
            for (Incidencia i : lista) {
                System.out.println(i.toString());
            }
        } catch (NullPointerException npe) {
            System.out.println("No se encontraron incidencias.");
        }
    }

    private static void saveOficina() {
        if (ControladorFichero.writeText("Oficina.csv", miOficina.toCSV())) {
            System.out.println("Proceso de volcado a disco exitoso");
        } else {
            System.out.println("Error al escribir en disco. ¿Tiene espacio en el disco?");
        }

        String jsonOficina = new Gson().toJson(miOficina);

        if (ControladorFichero.writeText("oficina.json", jsonOficina)) {
            System.out.println("Proceso de volcado json a disco exitoso");
        } else {
            System.out.println("Error al escribir en disco. ¿Tiene espacio en el disco?");
        }
    }

    private static void loadOficina(Scanner sc) {
        System.out.println("Se van a recargar los datos desde disco");
        System.out.println("Todos los datos actuales serán sustituidos");
        System.out.println("¿Está seguro?(S para Sí; Otra letra para No) ");
        String respuesta = leerStringTeclado(sc);
        if (respuesta.equals("S")) {
            miOficina = new Oficina(ControladorFichero.readText("Oficina.csv"));
        }
    }
}
