# Evaluacion 03 - Sistema de Gestion de Citas Medicas

## Servicios implementados

- `eureka-server`: registro de microservicios en `http://localhost:8761`.
- `api-gateway`: entrada unica en `http://localhost:8080`.
- `paciente-service`: pacientes, puerto `8085`.
- `medico-service`: medicos, especialidades, horarios medicos y consultorios, puerto `8086`.
- `cita-service`: citas y estados de cita, puerto `8087`.

## Orden de ejecucion

Ejecutar cada comando en una terminal distinta:

```powershell
cd eureka-server
..\paciente-service\mvnw.cmd spring-boot:run
```

```powershell
cd paciente-service
.\mvnw.cmd spring-boot:run
```

```powershell
cd medico-service
.\mvnw.cmd spring-boot:run
```

```powershell
cd cita-service
.\mvnw.cmd spring-boot:run
```

```powershell
cd api-gateway
..\paciente-service\mvnw.cmd spring-boot:run
```

## Flujo recomendado en Postman

Todas las rutas pueden probarse desde el gateway usando `http://localhost:8080`.

### 1. Crear paciente

`POST http://localhost:8080/api/pacientes`

```json
{
  "dni": "74589632",
  "nombres": "Juan",
  "apellidos": "Perez",
  "telefono": "999888777",
  "correo": "juan.perez@mail.com"
}
```

### 2. Crear especialidad

`POST http://localhost:8080/api/especialidades`

```json
{
  "nombre": "Cardiologia",
  "descripcion": "Atencion cardiovascular"
}
```

### 3. Crear consultorio

`POST http://localhost:8080/api/consultorios`

```json
{
  "numero": "C-203",
  "piso": 2,
  "ubicacion": "Torre A"
}
```

### 4. Crear medico

`POST http://localhost:8080/api/medicos`

```json
{
  "cmp": "CMP12345",
  "nombres": "Carlos",
  "apellidos": "Torres",
  "telefono": "987654321",
  "correo": "carlos.torres@mail.com",
  "idEspecialidad": 1,
  "idConsultorio": 1
}
```

### 5. Crear horario medico

`POST http://localhost:8080/api/horarios-medicos`

```json
{
  "idMedico": 1,
  "diaSemana": "JUEVES",
  "horaInicio": "08:00",
  "horaFin": "13:00"
}
```

### 6. Crear cita valida

`POST http://localhost:8080/api/citas`

```json
{
  "fecha": "2026-06-25",
  "hora": "10:30",
  "idPaciente": 1,
  "idMedico": 1,
  "motivo": "Dolor en el pecho"
}
```

### 7. Consultar cita integrada

`GET http://localhost:8080/api/citas/1`

Respuesta esperada:

```json
{
  "idCita": 1,
  "fecha": "2026-06-25",
  "hora": "10:30:00",
  "estado": "PROGRAMADA",
  "paciente": {
    "nombre": "Juan Perez",
    "dni": "74589632"
  },
  "medico": {
    "nombre": "Carlos Torres",
    "especialidad": "Cardiologia"
  },
  "consultorio": {
    "numero": "C-203",
    "piso": 2
  }
}
```

### 8. Probar cita duplicada

Repetir el `POST /api/citas` con la misma fecha, hora y medico. Debe responder error:

```json
{
  "mensaje": "El medico ya tiene una cita registrada en la misma fecha y hora"
}
```

### 9. Probar cita fuera de horario

Cambiar la hora a una fuera del rango del horario medico, por ejemplo `"15:30"`. Debe responder error:

```json
{
  "mensaje": "El medico no tiene horario disponible para la fecha y hora indicada"
}
```

### 10. Cancelar cita

`PUT http://localhost:8080/api/citas/1/cancelar`

