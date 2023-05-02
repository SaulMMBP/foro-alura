DROP SCHEMA IF EXISTS foro_alura;
CREATE DATABASE foro_alura;

USE foro_alura;

CREATE TABLE topicos (
    id_topico BIGINT AUTO_INCREMENT NOT NULL,
    titulo VARCHAR(100) NOT NULL,
    mensaje TEXT NOT NULL,
    fecha_creacion DATE NOT NULL,
    estado ENUM('SOLUCIONADO', 'NOSOLUCIONADO'),
    autor VARCHAR(200) NOT NULL,
    curso VARCHAR(200) NOT NULL,
    PRIMARY KEY (id_topico)
);