/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author manu_
 */
package com.Contratos.model;

import java.util.Map;

public class Contrato {
    private String id;
    private String fecha; // mantener String para parseo flexible (YYYY-MM-DD esperado)
    private String proveedor;
    private Double importe;
    private String objeto;
    private String organismo;
    private String tipoContrato;
    private Map<String,String> otros; // campos extra

    // Getters y setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public String getProveedor() { return proveedor; }
    public void setProveedor(String proveedor) { this.proveedor = proveedor; }
    public Double getImporte() { return importe; }
    public void setImporte(Double importe) { this.importe = importe; }
    public String getObjeto() { return objeto; }
    public void setObjeto(String objeto) { this.objeto = objeto; }
    public String getOrganismo() { return organismo; }
    public void setOrganismo(String organismo) { this.organismo = organismo; }
    public String getTipoContrato() { return tipoContrato; }
    public void setTipoContrato(String tipoContrato) { this.tipoContrato = tipoContrato; }
    public Map<String,String> getOtros() { return otros; }
    public void setOtros(Map<String,String> otros) { this.otros = otros; }
}
