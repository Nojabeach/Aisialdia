use aisialdia;
-- Inserta datos de prueba en la tabla actividades
INSERT INTO actividades (tipoActividad, fotoActividad) VALUES
('adolescentes', 'adolescentes.png'),
('bebe', 'bebe.png'),
('cultura', 'cultura.png'),
('ciclismo', 'ciclismo.png'),
('deportiva', 'deportiva.png'),
('indoor', 'indoor.png'),
('carrera', 'carrera.png'),
('infantil', 'infantil.png'),
('feria', 'feria.png'),
('outdoor', 'outdoor.png'),
('taller', 'taller.png'),
('music', 'music.png'),
('festival', 'festival.png'),
('danza', 'danza.png'),
('exposicion', 'exposicion.png'),
('rally', 'rally.png'),
('jazz', 'jazz.png'),
('teatro', 'teatro.png'),
('senderismo', 'senderismo.png'),
('montaña', 'montaña.png');


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
INSERT INTO usuarios (nombre, contrasena, email, fechaNacimiento, recibeNotificaciones, intereses, roles, permiso, consentimientoDatos,aceptacionTerminosWeb) VALUES
('Alejandro', '81dc9bdb52d04dc20036dbd8313ed055', 'alejandro@lloreriaDAMyDAW.es', '2024-06-07', false, 'Cultura', 'moderador', 2, '2024-04-01 12:00:00','2024-04-01 12:00:00'),
('Gonzalo', '81dc9bdb52d04dc20036dbd8313ed055', 'gonzalo@lloreriaDAMyDAW.es', '2024-06-07', false, 'Cine', 'usuario', 1, '2024-04-02 12:00:00','2024-04-01 12:00:00'),
('Grisella', '81dc9bdb52d04dc20036dbd8313ed055', 'grisella@lloreriaDAMyDAW.es', '2024-06-07', false, 'Teatro', 'usuario', 1, '2024-04-03 12:00:00','2024-04-01 12:00:00'),
('Yosiris', '81dc9bdb52d04dc20036dbd8313ed055', 'yosiris@lloreriaDAMyDAW.es', '2024-06-07', false, 'Cocina', 'usuario', 1, '2024-04-04 12:00:00','2024-04-01 12:00:00'),
('Noelia', '81dc9bdb52d04dc20036dbd8313ed055', 'noelia@lloreriaDAMyDAW.es', '2024-06-07', false, 'Biología', 'usuario', 1, '2024-04-05 12:00:00','2024-04-01 12:00:00'),
('Pelayo', '81dc9bdb52d04dc20036dbd8313ed055', 'pelayo@lloreriaDAMyDAW.es', '2024-06-07', false, 'Geología', 'moderador', 2, '2024-04-05 12:00:00','2024-04-01 12:00:00'),
('Maitane', '81dc9bdb52d04dc20036dbd8313ed055', 'maitane@lloreriaDAMyDAW.es', '2024-06-07', true, 'Moda', 'administrador', 99, '2024-04-05 12:00:00','2024-04-01 12:00:00'),
('Alvaro', '81dc9bdb52d04dc20036dbd8313ed055', 'alvaro@lloreriaDAMyDAW.es', '2024-06-07', true, 'Arte', 'usuario', 1, '2024-04-05 12:00:00','2024-04-01 12:00:00'),
('Pepe', '81dc9bdb52d04dc20036dbd8313ed055', 'pepe@lloreriaDAMyDAW.es', '2024-06-07', true, 'Viajes', 'usuario', 1, '2024-04-05 12:00:00','2024-04-01 12:00:00'),
('Angel', '81dc9bdb52d04dc20036dbd8313ed055', 'angel@lloreriaDAMyDAW.es', '2024-06-07', true, 'Tecnología', 'usuario', 1, '2024-04-05 12:00:00','2024-04-01 12:00:00'),
('Yulian', '81dc9bdb52d04dc20036dbd8313ed055', 'yulian@lloreriaDAMyDAW.es', '2024-06-07', true, 'Naturaleza', 'usuario', 1, '2024-04-05 12:00:00','2024-04-01 12:00:00'),
('Victor', '81dc9bdb52d04dc20036dbd8313ed055', 'victor@lloreriaDAMyDAW.es', '2024-06-07', true, 'Deportes', 'usuario', 1, '2024-04-05 12:00:00','2024-04-01 12:00:00');
-- Inserta datos de prueba en la tabla eventos
INSERT INTO eventos (nombre, fechaEvento, detalles, idUsuariocreador, fechaCreacion, fechaAprobacion, idModeradorAprobacion, fechaUltimaModificacion, fechaPublicacion, idModeradorPublicacion, fechaFinalizacion, idModeradorFinalizacion, MotivoFinalizacion, Ubicacion) VALUES
('Concierto en Bilbao', '2024-05-10 20:00:00', 'Concierto de música pop en Bilbao.', 1, '2024-05-01 12:00:00', '2024-05-02 12:00:00', 7, '2024-05-02 12:00:00', '2024-05-02 18:00:00', 7, NULL, NULL, NULL, 'Bilbao'),
('Feria de Artesanía en Getxo', '2024-05-15 10:00:00', 'Feria de artesanía en la plaza de Getxo.', 2, '2024-05-01 12:00:00', '2024-05-02 12:00:00', 7, '2024-05-02 12:00:00', '2024-05-02 18:00:00', 7, NULL, NULL, NULL, 'Getxo'),
('Ruta de Senderismo en Barakaldo', '2024-05-20 09:00:00', 'Ruta de senderismo por los montes de Barakaldo.', 3, '2024-05-01 12:00:00', '2024-05-02 12:00:00', 7, '2024-05-02 12:00:00', '2024-05-02 18:00:00', 7, NULL, NULL, NULL, 'Barakaldo'),
('Exposición de Arte en Bilbao', '2024-05-25 12:00:00', 'Exposición de arte contemporáneo en el museo de Bilbao.', 4, '2024-05-01 12:00:00', '2024-05-02 12:00:00', 7, '2024-05-02 12:00:00', '2024-05-02 18:00:00', 7, NULL, NULL, NULL, 'Bilbao'),
('Carrera Popular en Portugalete', '2024-06-01 09:30:00', 'Carrera popular por las calles de Portugalete.', 1, '2024-05-01 12:00:00', '2024-05-02 12:00:00', 7, '2024-05-02 12:00:00', '2024-05-02 18:00:00', 7, NULL, NULL, NULL, 'Portugalete'),
('Concierto de Jazz en Santurtzi', '2024-06-05 19:00:00', 'Concierto de jazz en la plaza del ayuntamiento de Santurtzi.', 2, '2024-05-01 12:00:00', '2024-05-02 12:00:00', 7, '2024-05-02 12:00:00', '2024-05-02 18:00:00', 7, NULL, NULL, NULL, 'Santurtzi'),
('Festival de Teatro en Basauri', '2024-06-10 20:30:00', 'Festival de teatro en el teatro de Basauri.', 3, '2024-05-01 12:00:00', '2024-05-02 12:00:00', 7, '2024-05-02 12:00:00', '2024-05-02 18:00:00', 7, NULL, NULL, NULL, 'Basauri'),
('Exhibición de Danza en Bilbao', '2024-06-15 18:00:00', 'Exhibición de danza contemporánea en el teatro Arriaga.', 4, '2024-05-01 12:00:00', '2024-05-02 12:00:00', 7, '2024-05-02 12:00:00', '2024-05-02 18:00:00', 7, NULL, NULL, NULL, 'Bilbao'),
('Mercado Medieval en Leioa', '2024-06-20 11:00:00', 'Mercado medieval en el centro de Leioa.', 1, '2024-05-01 12:00:00', '2024-05-02 12:00:00', 7, '2024-05-02 12:00:00', NULL, NULL, NULL, NULL, NULL, 'Leioa'),
('Carrera de Montaña en Mungia', '2024-06-25 08:00:00', 'Carrera de montaña por los montes de Mungia.', 2, '2024-05-01 12:00:00', '2024-05-02 12:00:00', 7, '2024-05-02 12:00:00', NULL, NULL, NULL, NULL, NULL, 'Mungia'),
('Festival de dantzak en Berango', '2024-08-20 19:00:00', 'Festival de dantzak en la plaza del pueblo de Berango.', 1, '2024-05-01 12:00:00', '2024-05-02 12:00:00', 7, '2024-05-02 12:00:00', '2024-05-02 18:00:00', 7, '2024-08-21 19:00:00', 7, 'Rechazado', 'Berango'),
('Feria de Pintxos en Urduliz', '2024-08-25 12:00:00', 'Feria de pintxos en el centro de Urduliz.', 2, '2024-05-01 12:00:00', '2024-05-02 12:00:00', 7, '2024-05-02 12:00:00', NULL, NULL, '2024-08-24 12:00:00', 7, 'ReporteNegativo', 'Urduliz'),
('Concierto de Rock en Sestao', '2024-09-01 20:00:00', 'Concierto de rock en el parque de Sestao.', 3, '2024-05-01 12:00:00', NULL, NULL, '2024-05-02 12:00:00', NULL, NULL, NULL, NULL, NULL, 'Sestao'),
('Festival de Cine en Algorta', '2024-09-05 17:00:00', 'Festival de cine en el teatro de Algorta.', 4, '2024-05-01 12:00:00', NULL, NULL, '2024-05-02 12:00:00', NULL, NULL, NULL, NULL, NULL, 'Algorta'),
('Festival de Música en Portugalete', '2024-09-10 19:00:00', 'Festival de música en el puerto de Portugalete.', 1, '2024-05-01 12:00:00', NULL, NULL, '2024-05-02 12:00:00', NULL, NULL, NULL, NULL, NULL, 'Portugalete'),
('Exposición de Fotografía en Bilbao', '2024-09-15 12:00:00', 'Exposición de fotografía en el museo de Bilbao.', 2, '2024-05-01 12:00:00', NULL, NULL, '2024-05-02 12:00:00', NULL, NULL, NULL, NULL, NULL, 'Bilbao'),
('Rally de Motos en Barakaldo', '2024-09-20 09:00:00', 'Rally de motos en el circuito de Barakaldo.', 3, '2024-05-01 12:00:00', NULL, NULL, '2024-05-02 12:00:00', NULL, NULL, NULL, NULL, NULL, 'Barakaldo'),
('Festival de Teatro en Basauri', '2024-09-25 20:30:00', 'Festival de teatro en el teatro de Basauri.', 4, '2024-05-01 12:00:00', NULL, NULL, '2024-05-02 12:00:00', NULL, NULL, NULL, NULL, NULL, 'Basauri');

INSERT INTO clasificacionEventos (idActividad, idEvento) VALUES
(12, 1),  -- Concierto en Bilbao -> Actividad: Music
(9, 2),   -- Feria de Artesanía en Getxo -> Actividad: Feria
(19, 3),   -- Ruta de Senderismo en Barakaldo -> Actividad: Senderismo
(15, 4),   -- Exposición de Arte en Bilbao -> Actividad: Exposición
(7, 5),   -- Carrera Popular en Portugalete -> Actividad: Carrera
(6, 6),   -- Concierto de Jazz en Santurtzi -> Actividad: Jazz
(13, 7),   -- Festival de Teatro en Basauri -> Actividad: Festival
(14, 8),   -- Exhibición de Danza en Bilbao -> Actividad: Danza
(9, 9),   -- Mercado Medieval en Leioa -> Actividad: Feria
(20, 10), -- Carrera de Montaña en Mungia -> Actividad: Montaña
(13, 11), -- Festival de dantzak en Berango -> Actividad: Festival
(9, 12), -- Feria de Pintxos en Urduliz -> Actividad: Feria
(14, 13), -- Taller de Cocina en Getxo -> Actividad: Taller
(8, 14), -- Espectáculo de Magia en Bilbao -> Actividad: Infantil
(4, 15), -- Carrera de Bicis en Barakaldo -> Actividad: Ciclismo
(13, 16), -- Festival de Cine en Santurtzi -> Actividad: Festival
(15, 17), -- Exposición de Fotografía en Basauri -> Actividad: Exposición
(19, 18); -- Ruta de Senderismo en Gorliz -> Actividad: Senderismo

-- Inserta datos de prueba en la tabla gestionFavoritos
INSERT INTO gestionFavoritos (idEvento, idUsuario, fechaCreacionFavorito) VALUES
(1, 1, '2024-05-01 12:00:00'),
(2, 2, '2024-05-02 12:00:00'),
(3, 3, '2024-05-03 12:00:00'),
(4, 4, '2024-05-04 12:00:00'),
(5, 5, '2024-05-05 12:00:00'),
(6, 6, '2024-05-06 12:00:00'),
(7, 7, '2024-05-07 12:00:00'),
(8, 8, '2024-05-08 12:00:00'),
(9, 9, '2024-05-09 12:00:00'),
(1, 10, '2024-05-10 12:00:00');

-- Inserta datos de prueba en la tabla accesos
INSERT INTO accesos (fechaAcceso, idUsuario) VALUES
('2024-05-01 12:00:00', 1),
('2024-05-02 12:00:00', 2),
('2024-05-03 12:00:00', 3),
('2024-05-04 12:00:00', 4),
('2024-05-04 12:00:00', 5),
('2024-05-04 12:00:00', 6),
('2024-05-04 12:00:00', 7),
('2024-05-05 12:00:00', 8),
('2024-05-06 12:00:00', 9),
('2024-05-07 12:00:00', 10),
('2024-05-08 12:00:00', 1),
('2024-05-09 12:00:00', 2),
('2024-05-10 12:00:00', 3);