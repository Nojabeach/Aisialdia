-- Crea un esquema llamado aisialdia
CREATE SCHEMA aisialdia;

-- Selecciona el esquema aisialdia para trabajar en él
USE aisialdia;

-- CREA LA TABLA actividades
CREATE TABLE actividades (
    -- ID de la actividad (clave primaria auto-incrementable)
    idActividad INT PRIMARY KEY AUTO_INCREMENT,
    -- Tipo de actividad (cadena de texto de hasta 255 caracteres)
    tipoActividad VARCHAR(255),
    -- Donde se guarda la ruta de la foto
    fotoActividad varchar(255) 
) ;

-- CREA LA TABLA usuarios
CREATE TABLE usuarios (
    idUsuario INT PRIMARY KEY AUTO_INCREMENT, -- ID del usuario (clave primaria auto-incrementable)
    nombre VARCHAR(255) NOT NULL, -- Nombre del usuario (cadena de texto obligatoria)
    contrasena VARCHAR(255) NOT NULL, -- Contraseña del usuario almacenada de forma segura (cadena de caracteres de 60) obligatoria
    email VARCHAR(255) NOT NULL, -- Correo electrónico del usuario (cadena de texto obligatoria)
    fechaNacimiento DATE, -- Fecha de nacimiento del usuario (fecha)
    recibeNotificaciones BOOLEAN DEFAULT FALSE, -- Indica si el usuario desea recibir notificaciones (booleano, por defecto FALSO)
    intereses TEXT, -- Intereses del usuario (texto)
    roles ENUM('usuario','moderador','administrador') DEFAULT 'usuario', -- Rol del usuario (enumeración con opciones: usuario, moderador, administrador, por defecto 'usuario')
    permiso int,-- Indice el nivel de permiso que va a tener el usuario
    consentimientoDatos DATETIME DEFAULT NULL, -- Fecha y hora del consentimiento del usuario para el tratamiento de sus datos (fecha y hora)
    aceptacionTerminosWeb datetime default null -- Fecha y hora del consentimiento del usuario para el tratamiento de sus datos (fecha y hora)
) ;

-- CREA LA TABLA eventos
CREATE TABLE eventos (
    idEvento INT PRIMARY KEY AUTO_INCREMENT, -- ID del evento (clave primaria auto-incrementable)
    nombre VARCHAR(255), -- Nombre del evento (cadena de texto de hasta 255 caracteres)
    fechaEvento datetime, -- Fecha del evento (fecha y hora)
    detalles TEXT, -- Detalles del evento (texto)
    idUsuariocreador INT, -- ID del usuario creador del evento (referencia a la tabla usuarios)
    fechaCreacion DATETIME, -- Fecha de creación del evento (fecha y hora)
    fechaAprobacion DATETIME, -- Fecha de aprobación del evento (fecha y hora)
    idModeradorAprobacion INT, -- ID del moderador que aprobó el evento (entero)
    fechaUltimaModificacion DATETIME, -- Fecha de ultima modificación del evento (fecha y hora)
    fechaPublicacion DATETIME, -- Fecha de publicación del evento (fecha y hora)
    idModeradorPublicacion INT, -- ID del moderador que publicó el evento (entero)
    fechaFinalizacion DATETIME, -- Fecha de finalización del evento (fecha y hora)
    idModeradorFinalizacion INT, -- ID del moderador que finalizó el evento (entero)
    MotivoFinalizacion ENUM('FinVisibilidad', 'Rechazado', 'ReporteNegativo', 'Otros') DEFAULT 'FinVisibilidad', -- Motivo de finalización del evento
    Ubicacion VARCHAR(255), -- Ubicación del evento (cadena de texto de hasta 255 caracteres)
    FOREIGN KEY (idUsuariocreador) REFERENCES usuarios(idUsuario)
) ;


-- Crea la tabla clasificacionEventos
CREATE TABLE clasificacionEventos (
    idActividad INT,
    idEvento INT,
    PRIMARY KEY (idActividad, idEvento),
    FOREIGN KEY (idActividad) REFERENCES actividades(idActividad),
    FOREIGN KEY (idEvento) REFERENCES eventos(idEvento)
);


-- Crea la tabla gestionFavoritos
CREATE TABLE gestionFavoritos (
    idEvento INT,
    idUsuario INT,
    fechaCreacionFavorito DATETIME,
    PRIMARY KEY (idEvento, idUsuario),
    FOREIGN KEY (idEvento) REFERENCES eventos(idEvento),
    FOREIGN KEY (idUsuario) REFERENCES usuarios(idUsuario)
);
-- Crea la tabla accesos
CREATE TABLE accesos (
    fechaAcceso DATETIME NOT NULL,
    idUsuario INT NOT NULL,
    PRIMARY KEY (fechaAcceso, idUsuario),
    FOREIGN KEY (idUsuario) REFERENCES usuarios(idUsuario)
);
CREATE TABLE interesesPorDefecto (
    idInteres INT PRIMARY KEY AUTO_INCREMENT, -- ID del interés (clave primaria auto-incrementable)
    nombreInteres VARCHAR(255) NOT NULL -- Nombre del interés (cadena de texto obligatoria)
) ;