DROP SCHEMA IF EXISTS foro_alura;
CREATE DATABASE foro_alura;

USE foro_alura;

CREATE TABLE topicos (
    id_topico BIGINT AUTO_INCREMENT NOT NULL,
    titulo VARCHAR(100) NOT NULL,
    mensaje TEXT NOT NULL,
    fecha_creacion DATE NOT NULL,
    estado ENUM('SOLUCIONADO', 'NOSOLUCIONADO') NOT NULL,
    autor VARCHAR(200) NOT NULL,
    curso VARCHAR(200) NOT NULL,
    PRIMARY KEY (id_topico)
);

INSERT INTO topicos(titulo, mensaje, fecha_creacion, estado, autor, curso) VALUES (
    'Topico 1', 
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas volutpat finibus purus nec tempor. Cras vulputate pellentesque ligula 
    venenatis volutpat. Maecenas cursus tempus mi, non interdum lectus porta nec. Ut cursus ligula eu rhoncus euismod. Integer sit amet maximus 
    dui, ut cursus mauris. Sed sed ligula ut lacus interdum efficitur. Sed at metus varius nisl pellentesque blandit sit amet non tortor. 
    In sed diam et dolor euismod semper. Cras luctus at leo sit amet rhoncus.',
    '2023-05-02',
    'NOSOLUCIONADO',
    'Saul MM',
    'Springboot'
    );
