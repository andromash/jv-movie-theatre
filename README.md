# Cinema online system

This is the RESTful API to wrk with online cinema ticketing system. This project was build with minding N-tier architecture.
Only back-end side included, of course. KISS, DRY, SOLID are the main principles.

Technologies used in this project:
- Java 11
- Spring MVC
- Spring Security
- Hibernate
- MySQL
- Tomcat Server
- Log4j
- Maven

Only authenticated users can use the full functionality of the app. Only login and sign up pages.
After a user is registered, they are assigned the 'USER' role by default. Users can have only two roles: USER and ADMIN.
Also, it is predicted that roles can be combined. So if a user has two roles - they can perform both sets of actions.

Admin user is created automatically while starting. email: `admin@gmail.com`, password: `12345`

To launch the project, download the project onto your machine and make sure you have MySQL Workbench on it.
Then you need to create schema `cinema` on your RDBMS.

This project runs on Apache Tomcat, so you will need to configure it as well.
Maven is used as the packaging tool, and you need to enable the import all of the dependencies and plugins.

Project created by Andrii Romash https://github.com/andromash
