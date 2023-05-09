package com.politecnicomalaga.oficina.seguros.maps;

/**
 *
 * @author noelia
 */
public class Incidencia_urgente extends Incidencia {

    protected String diasMax;

    public Incidencia_urgente(String fechaSuceso, String hora, String matPropia, String matAjena, String descripcion, String codIncidencia, String diasMax) {
        super(fechaSuceso, hora, matPropia, matAjena, descripcion, codIncidencia);
        this.diasMax = diasMax;
    }

    public Incidencia_urgente(String sCsv) {
        super(sCsv);
        String[] columnas = sCsv.split(";");

        if (columnas[0].equals("Incidencia")) {
            this.diasMax = columnas[8];
        }
    }

    @Override
    public String toString() {
        return super.toString()+ "#          " + diasMax;
    }

    @Override
    public String toCSV() {
		return String.format("Incidencia;%s;%s;%s;%s;%s;%s;%b;%s\n", fechaSuceso, hora, matPropia, matAjena, descripcion, codIncidencia,abierta, diasMax);
	}   
}
