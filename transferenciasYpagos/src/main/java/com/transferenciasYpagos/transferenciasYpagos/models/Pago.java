package com.transferenciasYpagos.transferenciasYpagos.models;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "pagoc34_14")
public class Pago {

    @Id
    private String id;
    private String pagos;
    private String normal;
    private String cifOrdenante;
    private String numero;
    private String fechaOperacion;
    private String ibanOrdenante;
    private String concepto;
    private String nombreOrdenante;
    private String ibanBeneficiario;
    private Double importe;
    private String nombreBeneficiario;
    private String gastos;


    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getPagos() {
        return pagos;
    }

    public void setPagos(String pagos) {
        this.pagos = pagos;
    }

    public String getNormal() {
        return normal;
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }

    public String getCifOrdenante() {
        return cifOrdenante;
    }

    public void setCifOrdenante(String cifOrdenante) {
        this.cifOrdenante = cifOrdenante;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getFechaOperacion() {
        return fechaOperacion;
    }

    public void setFechaOperacion(String fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }

    public String getIbanOrdenante() {
        return ibanOrdenante;
    }

    public void setIbanOrdenante(String ibanOrdenante) {
        this.ibanOrdenante = ibanOrdenante;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getNombreOrdenante() {
        return nombreOrdenante;
    }

    public void setNombreOrdenante(String nombreOrdenante) {
        this.nombreOrdenante = nombreOrdenante;
    }

    public String getIbanBeneficiario() {
        return ibanBeneficiario;
    }

    public void setIbanBeneficiario(String ibanBeneficiario) {
        this.ibanBeneficiario = ibanBeneficiario;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public String getNombreBeneficiario() {
        return nombreBeneficiario;
    }

    public void setNombreBeneficiario(String nombreBeneficiario) {
        this.nombreBeneficiario = nombreBeneficiario;
    }

    public String getGastos() {
        return gastos;
    }

    public void setGastos(String gastos) {
        this.gastos = gastos;
    }
}
