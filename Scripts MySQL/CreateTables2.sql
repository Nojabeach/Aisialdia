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
    aceptacionTerminosWebusuarios datetime default null -- Fecha y hora del consentimiento del usuario para el tratamiento de sus datos (fecha y hora)
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

-- CREA LA TABLA clasificacionEventos
CREATE TABLE clasificacionEventos (
	-- ID de la clasificacion del evento-actividad (entero)
	idClasificacion int  PRIMARY KEY AUTO_INCREMENT,
    -- ID de la actividad relacionada (entero)
    idActividad INT  , 
    -- ID del evento relacionado (entero)
    idEvento INT ,
    -- Clave foránea a la tabla actividades (referencia a la columna ID_actividad)
    FOREIGN KEY (idActividad) REFERENCES actividades(idActividad),
    -- Clave foránea a la tabla eventos (referencia a la columna ID_evento)
    FOREIGN KEY (idEvento) REFERENCES eventos(idEvento)
) ;

-- CREA LA TABLA gestionFavoritos
CREATE TABLE gestionFavoritos (
	-- ID de la gestion del favorito relacion entre evento-usuario (entero)
	idFavorito INT PRIMARY KEY AUTO_INCREMENT,
    -- ID del evento relacionado (entero)
    idEvento INT ,
    -- ID del usuario relacionado (entero)
    idUsuario INT  ,
    -- Fecha de creacion del favorito por parte del usuario
    fechaCreacionFavorito datetime,
    -- Clave foránea a la tabla eventos (referencia a la columna ID_evento)
    FOREIGN KEY (idEvento) REFERENCES eventos(idEvento),
    -- Clave foránea a la tabla usuarios (referencia a la columna ID_Usuario)
    FOREIGN KEY (idUsuario) REFERENCES usuarios(idUsuario)
) ; -- Agrega el motor de almacenamiento InnoDB

-- CREA LA TABLA accesos
CREATE TABLE accesos (
	idAcceso int primary key auto_increment,
    -- Fecha y hora del acceso (clave primaria, no permite nulos)
    fechaAcceso DATETIME  NOT NULL, 
    -- ID del usuario que realizó el acceso (entero)
    idUsuario INT  not null,
    -- Clave foránea a la tabla usuarios (referencia a la columna ID_Usuario)
    FOREIGN KEY (idUsuario) REFERENCES usuarios(idUsuario)
) ;
CREATE TABLE interesesPorDefecto (
    idInteres INT PRIMARY KEY AUTO_INCREMENT, -- ID del interés (clave primaria auto-incrementable)
    nombreInteres VARCHAR(255) NOT NULL -- Nombre del interés (cadena de texto obligatoria)
) ;