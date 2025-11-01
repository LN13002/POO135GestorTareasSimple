# Modulo RegistroHoras – Ricardo Aldana

## Carpetas creadas

Se añadieron las siguientes carpetas dentro del paquete `com.equipo7.apigestorproyectos`:

- `dto/` → Contiene clases de transferencia de datos o dto
- `services/` → Contiene la logica de negocio
- `repositories/` → Contiene las interfaces de acceso a base de datos.

## Archivos creados

`RegistroHoras.java` --> Modelo con las relaciones hacia `Empleado` y `Tarea`. Representa los registros de horas trabajadas
`RegistroHorasDTO.java` --> Objeto DTO usado para enviar/recibir datos del módulo
`RegistroHorasRepository.java` --> Interfaz que extiende `JpaRepository` para manejar operaciones CRUD automaticas
`RegistroHorasService.java` --> Capa de servicio que implementa la logica de guardado, busqueda y eliminacion de registros
`RegistroHorasController.java`--> Controlador REST que expone los endpoints `/api/registrohoras` para acceder al modulo

## Nota importante

El archivo **`RegistroHorasService.java`** presenta dependencias a `EmpleadoRepository` y `TareaRepository`,  
las cuales aún \*_no existen en el proyecto actual_
Por esa razon, este archivo puede mostrar errores temporales hasta que los demas implementen dichas capas

---
