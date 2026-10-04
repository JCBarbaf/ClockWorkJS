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

La API utiliza snake_case para los nombres de las propiedades JSON. Esta conversión se configura mediante Jackson en application.properties:

```properties
spring.jackson.property-naming-strategy=SNAKE_CASE
```
Esto tranforma nuestros atributos de camelCase a snake_case siendo lo que espera nuestro frontend.

### Obtener un trabajador por código de empleado
```text
GET /api/workers/{employee_code}
```

Devuelve los datos del trabajador si existe.

### Obtener todos los trabajadores

```text
GET /api/workers

Devuelve todos los trabajadores.

