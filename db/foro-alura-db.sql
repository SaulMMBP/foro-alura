DROP SCHEMA IF EXISTS foro_alura;
CREATE DATABASE foro_alura;

USE foro_alura;

CREATE TABLE usuarios (
    id_usuario BIGINT AUTO_INCREMENT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    contrasena VARCHAR(100) NOT NULL,
    PRIMARY KEY(id_usuario)
);

CREATE TABLE cursos (
    id_curso BIGINT AUTO_INCREMENT NOT NULL,
    nombre VARCHAR(200) NOT NULL,
    categoria VARCHAR(100) NOT NULL,
    PRIMARY KEY (id_curso)
);

CREATE TABLE topicos (
    id_topico BIGINT AUTO_INCREMENT NOT NULL,
    titulo VARCHAR(100) NOT NULL,
    mensaje TEXT NOT NULL,
    fecha_creacion DATE NOT NULL,
    estado ENUM('NO_RESPONDIDO', 'NO_SOLUCIONADO', 'SOLUCIONADO', 'CERRADO') NOT NULL,
    autor BIGINT NOT NULL,
    curso BIGINT NOT NULL,
    PRIMARY KEY (id_topico),
    FOREIGN KEY (autor) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (curso) REFERENCES cursos(id_curso)
);

CREATE TABLE respuestas (
    id_respuesta BIGINT AUTO_INCREMENT NOT NULL,
    mensaje TEXT NOT NULL,
    topico BIGINT NOT NULL,
    fecha_creacion DATE NOT NULL,
    autor BIGINT NOT NULL,
    solucion BOOLEAN NOT NULL DEFAULT false,
    PRIMARY KEY (id_respuesta),
    FOREIGN KEY (topico) REFERENCES topicos(id_topico),
    FOREIGN KEY (autor) REFERENCES usuarios(id_usuario)
);

# Datos ejemplo

INSERT INTO usuarios(nombre, email, contrasena) VALUES 
    ('Saul Malagon', 'saul@mail.com', '1234'),
    ('Daniel Juarez', 'daniel@mail.com', '5678');
    
INSERT INTO cursos(nombre, categoria) VALUES
    ('Springboot rest api', 'java'),
    ('Introducción a machine learning', 'python');

INSERT INTO topicos(titulo, mensaje, fecha_creacion, estado, autor, curso) VALUES 
    ('Topico 1', 
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas volutpat finibus purus nec tempor. Cras vulputate pellentesque ligula 
    venenatis volutpat. Maecenas cursus tempus mi, non interdum lectus porta nec. Ut cursus ligula eu rhoncus euismod. Integer sit amet maximus 
    dui, ut cursus mauris. Sed sed ligula ut lacus interdum efficitur. Sed at metus varius nisl pellentesque blandit sit amet non tortor. 
    In sed diam et dolor euismod semper. Cras luctus at leo sit amet rhoncus.',
    '2023-05-02',
    'NO_SOLUCIONADO',
    1,
    1),
    ('Topico 2', 
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas volutpat finibus purus nec tempor. Cras vulputate pellentesque ligula 
    venenatis volutpat. Maecenas cursus tempus mi, non interdum lectus porta nec. Ut cursus ligula eu rhoncus euismod. Integer sit amet maximus 
    dui, ut cursus mauris. Sed sed ligula ut lacus interdum efficitur. Sed at metus varius nisl pellentesque blandit sit amet non tortor. 
    In sed diam et dolor euismod semper. Cras luctus at leo sit amet rhoncus.',
    '2023-05-03',
    'NO_SOLUCIONADO',
    2,
    2);
    
INSERT INTO respuestas(mensaje, topico, fecha_creacion, autor, solucion) VALUES
    ('Lorem ipsum dolor sit amet, consectetur adipiscing elit.', 1, '2023-05-03', 2, 0),
    ('Maecenas cursus tempus mi, non interdum lectus porta nec. Ut cursus ligula eu rhoncus euismod. Integer sit amet maximus 
    dui, ut cursus mauris.', 2, '2023-05-03', 1, 1);
