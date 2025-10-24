-- =========================================================
-- Flyway V1: esquema inicial para apigestorproyectos
-- Tablas: empleados, proyectos, tareas, registro_horas
-- =========================================================

-- =========================
-- Tabla: empleados
-- =========================
create table if not exists empleados (
  id                   bigserial primary key,
  nombre               varchar(255),
  email                varchar(255) not null,
  cargo                varchar(255),
  fecha_contratacion   date,
  activo               boolean,
  constraint uk_empleados_email unique (email)
);

-- Índices adicionales (email ya queda indexado por unique)
-- create index if not exists idx_empleados_activo on empleados(activo);


-- =========================
-- Tabla: proyectos
-- =========================
create table if not exists proyectos (
  id                   bigserial primary key,
  nombre               varchar(255),
  descripcion          varchar(1000),
  fecha_inicio         date,
  fecha_fin_estimada   date,
  estado               varchar(20),
  presupuesto          numeric(19,2),
  -- Estados permitidos por tu enum EstadoProyecto
  constraint ck_proyectos_estado check (estado in ('PLANIFICACION','EN_PROGRESO','COMPLETADO','CANCELADO'))
);

-- Opcional: índice por estado/fechas si vas a filtrar mucho
-- create index if not exists idx_proyectos_estado on proyectos(estado);
-- create index if not exists idx_proyectos_fecha_inicio on proyectos(fecha_inicio);


-- =========================
-- Tabla: tareas
-- =========================
create table if not exists tareas (
  id                      bigserial primary key,
  nombre                  varchar(255),
  descripcion             varchar(1000),
  proyecto_id             bigint not null,
  empleado_asignado_id    bigint,
  fecha_creacion          timestamp,
  fecha_vencimiento       date,
  estado                  varchar(20),
  prioridad               varchar(20),

  constraint fk_tareas_proyecto
    foreign key (proyecto_id) references proyectos(id),

  constraint fk_tareas_empleado_asignado
    foreign key (empleado_asignado_id) references empleados(id),

  -- Estados y prioridades permitidas (enums EstadoTarea / Prioridad)
  constraint ck_tareas_estado check (estado in ('PENDIENTE','EN_PROGRESO','COMPLETADA','CANCELADA')),
  constraint ck_tareas_prioridad check (prioridad in ('BAJA','MEDIA','ALTA','CRITICA'))
);

-- Índices recomendados para joins y filtros
create index if not exists idx_tareas_proyecto on tareas(proyecto_id);
create index if not exists idx_tareas_empleado_asignado on tareas(empleado_asignado_id);
-- Opcional:
-- create index if not exists idx_tareas_estado on tareas(estado);
-- create index if not exists idx_tareas_prioridad on tareas(prioridad);


-- =========================
-- Tabla: registro_horas
-- =========================
create table if not exists registro_horas (
  id                     bigserial primary key,
  tarea_id               bigint not null,
  empleado_id            bigint not null,
  fecha                  date,
  horas_registradas      numeric(10,2),
  descripcion_actividad  varchar(1000),
  fecha_registro         timestamp default now(),

  constraint fk_registro_horas_tarea
    foreign key (tarea_id) references tareas(id),

  constraint fk_registro_horas_empleado
    foreign key (empleado_id) references empleados(id)
);

-- Índices para reportes frecuentes
create index if not exists idx_registro_horas_tarea on registro_horas(tarea_id);
create index if not exists idx_registro_horas_empleado on registro_horas(empleado_id);
create index if not exists idx_registro_horas_fecha on registro_horas(fecha);
