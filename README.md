# ClockWork

Aplicación de gestión de fichajes de trabajadores.

## Tecnologías

### Frontend

* HTML
* CSS
* JavaScript

### Backend

* Java
* Spring Boot
* Spring Data JPA
* Hibernate

### Base de datos

* MariaDB

## Estructura del proyecto

```text
ClockWorkJS/
├── frontend/
└── backend/
    ├── pom.xml
    └── src/
        └── main/
            ├── java/
            │   └── com/
            │       └── clockwork/
            │           ├── config/
            │           ├── controller/
            │           ├── model/
            │           └── repository/
            └── resources/
                └── application.properties
```

## Backend

El backend está desarrollado con Spring Boot y sigue una arquitectura por capas:

* `model/` → entidades que representan los datos de la aplicación.
* `repository/` → acceso a la base de datos mediante Spring Data JPA.
* `controller/` → endpoints de la API REST.
* `config/` → configuración e inicialización de datos.

## Iniciar el proyecto

Utilizamos Maven Wrapper, por lo que no es necesario instalar Maven manualmente. Es necesario tener Java instalado y configurado.

Para levantar el proyecto usamos los scripts que vienen en la plantilla desde la carpeta backend/, ejecutar:

### Windows

```text
.\mvnw.cmd spring-boot:run
```
### Linux
```text
.\mvnw.cmd spring-boot:run
```

Y iniciará en: http://localhost:3000

### Entidades

Actualmente existen las siguientes entidades:

#### Worker

Representa a un trabajador.

Sus datos son:

* `id`
* `employeeCode`
* `firstName`
* `lastNames`
* `email`
* `createdAt`
* `updatedAt`

Los campos `createdAt` y `updatedAt` se gestionan automáticamente mediante `@PrePersist` y `@PreUpdate`.

#### TimeLog

Representa un fichaje de un trabajador.

Sus datos son:

* `id`
* `worker`
* `type`
* `datetime`
* `createdAt`
* `updatedAt`

Los tipos de fichaje disponibles son:

* `Undefined`
* `ClockIn`
* `ClockOut`

Cada `TimeLog` pertenece a un `Worker`.

## Inicialización de datos

La clase `DataInitializer` se ejecuta al iniciar la aplicación.

Comprueba si la tabla `workers` está vacía. Si no existen trabajadores, crea los datos iniciales de prueba.

## JPA y Hibernate

**JPA (Jakarta Persistence API)** es una especificación de Java que define cómo trabajar con bases de datos relacionales utilizando objetos Java.

En este proyecto utilizamos **Hibernate** como implementación de JPA. Hibernate interpreta las anotaciones de JPA y se encarga de realizar las operaciones necesarias sobre MariaDB.

La comunicación se puede resumir como:

```text
Java
  ↓
JPA
  ↓
Hibernate
  ↓
MariaDB
```

Esto permite trabajar principalmente con objetos Java en lugar de escribir manualmente todas las consultas SQL.

## Anotaciones

En Java, las anotaciones (`@...`) permiten añadir información adicional al código. Frameworks como Spring y JPA las utilizan para saber cómo deben tratar determinadas clases, métodos o atributos.

### Anotaciones de JPA que usamos

* `@Entity` → indica que una clase representa una entidad gestionada por JPA.
* `@Table` → indica el nombre de la tabla asociada a la entidad.
* `@Id` → indica la clave primaria.
* `@GeneratedValue` → indica que la clave primaria se genera automáticamente.
* `@Column` → permite configurar la correspondencia entre un atributo y una columna.
* `@ManyToOne` → define una relación de muchos a uno.
* `@JoinColumn` → indica la columna utilizada para establecer una relación.
* `@Enumerated(EnumType.STRING)` → almacena un `enum` como texto.
* `@PrePersist` → ejecuta un método antes de insertar una entidad.
* `@PreUpdate` → ejecuta un método antes de actualizar una entidad.

### Anotaciones de Spring

* `@Component` → registra una clase como componente gestionado por Spring.
* `@RestController` → indica que una clase actúa como controlador REST.
* `@RequestMapping` → define una ruta base.
* `@GetMapping` → indica que un método responde a peticiones GET.
* `@PathVariable` → obtiene un valor de la URL.

### Cors
Dado que el front y el back se ejecutan en orígenes diferentes nos daba problemas encontramos una solución con la anotació:

* `@CrossOrigin`permite que el navegador acepte peticiones realizadas desde un origen diferente al del backend.


## Base de datos

Utilizamos MariaDB.

Hibernate se encarga de crear y actualizar las tablas a partir de las entidades JPA.

### `create`

Durante la creación inicial de las entidades utilizamos:

```properties
spring.jpa.hibernate.ddl-auto=create

Actualmente se utiliza:

```properties
spring.jpa.hibernate.ddl-auto=update
```
### `update`

Esto permite actualizar la estructura de las tablas sin recrearlas en cada inicio.


```properties
spring.jpa.hibernate.ddl-auto=none
```
### `none`
Desactiva la modificación automática de la base de datos.


## API

La API utilizaba snake_case para los nombres de las propiedades JSON. Esta conversión se configura mediante Jackson en application.properties:

```properties
spring.jackson.property-naming-strategy=SNAKE_CASE
```
Esto tranformaba nuestros atributos de camelCase a snake_case siendo lo que espera nuestro frontend.

Finalmente decidimos volver al camelCase.

### Obtener un trabajador por código de empleado
```text
GET /api/workers/{employeeCode}
```

Devuelve los datos del trabajador si existe mediante su codigo de empleado. 

La respuesta incluye, los datos del trabajor, la siguiente acción a realizar y el último fichaje realizado si existe.
Ejemplo:

{
    "worker": {
        "id": 2,
        "employeeCode": "BBB424242",
        "firstName": "Tamara",
        "lastNames": "Fernandez Viturro",
        "email": "tfernandezviturro@cifpfbmoll.eu"
    },
    "nextAction": "ClockOut",
    "lastLog": {
        "id": 5,
        "type": "ClockIn",
        "datetime": "2026-10-04T09:00:00"
    }
}

### Obtener todos los trabajadores

```text
GET /api/workers
```

Devuelve todos los trabajadores.
Ejemplo:

[
  {
    "employeeCode": "AAA676769",
    "firstName": "Juan Carlos",
    "lastNames": "Barba Fernández",
    "email": "jbarbafernandez@cifpfbmoll.eu",
    "createdAt": "2026-10-04T16:28:53.409147",
    "id": 1,
    "updatedAt": "2026-10-04T16:28:53.409147"
  },
  {
    "employeeCode": "BBB424242",
    "firstName": "Tamara",
    "lastNames": "Fernandez Viturro",
    "email": "tfernandezviturro@cifpfbmoll.eu",
    "createdAt": "2026-10-04T16:28:53.468098",
    "id": 2,
    "updatedAt": "2026-10-04T16:28:53.468098"
  },
  {
    "employeeCode": "CCC111112",
    "firstName": "Xavier",
    "lastNames": "Sastre Flexas",
    "email": "xsastref@cifpfbmoll.eu",
    "createdAt": "2026-10-04T16:28:53.47305",
    "id": 3,
    "updatedAt": "2026-10-04T16:28:53.47305"
  }
]

### Time Logs
Obtener todos los fichajes

```text
GET /api/time-logs
```
Devuelve todos los fichajes registrados, ordenados por fecha de forma descendente.

Cada fichaje contiene el trabajador, el tipo de fichaje y la fecha y hora.

### Registrar un fichaje

```text
POST /api/time-logs
```
Registra un nuevo fichaje para un trabajador.

El frontend envía:

{
    "workerId": 2,
    "type": "ClockIn"
}

El backend obtiene la fecha y hora actual, crea el TimeLog y lo guarda en MariaDB.


## Fuentes y documentación

* **Spring – Building REST services**
  Tutorial oficial de Spring sobre creación de servicios REST, controladores y Spring Data.
  https://spring.io/guides/tutorials/rest/

* **Spring - Accessing Data with JPA**
  Documentación oficial sobre Spring Data JPA y acceso a bases de datos mediante repositorios.
  https://spring.io/guides/gs/accessing-data-jpa/

* **Spring - CORS**
  Documentación oficial sobre configuración de Cross-Origin
  https://docs.spring.io/spring-framework/reference/web/webmvc-cors.html

* **Jakarta Persistence (JPA)**
  Documentación oficial de Jakarta Persistence, especificación utilizada para el mapeo entre objetos Java y bases de datos relacionales.
  https://jakarta.ee/specifications/persistence/4.0/jakarta-persistence-spec-4.0-m4



