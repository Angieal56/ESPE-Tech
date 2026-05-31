package com.example.ESPE_Tech.service;

import com.example.ESPE_Tech.persistence.HardwareEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class DataGeneratorService {

    public List<HardwareEntity> generar10000Equipos() {
        List<HardwareEntity> equipos = new ArrayList<>();
        String[] categorias = {"Laptop", "PC", "Servidor"};
        String[] estados = {"ACTIVO", "DEBAJA"};
        Random random = new Random();

        for (long i = 1; i <= 10000; i++) {
            String category = categorias[random.nextInt(categorias.length)];
            String modelo = "Modelo-" + category + "-" + i;

            // Precios aleatorios entre $300.00 y $5300.00
            BigDecimal precio = BigDecimal.valueOf(300 + (random.nextDouble() * 5000));

            // Fechas aleatorias entre hace 0 y 8 años
            LocalDate fechaCompra = LocalDate.now().minusYears(random.nextInt(9)).minusDays(random.nextInt(365));
            String estado = estados[random.nextInt(estados.length)];

            equipos.add(new HardwareEntity(i, modelo, category, precio, fechaCompra, estado));
        }
        return equipos;
    }
}