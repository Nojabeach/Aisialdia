use aisialdia;
-- Inserta datos de prueba en la tabla actividades
INSERT INTO actividades (tipoActividad, fotoActividad) VALUES
('adolescentes', 'adolescentes.png'),
('bebe', 'bebe.png'),
('cultura', 'cultura.png'),
('ciclismo', 'bici.png'),
('deportiva', 'deportiva.png'),
('indoor', 'indoor.png'),
('carrera', 'carrera.png'),
('infantil', 'infantil.png'),
('feria', 'feria.png'),
('outdoor', 'outdoor.png'),
('taller', 'taller.png'),
('music', 'music.png');


-- Inserta datos de prueba en la tabla interesesPorDefecto
INSERT INTO interesesPorDefecto (nombreInteres) VALUES
('Deportes'),
('Música'),
('Cultura'),
('Arte'),
('Teatro'),
('Gastronomía'),
('Viajes'),
('Cine'),
('Fotografía'),
('Naturaleza'),
('Moda'),
('Belleza'),
('Cocina'),
('Tecnología');

-- Inserta datos de prueba en la tabla usuarios
INSERT INTO usuarios (nombre, contrasena, email, fechaNacimiento, recibeNotificaciones, intereses, roles, permiso, consentimiento_datos,aceptacionTerminosWeb) VALUES
('Ane', 'contraseña1', 'ane@example.com', '1995-03-15', false, 'Deportes, Música, Cultura', 'usuario', 1, '2024-04-01 12:00:00','2024-04-01 12:00:00'),
('Jon', 'contraseña2', 'jon@example.com', '1990-08-25', false, 'Arte, Teatro, Deportes', 'usuario', 1, '2024-04-02 12:00:00','2024-04-01 12:00:00'),
('Alejandro', 'contraseña3', 'maite@example.com', '1987-11-10', false, 'Gastronomía, Viajes, Música', 'usuario', 1, '2024-04-03 12:00:00','2024-04-01 12:00:00'),
('Gonzalo', 'contraseña4', 'iker@example.com', '1992-06-20', false, 'Cine, Fotografía, Naturaleza', 'usuario', 1, '2024-04-04 12:00:00','2024-04-01 12:00:00'),
('Leire', 'contraseña5', 'leire@example.com', '1988-09-30', false, 'Moda, Belleza, Cocina', 'usuario', 1, '2024-04-05 12:00:00','2024-04-01 12:00:00'),
('Pelayo', '1234', 'leire@example.com', '1988-09-30', false, 'Moda, Belleza, Cocina', 'usuario', 2, '2024-04-05 12:00:00','2024-04-01 12:00:00'),
('Maitane', '1234', 'maitane@example.com', '1988-09-30', true, 'Moda, Belleza, Cocina', 'usuario', 99, '2024-04-05 12:00:00','2024-04-01 12:00:00');

-- Inserta datos de prueba en la tabla eventos
INSERT INTO eventos (nombre, fechaEvento, detalles, idUsuariocreador, fechaCreacion, fechaAprobacion, idModeradorAprobacion, fechaUltimaModificacion, fechaPublicacion, idModeradorPublicacion, fechaFinalizacion, idModeradorFinalizacion, MotivoFinalizacion, Ubicacion) VALUES
('Concierto en Bilbao', '2024-05-10 20:00:00', 'Concierto de música pop en Bilbao.', 1, '2024-05-01 12:00:00', '2024-05-02 12:00:00', 7, '2024-05-02 12:00:00', '2024-05-02 12:00:00', 7, NULL,  NULL,  NULL, 'Bilbao'),
('Feria de Artesanía en Getxo', '2024-05-15 10:00:00', 'Feria de artesanía en la plaza de Getxo.', 2,  '2024-05-01 12:00:00', '2024-05-02 12:00:00', 7, '2024-05-02 12:00:00', '2024-05-02 12:00:00', 7, NULL,  NULL,  NULL, 'Getxo'),
('Ruta de Senderismo en Barakaldo', '2024-05-20 09:00:00', 'Ruta de senderismo por los montes de Barakaldo.', 3,  '2024-05-01 12:00:00', '2024-05-02 12:00:00', 7, '2024-05-02 12:00:00', '2024-05-02 12:00:00', 7, NULL,  NULL,  NULL, 'Barakaldo'),
('Exposición de Arte en Bilbao', '2024-05-25 12:00:00', 'Exposición de arte contemporáneo en el museo de Bilbao.', 4,  '2024-05-01 12:00:00', '2024-05-02 12:00:00', 7, '2024-05-02 12:00:00', '2024-05-02 12:00:00', 7, NULL,  NULL,  NULL, 'Bilbao'),
('Carrera Popular en Portugalete', '2024-06-01 09:30:00', 'Carrera popular por las calles de Portugalete.', 1,  '2024-05-01 12:00:00', '2024-05-02 12:00:00', 7, '2024-05-02 12:00:00', '2024-05-02 12:00:00', 7, NULL,  NULL,  NULL,'Portugalete'),
('Concierto de Jazz en Santurtzi', '2024-06-05 19:00:00', 'Concierto de jazz en la plaza del ayuntamiento de Santurtzi.', 2,  '2024-05-01 12:00:00', '2024-05-02 12:00:00', 7, '2024-05-02 12:00:00', '2024-05-02 12:00:00', 7, NULL,  NULL,  NULL, 'Santurtzi'),
('Festival de Teatro en Basauri', '2024-06-10 20:30:00', 'Festival de teatro en el teatro de Basauri.', 3,  '2024-05-01 12:00:00', '2024-05-02 12:00:00', 7, '2024-05-02 12:00:00', '2024-05-02 12:00:00', 7, NULL,  NULL,  NULL, 'Basauri'),
('Exhibición de Danza en Bilbao', '2024-06-15 18:00:00', 'Exhibición de danza contemporánea en el teatro Arriaga.', 4,  '2024-05-01 12:00:00', '2024-05-02 12:00:00', 7, '2024-05-02 12:00:00', '2024-05-02 12:00:00', 7, NULL,  NULL,  NULL, 'Bilbao'),
('Mercado Medieval en Leioa', '2024-06-20 11:00:00', 'Mercado medieval en el centro de Leioa.', 1,  '2024-05-01 12:00:00', '2024-05-02 12:00:00', 7, '2024-05-02 12:00:00', '2024-05-02 12:00:00', 7, NULL,  NULL,  NULL, 'Leioa'),
('Carrera de Montaña en Mungia', '2024-06-25 08:00:00', 'Carrera de montaña por los montes de Mungia.', 2,'2024-05-01 12:00:00', '2024-05-02 12:00:00', 7, '2024-05-02 12:00:00', '2024-05-02 12:00:00', 7, NULL,  NULL,  NULL,'Mungia'),
('Festival de dantzak en Berango', '2024-08-20 19:00:00', 'Festival de dantzak en la plaza del pueblo de Berango.', 1, '2024-05-01 12:00:00', '2024-05-02 12:00:00', 7, '2024-05-02 12:00:00', '2024-05-02 12:00:00', 7, NULL,  NULL,  NULL, 'Berango'),
('Feria de Pintxos en Urduliz', '2024-08-25 12:00:00', 'Feria de pintxos en el centro de Urduliz.', 2, '2024-05-01 12:00:00', '2024-05-02 12:00:00', 7, '2024-05-02 12:00:00', '2024-05-02 12:00:00', 7, NULL,  NULL,  NULL, 'Urduliz');

-- Inserta datos de prueba en la tabla clasificacionEventos
INSERT INTO clasificacionEventos (idActividad, idEvento) VALUES
(11, 1),  -- Concierto en Bilbao -> Actividad: Music
(9, 2),   -- Feria de Artesanía en Getxo -> Actividad: Feria
(10, 3),  -- Ruta de Senderismo en Barakaldo -> Actividad: Music
(3, 4),   -- Exposición de Arte en Bilbao -> Actividad: Cultura
(7, 5),   -- Carrera Popular en Portugalete -> Actividad: Carrera
(12, 6),  -- Concierto de Jazz en Santurtzi -> Actividad: Music
(8, 7),   -- Festival de Teatro en Basauri -> Actividad: Infantil
(2, 8),   -- Exhibición de Danza en Bilbao -> Actividad: Bebés
(9, 9),   -- Mercado Medieval en Leioa -> Actividad: Feria
(5, 10),  -- Carrera de Montaña en Mungia -> Actividad: Deportiva
(3, 11),  -- Festival de dantzak en Berango -> Actividad: Cultura
(9, 12);  -- Feria de Pintxos en Urduliz -> Actividad: Feria


-- Inserta datos de prueba en la tabla gestionFavoritos
INSERT INTO gestionFavoritos (idEvento, idUsuario, fechaCreacionFavorito) VALUES
(1, 1, '2024-05-01 12:00:00'),
(2, 2, '2024-05-02 12:00:00'),
(7, 3, '2024-05-03 12:00:00'),
(7, 4, '2024-05-04 12:00:00');

-- Inserta datos de prueba en la tabla accesos
INSERT INTO accesos (fechaAcceso, idUsuario) VALUES
('2024-05-01 12:00:00', 1),
('2024-05-02 12:00:00', 2),
('2024-05-03 12:00:00', 3),
('2024-05-04 12:00:00', 4),
('2024-05-04 12:00:00', 5),
('2024-05-04 12:00:00', 6),
('2024-05-04 12:00:00', 7);