JDK 11. 
* Application will not run with newer JDK (No LTS).

To run application please start docker container by command:

***
docker run --name swapi -it -p 8080:8080 softwareplant/swapi:latest
***

and then start the application by IDE or by command: java -jar <path to file>
  *Please make sure that default java version is selected to 11 on OS.


After running application Swagger UI is available under the address:
***
http://localhost:8081/swagger-ui.html
***
