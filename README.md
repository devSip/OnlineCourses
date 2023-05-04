# KURSU TINKLALAPIS

Kursu tinklalapis - baigiamasis Code Academy JAVA kurso Alberto, Sabinos, Arvydo ir Vladislavo darbas.

It is an online courses website where users can browse and choose from various topics and learn whenever and wherever they want. This project is built using Java Spring Boot and React.

## TABLE OF CONTENTS

*  [FEATURES](#1-features)
*  [PREREQUISITES](#2-prerequisites)
*  [USAGE](#3-usage)
*  [RUNNING TESTS](#4-running-tests)
*  [DEVELOPMENT](#5-development)
*  [CONTRIBUTING](#6-contributing)
*  [FOLDER STRUCTURE](#7-folder-structure)
*  [TECHNOLOGIES](#8-technologies)
*  [LICENSE](#9-license)
*  [ACKNOWLEDGEMENTS](#10-acknowledgements)
*  [CONTACT](#11-contact)

##  1. <a name='FEATURES'></a>FEATURES

Browse and choose from various topics, including exact science, social science, humanitarian science, and nature science. Read course summaries, check professors and their qualifications. Sign up for courses and access the course material whenever, wherever. 

##  2. <a name='PREREQUISITES'></a>PREREQUISITES

Java

Spring Boot

Maven

React.js

MySQL

### To set up the MySQL database, follow these steps:

Create a new database in your MySQL instance. Run the SQL scripts provided in the backend/src/main/resources folder to create the database schema and tables. Running the Application Clone the repository to your local machine. Navigate to the kursu-tinklalapis directory. Run npm install to install the necessary dependencies. Run npm start to start the React development server. Navigate to the backend directory. Run the Spring Boot application using the following command: mvn spring-boot:run Open your browser and go to http://localhost:3000 to access the application.

##  3. <a name='USAGE'></a>USAGE

Roles 

This application has four types of users based on roles:

UNREGISTERED USER: Can browse through available courses and read about professors, sign up.

STUDENT: Can book courses they are interested in, check their profile and booked registration, update profile information, remove registrations.

PROFESSOR: Can see a full list of students and registrations.

ADMIN: Can access the full list of students, professors, courses, and registrations. Can add new professors, courses, edit information, remove or edit registrations.

Registration

Unregistered User: Browse the available courses and read about professors. Sign up for courses to become a student. 

Student: Log in to access the profile and registration page. Book the courses they are interested in, check their profile and booked registrations, update profile information, and remove registrations. 

Professor: Log in to access the full list of students and registrations. 

Admin: Log in to access the full list of students, professors, courses, and registrations. Can add new professors, courses, edit information, remove or edit registrations.

##  4. <a name='RUNNINGTESTS'></a>RUNNING TESTS

To run the tests, navigate to the backend directory and run the following command: mvn test

##  5. <a name='DEVELOPMENT'></a>DEVELOPMENT

Setup

To get started with development, follow these steps:

Clone the repository to your local machine. Install the necessary dependencies using npm install for the frontend and mvn spring-boot:run for the backend. Create a .env file in the root directory with the necessary environment variables (e.g. database credentials). Run npm start to start the React development server and mvn spring-boot:run to start the Spring Boot application. Open your browser and go to http://localhost:3000 to access the application.

##  6. <a name='CONTRIBUTING'></a>CONTRIBUTING

We welcome contributions from everyone. To contribute to the project, follow these steps:

Fork the repository. Create a new branch for your feature or bug fix. Write tests for your changes and make sure all tests pass. Submit a pull request with your changes.

##  7. <a name='FOLDERSTRUCTURE'></a>FOLDER STRUCTURE

Here is the folder structure for the project:

![folder structure](https://user-images.githubusercontent.com/128668782/233309012-3bfcb8c0-85fd-4e41-9c03-c660d9fb96d7.png)

##  8. <a name='TECHNOLOGIES'></a>TECHNOLOGIES

Java Spring Boot: Used for building the backend server and REST APIs.

React: Used for building the frontend user interface.

Node.js: Used as a package manager for the frontend.

Maven: Used as a build tool for the backend.

MySQL: Used as a relational database management.

##  9. <a name='LICENSE'></a>LICENSE

This project does not have a license, which means that users are free to use, modify, and distribute the source code as they wish.

##  10. <a name='ACKNOWLEDGEMENTS'></a>ACKNOWLEDGEMENTS

This project was inspired by the need for an online courses website that allows users to learn whenever and wherever they want. We would like to thank our contributors and supporters for their valuable feedback and support.

##  11. <a name='CONTACT'></a>CONTACT

Please feel free to contact us at kursutinklalapis@example.com if you have any questions or suggestions.
