# Arda's Legends Backend

This codebase contains all the game logic and handles the game state of the Arda's Legends Lord of the Rings server roleplaying game.

## The Game

Set in the Lord of the Rings universe, Arda's Legends is a Minecraft server using the LOTR mod, and uses a custom-made roleplaying system involving 4X elements. 

Players can choose a faction and raise it to glory, make it expand, engage in diplomacy with other factions, wage legendary battles and make it become a legend in the world of Arda.

## How to set up the project

*Work In Progress*

- Set up Java project:
    - Install Git and Java
    - Clone the repository
    - Run Maven clean install
- Set up database:
    - Install PostgreSQL
    - Launch the SQL scripts in src/main/resources/database-initialization
- Launch application by running src/main/java/com/ardaslegends/Application.java

## Technical Background

The project is split into two repositories, this one and https://github.com/Ardas-Legends-Development-Team/AL-frontend

### Backend

A classical Java Spring Boot project using a PostgreSQL database.

### Frontend

A Vue.js app augmented by Typescript and TailwindCSS.


## High-Level Code Structure

The root folder contains multiple folders listed below, as well as the project Maven pom.xml, and docker files to be able to deploy the application anywhere.

### docs

- **diagrams**: contains UML or other graphs, usually explaining program flows or structure

- **adoc**: contains the documentation for the Discord Bot

### src/main/resources

- **database-initialization**: contains SQL scripts to populate a development database for testing purposes

- **application.properties**: contains some Spring App launch configuration

### src/test

Contains all test code for the backend and is organized in folders depending on which part of the application we are testing.

*Example: service folder will test all classes in the src/main/java/service package* 


### src/main/java

Contains all the Java source code and is organized as below:


