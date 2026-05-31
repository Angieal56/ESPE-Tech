package com.example.ESPE_Tech.service;

import com.example.ESPE_Tech.controller.dto.CategoriaReporteDTO;
import com.example.ESPE_Tech.persistence.HardwareEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InventarioService {

    // PARADIGMA IMPERATIVO (Estilo Tradicional)
    public Map<String, CategoriaReporteDTO> procesarImperativo(List<HardwareEntity> todosLosEquipos) {

        Map<String, BigDecimal> sumaPrecios = new HashMap<>();
        Map<String, Integer> contadorEquipos = new HashMap<>();
        Map<String, HardwareEntity> equipoMasCaroPorCategoria = new HashMap<>();


        LocalDate fechaLimite = LocalDate.now().minusYears(5);

        // 1. Bucle for-each tradicional para recorrer los 10,000 registros uno por uno
        for (HardwareEntity equipo : todosLosEquipos) {


            if (equipo.getFechaCompra().isAfter(fechaLimite) && "ACTIVO".equalsIgnoreCase(equipo.getEstado())) {
                String cat = equipo.getCategoria();


                if (!sumaPrecios.containsKey(cat)) {
                    sumaPrecios.put(cat, BigDecimal.ZERO);
                    contadorEquipos.put(cat, 0);
                }


                sumaPrecios.put(cat, sumaPrecios.get(cat).add(equipo.getPrecio()));


                contadorEquipos.put(cat, contadorEquipos.get(cat) + 1);


                if (!equipoMasCaroPorCategoria.containsKey(cat)) {
                    equipoMasCaroPorCategoria.put(cat, equipo);
                } else {
                    HardwareEntity actualMasCaro = equipoMasCaroPorCategoria.get(cat);
                    if (equipo.getPrecio().compareTo(actualMasCaro.getPrecio()) > 0) {
                        equipoMasCaroPorCategoria.put(cat, equipo);
                    }
                }
            }
        }

        // 2. DTO final combinando los mapas de acumulación
        Map<String, CategoriaReporteDTO> resultadoFinal = new HashMap<>();
        for (String cat : sumaPrecios.keySet()) {
            BigDecimal total = sumaPrecios.get(cat);
            int cantidad = contadorEquipos.get(cat);
            HardwareEntity masCaro = equipoMasCaroPorCategoria.get(cat);

            double promedio = 0.0;
            if (cantidad > 0) {

                promedio = total.divide(BigDecimal.valueOf(cantidad), 2, RoundingMode.HALF_UP).doubleValue();
            }

            CategoriaReporteDTO dto = new CategoriaReporteDTO(
                    total.setScale(2, RoundingMode.HALF_UP),
                    promedio,
                    masCaro.getModelo() + " ($" + masCaro.getPrecio().setScale(2, RoundingMode.HALF_UP) + ")"
            );
            resultadoFinal.put(cat, dto);
        }

        return resultadoFinal;
    }
    // PARADIGMA FUNCIONAL / DECLARATIVO (Java Streams API)
    public Map<String, CategoriaReporteDTO> procesarFuncional(List<HardwareEntity> todosLosEquipos) {
        LocalDate fechaLimite = LocalDate.now().minusYears(5);


        return todosLosEquipos.stream()

                .filter(equipo -> equipo.getFechaCompra().isAfter(fechaLimite))
                .filter(equipo -> "ACTIVO".equalsIgnoreCase(equipo.getEstado()))

                .collect(java.util.stream.Collectors.groupingBy(
                        HardwareEntity::getCategoria,
                        java.util.stream.Collectors.collectingAndThen(
                                java.util.stream.Collectors.toList(),
                                listaPorCategoria -> {
                                    // valor total sumando con BigDecimal
                                    BigDecimal total = listaPorCategoria.stream()
                                            .map(HardwareEntity::getPrecio)
                                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                                    // Promedio aritmético
                                    double promedio = listaPorCategoria.isEmpty() ? 0.0 :
                                            total.divide(BigDecimal.valueOf(listaPorCategoria.size()), 2, RoundingMode.HALF_UP).doubleValue();

                                    // Equipo más caro usando max() y Optional
                                    String masCaro = listaPorCategoria.stream()
                                            .max(java.util.Comparator.comparing(HardwareEntity::getPrecio))
                                            .map(e -> e.getModelo() + " ($" + e.getPrecio().setScale(2, RoundingMode.HALF_UP) + ")")
                                            .orElse("Ninguno");

                                    return new CategoriaReporteDTO(total.setScale(2, RoundingMode.HALF_UP), promedio, masCaro);
                                }
                        )
                ));
    }
}