# Laboratorio 2 FSI: Reglas de negocio con Spring Boot y Drools

Universidad de Antioquia, Fundamentos de Sistemas de Información, 2026.
Autor: Carlos Alberto Aucu Gomez.

## Contenido

- `labdrools/`: parte guiada. Evaluación de créditos bancarios con Spring Boot 3.2.5, Drools 8.44 y vistas Thymeleaf. Puerto 8080.
- `aerolinea/`: ejercicio del laboratorio. Diez reglas Drools para una aerolínea, con pruebas unitarias. Puerto 8081.

## Cómo ejecutar

Requiere JDK 17 o superior y Maven.

```
cd labdrools
mvn spring-boot:run
```

```
cd aerolinea
mvn test
mvn spring-boot:run
```

Para la explicación con IA de `labdrools`, defina la variable de entorno `OPENAI_API_KEY` antes de arrancar.
Cada carpeta incluye su colección de Postman.
