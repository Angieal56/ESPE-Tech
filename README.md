#  ESPE-Tech: Módulo Analítico de Inventario de Hardware

Este proyecto implementa una solución de nivel empresarial bajo **Spring Boot 3.4+** para el procesamiento masivo de datos de hardware en los laboratorios de la institución, comparando rigurosamente el rendimiento y la mantenibilidad de dos paradigmas de programación principales.

## Arquitectura del Sistema
El ecosistema está diseñado bajo principios de arquitectura limpia y desacoplada:
* **`persistence`**: Gestión del modelo relacional `HardwareEntity`.
* **`service`**: Capa lógica encargada de simular 10,000 registros y procesarlos bajo enfoques Imperativos y Declarativos.
* **`controller`**: Controladores REST para la exposición de endpoints analíticos (`/reporte-imperativo` y `/reporte-funcional`).
* **`dto`**: Objetos de transferencia de datos limpios para el renderizado del reporte estructurado.

---

## Análisis Comparativo de Paradigmas (Argumentación Técnica)

Al procesar un lote controlado de **10,000 registros de hardware**, se evaluaron ambos enfoques bajo tres criterios técnicos de la ingeniería de software:

### 1. Líneas de Código y Concisión
* **Paradigma Imperativo:** Requiere la inicialización manual de colecciones intermedias (`HashMap`), bucles anidados (`for`), bloques condicionales (`if/else`) y lógica repetitiva para calcular promedios y elementos máximos. Esto incrementa el tamaño del archivo y el riesgo de errores tipográficos.
* **Paradigma Funcional (Streams):** Reduce el tamaño del código de manera drástica. Al expresar la solución de forma declarativa mediante operadores encadenados (`.filter()`, `.collect()`, `.groupingBy()`), se elimina el código repetitivo (*boilerplate*), logrando resolver el problema en una única instrucción fluida.

### 2. Legibilidad y Comprensión Semántica
* **Paradigma Imperativo:** Se enfoca en el **"CÓMO"** se deben hacer las cosas paso a paso. El lector del código debe rastrear manualmente el estado de múltiples variables mutables en el tiempo para entender la meta del algoritmo.
* **Paradigma Funcional:** Se enfoca en el **"QUÉ"** se desea obtener. Leer el flujo de un Stream es equivalente a leer una oración en lenguaje natural: *"Filtrar activos recientes ➡️ agrupar por categoría ➡️ reducir estadísticas"*. Esto eleva significativamente la claridad del software.

### 3. Facilidad de Mantenimiento y Eficiencia Analítica
* **Mantenibilidad:** El enfoque funcional elimina la mutabilidad de variables globales, lo que reduce a cero los efectos secundarios (*side-effects*) y facilita la creación de pruebas unitarias aisladas. Modificar un filtro en un Stream requiere solo cambiar una línea, mientras que en un bucle imperativo puede obligar a reestructurar múltiples bloques anidados.
* **Rendimiento en Pruebas Reales:** * **Tiempo Imperativo:** ~40.36 milisegundos.
    * **Tiempo Funcional (Streams):** ~10.65 milisegundos.
    * *Conclusión de Eficiencia:* La API de Streams de Java demostró ser **casi 4 veces más rápida** en el procesamiento masivo. Su motor interno optimiza el recorrido de colecciones en memoria de manera mucho más eficiente que un hilo secuencial clásico controlado por acumuladores manuales.

#### Autor
*Angie Nicole Alvarado A.*