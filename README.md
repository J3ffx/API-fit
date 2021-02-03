# API-fit

API-fit is a REST API for running challenges for geeks.

## Installation

The project repository is on GitHub on master : [APIfit](#https://github.com/J3ffx/API-fit/tree/master/APIfit).

### Requirements

To run the project you need :

- A development environment : Eclipse (JEE version).
- Payara as development environment, version : 5.2020.7
- A specific Oracle JDK, version : 11.
- MySQL Server.

> For a better use, we advise you to use POSTMAN and to download it on your Desktop.

### Documentation

Documentation of the project can be found on APIfit/doc file as swagger.json and index.html.

## Description

This application is used to help Geeks stay healthy and fit with running challenges.

A challenge is a ride that Players have to complete by running in an online world.
The running challenge is made of few steps :

- CheckPoints :

Every challenge has many CheckPoints (Ppassage) which is a step in the challenge.

- Segments :

A Segment is a point between two CheckPoints. After a CheckPoint, you can choose between two Segments.

- Obstacles :

Finally, in every Segment you can find Obstacles which can be a riddle or a dare.

### Features

API-fit has 3 types of Users :

#### PUBLIC

Those who are not signed in or not registered. They can only see available challenges and sign up/sign in.

#### PLAYERS

They have a profile they can access, modify information, subscribe to challenges and suggest a theme for a challenge to ADMINS, unsubscribe and disconnect.

#### ADMINS

They have a full access. They also can see all users, modify their information, unsubscribe a user, create a challenge, edit a challenge, set the number of players, import a virtual map, and see suggested themes of users.
When an ADMIN creates a challenge, he/she has to create the CheckPoints, the Segments, the Obstacles with their objectives.

Full routes and notes are available [here](#https://docs.google.com/document/d/1VyX63MnN7kr6DBpVu9fcWI3ou-p1KMbhRigDm-1UQCs/edit).

### Dependencies and plugins

APIfit uses a main dependency :

- Eclipse Jersey
```
<dependency>
    <groupId>org.glassfish.jersey</groupId>
    <artifactId>jersey-bom</artifactId>
    <version>${jersey.version}</version>
    <type>pom</type>
    <scope>import</scope>
</dependency>

```
Pluging for the documentation :

- JAXRS Analyser from [sdaschner](#https://github.com/sdaschner/jaxrs-analyzer)'s github :

```
<plugin>
    <groupId>com.sebastian-daschner</groupId>
    <artifactId>jaxrs-analyzer-maven-plugin</artifactId>
    <version>0.17</version>
    <executions>
        <execution>
            <goals>
                <goal>analyze-jaxrs</goal>
            </goals>
            <configuration>
                <backend>swagger</backend>
            </configuration>
        </execution>
    </executions>
</plugin>
```
Also, we recommend you to check our persistence.xml file for JPA.

## Running the application

### Import the project 

After importing the GitHub project, you have to create a Payara Server :

> Right click on project > Run As > Run on Server

*Do not forget to have Payara as Runtime and JPA on your Project Facets !*

### Launch MySQL Server

### How to Use

After running the application, you can put requests on POSTMAN to interact with the database.

Some examples of requests : 

* example 1

* example 2
* example 3

## Generate a new documentation

### Swagger.json

To generate the Swagger.json file, follow those two steps :

1. Right click on project > Run As > Maven Clean.
2. Right click on project > Run As > Maven Install.

> The swagger.json file can be found in APIfit/target/jaxrs-analyser/swagger.json.

### Web version

To generate the index.html with its CSS file of the documentation :

1. Install bootprint-openapi with the steps [here](#https://github.com/bootprint/bootprint-openapi).
2. After generating the swagger.json file, replace it with the swagger.json file of APIfit/doc.
3. Then on a terminal, run the following command :
```
bootprint openapi APIfit\doc\swagger.json APIfit\doc

```
> Your swagger.json will be converted into a HTML file.

## UML

### Model package 

![Model](#https://github.com/J3ffx/API-fit/blob/main/Model.png?raw=true)


## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.
Do not copy without permission. If help or questions are needed you can ask to the contributors : [Büsra DAGLI](#https://github.com/BusraDagli) and [Jérôme FANFAN](#https://github.com/J3ffx).
We would like to thank the contributors of bootprint-openapi and those of jaxrs-analyser to help for our documentation.


This project was made for the course **Application Internet Avancées** teached by Cédric WEMMERT at ENSISA (Université d'Haute Alsace).
