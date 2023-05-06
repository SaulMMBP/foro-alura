# Challenge ONE | Back End | Foro Alura 

<div align="center" >
     <img width="200" heigth="200" src="https://user-images.githubusercontent.com/91544872/209678377-70b50b21-33de-424c-bed8-6a71ef3406ff.png">
</div>

<div align="center">
     <a href="https://www.linkedin.com/company/oracle/">
          <img src="https://img.shields.io/badge/linkedin:-🧡 Oracle-C74634?style=for-the-badge&logo=linkedin"/>
     </a>
     <a href="https://www.linkedin.com/company/alura-latam/mycompany/">
          <img src="https://img.shields.io/badge/linkedin:-💙 Alura Latam-061E3C?style=for-the-badge&logo=linkedin"/>
     </a>

---
¡Bienvenido al proyecto con mi solución al desafío Foro Alura con Java y Spring! 

---
</div>

## 🗄️ Base de datos

```mermaid
erDiagram
     usuarios {
          BIGINT id_usuario
          VARCHAR nombre
          VARCHAR email
          VARCHAR contrasena
     }

     cursos {
          BIGINT id_curso
          VARCHAR nombre
          VARCHAR categoria
     }

     topicos {
          BIGINT id_topico
          VARCHAR titulo
          TEXT mensaje
          DATE fecha_creacion
          ENUM estado
          VARCHAR autor
          VARCHAR curso
     }

     respuestas {
          BIGINT respuestas
          TEXT mensaje
          BIGINT topico
          DATE fecha_creacion
          BIGINT autor
          BOOLEAN solucion
     }

     usuarios ||--|{ topicos : make
     usuarios ||--|{ respuestas : make
     topicos }|--|| cursos : of
     topicos ||--|{ respuestas : has
```

## 🖥️ Tecnologías Utilizadas:

- [Eclipse](https://www.eclipse.org/)
- [MySql](https://www.mysql.com/)
- [Java](https://www.java.com/en/)

- [Spring Security](https://start.spring.io/)
- [Token JWT](https://jwt.io/)

## ⚙️ Indicaciones para ejecución desde IDE

1. Ejecutar script sql de la carpeta db en su sistema gestor de base de datos de mysql. Yo usé MySQL Workbench.
2. Importar el proyecto a su espacio de trabajo.
3. Asignar las variables de entorno siguientes en su IDE.
     - (Opcional) MYSQL_HOST: el host de tu base de datos, si usas localhost puedes omitirlo
     - MYSQL_USER: tu usuario de mysql
     - MYSQL_PASSWORD: tu contraseña de mysql
     - SECURITY_SECRET: cualquier string, yo le puse "quesadilla"
4. Una vez en ejecución, puedes utilizar postman para realizar las peticiones, pero primero debes realizar una petición a "/login" con los datos de algún usuario de la base de datos para recibir un token con el que podrás realizar peticiones a cualquier otro endpoint por hasta 2 horas. Pasando las 2 horas, tendrás que loggearte de nuevo.

## Samples
### Login
- Obtener JWT Token

Request
```json
{
    "email": "saul@mail.com",
    "contrasena": "1234"
}
```

General Headers
```
URL: http://localhost:8080/login
Method: POST
Status Code: 200 OK
```

Response 
```json
{
    "jwtToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJmb3JvIGFsdXJhIiwic3ViIjoic2F1bEBtYWlsLmNvbSIsImlkIjoxLCJleHAiOjE2ODM0MDQ5Mjh9.w6Vk0RCLVxRx5Cz9YKr78WQM96C9u8vK6BV7Sk7z2K4"
}
```

### Usuarios
-  Obtener todos los usuarios

General Headers
```
URL: http://localhost:8080/usuarios
Method: GET
Status Code: 200 OK
```

Response
```json
{
    "_embedded": {
        "usuarios": [
            {
                "id": 1,
                "nombre": "Saul Malagon",
                "email": "saul@mail.com",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/usuarios/1"
                    },
                    "usuarios": {
                        "href": "http://localhost:8080/usuarios"
                    }
                }
            },
            {
                "id": 2,
                "nombre": "Daniel Juarez",
                "email": "daniel@mail.com",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/usuarios/2"
                    },
                    "usuarios": {
                        "href": "http://localhost:8080/usuarios"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/usuarios"
        }
    }
}
```

- Obtener usuario con id 1

General Headers
```
URL: http://localhost:8080/usuarios/1
Method: GET
Status Code: 200 OK
```
Resposne
```json
{
    "id": 1,
    "nombre": "Saul Malagon",
    "email": "saul@mail.com",
    "_links": {
        "self": {
            "href": "http://localhost:8080/usuarios/1"
        },
        "usuarios": {
            "href": "http://localhost:8080/usuarios"
        }
    }
}
```

-  Crear nuevo usuario
Request
```json
{
    "nombre": "Alejandro Lopez",
    "email": "alopez@mail.com",
    "contrasena": "1234"
}
```

General Headers
```
URL: http://localhost:8080/usuarios
Method: POST
Status Code: 201 Created
```

Response
```json
{
    "id": 3,
    "nombre": "Alejandro Lopez",
    "email": "alopez@mail.com",
    "_links": {
        "self": {
            "href": "http://localhost:8080/usuarios/3"
        },
        "usuarios": {
            "href": "http://localhost:8080/usuarios"
        }
    }
}
```
- Actualizar datos de usuario

Campos actualizables
-   - nombre: string
    - email: string 
    - contrasena: string

Request
```json
{
    "id": 3,
    "email": "alex.lopez@mail.com"
}
```

General Headers
```
URL: http://localhost:8080/usuarios
Method: PUT
Status Code: 201 Created
```

Response
```json
{
    "id": 3,
    "nombre": "Alejandro Lopez",
    "email": "alex.lopez@mail.com",
    "_links": {
        "self": {
            "href": "http://localhost:8080/usuarios/3"
        },
        "usuarios": {
            "href": "http://localhost:8080/usuarios"
        }
    }
}
```
- Eliminar usuario con id 3

General Headers 
```
URL: http://localhost:8080/usuarios/3
Method: DELETE
Status Code: 204 No Content
```

### Cursos
- Obtener todos los cursos

General Headers
```
URL: http://localhost:8080/cursos
Method: GET
Status Code: 200 OK
```

Response
```json
{
    "_embedded": {
        "cursos": [
            {
                "id": 1,
                "nombre": "Springboot rest api",
                "categoria": "java",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/cursos/1"
                    },
                    "cursos": {
                        "href": "http://localhost:8080/cursos"
                    }
                }
            },
            {
                "id": 2,
                "nombre": "Introducción a machine learning",
                "categoria": "python",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/cursos/2"
                    },
                    "cursos": {
                        "href": "http://localhost:8080/cursos"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/cursos"
        }
    }
}
```

- Obtener curso con id 1

General Headers
```
URL: http://localhost:8080/cursos/1
Method: GET
Status Code: 200 OK
```

Response 
```json
{
    "id": 1,
    "nombre": "Springboot rest api",
    "categoria": "java",
    "_links": {
        "self": {
            "href": "http://localhost:8080/cursos/1"
        },
        "cursos": {
            "href": "http://localhost:8080/cursos"
        }
    }
}
```

- Crear nuevo curso

Request
```json
{
    "nombre": "Java SE",
    "categoria": "Tecnología"
}
```

General Headers
```
URL: http://localhost:8080/cursos
Method: POST
Status Code: 201 Created
```

Response
```json
{
    "id": 3,
    "nombre": "Java SE",
    "categoria": "Tecnología",
    "_links": {
        "self": {
            "href": "http://localhost:8080/cursos/3"
        },
        "cursos": {
            "href": "http://localhost:8080/cursos"
        }
    }
}
```

- Actualizar datos de curso

Campos actualizables
-   - nombre: string
    - categoria: string

Request
```json
{
    "id": 3,
    "categoria": "java"
}
```

General Headers
```
URL: http://localhost:8080/cursos
Method: PUT
Status Code: 201 Created
```

Response
```json
{
    "id": 3,
    "nombre": "Java SE",
    "categoria": "java",
    "_links": {
        "self": {
            "href": "http://localhost:8080/cursos/3"
        },
        "cursos": {
            "href": "http://localhost:8080/cursos"
        }
    }
}
```

- Eliminar curso con id 3
```
URL: http://localhost:8080/cursos/3
Method: DELETE
Status Code: 204 No Content
```

### Topicos
- Obtener todos los topicos

General Headers
```
URL: http://localhost:8080/topicos
Method: GET
Status Code: 200 OK
```

Response
```json
{
    "_embedded": {
        "topicos": [
            {
                "id": 1,
                "titulo": "Topico 1",
                "mensaje": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas volutpat finibus purus nec tempor. Cras vulputate pellentesque ligula \n    venenatis volutpat. Maecenas cursus tempus mi, non interdum lectus porta nec. Ut cursus ligula eu rhoncus euismod. Integer sit amet maximus \n    dui, ut cursus mauris. Sed sed ligula ut lacus interdum efficitur. Sed at metus varius nisl pellentesque blandit sit amet non tortor. \n    In sed diam et dolor euismod semper. Cras luctus at leo sit amet rhoncus.",
                "fechaCreacion": "2023-05-02T00:00:00",
                "estado": "NO_SOLUCIONADO",
                "autor": {
                    "id": 1,
                    "nombre": "Saul Malagon",
                    "email": "saul@mail.com"
                },
                "curso": {
                    "id": 1,
                    "nombre": "Springboot rest api",
                    "categoria": "java"
                },
                "respuestas": [
                    {
                        "id": 1,
                        "mensaje": "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                        "fechaCreacion": "2023-05-03T00:00:00",
                        "solucion": false,
                        "autor": {
                            "id": 2,
                            "nombre": "Daniel Juarez",
                            "email": "daniel@mail.com"
                        }
                    }
                ],
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/topicos/1"
                    },
                    "autor": {
                        "href": "http://localhost:8080/usuarios/1"
                    },
                    "curso": {
                        "href": "http://localhost:8080/cursos/1"
                    },
                    "topicos": {
                        "href": "http://localhost:8080/topicos"
                    }
                }
            },
            {
                "id": 2,
                "titulo": "Topico 2",
                "mensaje": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas volutpat finibus purus nec tempor. Cras vulputate pellentesque ligula \n    venenatis volutpat. Maecenas cursus tempus mi, non interdum lectus porta nec. Ut cursus ligula eu rhoncus euismod. Integer sit amet maximus \n    dui, ut cursus mauris. Sed sed ligula ut lacus interdum efficitur. Sed at metus varius nisl pellentesque blandit sit amet non tortor. \n    In sed diam et dolor euismod semper. Cras luctus at leo sit amet rhoncus.",
                "fechaCreacion": "2023-05-03T00:00:00",
                "estado": "NO_SOLUCIONADO",
                "autor": {
                    "id": 2,
                    "nombre": "Daniel Juarez",
                    "email": "daniel@mail.com"
                },
                "curso": {
                    "id": 2,
                    "nombre": "Introducción a machine learning",
                    "categoria": "python"
                },
                "respuestas": [
                    {
                        "id": 2,
                        "mensaje": "Maecenas cursus tempus mi, non interdum lectus porta nec. Ut cursus ligula eu rhoncus euismod. Integer sit amet maximus \n    dui, ut cursus mauris.",
                        "fechaCreacion": "2023-05-03T00:00:00",
                        "solucion": true,
                        "autor": {
                            "id": 1,
                            "nombre": "Saul Malagon",
                            "email": "saul@mail.com"
                        }
                    }
                ],
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/topicos/2"
                    },
                    "autor": {
                        "href": "http://localhost:8080/usuarios/2"
                    },
                    "curso": {
                        "href": "http://localhost:8080/cursos/2"
                    },
                    "topicos": {
                        "href": "http://localhost:8080/topicos"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/topicos"
        }
    }
}
```

- Obtener usuario con id 1

General headers
```
URL: http://localhost:8080/topicos/1
Method: GET
Status Code: 200 OK
```

Response
```json
{
    "id": 1,
    "titulo": "Topico 1",
    "mensaje": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas volutpat finibus purus nec tempor. Cras vulputate pellentesque ligula \n    venenatis volutpat. Maecenas cursus tempus mi, non interdum lectus porta nec. Ut cursus ligula eu rhoncus euismod. Integer sit amet maximus \n    dui, ut cursus mauris. Sed sed ligula ut lacus interdum efficitur. Sed at metus varius nisl pellentesque blandit sit amet non tortor. \n    In sed diam et dolor euismod semper. Cras luctus at leo sit amet rhoncus.",
    "fechaCreacion": "2023-05-02T00:00:00",
    "estado": "NO_SOLUCIONADO",
    "autor": {
        "id": 1,
        "nombre": "Saul Malagon",
        "email": "saul@mail.com"
    },
    "curso": {
        "id": 1,
        "nombre": "Springboot rest api",
        "categoria": "java"
    },
    "respuestas": [
        {
            "id": 1,
            "mensaje": "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            "fechaCreacion": "2023-05-03T00:00:00",
            "solucion": false,
            "autor": {
                "id": 2,
                "nombre": "Daniel Juarez",
                "email": "daniel@mail.com"
            }
        }
    ],
    "_links": {
        "self": {
            "href": "http://localhost:8080/topicos/1"
        },
        "autor": {
            "href": "http://localhost:8080/usuarios/1"
        },
        "curso": {
            "href": "http://localhost:8080/cursos/1"
        },
        "topicos": {
            "href": "http://localhost:8080/topicos"
        }
    }
}
```

- Crear nuevo topico

Request
```json
{
    "titulo": "Topico 3",
    "mensaje": "Donec tincidunt metus ut risus faucibus, luctus tempor neque luctus. Etiam quis luctus metus. Pellentesque non lacus nec diam consequat faucibus. In bibendum nisi lobortis dictum luctus. Suspendisse consectetur ut leo et congue. Interdum et malesuada fames ac ante ipsum primis in faucibus. Praesent cursus feugiat bibendum. Nullam sed sollicitudin augue. Nullam cursus eu ante ac posuere.",
    "autor_id": 1,
    "curso_id": 1
}
```

General Headers
```
URL: http://localhost:8080/topicos
Method: POST
Status Code: 201 Created
```

Response
```json
{
    "id": 3,
    "titulo": "Topico 3",
    "mensaje": "Donec tincidunt metus ut risus faucibus, luctus tempor neque luctus. Etiam quis luctus metus. Pellentesque non lacus nec diam consequat faucibus. In bibendum nisi lobortis dictum luctus. Suspendisse consectetur ut leo et congue. Interdum et malesuada fames ac ante ipsum primis in faucibus. Praesent cursus feugiat bibendum. Nullam sed sollicitudin augue. Nullam cursus eu ante ac posuere.",
    "fechaCreacion": "2023-05-06T13:39:26.8561181",
    "estado": "NO_RESPONDIDO",
    "autor": {
        "id": 1,
        "nombre": "Saul Malagon",
        "email": "saul@mail.com"
    },
    "curso": {
        "id": 1,
        "nombre": "Springboot rest api",
        "categoria": "java"
    },
    "respuestas": [],
    "_links": {
        "self": {
            "href": "http://localhost:8080/topicos/3"
        },
        "autor": {
            "href": "http://localhost:8080/usuarios/1"
        },
        "curso": {
            "href": "http://localhost:8080/cursos/1"
        },
        "topicos": {
            "href": "http://localhost:8080/topicos"
        }
    }
}
```

- Actualizar datos de topico

Campos actualizables
-   - titulo: string
    - mensaje: string
    - estado: string(NO_SOLUCIONADO, SOLUCIONADO, CERRADO)

Request
```json
{
    "id": 3,
    "mensaje": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
}
```

General headers
```
URL: http://localhost:8080/topicos
Method: PUT
Status Code: 201 Created
```

Response
```json
{
    "id": 3,
    "titulo": "Topico 3",
    "mensaje": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. ",
    "fechaCreacion": "2023-05-06T00:00:00",
    "estado": "NO_RESPONDIDO",
    "autor": {
        "id": 1,
        "nombre": "Saul Malagon",
        "email": "saul@mail.com"
    },
    "curso": {
        "id": 1,
        "nombre": "Springboot rest api",
        "categoria": "java"
    },
    "respuestas": [],
    "_links": {
        "self": {
            "href": "http://localhost:8080/topicos/3"
        },
        "autor": {
            "href": "http://localhost:8080/usuarios/1"
        },
        "curso": {
            "href": "http://localhost:8080/cursos/1"
        },
        "topicos": {
            "href": "http://localhost:8080/topicos"
        }
    }
}
```

- Eliminar topico don id 3
```
URL: http://localhost:8080/topicos/3
Method: DELETE
Status Code: 204 No Content
```

### Respuestas
- Obtener todas las respuestas

General headers
```
URL: http://localhost:8080/respuestas
Method: GET
Status Code: 200 OK
```

Response
```json
{
    "_embedded": {
        "respuestas": [
            {
                "id": 1,
                "mensaje": "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                "fechaCreacion": "2023-05-03T00:00:00",
                "solucion": false,
                "autor": {
                    "id": 2,
                    "nombre": "Daniel Juarez",
                    "email": "daniel@mail.com"
                },
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/respuestas/1"
                    },
                    "autor": {
                        "href": "http://localhost:8080/usuarios/2"
                    },
                    "topico": {
                        "href": "http://localhost:8080/topicos/1"
                    },
                    "respuestas": {
                        "href": "http://localhost:8080/respuestas"
                    }
                }
            },
            {
                "id": 2,
                "mensaje": "Maecenas cursus tempus mi, non interdum lectus porta nec. Ut cursus ligula eu rhoncus euismod. Integer sit amet maximus \n    dui, ut cursus mauris.",
                "fechaCreacion": "2023-05-03T00:00:00",
                "solucion": true,
                "autor": {
                    "id": 1,
                    "nombre": "Saul Malagon",
                    "email": "saul@mail.com"
                },
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/respuestas/2"
                    },
                    "autor": {
                        "href": "http://localhost:8080/usuarios/1"
                    },
                    "topico": {
                        "href": "http://localhost:8080/topicos/2"
                    },
                    "respuestas": {
                        "href": "http://localhost:8080/respuestas"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/respuestas"
        }
    }
}
```

- Obtener respuesta con id 1

General headers
```
URL: http://localhost:8080/respuestas/1
Method: GET
Status Code: 200 OK
```

Response
```json
{
    "id": 1,
    "mensaje": "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
    "fechaCreacion": "2023-05-03T00:00:00",
    "solucion": false,
    "autor": {
        "id": 2,
        "nombre": "Daniel Juarez",
        "email": "daniel@mail.com"
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/respuestas/1"
        },
        "autor": {
            "href": "http://localhost:8080/usuarios/2"
        },
        "topico": {
            "href": "http://localhost:8080/topicos/1"
        },
        "respuestas": {
            "href": "http://localhost:8080/respuestas"
        }
    }
}
```
- Crear nueva respuesta

Request
```json
{
    "mensaje": "jajajajajaja",
    "autor_id": 1,
    "topico_id": 1
}
```

General Headers
```
URL: http://localhost:8080/respuestas
Method: POST
Status Code: 201 Created
```

Response
```json
{
    "id": 3,
    "mensaje": "jajajajajaja",
    "fechaCreacion": "2023-05-06T13:49:29.9938836",
    "solucion": false,
    "autor": {
        "id": 1,
        "nombre": "Saul Malagon",
        "email": "saul@mail.com"
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/respuestas/3"
        },
        "autor": {
            "href": "http://localhost:8080/usuarios/1"
        },
        "topico": {
            "href": "http://localhost:8080/topicos/1"
        },
        "respuestas": {
            "href": "http://localhost:8080/respuestas"
        }
    }
}
```
- Actualizar respuesta con id 3

Campos actualizables
-   - mensaje: string
    - solucion: boolean

Request
```json
{
    "id": 3,
    "solucion": true
}
```

General headers
```
URL: http://localhost:8080/respuestas
Method: PUT
Status Code: 201 Created
```

Response
```json
{
    "id": 3,
    "mensaje": "jajajajajaja",
    "fechaCreacion": "2023-05-06T00:00:00",
    "solucion": true,
    "autor": {
        "id": 1,
        "nombre": "Saul Malagon",
        "email": "saul@mail.com"
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/respuestas/3"
        },
        "autor": {
            "href": "http://localhost:8080/usuarios/1"
        },
        "topico": {
            "href": "http://localhost:8080/topicos/1"
        },
        "respuestas": {
            "href": "http://localhost:8080/respuestas"
        }
    }
}
```
- Eliminar respuesta con id 3
```
URL: http://localhost:8080/respuestas/3
Method: DELETE
Status Code: 204 No Content
```

