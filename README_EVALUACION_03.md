# Evaluacion 03 - Sistema Integral de Gestion Hospitalaria

Sistema de gestion de citas medicas desarrollado con arquitectura de microservicios usando Spring Boot, Spring Cloud Eureka, API Gateway, Spring Data JPA y MySQL.

El objetivo del proyecto es administrar pacientes, medicos, especialidades, consultorios, horarios medicos, citas y atenciones, demostrando registro de servicios, enrutamiento centralizado, validaciones de negocio y pruebas con Postman.

## Arquitectura

```text
                 Navegador
                    |
                    v
            Eureka Server :8761
                    ^
                    |
  -------------------------------------------------
  |                 |               |             |
API Gateway     Pacientes        Medicos        Citas
   :8080          :8085           :8086         :8087
                    |               |             |
              bd_paciente       bd_medico      bd_cita
```

## Microservicios

| Servicio | Puerto | Responsabilidad |
| --- | ---: | --- |
| `eureka-server` | `8761` | Registro y descubrimiento de servicios |
| `api-gateway` | `8080` | Entrada unica para consumir los microservicios |
| `paciente-service` | `8085` | CRUD de pacientes |
| `medico-service` | `8086` | CRUD de medicos, especialidades, consultorios y horarios |
| `cita-service` | `8087` | CRUD de citas, estados y atenciones |

## Tecnologias

- Java 21
- Spring Boot 4.0.6
- Spring Cloud 2025.1.0
- Spring Cloud Netflix Eureka
- Spring Cloud Gateway MVC
- Spring Data JPA
- Spring Validation
- Spring Security
- MySQL / XAMPP
- Maven
- Postman

## Requisitos Previos

- Java 21 instalado.
- Maven instalado.
- MySQL activo en `localhost:3306`.
- Usuario MySQL por defecto: `root` sin password.

Las bases se crean automaticamente porque las URLs usan `createDatabaseIfNotExist=true`.

## Bases De Datos

| Servicio | Base de datos |
| --- | --- |
| `paciente-service` | `bd_paciente_eval` |
| `medico-service` | `bd_medico_eval` |
| `cita-service` | `bd_cita_eval` |

## Orden De Ejecucion

Abrir una terminal por servicio y ejecutar en este orden.

### 1. Eureka Server

```powershell
cd eureka-server
mvn spring-boot:run
```

Abrir en navegador:

```text
http://localhost:8761
```

### 2. Paciente Service

```powershell
cd paciente-service
mvn spring-boot:run
```

### 3. Medico Service

```powershell
cd medico-service
mvn spring-boot:run
```

### 4. Cita Service

```powershell
cd cita-service
mvn spring-boot:run
```

### 5. API Gateway

```powershell
cd api-gateway
mvn spring-boot:run
```

## Verificacion En Eureka

En el navegador:

```text
http://localhost:8761
```

Deben aparecer como `UP`:

```text
API-GATEWAY
PACIENTE-SERVICE
MEDICO-SERVICE
CITA-SERVICE
```

## Rutas Por API Gateway

Todas las pruebas del entregable pueden hacerse desde:

```text
http://localhost:8080
```

| Recurso | URL |
| --- | --- |
| Pacientes | `http://localhost:8080/api/pacientes` |
| Especialidades | `http://localhost:8080/api/especialidades` |
| Consultorios | `http://localhost:8080/api/consultorios` |
| Medicos | `http://localhost:8080/api/medicos` |
| Horarios medicos | `http://localhost:8080/api/horarios-medicos` |
| Citas | `http://localhost:8080/api/citas` |
| Estados de cita | `http://localhost:8080/api/estados-cita` |
| Atenciones | `http://localhost:8080/api/atenciones` |

## Pruebas En Postman

Configurar `Body -> raw -> JSON` y `Content-Type: application/json`.

### 1. Registrar Paciente

`POST http://localhost:8080/api/pacientes`

```json
{
  "dni": "73928461",
  "nombres": "Valeria",
  "apellidos": "Quispe Rojas",
  "fechaNacimiento": "1998-04-12",
  "sexo": "F",
  "telefono": "965432187",
  "direccion": "Jr. Los Alamos 245, Lima",
  "correo": "valeria.quispe739@correo.com",
  "estado": "A"
}
```

### 2. Listar Pacientes

`GET http://localhost:8080/api/pacientes`

### 3. Registrar Especialidad

`POST http://localhost:8080/api/especialidades`

```json
{
  "nombre": "Neurologia",
  "descripcion": "Especialidad del sistema nervioso",
  "estado": "A"
}
```

### 4. Registrar Consultorio

`POST http://localhost:8080/api/consultorios`

```json
{
  "numero": "C-405",
  "piso": 4,
  "ubicacion": "Torre B"
}
```

### 5. Registrar Medico

`POST http://localhost:8080/api/medicos`

Usar el `idEspecialidad` y `idConsultorio` creados antes.

```json
{
  "cmp": "CMP301",
  "nombres": "Fernando",
  "apellidos": "Salazar Nunez",
  "telefono": "954781236",
  "correo": "fernando.salazar301@clinica.com",
  "idEspecialidad": 1,
  "idConsultorio": 1,
  "estado": "A"
}
```

### 6. Registrar Horario Medico

`POST http://localhost:8080/api/horarios-medicos`

```json
{
  "idMedico": 1,
  "diaSemana": "LUNES",
  "horaInicio": "08:00:00",
  "horaFin": "12:00:00"
}
```

### 7. Registrar Cita

`POST http://localhost:8080/api/citas`

`2026-07-13` cae lunes, por eso coincide con el horario anterior.

```json
{
  "fecha": "2026-07-13",
  "hora": "09:00:00",
  "idPaciente": 1,
  "idMedico": 1,
  "idEstado": 1,
  "motivo": "Consulta general"
}
```

### 8. Consultar Cita Detallada

`GET http://localhost:8080/api/citas/1`

Respuesta esperada:

```json
{
  "idCita": 1,
  "fecha": "2026-07-13",
  "hora": "09:00:00",
  "estado": "PROGRAMADA",
  "paciente": {
    "nombre": "Valeria Quispe Rojas",
    "dni": "73928461"
  },
  "medico": {
    "nombre": "Fernando Salazar Nunez",
    "especialidad": "Neurologia"
  },
  "consultorio": {
    "numero": "C-405",
    "piso": 4
  }
}
```

### 9. Actualizar Estado De Cita

`PUT http://localhost:8080/api/citas/1/estado?idEstado=2`

Estados:

```text
1 = PROGRAMADA
2 = CONFIRMADA
3 = ATENDIDA
4 = CANCELADA
```

### 10. Cancelar Cita

`PUT http://localhost:8080/api/citas/1/cancelar`

### 11. Registrar Atencion

`POST http://localhost:8080/api/atenciones`

```json
{
  "idCita": 1,
  "diagnostico": "Evaluacion medica general",
  "tratamiento": "Reposo, hidratacion y control en 7 dias",
  "observaciones": "Paciente estable"
}
```

### 12. Listar Atenciones

`GET http://localhost:8080/api/atenciones`

## Validaciones De Negocio

### Cita Duplicada

Repetir el registro de una cita con el mismo medico, fecha y hora.

Respuesta esperada:

```json
{
  "mensaje": "El medico ya tiene una cita registrada en la misma fecha y hora"
}
```

### Horario No Disponible

Intentar registrar una cita fuera del horario del medico:

```json
{
  "fecha": "2026-07-13",
  "hora": "15:00:00",
  "idPaciente": 1,
  "idMedico": 1,
  "idEstado": 1,
  "motivo": "Intento fuera de horario disponible"
}
```

Respuesta esperada:

```json
{
  "mensaje": "El medico no tiene horario disponible para la fecha y hora indicada"
}
```

## Evidencias Recomendadas

1. Eureka Server mostrando servicios registrados.
2. Registro y listado de pacientes.
3. Registro de especialidad y consultorio.
4. Registro y listado de medicos.
5. Registro de horario medico.
6. Registro, listado y detalle integrado de cita.
7. Actualizacion/cancelacion de cita.
8. Registro/listado de atenciones.
9. Validacion de cita duplicada.
10. Validacion de horario no disponible.

## Conclusiones

1. Se implemento un sistema de gestion de citas medicas basado en microservicios, separando responsabilidades entre pacientes, medicos, citas, Eureka Server y API Gateway.
2. Se valido el flujo principal mediante Postman, incluyendo registro, listado, actualizacion, cancelacion e integracion de datos entre servicios.
3. Se aplicaron validaciones de negocio para impedir citas duplicadas y citas fuera del horario medico disponible, reforzando la consistencia del sistema.
