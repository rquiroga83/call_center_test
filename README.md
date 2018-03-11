# Solucion prueba call center


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

Antes de ejecutar el proyecto es necesario tener instalada una base de datos MongoDB (https://docs.mongodb.com/manual/tutorial/install-mongodb-on-ubuntu/) y configurar los datos de la misma en el archivo "aplication.properties"; por defecto estaen el archivo esta configurada una base de datos en localhost y utiliza el repositorio test para almacenar los documentos (no tiene credenciales de acceso). 

![alt text](https://github.com/rquiroga83/call_center_test/blob/develop/images/004.png)

Se puede utilizar la herramienta compass para visulizar el contenido de los repositorios

![alt text](https://github.com/rquiroga83/call_center_test/blob/develop/images/005.png)

Para el almacenamiento de los datos de pruebas es necesarion hacer la compilacion del proyecto para que el test unit "PeopleTests" almacene los documentos correspondientes a operadores, supervisores y directores.


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

Si ocurre que no existen agentes o la central tiene mas de 10 llamadas, la llamada quedara en espera y el servicio enviara una repuesta similar a la siguente

```json
{
    "result": 5,
    "info": null,
    "message": "Central reject call, call await 58"
}
```

# Diseño

La arquitectura de la aplicacion se baso en 3 patrones de diseño Singleton, Command y Estrategia.

El programa tiene 4 componenetes fundamentales:

### La central
La central es la simulacion de una central telefonica esta se encuentra representada en el singleton "com.almundo.call.Central" esta clase contiene un array de la clase "com.almundo.call.Call" y metodos para la creacion y eliminacion de llamadas. La clase "Call" es una extexion de "Thread", aqui se almacena la informacion de la llamada (Como el empleado asignado), cuando se ejecuta inicia un hilo de duracion aleatoria entre 5 y 10 segundos. 

![alt text](https://github.com/rquiroga83/call_center_test/blob/develop/images/006.png)

### Ejecucion de la llamada
Para la ejecuion de la llamada se utiliza un patron de diseño llamado Comando, el cual ejecuta la operacion de llamada encapsulando la peticion en un objeto, para implemtar este patron se utilizan las siguentes calses:

* com.almundo.call.command.NewCallCommand: Objeto que ecapsula la peticion de la llamada
* com.almundo.call.invoker.CommandInvoker: Objeto encargado de la invocacion del comando
* com.almundo.call.receiver.CallExecutor: Receptor de la invocacion

La clase CallExecutor contiene el metodo "dispatchCall" que realiza la logica de asignacion del operador a la llamada y coloca la llamada en la central.

![alt text](https://github.com/rquiroga83/call_center_test/blob/develop/images/009.png)

### Repositorio de empleados
Para el almacenamiento de la lista de empleados que atenderan la llamada se utiliza un repositorio en MongoDb con tres colecciones; "operator", "supervisor" y "director", estas colecciones estan representadas por las clases "com.almundo.people.Operator", "com.almundo.people.Supervisor" y "com.almundo.people.Director" respectivamente, las tres clases heredan de la clase "com.almundo.people.Employee" y contienen los mismos campos, todos informativos, el campo "available" se utiliza para determinar la diponibildad del agente "Y" -> Disponible, "N" -> No disponible

![alt text](https://github.com/rquiroga83/call_center_test/blob/develop/images/010.png)

### Selector de empleados
Para seleccionar el empleado que atendera la llamada se utiliza un patron de diseño llamado Estrategia, el cual tiene 1 estrategia de asignacion para cada tipo de empleado, estas estrategias estan representadas en las clases "com.almundo.call.assignor.EmployeeAllocatorOperatorService", "com.almundo.call.assignor.EmployeeAllocatorSupervisorService" y "com.almundo.call.assignor.EmployeeAllocatorDirectorService", estas estrategias son invocadas en el metodo "dispatchCall" de la clase "CallExecutor" aca se ejecuta secuencialemnte una por una hasta encontrar un empleado disponible.


![alt text](https://github.com/rquiroga83/call_center_test/blob/develop/images/011.png)

## Que pasa cuando no hay empleados disponibles o la central esta llena de llamadas ?

Cuando no hay un agente que atienda la llamada o la central se encuentra llena, la central tiene una cola FIFO la cual almacena la llamada y la deja en espera en el momento que se finalizan las llamdas en curso, asigna automaticamente las llamadas de la cola a la central con un empleado disponible, este proceso se puede revisar en las funciones "freeCall()" de la clase "Call" y la funcion "dispatchCall()" de la clase "CallExecutor"

# Unit Tests

En el desarrollo existen los siguentes Unit Test.

### CallTests

Prueba la clase Call, con un test de la generacion de tiempo aleatorio y un test de la manipulacion de los hilos de llamadas

### CentralTests

Pureba las clase Central, se manupula la ubicacion de llamadas en la centra, testeando los errores de sobrepaso de la capacidad de la central, y una pueba de concurrencia de llamadas.

### NewCallCommandTests
Prueba la ejecucion del comando de creacion de llamada (Clase NewCallCommand y toda la estructura del patron comando y estartegia), se realiza la prueba de creacion de una llamada con el comando, se testea el proceso de llamada en espera cuando no existen agentes que atiendan la llamada y testea el proceso de cola de llamadas cuando la central esta llena.



