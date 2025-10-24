-- V2__seed_datos_iniciales.sql
-- Inserta 10 empleados, 10 proyectos, 12 tareas y 24 registros de horas.
-- Usa los valores de tus ENUM como VARCHAR: (EstadoProyecto) PLANIFICACION, EN_PROGRESO, COMPLETADO, CANCELADO
--                                        : (EstadoTarea)    PENDIENTE, EN_PROGRESO, COMPLETADA, CANCELADA
--                                        : (Prioridad)      BAJA, MEDIA, ALTA, CRITICA

-- ================
-- Empleados (10)
-- ================
insert into empleados (nombre, email, cargo, fecha_contratacion, activo) values
('Ana López',           'ana.lopez@demo.com',       'Project Manager',   '2023-01-15', true),
('Bruno García',        'bruno.garcia@demo.com',    'Backend Dev',       '2023-03-02', true),
('Carla Martínez',      'carla.martinez@demo.com',  'Frontend Dev',      '2024-05-20', true),
('Daniel Pérez',        'daniel.perez@demo.com',    'QA Engineer',       '2022-11-01', true),
('Elena Rodríguez',     'elena.rodriguez@demo.com', 'UX Designer',       '2024-08-10', true),
('Fernando Torres',     'fernando.torres@demo.com', 'Scrum Master',      '2021-07-05', true),
('Gabriela Rivas',      'gabriela.rivas@demo.com',  'Data Analyst',      '2024-02-14', true),
('Hugo Sánchez',        'hugo.sanchez@demo.com',    'DevOps',            '2023-09-30', true),
('Irene Castillo',      'irene.castillo@demo.com',  'Backend Dev',       '2022-04-21', true),
('Javier Núñez',        'javier.nunez@demo.com',    'Product Owner',     '2020-12-12', true);

-- ================
-- Proyectos (10)
-- ================
insert into proyectos (nombre, descripcion, fecha_inicio, fecha_fin_estimada, estado, presupuesto) values
('Portal Clientes',        'Nuevo portal de atención al cliente',     '2024-01-10', '2024-12-20', 'EN_PROGRESO',  85000.00),
('App Movil Ventas',       'Aplicación móvil para fuerza de ventas',  '2024-03-01', '2025-03-01', 'EN_PROGRESO', 120000.00),
('ETL Reportes',           'Pipeline de datos para reportes BI',      '2023-10-05', '2024-06-30', 'COMPLETADO',   60000.00),
('Sitio Marketing',        'Rediseño sitio institucional',            '2024-07-15', '2024-11-30', 'EN_PROGRESO',  40000.00),
('Chat Soporte',           'Chat en vivo con bots',                   '2025-01-05', '2025-06-30', 'PLANIFICACION',70000.00),
('Onboarding Interno',     'Portal de onboarding empleados',          '2023-02-01', '2023-09-01', 'COMPLETADO',   30000.00),
('Migración Cloud',        'Migración a Kubernetes',                  '2024-09-01', '2025-05-30', 'EN_PROGRESO', 150000.00),
('Módulo Facturación',     'Facturación electrónica',                  '2024-05-10', '2024-10-31', 'EN_PROGRESO',  90000.00),
('DataLake',               'Lago de datos corporativo',               '2022-01-15', '2023-05-15', 'CANCELADO',   200000.00),
('SEO/Analytics',          'Mejoras SEO y analítica',                 '2024-08-01', '2024-12-01', 'EN_PROGRESO',  35000.00);

-- ================
-- Tareas (12)
-- ================
-- Nota: referenciamos proyectos/empleados por sus claves naturales (nombre/email) para no fijar IDs.
insert into tareas
  (nombre, descripcion, proyecto_id, empleado_asignado_id, fecha_creacion, fecha_vencimiento, estado, prioridad)
values
('Diseñar wireframes',      'Wireframes secciones principales',
   (select id from proyectos where nombre = 'Sitio Marketing'),
   (select id from empleados where email = 'elena.rodriguez@demo.com'),
   now(), '2024-10-15', 'EN_PROGRESO', 'ALTA'),

('API clientes v1',         'Endpoints CRUD clientes',
   (select id from proyectos where nombre = 'Portal Clientes'),
   (select id from empleados where email = 'bruno.garcia@demo.com'),
   now(), '2024-09-30', 'EN_PROGRESO', 'ALTA'),

('Tests API clientes',      'Pruebas automáticas de API',
   (select id from proyectos where nombre = 'Portal Clientes'),
   (select id from empleados where email = 'daniel.perez@demo.com'),
   now(), '2024-10-10', 'PENDIENTE', 'MEDIA'),

('CI/CD Portal',            'Pipeline CI/CD portal',
   (select id from proyectos where nombre = 'Portal Clientes'),
   (select id from empleados where email = 'hugo.sanchez@demo.com'),
   now(), '2024-10-05', 'EN_PROGRESO', 'ALTA'),

('App Ventas - Login',      'Pantalla y flujo de login',
   (select id from proyectos where nombre = 'App Movil Ventas'),
   (select id from empleados where email = 'carla.martinez@demo.com'),
   now(), '2024-11-01', 'EN_PROGRESO', 'ALTA'),

('App Ventas - Sync',       'Sincronización offline',
   (select id from proyectos where nombre = 'App Movil Ventas'),
   (select id from empleados where email = 'irene.castillo@demo.com'),
   now(), '2024-12-15', 'PENDIENTE', 'CRITICA'),

('ETL - Limpieza',          'Limpieza de datos crudos',
   (select id from proyectos where nombre = 'ETL Reportes'),
   (select id from empleados where email = 'gabriela.rivas@demo.com'),
   now(), '2024-06-10', 'COMPLETADA', 'MEDIA'),

('Chatbot intents',         'Definir intents y training',
   (select id from proyectos where nombre = 'Chat Soporte'),
   (select id from empleados where email = 'ana.lopez@demo.com'),
   now(), '2025-03-15', 'PLANIFICACION', 'ALTA'),

('Infra k8s',               'Despliegue base EKS/GKE',
   (select id from proyectos where nombre = 'Migración Cloud'),
   (select id from empleados where email = 'hugo.sanchez@demo.com'),
   now(), '2025-02-28', 'EN_PROGRESO', 'CRITICA'),

('Factura - XML',           'Generación XML factura',
   (select id from proyectos where nombre = 'Módulo Facturación'),
   (select id from empleados where email = 'bruno.garcia@demo.com'),
   now(), '2024-09-20', 'EN_PROGRESO', 'ALTA'),

('SEO técnico',             'Core Web Vitals y sitemaps',
   (select id from proyectos where nombre = 'SEO/Analytics'),
   (select id from empleados where email = 'elena.rodriguez@demo.com'),
   now(), '2024-09-30', 'PENDIENTE', 'MEDIA'),

('Dashboard BI',            'Dashboard de KPIs en portal',
   (select id from proyectos where nombre = 'Portal Clientes'),
   (select id from empleados where email = 'gabriela.rivas@demo.com'),
   now(), '2024-11-05', 'PENDIENTE', 'ALTA');

-- Índices (por si tu V1 no los tenía)
create index if not exists idx_tareas_estado on tareas(estado);
create index if not exists idx_tareas_prioridad on tareas(prioridad);

-- =====================
-- Registro de horas (24)
-- =====================
-- Dos registros por cada una de 12 tareas
insert into registro_horas (tarea_id, empleado_id, fecha, horas_registradas, descripcion_actividad, fecha_registro)
select t.id, e.id, current_date - 7, 2.50, 'Análisis inicial', now()
from tareas t
join empleados e on e.id = t.empleado_asignado_id
limit 12;

insert into registro_horas (tarea_id, empleado_id, fecha, horas_registradas, descripcion_actividad, fecha_registro)
select t.id, e.id, current_date - 3, 3.75, 'Desarrollo / ajustes', now()
from tareas t
join empleados e on e.id = t.empleado_asignado_id
limit 12;
