# Microservicios - Sistema Integral de Gestion Hospitalaria

Proyecto de evaluacion desarrollado con Spring Boot y arquitectura de microservicios para gestionar citas medicas.

## Servicios

| Servicio | Puerto |
| --- | ---: |
| Eureka Server | `8761` |
| API Gateway | `8080` |
| Paciente Service | `8085` |
| Medico Service | `8086` |
| Cita Service | `8087` |

## Documentacion Del Examen

La guia completa de ejecucion, endpoints y pruebas en Postman esta en:

[README_EVALUACION_03.md](README_EVALUACION_03.md)

## Ejecucion Rapida

Ejecutar en orden:

```powershell
cd eureka-server
mvn spring-boot:run
```

```powershell
cd paciente-service
mvn spring-boot:run
```

```powershell
cd medico-service
mvn spring-boot:run
```

```powershell
cd cita-service
mvn spring-boot:run
```

```powershell
cd api-gateway
mvn spring-boot:run
```

Eureka:

```text
http://localhost:8761
```

Gateway:

```text
http://localhost:8080
```
