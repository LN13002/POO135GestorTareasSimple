-- V2__seed_datos_iniciales.sql
-- Inserta 10 empleados, 10 proyectos, 12 tareas y 24 registros de horas.
-- SIN caracteres especiales para evitar problemas de encoding

-- =========================
-- Empleados (10)
-- =========================
INSERT INTO empleados (nombre, email, cargo, fecha_contratacion, activo) VALUES
('Ana Lopez',           'ana.lopez@demo.com',       'Project Manager',   '2023-01-15', true),
('Bruno Garcia',        'bruno.garcia@demo.com',    'Backend Dev',       '2023-03-02', true),
('Carla Martinez',      'carla.martinez@demo.com',  'Frontend Dev',      '2024-05-20', true),
('Daniel Perez',        'daniel.perez@demo.com',    'QA Engineer',       '2022-11-01', true),
('Elena Rodriguez',     'elena.rodriguez@demo.com', 'UX Designer',       '2024-08-10', true),
('Fernando Torres',     'fernando.torres@demo.com', 'Scrum Master',      '2021-07-05', true),
('Gabriela Rivas',      'gabriela.rivas@demo.com',  'Data Analyst',      '2024-02-14', true),
('Hugo Sanchez',        'hugo.sanchez@demo.com',    'DevOps',            '2023-09-30', true),
('Irene Castillo',      'irene.castillo@demo.com',  'Backend Dev',       '2022-04-21', true),
('Javier Nunez',        'javier.nunez@demo.com',    'Product Owner',     '2020-12-12', true);

-- =========================
-- Proyectos (10)
-- =========================
INSERT INTO proyectos (nombre, descripcion, fecha_inicio, fecha_fin_estimada, estado, presupuesto) VALUES
('Portal Clientes',        'Nuevo portal de atencion al cliente',     '2024-01-10', '2024-12-20', 'EN_PROGRESO',  85000.00),
('App Movil Ventas',       'Aplicacion movil para fuerza de ventas',  '2024-03-01', '2025-03-01', 'EN_PROGRESO', 120000.00),
('ETL Reportes',           'Pipeline de datos para reportes BI',      '2023-10-05', '2024-06-30', 'COMPLETADO',   60000.00),
('Sitio Marketing',        'Rediseno sitio institucional',            '2024-07-15', '2024-11-30', 'EN_PROGRESO',  40000.00),
('Chat Soporte',           'Chat en vivo con bots',                   '2025-01-05', '2025-06-30', 'PLANIFICACION',70000.00),
('Onboarding Interno',     'Portal de onboarding empleados',          '2023-02-01', '2023-09-01', 'COMPLETADO',   30000.00),
('Migracion Cloud',        'Migracion a Kubernetes',                  '2024-09-01', '2025-05-30', 'EN_PROGRESO', 150000.00),
('Modulo Facturacion',     'Facturacion electronica',                 '2024-05-10', '2024-10-31', 'EN_PROGRESO',  90000.00),
('DataLake',               'Lago de datos corporativo',               '2022-01-15', '2023-05-15', 'CANCELADO',   200000.00),
('SEO Analytics',          'Mejoras SEO y analitica',                 '2024-08-01', '2024-12-01', 'EN_PROGRESO',  35000.00);

-- =========================
-- Tareas (12)
-- =========================
INSERT INTO tareas (nombre, descripcion, proyecto_id, empleado_asignado_id, fecha_creacion, fecha_vencimiento, estado, prioridad)
VALUES
('Disenar wireframes', 'Wireframes secciones principales',
   (SELECT id FROM proyectos WHERE nombre = 'Sitio Marketing'),
   (SELECT id FROM empleados WHERE email = 'elena.rodriguez@demo.com'),
   CURRENT_TIMESTAMP, '2024-10-15', 'EN_PROGRESO', 'ALTA'),

('API clientes v1', 'Endpoints CRUD clientes',
   (SELECT id FROM proyectos WHERE nombre = 'Portal Clientes'),
   (SELECT id FROM empleados WHERE email = 'bruno.garcia@demo.com'),
   CURRENT_TIMESTAMP, '2024-09-30', 'EN_PROGRESO', 'ALTA'),

('Tests API clientes', 'Pruebas automaticas de API',
   (SELECT id FROM proyectos WHERE nombre = 'Portal Clientes'),
   (SELECT id FROM empleados WHERE email = 'daniel.perez@demo.com'),
   CURRENT_TIMESTAMP, '2024-10-10', 'PENDIENTE', 'MEDIA'),

('CI/CD Portal', 'Pipeline CI/CD portal',
   (SELECT id FROM proyectos WHERE nombre = 'Portal Clientes'),
   (SELECT id FROM empleados WHERE email = 'hugo.sanchez@demo.com'),
   CURRENT_TIMESTAMP, '2024-10-05', 'EN_PROGRESO', 'ALTA'),

('App Ventas - Login', 'Pantalla y flujo de login',
   (SELECT id FROM proyectos WHERE nombre = 'App Movil Ventas'),
   (SELECT id FROM empleados WHERE email = 'carla.martinez@demo.com'),
   CURRENT_TIMESTAMP, '2024-11-01', 'EN_PROGRESO', 'ALTA'),

('App Ventas - Sync', 'Sincronizacion offline',
   (SELECT id FROM proyectos WHERE nombre = 'App Movil Ventas'),
   (SELECT id FROM empleados WHERE email = 'irene.castillo@demo.com'),
   CURRENT_TIMESTAMP, '2024-12-15', 'PENDIENTE', 'CRITICA'),

('ETL - Limpieza', 'Limpieza de datos crudos',
   (SELECT id FROM proyectos WHERE nombre = 'ETL Reportes'),
   (SELECT id FROM empleados WHERE email = 'gabriela.rivas@demo.com'),
   CURRENT_TIMESTAMP, '2024-06-10', 'COMPLETADA', 'MEDIA'),

('Chatbot intents', 'Definir intents y training',
   (SELECT id FROM proyectos WHERE nombre = 'Chat Soporte'),
   (SELECT id FROM empleados WHERE email = 'ana.lopez@demo.com'),
   CURRENT_TIMESTAMP, '2025-03-15', 'PENDIENTE', 'ALTA'),

('Infra k8s', 'Despliegue base EKS/GKE',
   (SELECT id FROM proyectos WHERE nombre = 'Migracion Cloud'),
   (SELECT id FROM empleados WHERE email = 'hugo.sanchez@demo.com'),
   CURRENT_TIMESTAMP, '2025-02-28', 'EN_PROGRESO', 'CRITICA'),

('Factura - XML', 'Generacion XML factura',
   (SELECT id FROM proyectos WHERE nombre = 'Modulo Facturacion'),
   (SELECT id FROM empleados WHERE email = 'bruno.garcia@demo.com'),
   CURRENT_TIMESTAMP, '2024-09-20', 'EN_PROGRESO', 'ALTA'),

('SEO tecnico', 'Core Web Vitals y sitemaps',
   (SELECT id FROM proyectos WHERE nombre = 'SEO Analytics'),
   (SELECT id FROM empleados WHERE email = 'elena.rodriguez@demo.com'),
   CURRENT_TIMESTAMP, '2024-09-30', 'PENDIENTE', 'MEDIA'),

('Dashboard BI', 'Dashboard de KPIs en portal',
   (SELECT id FROM proyectos WHERE nombre = 'Portal Clientes'),
   (SELECT id FROM empleados WHERE email = 'gabriela.rivas@demo.com'),
   CURRENT_TIMESTAMP, '2024-11-05', 'PENDIENTE', 'ALTA');

-- Indices adicionales
CREATE INDEX IF NOT EXISTS idx_tareas_estado ON tareas(estado);
CREATE INDEX IF NOT EXISTS idx_tareas_prioridad ON tareas(prioridad);

-- =========================
-- Registro de horas (24)
-- =========================
-- Primer registro para cada tarea (12 registros)
INSERT INTO registro_horas (tarea_id, empleado_id, fecha, horas_registradas, descripcion_actividad, fecha_registro)
SELECT 
  t.id, 
  t.empleado_asignado_id, 
  CURRENT_DATE - INTERVAL '7 days', 
  2.50, 
  'Analisis inicial', 
  CURRENT_TIMESTAMP
FROM tareas t
WHERE t.empleado_asignado_id IS NOT NULL
ORDER BY t.id;

-- Segundo registro para cada tarea (12 registros mas)
INSERT INTO registro_horas (tarea_id, empleado_id, fecha, horas_registradas, descripcion_actividad, fecha_registro)
SELECT 
  t.id, 
  t.empleado_asignado_id, 
  CURRENT_DATE - INTERVAL '3 days', 
  3.75, 
  'Desarrollo y ajustes', 
  CURRENT_TIMESTAMP
FROM tareas t
WHERE t.empleado_asignado_id IS NOT NULL
ORDER BY t.id;