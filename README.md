# Hack My Teeth - A deliberately vulnerable java/python application

### __DO NOT RUN THIS ON ANY PUBLICLY AVAILABLE SERVER__

A Java Web App using Spring framework, the repository contains:
 - Java Spring Application source code with Maven as package management
 - MySQL dumped data

## TODO

- Create Dockerfile to build image to run the Java Spring application
- Create Dockerfile to use the MySQL dumped data to host a MySQL database services
- Spin-up both using docker run
- Make the Java Spring wait for the presence of the MySQL database server.
- Create a docker-compose for 1-liner spinning up
