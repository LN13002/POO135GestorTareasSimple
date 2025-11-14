# 📊 API Gestor de Proyectos Simple

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-blue.svg)](https://www.postgresql.org/)
[![Maven](https://img.shields.io/badge/Maven-3.8+-red.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

Una API REST desarrollada con Spring Boot para la gestión eficiente de proyectos, tareas y registro de horas de trabajo de empleados.

---

## 👥 Equipo de Desarrollo

| Nombre | Carnet | Email |
|--------|--------|-------|
| **Kevin Manuel Lemus Najarro** | ln13002 | ln13002@ues.edu.sv |
| **Kevin Geovanni González Salazar** | gs24037 | gs24037@ues.edu.sv |
| **José Gerardo Pleites Campos** | pc24020 | pc24020@ues.edu.sv |
| **Luis Alberto Rodríguez Lara** | rl15028 | rl15028@ues.edu.sv |
| **Ricardo José Guevara Aldana** | ga24023 | ga24023@ues.edu.sv |

---

## 🎯 Descripción del Proyecto

El **Gestor de Proyectos Simple** es una solución integral que permite a las organizaciones administrar sus proyectos, asignar tareas a empleados y realizar un seguimiento detallado del tiempo invertido en cada actividad.

### ✨ Características Principales

- 🏢 **Gestión de Empleados**: CRUD completo con búsqueda, filtros y paginación
- 📋 **Gestión de Proyectos**: Seguimiento de estados, presupuestos y fechas
- ✅ **Gestión de Tareas**: Asignación con prioridades, estados y fechas de vencimiento
- ⏰ **Registro de Horas**: Sistema de timetracking detallado por tarea y empleado
- 📊 **Cálculos Automáticos**: Total de horas por proyecto y estadísticas
- 🔍 **Búsqueda Avanzada**: Filtros personalizados en todos los módulos
- 📈 **Métricas**: Progreso de proyectos basado en tareas completadas
- 📝 **Documentación**: Swagger/OpenAPI 3.0 integrado

---

## 🛠️ Stack Tecnológico

| Categoría | Tecnología | Versión |
|-----------|-----------|---------|
| **Framework** | Spring Boot | 3.4.0 |
| **Lenguaje** | Java | 17 |
| **Base de Datos** | PostgreSQL | 17 |
| **ORM** | Spring Data JPA | - |
| **Migraciones** | Flyway | - |
| **Validación** | Jakarta Validation | - |
| **Mapeo** | MapStruct | 1.5.5 |
| **Documentación** | SpringDoc OpenAPI | 3.0 |
| **Testing** | JUnit 5 + Mockito | - |
| **Build Tool** | Maven | 3.8+ |
| **Utilidades** | Lombok | - |

---

## 📦 Instalación y Configuración

### Prerrequisitos

- ☕ **Java 17+** ([Descargar aquí](https://www.oracle.com/java/technologies/downloads/))
- 🏗️ **Maven 3.8+** ([Descargar aquí](https://maven.apache.org/download.cgi))
- 🐘 **PostgreSQL 17** ([Descargar aquí](https://www.postgresql.org/download/))
- 🔧 **IDE** (IntelliJ IDEA recomendado)

### Pasos de Instalación

1. **Clonar el repositorio**
```bash
   git clone https://github.com/LN13002/POO135GestorTareasSimple.git
   cd POO135GestorTareasSimple/apigestorproyectos
```

2. **Crear la base de datos**
```sql
   CREATE DATABASE "POO135GestorTareasSimple";
```

3. **Configurar `application.properties`**
   
   Ubicación: `src/main/resources/application.properties`
```properties
   # Datasource
   spring.datasource.url=jdbc:postgresql://localhost:5432/POO135GestorTareasSimple?currentSchema=public
   spring.datasource.username=postgres
   spring.datasource.password=tu_contraseña
   
   # JPA/Hibernate
   spring.jpa.hibernate.ddl-auto=validate
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.format_sql=true
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
   
   # Flyway
   spring.flyway.enabled=true
   spring.flyway.locations=classpath:db/migration
   spring.flyway.baseline-on-migrate=true
```

4. **Compilar el proyecto**
```bash
   mvn clean install
```

5. **Ejecutar la aplicación**
```bash
   mvn spring-boot:run
```

6. **Acceder a la documentación**
   - **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
   - **OpenAPI JSON**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

---

## 🧪 Testing

El proyecto cuenta con una suite completa de pruebas unitarias y de integración desarrolladas con **JUnit 5** y **Mockito**.

### 📊 Cobertura de Tests

| Componente | Tests | Descripción |
|------------|-------|-------------|
| **Controllers** | 28 | Tests de endpoints REST con MockMvc |
| ├─ ProyectoController | 7 | CRUD + horas totales + progreso |
| ├─ EmpleadoController | 5 | CRUD con paginación |
| ├─ TareaController | 5 | CRUD completo |
| └─ RegistroHorasController | 4 | CRUD de registros |
| **Services** | 40 | Lógica de negocio |
| ├─ ProyectoService | 12 | Validaciones y cálculos |
| ├─ EmpleadoService | 10 | Gestión y validaciones |
| ├─ TareaService | 10 | Asignaciones y estados |
| └─ RegistroHorasService | 8 | Tracking de horas |
| **Models** | 11 | Validación de entidades |
| └─ RegistroHoras | 11 | Getters, setters, equals, hashCode |
| **Total** | **79+** | **Alta cobertura** |

### 🚀 Ejecutar Tests
```bash
# Ejecutar todos los tests
mvn test

# Ejecutar tests con reporte detallado
mvn test -Dtest=ProyectoControllerTest

# Ejecutar tests con cobertura (requiere plugin Jacoco)
mvn clean test jacoco:report

# Ver reporte de cobertura
# El reporte se genera en: target/site/jacoco/index.html
```

### 🛠️ Tecnologías de Testing

- **JUnit 5 (Jupiter)**: Framework principal de testing
- **Mockito**: Mocking y stubbing de dependencias
- **MockMvc**: Testing de controllers REST
- **AssertJ**: Assertions fluidas y expresivas
- **@WebMvcTest**: Tests de capa web aislados
- **@ExtendWith(MockitoExtension)**: Integración Mockito + JUnit 5

### ✅ Ejemplo de Test
```java
@Test
void crearProyecto_DebeCrearProyecto() {
    // Arrange
    when(proyectoRepository.save(any(Proyecto.class))).thenReturn(proyecto);

    // Act
    ProyectoResponseDTO result = service.crearProyecto(createDTO);

    // Assert
    assertNotNull(result);
    assertEquals("Proyecto Test", result.nombre());
    verify(proyectoRepository).save(any(Proyecto.class));
}
```

---

## 📚 Documentación de la API

### 🏢 Empleados

#### Listar Empleados (Paginado)
```http
GET /api/empleados?page=0&size=10&q=Juan&activo=true
```

**Parámetros de Query:**
- `q` (opcional): Búsqueda por nombre, email o cargo
- `activo` (opcional): Filtrar por estado activo/inactivo
- `page` (opcional): Número de página (default: 0)
- `size` (opcional): Tamaño de página (default: 10)
- `sort` (opcional): Ordenamiento (default: id,DESC)

**Respuesta 200 OK:**
```json
{
  "content": [
    {
      "id": 1,
      "nombre": "Juan Pérez",
      "email": "juan@example.com",
      "cargo": "Desarrollador Senior",
      "fechaContratacion": "2024-01-15",
      "activo": true
    }
  ],
  "totalElements": 1,
  "totalPages": 1,
  "size": 10,
  "number": 0
}
```

#### Obtener Empleado por ID
```http
GET /api/empleados/{id}
```

#### Crear Empleado
```http
POST /api/empleados
Content-Type: application/json

{
  "nombre": "Juan Pérez",
  "email": "juan@example.com",
  "cargo": "Desarrollador",
  "fechaContratacion": "2024-01-15",
  "activo": true
}
```

**Validaciones:**
- `nombre`: Requerido, máx 255 caracteres
- `email`: Requerido, formato email válido, máx 255 caracteres
- `fechaContratacion`: Requerida
- `activo`: Requerido

#### Actualizar Empleado
```http
PUT /api/empleados/{id}
Content-Type: application/json

{
  "nombre": "Juan Pérez Actualizado",
  "email": "juan.nuevo@example.com",
  "cargo": "Senior Developer",
  "fechaContratacion": "2024-01-15",
  "activo": true
}
```

#### Eliminar Empleado
```http
DELETE /api/empleados/{id}
```

---

### 📋 Proyectos

#### Listar Proyectos
```http
GET /api/proyectos
```

**Respuesta 200 OK:**
```json
[
  {
    "id": 1,
    "nombre": "Sistema de Gestión",
    "descripcion": "Sistema integral de gestión empresarial",
    "fechaInicio": "2024-01-01",
    "fechaFinEstimada": "2024-12-31",
    "estado": "EN_PROGRESO",
    "presupuesto": 100000.00
  }
]
```

**Estados disponibles:**
- `PLANIFICACION`
- `EN_PROGRESO`
- `COMPLETADO`
- `CANCELADO`

#### Obtener Proyecto por ID
```http
GET /api/proyectos/{id}
```

#### Crear Proyecto
```http
POST /api/proyectos
Content-Type: application/json

{
  "nombre": "Nuevo Proyecto",
  "descripcion": "Descripción del proyecto",
  "fechaInicio": "2024-01-01",
  "fechaFinEstimada": "2024-12-31",
  "presupuesto": 50000.00
}
```

**Validaciones:**
- `nombre`: Requerido, máx 255 caracteres
- `fechaInicio`: Requerida
- `fechaFinEstimada`: Requerida, debe ser >= fechaInicio
- `presupuesto`: Requerido, mínimo 0.01

#### Actualizar Proyecto
```http
PUT /api/proyectos/{id}
Content-Type: application/json

{
  "nombre": "Proyecto Actualizado",
  "descripcion": "Nueva descripción",
  "fechaInicio": "2024-01-01",
  "fechaFinEstimada": "2024-12-31",
  "presupuesto": 75000.00
}
```

#### Eliminar Proyecto
```http
DELETE /api/proyectos/{id}
```

#### Obtener Horas Totales del Proyecto
```http
GET /api/proyectos/{id}/horas-totales
```

**Respuesta 200 OK:**
```json
250.50
```

#### Obtener Progreso del Proyecto
```http
GET /api/proyectos/{id}/progreso
```

**Respuesta 200 OK:**
```json
75.5
```

---

### ✅ Tareas

#### Listar Tareas
```http
GET /api/tareas
```

**Respuesta 200 OK:**
```json
[
  {
    "id": 1,
    "nombre": "Implementar API REST",
    "descripcion": "Desarrollar endpoints para gestión de usuarios",
    "proyectoId": 1,
    "proyectoNombre": "Sistema de Gestión",
    "empleadoAsignadoId": 1,
    "empleadoAsignadoNombre": "Juan Pérez",
    "fechaCreacion": "2024-01-15T10:30:00",
    "fechaVencimiento": "2024-02-15",
    "estado": "EN_PROGRESO",
    "prioridad": "ALTA"
  }
]
```

**Estados disponibles:**
- `PENDIENTE`
- `EN_PROGRESO`
- `COMPLETADA`
- `CANCELADA`

**Prioridades disponibles:**
- `BAJA`
- `MEDIA`
- `ALTA`
- `CRITICA`

#### Obtener Tarea por ID
```http
GET /api/tareas/{id}
```

#### Crear Tarea
```http
POST /api/tareas
Content-Type: application/json

{
  "nombre": "Nueva Tarea",
  "descripcion": "Descripción de la tarea",
  "proyectoId": 1,
  "empleadoAsignadoId": 1,
  "fechaVencimiento": "2024-03-01",
  "estado": "PENDIENTE",
  "prioridad": "ALTA"
}
```

**Validaciones:**
- `nombre`: Requerido, entre 3 y 100 caracteres
- `proyectoId`: Requerido
- `estado`: Requerido
- `prioridad`: Requerida
- `empleadoAsignadoId`: Opcional

#### Actualizar Tarea
```http
PUT /api/tareas/{id}
Content-Type: application/json

{
  "nombre": "Tarea Actualizada",
  "descripcion": "Nueva descripción",
  "empleadoAsignadoId": 2,
  "fechaVencimiento": "2024-03-15",
  "estado": "EN_PROGRESO",
  "prioridad": "MEDIA"
}
```

#### Eliminar Tarea
```http
DELETE /api/tareas/{id}
```

---

### ⏰ Registro de Horas

#### Listar Registros
```http
GET /api/registrohoras
```

**Respuesta 200 OK:**
```json
[
  {
    "id": 1,
    "tareaId": 1,
    "empleadoId": 1,
    "fecha": "2024-01-15",
    "horasRegistradas": 8.5,
    "descripcionActividad": "Desarrollo de API REST y documentación",
    "fechaRegistro": "2024-01-15T18:30:00"
  }
]
```

#### Obtener Registro por ID
```http
GET /api/registrohoras/{id}
```

#### Crear Registro de Horas
```http
POST /api/registrohoras
Content-Type: application/json

{
  "tareaId": 1,
  "empleadoId": 1,
  "fecha": "2024-01-15",
  "horasRegistradas": 8.5,
  "descripcionActividad": "Desarrollo de funcionalidades del módulo de usuarios",
  "fechaRegistro": "2024-01-15T18:30:00"
}
```

**Validaciones:**
- `tareaId`: Requerido
- `empleadoId`: Requerido
- `fecha`: Requerida
- `horasRegistradas`: Requerido, mínimo 0.1
- `descripcionActividad`: Requerida
- `fechaRegistro`: Requerido

#### Eliminar Registro
```http
DELETE /api/registrohoras/{id}
```

---

## 🏗️ Arquitectura del Proyecto
```
apigestorproyectos/
├── src/
│   ├── main/
│   │   ├── java/com/equipo7/apigestorproyectos/
│   │   │   ├── controllers/          # Controladores REST
│   │   │   │   ├── ProyectoController.java
│   │   │   │   ├── EmpleadoController.java
│   │   │   │   ├── TareaController.java
│   │   │   │   └── RegistroHorasController.java
│   │   │   ├── services/             # Lógica de negocio
│   │   │   │   ├── ProyectoService.java
│   │   │   │   ├── EmpleadoService.java
│   │   │   │   ├── TareaService.java
│   │   │   │   └── RegistroHorasService.java
│   │   │   ├── repository/           # Capa de acceso a datos
│   │   │   │   ├── ProyectoRepository.java
│   │   │   │   ├── EmpleadoRepository.java
│   │   │   │   ├── TareaRepository.java
│   │   │   │   └── RegistroHorasRepository.java
│   │   │   ├── models/               # Entidades JPA
│   │   │   │   ├── Proyecto.java
│   │   │   │   ├── Empleado.java
│   │   │   │   ├── Tarea.java
│   │   │   │   ├── RegistroHoras.java
│   │   │   │   ├── EstadoProyecto.java
│   │   │   │   ├── EstadoTarea.java
│   │   │   │   └── Prioridad.java
│   │   │   ├── dto/                  # Data Transfer Objects
│   │   │   │   ├── respuesta/
│   │   │   │   └── solicitud/
│   │   │   ├── mappers/              # MapStruct mappers
│   │   │   ├── exceptions/           # Manejo de excepciones
│   │   │   └── config/               # Configuraciones
│   │   └── resources/
│   │       ├── application.properties
│   │       └── db/migration/         # Scripts Flyway
│   └── test/
│       └── java/com/equipo7/apigestorproyectos/
│           ├── controllers/          # Tests de controllers
│           ├── services/             # Tests de services
│           └── models/               # Tests de models
└── pom.xml
```

---

## 🔐 Códigos de Estado HTTP

| Código | Descripción |
|--------|-------------|
| **200** | OK - Solicitud exitosa |
| **201** | Created - Recurso creado exitosamente |
| **204** | No Content - Recurso eliminado exitosamente |
| **400** | Bad Request - Datos de entrada inválidos |
| **404** | Not Found - Recurso no encontrado |
| **500** | Internal Server Error - Error del servidor |

---

## 🚀 Características Avanzadas

### Validaciones
- Validación de datos con Jakarta Validation (`@Valid`, `@NotBlank`, `@Min`, etc.)
- Validaciones personalizadas en capa de servicio
- Manejo de errores consistente

### Mapeo de Objetos
- MapStruct para conversión DTO ↔ Entity
- Mapeos personalizados para relaciones complejas

### Paginación y Filtros
- Paginación en endpoint de empleados
- Búsqueda por texto en múltiples campos
- Filtros por estado activo/inactivo

### Documentación
- Swagger UI integrado
- Anotaciones OpenAPI 3.0
- Ejemplos de request/response

---

## 📝 Modelo de Datos

### Diagrama ER (Simplificado)
```
┌─────────────┐         ┌──────────────┐         ┌─────────────┐
│  Empleado   │         │   Proyecto   │         │    Tarea    │
├─────────────┤         ├──────────────┤         ├─────────────┤
│ id (PK)     │────┐    │ id (PK)      │────┐    │ id (PK)     │
│ nombre      │    │    │ nombre       │    │    │ nombre      │
│ email       │    │    │ descripcion  │    └───>│ proyecto_id │
│ cargo       │    │    │ fechaInicio  │         │ empleado_id │<──┐
│ fechaContr..│    │    │ fechaFinEst..│         │ estado      │   │
│ activo      │    │    │ estado       │         │ prioridad   │   │
└─────────────┘    │    │ presupuesto  │         └─────────────┘   │
                   │    └──────────────┘                            │
                   │                                                 │
                   │    ┌──────────────────┐                       │
                   └───>│ RegistroHoras    │                       │
                        ├──────────────────┤                       │
                        │ id (PK)          │                       │
                        │ tarea_id         │───────────────────────┘
                        │ empleado_id      │
                        │ fecha            │
                        │ horasRegistradas │
                        │ descripcion      │
                        │ fechaRegistro    │
                        └──────────────────┘
```

---

## 🐛 Solución de Problemas

### Error de conexión a PostgreSQL
```
Caused by: org.postgresql.util.PSQLException: Connection refused
```
**Solución:** Verifica que PostgreSQL esté corriendo y el puerto 5432 esté disponible.

### Error de validación de schema
```
Schema-validation: missing column
```
**Solución:** Asegúrate de que las migraciones Flyway se hayan ejecutado correctamente.

### Tests fallan
```
No tests found
```
**Solución:** Ejecuta `mvn clean test` para limpiar y recompilar.

---

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo [LICENSE](LICENSE) para más detalles.

---

## 📞 Contacto y Soporte

Para consultas, reportar bugs o sugerencias:

- 📧 Email del equipo: ln13002@ues.edu.sv
- 🐛 Issues: [GitHub Issues](https://github.com/LN13002/POO135GestorTareasSimple/issues)
- 📖 Wiki: [Documentación adicional](https://github.com/LN13002/POO135GestorTareasSimple/wiki)

---

## 🙏 Agradecimientos

- Universidad de El Salvador - Facultad de Ingeniería y Arquitectura
- Cátedra de Programación Orientada a Objetos (POO135)
- Todos los miembros del equipo por su dedicación y esfuerzo

---

<div align="center">

**Desarrollado con ❤️ por el Equipo 7**

[![GitHub](https://img.shields.io/badge/GitHub-POO135GestorTareasSimple-blue?logo=github)](https://github.com/LN13002/POO135GestorTareasSimple)

</div>