# Challenge ONE | Back End | Foro Alura 

<div align="center" >
     <img width="200" heigth="200" src="https://user-images.githubusercontent.com/91544872/209678377-70b50b21-33de-424c-bed8-6a71ef3406ff.png">
</div>

<div align="center">

  [![](https://img.shields.io/badge/linkedin:-🧡%20Oracle-C74634?style=for-the-badge&logo=linkedin)](https://www.linkedin.com/company/oracle/)
  [![](https://img.shields.io/badge/linkedin:-💙%20Alura%20Latam-061E3C?style=for-the-badge&logo=linkedin)](https://www.linkedin.com/company/alura-latam/mycompany/)

  [![](https://img.shields.io/badge/linkedin:-🐔%20Saul%20Malagon%20Martinez-061E3C?style=for-the-badge&logo=linkedin)](https://www.linkedin.com/company/alura-latam/mycompany/)

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

     roles {
        BIGINT id_rol
        VARCHAR rol
     }

     usuarios_roles {
        BIGINT usuario_id
        BIGINT rol_id
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
     usuarios ||--|{ usuarios_roles : has
     roles ||--|{ usuarios_roles : has
     topicos }|--|| cursos : of
     topicos ||--|{ respuestas : has
```

## 🖥️ Tecnologías Utilizadas:
<div align="center">

[![](https://img.shields.io/badge/-Eclipse-F7941E?style=for-the-badge&logo=eclipse)](https://www.eclipse.org/)
[![](https://img.shields.io/badge/-MySQL-ffffff?style=for-the-badge&logo=mysql)](https://www.mysql.com/)
[![](https://img.shields.io/badge/-Java-C74634?style=for-the-badge&logo=java)](https://www.java.com/en/)
[![](https://img.shields.io/badge/-spring%20boot-1B1F23?style=for-the-badge&logo=spring)](https://start.spring.io/)
[![](https://img.shields.io/badge/-jwt%20token-000000?style=for-the-badge&logo=jwt)](https://jwt.io/)
[![](https://img.shields.io/badge/-swagger-ffffff?style=for-the-badge&logo=swagger)](https://jwt.io/)

</div>

## ⚙️ Indicaciones para ejecución desde IDE

1. Ejecutar script sql de la carpeta db en su sistema gestor de base de datos de mysql. Yo usé MySQL Workbench.
2. Importar el proyecto a su espacio de trabajo.
3. Asignar las variables de entorno siguientes en su IDE.
     - (Opcional) MYSQL_HOST: el host de tu base de datos, si usas localhost puedes omitirlo
     - MYSQL_USER: tu usuario de mysql
     - MYSQL_PASSWORD: tu contraseña de mysql
     - SECURITY_SECRET: cualquier string, yo le puse "quesadilla"
4. Una vez en ejecución, puedes utilizar postman para realizar las peticiones, pero primero debes realizar una petición a "/login" con los datos de algún usuario de la base de datos para recibir un token con el que podrás realizar peticiones a cualquier otro endpoint por hasta 2 horas. Pasando las 2 horas, tendrás que loggearte de nuevo.

**NOTA:** El proyecto está documentado con swagger, por lo que se puede visualizar por medio de la ruta http://localhost:8080/swagger-ui.html en caso de ejecutarse de manera local (en un ambiente de desarrollo)

# Foro Alura API DOCS
Practice project - Alura Challenge ONE

## Version: 1.0.0

**Contact information:**  
Saul Malagon Martinez  
smalagonmtz@gmail.com  

### /login

#### POST
##### Summary:

Obtiene un Token JWT

##### Request body

```json
{
  "email": "string",
  "contrasena": "string"
}
```

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

```json
{
  "jwtToken": "string"
}
```

### /usuarios

#### GET
##### Summary:

Obtiene una lista paginada de todos los usuarios

##### Parameters

| Name | Located in | Required | Schema |
| ---- | ---------- | -------- | ---- |
| page | query | No | integer |
| pageSize | query | No | integer |
| sortBy | query | No | string |
| orderBy | query | No | string |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

```json
{
  "_embedded": {
    "usuarios": [
      {
        "id": 0,
        "nombre": "string",
        "email": "string",
        "_links": {
          "self": {
            "href": "string"
          },
          "usuarios": {
            "href": "string"
          }
        }
      }
    ]
  },
  "_links": {
    "self": {
      "href": "string"
    },
    "next": {
      "href": "string"
    },
    "previous": {
      "href": "string"
    }
  },
  "page": {
    "size": 0,
    "totalElements": 0,
    "totalPages": 0,
    "number": 0
  }
}
```

#### PUT
##### Summary:

Modifica la información de un usuario

##### Description:

Solo los usuarios con rol `ADMIN` pueden modificar la información de cualquier usuario, los usuarios sin rol `ADMIN` solo pueden modificar su propia información.

##### Request body

```json
{
  "id": 0,
  "nombre": "string",
  "email": "string",
  "contrasena": "string"
}
```

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

```json
{
  "id": 0,
  "nombre": "string",
  "email": "string",
  "_links": {
    "self": {
      "href": "string"
    },
    "usuarios": {
      "href": "string"
    }
  }
}
```

##### Security

| Security Auth | Format | Header | Sample |
| --- | --- | --- | --- |
| Bearer token | JWT | Authorization | Bearer asdfasd34123fdsasdfqwev123c123ca |

#### POST
##### Summary:

Crea un nuevo usuario

##### Description 

Se debe usar una contraseña encriptada con bcrypt para registrar un nuevo usuario.

##### Request body

```json
{
  "nombre": "string",
  "email": "string",
  "contrasena": "string"
}
```

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

```json
{
  "id": 0,
  "nombre": "string",
  "email": "string",
  "_links": {
    "self": {
      "href": "string"
    },
    "usuarios": {
      "href": "string"
    }
  }
}
```

### /usuarios/{id}

#### GET
##### Summary:

Obtiene un usuario por su id

##### Parameters

| Name | Located in | Required | Schema |
| ---- | ---------- | -------- | ---- |
| id | path | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

```json
{
  "id": 0,
  "nombre": "string",
  "email": "string",
  "_links": {
    "self": {
      "href": "string"
    },
    "usuarios": {
      "href": "string"
    }
  }
}
```

#### DELETE
##### Summary:

Elimina un usuario por su id

##### Description:

Solo los usuarios con rol `ADMIN` puden eliminar cualquier usuario, los usuarios sin rol `ADMIN` solo pueden eliminar su propia cuenta.

##### Parameters

| Name | Located in | Required | Schema |
| ---- | ---------- | -------- | ---- |
| id | path | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

##### Security

| Security Auth | Format | Header | Sample |
| --- | --- | --- | --- |
| Bearer token | JWT | Authorization | Bearer asdfasd34123fdsasdfqwev123c123ca |

### /topicos

#### GET
##### Summary:

Obtiene una lista paginada de todos los topicos

##### Parameters

| Name | Located in | Required | Schema |
| ---- | ---------- | -------- | ---- |
| page | query | No | integer |
| pageSize | query | No | integer |
| sortBy | query | No | string |
| orderBy | query | No | string |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

```json
{
  "_embedded": {
    "topicos": [
      {
        "id": 0,
        "titulo": "string",
        "mensaje": "string",
        "fechaCreacion": "2023-05-14T22:32:46.951Z",
        "estado": "NO_RESPONDIDO",
        "autor": {
          "id": 0,
          "nombre": "string",
          "email": "string",
          "_links": {
            "self": {
              "href": "string"
            },
            "usuarios": {
              "href": "string"
            }
          }
        },
        "curso": {
          "id": 0,
          "nombre": "string",
          "categoria": "string",
          "instructor": {
            "id": 0,
            "nombre": "string",
            "email": "string",
            "_links": {
              "self": {
                "href": "string"
              },
              "usuarios": {
                "href": "string"
              }
            }
          },
          "_links": {
            "self": {
              "href": "string"
            },
            "cursos": {
              "href": "string"
            }
          }
        },
        "respuestas": [
          {
            "id": 0,
            "mensaje": "string",
            "fechaCreacion": "2023-05-14T22:32:46.951Z",
            "solucion": true,
            "autor": {
              "id": 0,
              "nombre": "string",
              "email": "string",
              "_links": {
                "self": {
                  "href": "string"
                },
                "usuarios": {
                  "href": "string"
                }
              }
            },
            "_links": {
              "self": {
                "href": "string"
              },
              "autor": {
                "href": "string"
              },
              "topico": {
                "href": "string"
              },
              "respuestas": {
                "href": "string"
              }
            }
          }
        ],
        "_links": {
          "self": {
            "href": "string"
          },
          "topicos": {
            "href": "string"
          }
        }
      }
    ]
  },
  "_links": {
    "self": {
      "href": "string"
    },
    "next": {
      "href": "string"
    },
    "previous": {
      "href": "string"
    }
  },
  "page": {
    "size": 0,
    "totalElements": 0,
    "totalPages": 0,
    "number": 0
  }
}
```

#### PUT
##### Summary:

Modifica la información de un tópico

##### Description:

Solo el autor del tópico o un usuario con rol `ADMIN` pueden modificar la información del mismo

##### Request body

```json
{
  "id": 0,
  "titulo": "string",
  "mensaje": "string",
  "fechaCreacion": "2023-05-14T22:54:09.339Z",
  "estado": "NO_RESPONDIDO",
  "autor": {
    "id": 0,
    "nombre": "string",
    "email": "string",
    "_links": {
      "self": {
        "href": "string"
      },
      "usuarios": {
        "href": "string"
      }
    }
  },
  "curso": {
    "id": 0,
    "nombre": "string",
    "categoria": "string",
    "instructor": {
      "id": 0,
      "nombre": "string",
      "email": "string",
      "_links": {
        "self": {
          "href": "string"
        },
        "usuarios": {
          "href": "string"
        }
      }
    },
    "_links": {
      "self": {
        "href": "string"
      },
      "cursos": {
        "href": "string"
      }
    }
  },
  "respuestas": [
    {
      "id": 0,
      "mensaje": "string",
      "fechaCreacion": "2023-05-14T22:54:09.339Z",
      "solucion": true,
      "autor": {
        "id": 0,
        "nombre": "string",
        "email": "string",
        "_links": {
          "self": {
            "href": "string"
          },
          "usuarios": {
            "href": "string"
          }
        }
      },
      "_links": {
        "self": {
          "href": "string"
        },
        "autor": {
          "href": "string"
        },
        "topico": {
          "href": "string"
        },
        "respuestas": {
          "href": "string"
        }
      }
    }
  ],
  "_links": {
    "self": {
      "href": "string"
    },
    "topicos": {
      "href": "string"
    }
  }
}
```

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

##### Security

| Security Auth | Format | Header | Sample |
| --- | --- | --- | --- |
| Bearer token | JWT | Authorization | Bearer asdfasd34123fdsasdfqwev123c123ca |S

#### POST
##### Summary:

Crea un tópico

##### Description:

El autor del tópico será usuario loggeado

##### Request body

```json
{
  "titulo": "string",
  "mensaje": "string",
  "autorId": 0,
  "cursoId": 0
}
```

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

```json
{
  "id": 0,
  "titulo": "string",
  "mensaje": "string",
  "fechaCreacion": "2023-05-14T22:59:05.031Z",
  "estado": "NO_RESPONDIDO",
  "autor": {
    "id": 0,
    "nombre": "string",
    "email": "string",
    "_links": {
      "self": {
        "href": "string"
      },
      "usuarios": {
        "href": "string"
      }
    }
  },
  "curso": {
    "id": 0,
    "nombre": "string",
    "categoria": "string",
    "instructor": {
      "id": 0,
      "nombre": "string",
      "email": "string",
      "_links": {
        "self": {
          "href": "string"
        },
        "usuarios": {
          "href": "string"
        }
      }
    },
    "_links": {
      "self": {
        "href": "string"
      },
      "cursos": {
        "href": "string"
      }
    }
  },
  "respuestas": [
    {
      "id": 0,
      "mensaje": "string",
      "fechaCreacion": "2023-05-14T22:59:05.031Z",
      "solucion": true,
      "autor": {
        "id": 0,
        "nombre": "string",
        "email": "string",
        "_links": {
          "self": {
            "href": "string"
          },
          "usuarios": {
            "href": "string"
          }
        }
      },
      "_links": {
        "self": {
          "href": "string"
        },
        "autor": {
          "href": "string"
        },
        "topico": {
          "href": "string"
        },
        "respuestas": {
          "href": "string"
        }
      }
    }
  ],
  "_links": {
    "self": {
      "href": "string"
    },
    "topicos": {
      "href": "string"
    }
  }
}
```

##### Security

| Security Auth | Format | Header | Sample |
| --- | --- | --- | --- |
| Bearer token | JWT | Authorization | Bearer asdfasd34123fdsasdfqwev123c123ca |

### /topicos/{id}

#### GET
##### Summary:

Obtiene un tópico por su id

##### Parameters

| Name | Located in | Required | Schema |
| ---- | ---------- | -------- | ---- |
| id | path | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

```json
{
  "id": 0,
  "titulo": "string",
  "mensaje": "string",
  "fechaCreacion": "2023-05-14T23:04:19.431Z",
  "estado": "NO_RESPONDIDO",
  "autor": {
    "id": 0,
    "nombre": "string",
    "email": "string",
    "_links": {
      "self": {
        "href": "string"
      },
      "usuarios": {
        "href": "string"
      }
    }
  },
  "curso": {
    "id": 0,
    "nombre": "string",
    "categoria": "string",
    "instructor": {
      "id": 0,
      "nombre": "string",
      "email": "string",
      "_links": {
        "self": {
          "href": "string"
        },
        "usuarios": {
          "href": "string"
        }
      }
    },
    "_links": {
      "self": {
        "href": "string"
      },
      "cursos": {
        "href": "string"
      }
    }
  },
  "respuestas": [
    {
      "id": 0,
      "mensaje": "string",
      "fechaCreacion": "2023-05-14T23:04:19.431Z",
      "solucion": true,
      "autor": {
        "id": 0,
        "nombre": "string",
        "email": "string",
        "_links": {
          "self": {
            "href": "string"
          },
          "usuarios": {
            "href": "string"
          }
        }
      },
      "_links": {
        "self": {
          "href": "string"
        },
        "autor": {
          "href": "string"
        },
        "topico": {
          "href": "string"
        },
        "respuestas": {
          "href": "string"
        }
      }
    }
  ],
  "_links": {
    "self": {
      "href": "string"
    },
    "topicos": {
      "href": "string"
    }
  }
}
```

#### DELETE
##### Summary:

Elmina un tópico

##### Description:

Solo el autor del tópico o un usuario con rol `ADMIN` puede eliminar el mismo.

##### Parameters

| Name | Located in | Required | Schema |
| ---- | ---------- | -------- | ---- |
| id | path | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

##### Security

| Security Auth | Format | Header | Sample |
| --- | --- | --- | --- |
| Bearer token | JWT | Authorization | Bearer asdfasd34123fdsasdfqwev123c123ca |

### /respuestas

#### GET
##### Summary:

Obtiene un lista paginada de todas las respuestas a tópicos

##### Parameters

| Name | Located in | Required | Schema |
| ---- | ---------- | -------- | ---- |
| page | query | No | integer |
| pageSize | query | No | integer |
| sortBy | query | No | string |
| orderBy | query | No | string |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

```json
{
  "_embedded": {
    "respuestas": [
      {
        "id": 0,
        "mensaje": "string",
        "fechaCreacion": "2023-05-14T22:14:07.970Z",
        "solucion": true,
        "autor": {
          "id": 0,
          "nombre": "string",
          "email": "string",
          "_links": {
            "self": {
              "href": "string"
            },
            "usuarios": {
              "href": "string"
            }
          }
        },
        "_links": {
          "self": {
            "href": "string"
          },
          "autor": {
            "href": "string"
          },
          "topico": {
            "href": "string"
          },
          "respuestas": {
            "href": "string"
          }
        }
      }
    ]
  },
  "_links": {
    "self": {
      "href": "string"
    }
  },
  "page": {
    "size": 0,
    "totalElements": 0,
    "totalPages": 0,
    "number": 0
  }
}
```

#### PUT
##### Summary:

Modifica la información de una respuesta

##### Description:

Solo el autor o un usuario con rol `ADMIN` pueden modificar la información de una respuesta.

##### Request body

```json
{
  "id": 0,
  "mensaje": "string",
  "solucion": true,
  "autorId": 0
}
```

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

```json
{
  "id": 0,
  "mensaje": "string",
  "fechaCreacion": "2023-05-14T22:18:06.027Z",
  "solucion": true,
  "autor": {
    "id": 0,
    "nombre": "string",
    "email": "string",
    "_links": {
      "self": {
        "href": "string"
      },
      "usuarios": {
        "href": "string"
      }
    }
  },
  "_links": {
    "self": {
      "href": "string"
    },
    "autor": {
      "href": "string"
    },
    "topico": {
      "href": "string"
    },
    "respuestas": {
      "href": "string"
    }
  }
}
```

##### Security

| Security Auth | Format | Header | Sample |
| --- | --- | --- | --- |
| Bearer token | JWT | Authorization | Bearer asdfasd34123fdsasdfqwev123c123ca |

#### POST
##### Summary:

Crea una respuesta a un tópico específico

##### Description:

El autor de la respuesta será el usuario loggeado.

##### Request body

```json
{
  "mensaje": "string",
  "autorId": 0,
  "topicoId": 0
}
```

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

```json
{
  "id": 0,
  "mensaje": "string",
  "fechaCreacion": "2023-05-14T22:27:35.762Z",
  "solucion": true,
  "autor": {
    "id": 0,
    "nombre": "string",
    "email": "string",
    "_links": {
      "self": {
        "href": "string"
      },
      "usuarios": {
        "href": "string"
      }
    }
  },
  "_links": {
    "self": {
      "href": "string"
    },
    "autor": {
      "href": "string"
    },
    "topico": {
      "href": "string"
    },
    "respuestas": {
      "href": "string"
    }
  }
}
```

##### Security

| Security Auth | Format | Header | Sample |
| --- | --- | --- | --- |
| Bearer token | JWT | Authorization | Bearer asdfasd34123fdsasdfqwev123c123ca |

### /respuestas/{id}

#### GET
##### Summary:

Obtiene una respuesta por su id

##### Parameters

| Name | Located in | Required | Schema |
| ---- | ---------- | -------- | ---- |
| id | path | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

```json
{
  "id": 0,
  "mensaje": "string",
  "fechaCreacion": "2023-05-14T22:29:59.541Z",
  "solucion": true,
  "autor": {
    "id": 0,
    "nombre": "string",
    "email": "string",
    "_links": {
      "self": {
        "href": "string"
      },
      "usuarios": {
        "href": "string"
      }
    }
  },
  "_links": {
    "self": {
      "href": "string"
    },
    "autor": {
      "href": "string"
    },
    "topico": {
      "href": "string"
    },
    "respuestas": {
      "href": "string"
    }

  }
}
```

#### DELETE
##### Summary:

Elimina una respuesta

##### Description:

Solo el autor o un usuario con rol `ADMIN` pueden eliminar una respuesta.

##### Parameters

| Name | Located in | Required | Schema |
| ---- | ---------- | -------- | ---- |
| id | path | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

##### Security

| Security Auth | Format | Header | Sample |
| --- | --- | --- | --- |
| Bearer token | JWT | Authorization | Bearer asdfasd34123fdsasdfqwev123c123ca |


### /cursos

#### GET
##### Summary:

Obtiene una lista paginada de todos los cursos

##### Parameters

| Name | Located in | Required | Schema |
| ---- | ---------- | -------- | ---- |
| page | query | No | integer |
| pageSize | query | No | integer |
| sortBy | query | No | string |
| orderBy | query | No | string |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

```json
{
  "_embedded": {
    "cursos": [
      {
        "id": 0,
        "nombre": "string",
        "categoria": "string",
        "instructor": {
          "id": 0,
          "nombre": "string",
          "email": "string",
          "_links": {
            "self": {
              "href": "string"
            },
            "usuarios": {
              "href": "string"
            }
          }
        },
        "_links": {
          "self": {
            "href": "string"
          },
          "cursos": {
            "href": "string"
          }
        }
      }
    ]
  },
  "_links": {
    "self": {
      "href": "string"
    }
  },
  "page": {
    "size": 0,
    "totalElements": 0,
    "totalPages": 0,
    "number": 0
  }
}
```

#### PUT
##### Summary:

Modifica la información de un curso

##### Description:

Solo el instructor del curso o un usuario con rol `ADMIN` pueden modificar la información de un curso.

##### Request Body

```json
{
  "id": 0,
  "nombre": "string",
  "categoria": "string",
  "instructorId": 0
}
```

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

```json
{
  "id": 0,
  "nombre": "string",
  "categoria": "string",
  "instructor": {
    "id": 0,
    "nombre": "string",
    "email": "string",
    "_links": {
      "self": {
        "href": "string"
      },
      "usuarios": {
        "href": "string"
      }
    }
  },
  "_links": {
    "self": {
      "href": "string"
    },
    "cursos": {
      "href": "string"
    }
  }
}
```

##### Security

| Security Auth | Format | Header | Sample |
| --- | --- | --- | --- |
| Bearer token | JWT | Authorization | Bearer asdfasd34123fdsasdfqwev123c123ca |

#### POST
##### Summary:

Crea un curso

##### Description:

Solo los usuarios con rol `ADMIN` y/o `INSTRUCTOR` pueden crear cursos.

##### Request body

```json
{
  "nombre": "string",
  "categoria": "string",
  "instructorId": 0
}
```

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

```json
{
  "id": 0,
  "nombre": "string",
  "categoria": "string",
  "instructor": {
    "id": 0,
    "nombre": "string",
    "email": "string",
    "_links": {
      "self": {
        "href": "string"
      },
      "usuarios": {
        "href": "string"
      }
    }
  },
  "_links": {
    "self": {
      "href": "string"
    },
    "cursos": {
      "href": "string"
    }
  }
}
```

##### Security

| Security Auth | Format | Header | Sample |
| --- | --- | --- | --- |
| Bearer token | JWT | Authorization | Bearer asdfasd34123fdsasdfqwev123c123ca |

### /cursos/{id}

#### GET
##### Summary:

Obtiene un curso por su id

##### Parameters

| Name | Located in | Required | Schema |
| ---- | ---------- | -------- | ---- |
| id | path | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

```json
{
  "id": 0,
  "nombre": "string",
  "categoria": "string",
  "instructor": {
    "id": 0,
    "nombre": "string",
    "email": "string",
    "_links": {
      "self": {
        "href": "string"
      },
      "usuarios": {
        "href": "string"
      }
    }
  },
  "_links": {
    "self": {
      "href": "string"
    },
    "cursos": {
      "href": "string"
    }
  }
}
```

#### DELETE
##### Summary:

Eliminar un curso

##### Description:

Solo el instructor del curso o un usuario con rol `ADMIN` pueden eliminar un curso.

##### Parameters

| Name | Located in | Required | Schema |
| ---- | ---------- | -------- | ---- |
| id | path | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

##### Security

| Security Auth | Format | Header | Sample |
| --- | --- | --- | --- |
| Bearer token | JWT | Authorization | Bearer asdfasd34123fdsasdfqwev123c123ca |