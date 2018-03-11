### Solucion prueba call center


El desarrollo de la prueba se realizo mediante la implemetacion de un servicio rest con Spring Boot

El proyecto no requiere una instalacion especifica en un servidor de aplicacion puede ser abierto un IDE como proyecto Maven y ejecutarse directamente desde IDE

Para el desarrollo se utilizaron las siguentes tecnologias

Spring Boot

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

MongoDb y Spring Data

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>
```

Antes de ejecutar el proyecto es necesario tener instalada un base de datos MongoDB (https://docs.mongodb.com/manual/tutorial/install-mongodb-on-ubuntu/) y configurar los datos de la misma en el archivo aplication.properties; por defecto esta configurada un base de datos en localhost y utiliza el repositorio test para almacenar los documentos 

![alt text](https://github.com/rquiroga83/call_center_test/blob/develop/images/004.png)

Se puede utilizar la herramienta compass para visulizar el contenido de los repositorios

![alt text](https://github.com/rquiroga83/call_center_test/blob/develop/images/005.png)

Para el almacenamiento de los datos de prubas es necesarion hacer la compilacion del proyecto para que el caso de pruebas "PeopleTests" almacene los documentos correspondientes a operadores, supervisores y directores.


Cuando el proyecto se ejecuta se expone por el puerto 8080 el recurso 

```
localhost:8080/callservice/newcall
```

Con un navegador o con la herramienta postman se puede realizar la prueba del servicio

![alt text](https://github.com/rquiroga83/call_center_test/blob/develop/images/002.png)


El servicio responde con un json el cual en condiciones normales regresa la informacion de la llamada y el agente asignado

```json
{
    "result": 0,
    "info": {
        "idEmploy": "5aa4acddb9224f7046d166d3",
        "idCall": "45",
        "typeEmploy": "operator",
        "nameEmploy": "Alejandro Chitiva"
    },
    "message": null
}
```







