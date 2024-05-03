-- Inserta datos de prueba en la tabla actividades
INSERT INTO actividades (tipoActividad, fotoActividad) VALUES
('Deportes', 'foto1.jpg'),
('Arte', 'foto2.jpg'),
('Música', 'foto3.jpg'),
('Cine', 'foto4.jpg'),
('Teatro', 'foto5.jpg'),
('Gastronomía', 'foto6.jpg'),
('Danza', 'foto7.jpg'),
('Literatura', 'foto8.jpg'),
('Historia', 'foto9.jpg'),
('Ciencia', 'foto10.jpg');

-- Inserta datos de prueba en la tabla usuarios
INSERT INTO usuarios (nombre, contrasena, email, fechaNacimiento, recibeNotificaciones, intereses, roles, permiso, consentimiento_datos) VALUES
('Jon Ander', 'contrasena1', 'jonander@example.com', '1990-01-01', TRUE, 'Deportes, Música', 'usuario', 1, NOW()),
('Ainhoa', 'contrasena2', 'ainhoa@example.com', '1985-02-02', FALSE, 'Arte, Cine', 'moderador', 2, NOW()),
('Iker', 'contrasena3', 'iker@example.com', '1980-03-03', TRUE, 'Teatro, Gastronomía', 'administrador', 3, NOW()),
('Maite', 'contrasena4', 'maite@example.com', '1975-04-04', FALSE, 'Danza, Literatura', 'usuario', 1, NOW()),
('Xabier', 'contrasena5', 'xabier@example.com', '1970-05-05', TRUE, 'Historia, Ciencia', 'moderador', 2, NOW()),
('Nekane', 'contrasena6', 'nekane@example.com', '1965-06-06', FALSE, 'Deportes, Arte', 'administrador', 3, NOW()),
('Eneko', 'contrasena7', 'eneko@example.com', '1960-07-07', TRUE, 'Música, Cine', 'usuario', 1, NOW()),
('Izaskun', 'contrasena8', 'izaskun@example.com', '1955-08-08', FALSE, 'Teatro, Gastronomía', 'moderador', 2, NOW()),
('Gorka', 'contrasena9', 'gorka@example.com', '1950-09-09', TRUE, 'Danza, Literatura', 'administrador', 3, NOW()),
('Leire', 'contrasena10', 'leire@example.com', '1945-10-10', FALSE, 'Historia, Ciencia', 'usuario', 1, NOW());

-- Inserta datos de prueba en la tabla eventos
INSERT INTO eventos (nombre, fechaEvento, detalles, idUsuariocreador, fechaCreacion, fechaAprobacion, idModeradorAprobacion, fechaUltimaModificacion, fechaPublicacion, idModeradorPublicacion, fechaFinalizacion, idModeradorFinalizacion, MotivoFinalizacion, Ubicacion) VALUES
('Evento 1', '2022-01-01 10:00:00', 'Detalles del evento 1', 1, NOW(), NOW(), 2, NOW(), NOW(), 2, '2022-01-01 12:00:00', 2, 'FinVisitas', 'Calle Falsa 1, 12345, Madrid'),
('Evento 2', '2022-02-02 10:00:00', 'Detalles del evento 2', 2, NOW(), NOW(), 3, NOW(), NOW(), 3, '2022-02-02 12:00:00', 3, 'FinVisitas', 'Calle Falsa 2, 12345, Madrid'),
('Evento 3', '2022-03-03 10:00:00', 'Detalles del evento 3', 3, NOW(), NOW(), 1, NOW(), NOW(), 1, '2022-03-03 12:00:00', 1, 'FinVisitas', 'Calle Falsa 3, 12345, Madrid'),
('Evento 4', '2022-04-04 10:00:00', 'Detalles del evento 4', 4, NOW(), NOW(), 2, NOW(), NOW(), 2, '2022-04-04 12:00:00', 2, 'FinVisitas', 'Calle Falsa 4, 12345, Madrid'),
('Evento 5', '2022-05-05 10:00:00', 'Detalles del evento 5', 5, NOW(), NOW(), 3, NOW(), NOW(), 3, '2022-05-05 12:00:00', 3, 'FinVisitas', 'Calle Falsa 5, 12345, Madrid'),
('Evento 6', '2022-06-06 10:00:00', 'Detalles del evento 6', 6, NOW(), NOW(), 1, NOW(), NOW(), 1, '2022-06-06 12:00:00', 1, 'FinVisitas', 'Calle Falsa 6, 12345, Madrid'),
('Evento 7', '2022-07-07 10:00:00', 'Detalles del evento 7', 7, NOW(), NOW(), 2, NOW(), NOW(), 2, '2022-07-07 12:00:00', 2, 'FinVisitas', 'Calle Falsa 7, 12345, Madrid'),
('Evento 8', '2022-08-08 10:00:00', 'Detalles del evento 8', 8, NOW(), NOW(), 3, NOW(), NOW(), 3, '2022-08-08 12:00:00', 3, 'FinVisitas', 'Calle Falsa 8, 12345, Madrid'),
('Evento 9', '2022-09-09 10:00:00', 'Detalles del evento 9', 9, NOW(), NOW(), 1, NOW(), NOW(), 1, '2022-09-09 12:00:00', 1, 'FinVisitas', 'Calle Falsa 9, 12345, Madrid'),
('Evento 10', '2022-10-10 10:00:00', 'Detalles del evento 10', 10, NOW(), NOW(), 2, NOW(), NOW(), 2, '2022-10-10 12:00:00', 2, 'FinVisitas', 'Calle Falsa 10, 12345, Madrid');



-- Inserta datos de prueba en la tabla clasificacionEventos
INSERT INTO clasificacionEventos (idActividad, idEvento) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 10);

-- Inserta datos de prueba en la tabla gestionFavoritos
INSERT INTO gestionFavoritos (idEvento, idUsuario, fechaCreacionFavorito) VALUES
(1, 1, NOW()),
(2, 2, NOW()),
(3, 3, NOW()),
(4, 4, NOW()),
(5, 5, NOW()),
(6, 6, NOW()),
(7, 7, NOW()),
(8, 8, NOW()),
(9, 9, NOW()),
(10, 10, NOW());

-- Inserta datos de prueba en la tabla accesos
INSERT INTO accesos (fechaAcceso, idUsuario) VALUES
(NOW(), 1),
(NOW(), 2),
(NOW(), 3),
(NOW(), 4),
(NOW(), 5),
(NOW(), 6),
(NOW(), 7),
(NOW(), 8),
(NOW(), 9),
(NOW(), 10);