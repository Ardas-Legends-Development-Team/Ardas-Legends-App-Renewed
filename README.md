# Arda's Legends Backend

This codebase contains all the game logic and handles the game state of the Arda's Legends Lord of the Rings server
roleplaying game.

## The Game

Set in the Lord of the Rings universe, Arda's Legends is a Minecraft server using the LOTR mod, and uses a custom-made
roleplaying system involving 4X elements.

Players can choose a faction and raise it to glory, make it expand, engage in diplomacy with other factions, wage
legendary battles and make it become a legend in the world of Arda.

## How to set up the project

- Set up Java project:
    - Install Git and Java
    - Clone the repository
    - Install JDK 21
    - Run Maven clean & Maven install
- Set up database:
    - Install PostgreSQL
    - Launch the SQL scripts in src/main/resources/database-initialization in the predefined order. It does those operations in order:
       - Drop and create a new schema to prepare the database
       - Insert static data such as regions, claimbuild types etc.
       - (Optional) Insert development test data (only for development & testing purposes)
- Get application-dev.properties and security-config.yml files from team lead in order to be able to launch the application with the necessary configuration.
- Launch application by running src/main/java/com/ardaslegends/Application.java

## Contributing

**DISCLAIMER:** communication is key to success, to don't hesitate to communicate with the team lead if you have questions or need to coordinate on your tasks.

The branches follow a typical feature-branch architecture, meaning that:
- main branch is reserved for releases
- develop branch is the main development branch and where all features, bugs and refactors are merged into
- for each feature, bugfix etc. a separate branch MUST be created, prefixed with it's type. For example feature/feature-1, bug/bugfix-1. When the development of the specific element is done, then a Pull Request should be created, adding the team lead as the reviewer along with any other members you find suitable. After the feature is validated, the branch is squashed and merged with the develop branch.

Each new branch must be linked to an issue, and the developer is responsible with keeping the linked issue up to date, notably checking tasklists.

In the project board, when a new issue is being taken care of by a developper from the Current Spring, then he assigns himself and updates it's status to In-Progress.

## Technical Background

The project is split into two repositories, this one and https://github.com/Ardas-Legends-Development-Team/AL-frontend

### Backend

A classical Java Spring Boot project using a PostgreSQL database.

### Frontend

A Vue.js app augmented by Typescript and TailwindCSS.

## High-Level Code Structure

The root folder contains multiple folders listed below, as well as the project Maven pom.xml, and docker files to be
able to deploy the application anywhere.

### docs

- **diagrams**: contains UML or other graphs, usually explaining program flows or structure

- **adoc**: contains the documentation for the Discord Bot

### src/main/resources

- **database-initialization**: contains SQL scripts to populate a development database for testing purposes

- **application.properties**: contains some Spring App launch configuration

### src/test

Contains all test code for the backend and is organized in folders depending on which part of the application we are
testing.

*Example: service folder will test all classes in the src/main/java/service package*

### src/main/java

The application is separated into multiple layers to facilitate organization and code scaling:

- Domain layer: all data structure definitions, used by the rest of the application, *for example to define what an Army
  is*.
- Service layer: all processing and calculation logic. The service contains the biggest chunk of processes and defines
  the rules in the code.
- Repository layer: all communication code between the application and the database. Defines what operations we can do
  on the DB tables.
- Presentation layer: the entry point of our application once it is running. All operations called by the frontend start
  here.

A simple flow of a frontend API request:

Presentation (REST controllers) <-> Service <-> Repository <-> Database

During this flow objects from the Domain layer are being used

Contains all the Java source code and is organized as below:

|           Path           | - |                                                                    Usage                                                                     |
|:------------------------:|:-:|:--------------------------------------------------------------------------------------------------------------------------------------------:|
|    **configuration**     | : |                           Holds all configuration classes for Spring, security, convertions, property loading etc.                           |
|        **domain**        | : | Holds all entity data, which represents the data structures of the application. It is used to create the database schema from our Java code. |
|     **presentation**     | : |                                         Contains all endpoints available to interact with the server                                         |
|   **presentation/api**   | : |           Contains all REST API controllers that are used by the website. Those controllers then call upon service layer methods.            |
| **presentation/discord** | : |  Contains all discord command code and calls relevant service layer code *Javacord library is now deprecated, the bot will soon be removed*  |
|      **repository**      | : |                                             Contains all code necessary to access the database.                                              |
|       **service**        | : |                                       Contains all business logic and calculations of the application.                                       |

