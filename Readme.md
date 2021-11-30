# Práctica 1. Web y API  REST con Spring

## Description

Aplicación web que incluye con un listado de libros y comentarios de cada libro, la aplicación también incluye una API REST.
La aplicación consta de dos Controllers, uno para la API Rest y otro que maneja la aplicación web sirviendo las templates de Mustache, un Servicio que maneja la lógica de negocio y el acceso a los datos, y luegos las clases de Modelo.
No hay base de datos, solo almacenamiento en memoria, donde se almancenan libros y sus comentarios en la misma estructura.

    private ConcurrentMap<Long, Book> books = new ConcurrentHashMap<>();

En un primer diseño el almacenamiento consistia en dos Maps:

    private ConcurrentMap<Long, Book> books = new ConcurrentHashMap<>();
    private ConcurrentMap<Pair(Long,Long), Comment> comment = new ConcurrentHashMap<>();

Había dos Servicios y en ellos se accedía a las estructuras de datos, Comment se accedía usando el BookId y el CommentId, al final se optó por la simplicidad de mantener los Comments en la misma estructura de los Books.

## Technologies used

- Java 17
- Spring Boot 2.6.0
- OpenAPI 3.0.0
- Mustache
- JUnit 5
- SpringDoc OpenAPI
- [OpenAPI Generator for HTML](https://openapi-generator.tech/docs/generators/html2/)
- [Java Faker](https://github.com/DiUS/java-faker)
- [Error Handling Spring Boot](https://github.com/wimdeblauwe/blog-example-code/tree/master/error-handling-lib-example)

## Requirements

- Java 17
- Maven 3.5 or higher

## Documentation

- [Requirements](./Requirements.md)
- [OpenAPI spec](./api-docs/api-docs.yaml)
- [OpenAPI html](./api-docs/api-docs.html)

## Build

    mvn clean verify

Genera el executable jar dentro de /target, la OpenAPI spec y el OpenAPI html dentro de la carpeta /api-docs

## Setup 

Spring boot application:

    > mvn spring:boot run

or

    > java -jar ./target/holamundo-spring-0.0.1-SNAPSHOT.jar


Hay multiples maneras de ejecutar una aplicación de Spring Boot, la documentación explica varias de ellas:

https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-running-your-application.html 

## How to test

Durante el arranque de la aplicación se genera test-data, 5 libros (con Ids 1-5) con 2 comentarios cada uno (con Ids 1-2), con datos aleatorios. 
Se pueden probar los endpoints usando la [Postman](https://www.postman.com/) collection incluida:

- [Postman Collection](./MasterCloudApps-Books.postman_collection.json)
