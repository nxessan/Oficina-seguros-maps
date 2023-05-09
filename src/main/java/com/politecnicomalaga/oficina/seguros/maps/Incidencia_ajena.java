package com.politecnicomalaga.oficina.seguros.maps;

/**
 *
 * @author noelia
 */
public class Incidencia_ajena extends Incidencia {

    private String dniAjeno;

    public Incidencia_ajena(String fechaSuceso, String hora, String matPropia, String matAjena, String descripcion, String codIncidencia, String dniAjeno) {
        super(fechaSuceso, hora, matPropia, matAjena, descripcion, codIncidencia);
        this.dniAjeno = dniAjeno;
    }

    public Incidencia_ajena(String sCsv) {
        super(sCsv);
        String[] columnas = sCsv.split(";");

        if (columnas[0].equals("Incidencia")) {
            this.dniAjeno = columnas[8];
        }
    }

    public String getDniAjeno() {
        return dniAjeno;
    }

    public void setDniAjeno(String dniAjeno) {
        this.dniAjeno = dniAjeno;
    }

    @Override
    public String toString() {
        return super.toString()+ "#          " + dniAjeno;
    }

    @Override
    public String toCSV() {
		return String.format("Incidencia;%s;%s;%s;%s;%s;%s;%b;%s\n", fechaSuceso, hora, matPropia, matAjena, descripcion, codIncidencia,abierta, dniAjeno);
	}   
}
