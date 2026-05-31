package com.example.ESPE_Tech.controller.dto;

import java.math.BigDecimal;

public class CategoriaReporteDTO {
    private BigDecimal valorTotal;
    private double precioPromedio;
    private String equipoMasCaro;

    public CategoriaReporteDTO() {}

    public CategoriaReporteDTO(BigDecimal valorTotal, double precioPromedio, String equipoMasCaro) {
        this.valorTotal = valorTotal;
        this.precioPromedio = precioPromedio;
        this.equipoMasCaro = equipoMasCaro;
    }

    // Getters y Setters
    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }

    public double getPrecioPromedio() { return precioPromedio; }
    public void setPrecioPromedio(double precioPromedio) { this.precioPromedio = precioPromedio; }

    public String getEquipoMasCaro() { return equipoMasCaro; }
    public void setEquipoMasCaro(String equipoMasCaro) { this.equipoMasCaro = equipoMasCaro; }
}