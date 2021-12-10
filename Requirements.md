# Práctica 2. Bases de datos

## Enunciado

### SpringBoot+MySQL

Se desean realizar los siguientes cambios a la práctica de libros y comentarios:

- Añadir persistencia a la aplicación con Spring Data y MySQL
- Quitar completamente la interfaz web:
    - Eliminar ficheros estáticos y templates y su dependencia del pom.xml
    - Eliminar el controlador web
    - Eliminar la gestión de la sesión
- Los usuarios que crean comentarios pasan a ser una entidad propia (User) con las propiedades nick y email. El nick
  deberá ser único.
- Nuevas operaciones de la API REST:
    - Se debe añadir un endpoint para gestionar usuarios (creación, actualización del email y consulta).
    - Sólo se podrán borrar usuarios si estos no tienen comentarios asociados a libros.
    - Se deberá añadir un endpoint de consulta de todos los comentarios de un usuario concreto. En este caso, cada
      comentario deberá contener el id del Libro que comentan.
- Cambios en las operaciones REST ya existentes:
    - Al consultar un Libro, se devolverán todos sus comentarios. Los comentarios en este caso deberán traer el texto,
      el nick y el email del Usuario.
    - Al crear un Comentario, se deberá proporcionar el nick del usuario. Se usará ese nick para crear la relación en la
      base de datos.
- Operaciones que no cambian:
    - Crear un libro.
    - Obtener un listado con el identificador y el título de cada uno de los libros (pero no el resto de la información)
      .
    - Obtener toda la información de un libro determinado y además sus comentarios
    - Borrar un libro
    - Crear un comentario asociado a un libro
    - Obtener la información de un comentario concreto
    - Borrar un comentario
- Documentación
    - Se deberá actualizar la colección Postman y la documentación OpenAPI con los nuevos endpoints REST.

## Formato de entrega

La práctica se entregará teniendo en cuenta los siguientes aspectos:

- El pom.xml estára en la raíz de la carpeta.
- La práctica se entregará como un fichero .zip del proyecto Maven. El nombre del fichero .zip será el correo URJC del
  alumno (sin @alumnos.urjc.es).
- El proyecto se puede crear con cualquier editor o IDE.
- La práctica se entregará por el aula virtual con la fecha indicada.

Las prácticas se podrán realizar de forma individual o por parejas. En caso de que la práctica se haga por parejas:

- Sólo será entregada por uno de los alumnos
- El nombre del fichero .zip contendrá el correo de ambos alumnos separado por guión. Por ejemplo
  p.perezf2019-z.gonzalez2019.zip
