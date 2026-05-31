package com.example.ESPE_Tech.controller; // <-- CORREGIDO: Sin el ".web"

import com.example.ESPE_Tech.controller.dto.ReporteFinalDTO;      // <-- CORREGIDO
import com.example.ESPE_Tech.controller.dto.CategoriaReporteDTO;  // <-- CORREGIDO
import com.example.ESPE_Tech.persistence.HardwareEntity;
import com.example.ESPE_Tech.service.DataGeneratorService;        // <-- CORREGIDO: Apunta a tu carpeta "service"
import com.example.ESPE_Tech.service.InventarioService;           // <-- CORREGIDO: Apunta a tu carpeta "service"
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/inventario")
public class InventarioController {

    private final DataGeneratorService dataGeneratorService;
    private final InventarioService inventarioService;

    // Inyección de dependencias por constructor (Arquitectura desacoplada limpia)
    public InventarioController(DataGeneratorService dataGeneratorService, InventarioService inventarioService) {
        this.dataGeneratorService = dataGeneratorService;
        this.inventarioService = inventarioService;
    }

    @GetMapping("/reporte-imperativo")
    public ReporteFinalDTO getReporteImperativo() {
        // Generar el conjunto masivo de datos
        List<HardwareEntity> datos = dataGeneratorService.generar10000Equipos();

        // Registrar tiempo inicial en nanosegundos
        long inicio = System.nanoTime();
        Map<String, CategoriaReporteDTO> resultado = inventarioService.procesarImperativo(datos);
        long fin = System.nanoTime();

        return new ReporteFinalDTO("Imperativo Tradicional (Bucles y Acumuladores)", (fin - inicio), resultado);
    }

    @GetMapping("/reporte-funcional")
    public ReporteFinalDTO getReporteFuncional() {
        // Generar el conjunto masivo de datos
        List<HardwareEntity> datos = dataGeneratorService.generar10000Equipos();

        // Registrar tiempo inicial en nanosegundos
        long inicio = System.nanoTime();
        Map<String, CategoriaReporteDTO> resultado = inventarioService.procesarFuncional(datos);
        long fin = System.nanoTime();

        return new ReporteFinalDTO("Declarativo / Funcional (Java Streams API)", (fin - inicio), resultado);
    }
}