# Pruebas de integración
Para la realización de los test de integración se utiliza [Postman](https://postman.com) construyendo una colección *([prices-search.postman_collection](postman/prices-search.postman_collection.json))* a partir de la definición del API/REST realizada *([openapi.yaml](../pricelist-app/src/main/resources/openapi.yaml))*

En esta colección se han incluido tests que verifican los datos de respuesta a la petición API/REST. En particular se verifica que la respuesta tiene un status 200 y que los datos recibidos contiene el inventario esperado.

Con el objetivo de realizar todos los casos de prueba previstos en la propuesta se utilizará un fichero de datos *([postman-tests-data](postman/postman-tests-data.json))* que, además de incluir los parámetros de la petición, tienen el valor de tarifa esperado.

Con el fin de permitir las pruebas en local (sin necesidad de contar con una infraestructura de servidores) y que la misma sea lo más fiel posible, se utiliza *([docker-compose](docker-compose.yaml))*. La infraestructura para las pruebas de integración consiste en el servidor del API y un servidor de BBDD.   
Para contar con la estructura de datos inicial, se crea y alimenta una tabla *([pricelist-db.sql](sql/pricelist-db.sql))* en la base de datos del servidor docker al arrancar
> ***Nota**: Se ha seleccionado H2 como servidor de base de datos por ser este el indicado en la propuesta, aunque podría haber sido cualquier otro: MySQL, Postgresql, ...*

Para el desarrollo de las pruebas de integración, con antierioridad a la implementación de la solución, se incluye en el *([docker-compose](docker-compose.yaml))* un servidor de API "mock" ([wiremock](https://wiremock.org/)) que mediante una serie de ficheros de configuración *([test1.json](mock-server/mappings/test1.json), [test2.json](mock-server/mappings/test2.json), [test3.json](mock-server/mappings/test3.json), [test4.json](mock-server/mappings/test4.json) y [test5.json](mock-server/mappings/test5.json))* permitirá simular las respuestas esperadas, permitiendo la ejecución de las pruebas antes de desarrollar el servicio.
> ***Nota**: En un caso más complejo, en el cual el desarrollo del servicio pudiera ser largo, este tipo de servidores pemiten realizar el desarrollo en paralelo de clientes del servicio, que podrían utilizar el servidor mock en sus pruebas*

Por último, para automatizar las pruebas de integración, permitiendo su inclusión en sistemas de integración continua, se utiliza la herramienta [newman](https://www.npmjs.com/package/newman)

La ejecución de las pruebas, sin necesidad de instalar otras herramientas, se realiza ejecutando la siguiente instrucción desde el directorio de pruebas de integración:
>```console
>docker run -v $PWD/postman:/etc/newman -t postman/newman run prices-search.postman_collection.json -e docker.postman_environment.json -d postman-tests-data.json
>```

> ***Nota**: Para permitir la ejecución de las pruebas tanto contra el servidor definitivo, como usando el servidor "mock", se han definido dos ficheros de entorno postman ([docker](postman/docker.postman_environment.json) y [docker-mock](postman/docker-mock.postman_environment.json)) que son utilizados como parámetro en la llamada a newman*



https://user-images.githubusercontent.com/47243080/163686302-73d98f34-1c06-4977-bfb6-09dc1a8df925.mp4


