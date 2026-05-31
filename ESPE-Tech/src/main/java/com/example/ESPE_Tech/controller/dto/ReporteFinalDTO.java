package com.example.ESPE_Tech.controller.dto;

import java.util.Map;

public class ReporteFinalDTO {
    private String paradigmaUtilizado;
    private long tiempoEjecucionNanos;
    private Map<String, CategoriaReporteDTO> reportePorCategoria;

    public ReporteFinalDTO(String paradigmaUtilizado, long tiempoEjecucionNanos, Map<String, CategoriaReporteDTO> reportePorCategoria) {
        this.paradigmaUtilizado = paradigmaUtilizado;
        this.tiempoEjecucionNanos = tiempoEjecucionNanos;
        this.reportePorCategoria = reportePorCategoria;
    }

    // Getters y Setters
    public String getParadigmaUtilizado() { return paradigmaUtilizado; }
    public long getTiempoEjecucionNanos() { return tiempoEjecucionNanos; }
    public Map<String, CategoriaReporteDTO> getReportePorCategoria() { return reportePorCategoria; }
}