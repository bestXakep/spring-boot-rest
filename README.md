## Project name
### Spring Boot project for saving data from HTTP response to data base.

## Description
REST API project. Spring Boot project for saving data from HTTP server response to the database. We have end-points which persist some data in JSON format. We need to obtain data from these end-points via OkHttpClient and than to store its data in the PostgreSQL database. The main demand was used SpringBoot framework for creating an app.
Participation - This is a mini project that loads JSON data from the HTTP REST server to the database. The code is covered by unit tests that check the functionality. I also implemented MVC for the ability to display data on HTML pages and perform CRUD operations, validation input data on HTML pages.

1. The project is executed on SpringBoot.
2. Build Maven.
3. Http client OkHttp.
4. Postgres database.
5. Mockito Tests, Junit.

## Table of Contents
* [Project name](#project-name)
* [Description](#description)
* [Technologies](#technologies)
* [Prerequisites](#prerequisites)
* [Installation and running](#installation-and-running)
* [Authors](#author)

### Technologies
* Maven
* Spring Boot
* PostgreSQL Data Base
* OkHTTP
* JUnit
* Mockito
* HTML

### Prerequisites
To run the project you need installed : 
  * Java 8 (jre/jdk) or higher version  
  * Apache Maven 3.0.1 or higher version
  * Intellij Idea (ultimate)
  * pgadmin, min version 4-4.1
    
### Installation and running
 * Install JDK, JRE, set parameters for environment variables. Install Apache Tomcat, install Apache Maven. 
 * Install Intellij Idea.
 * Clone/fork or download the project [project](https://github.com/bestXakep/spring-boot-rest)  from the GitHub.
 * Add pom.xml file as Maven dependencies.
 * Add Spring Boot configuration and write the path to main class "com.ray.parker.DemoApplication".
 * Install pgadmin and set up your postgreSQL database. Create schema = "json", name = "document" as noted in Document class.
 * Run application. The project will be available on localhost:8080.

## Author
Bodyak Iaroslav (e-mail: [4456602@gmail.com](mailto:4456602@gmail.com))




