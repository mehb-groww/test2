# Hack My Teeth - A deliberately vulnerable java/python application

As the title says, this repository contains source codes for dockerized vulnerable java web and python application.
The dockerized system consists of:
 - Java Spring Boot Application
 - Flask API Python Server
 - MariaDB
 - nginx for reversed proxy to map both of the web applications into port 80

## Dependencies

```
Docker CE > 18.06
```

## How to run

### __DO NOT RUN THIS ON ANY PUBLICLY AVAILABLE SERVER___

To run the whole thing,
Please make sure that __port 80 is available on your machine__ and __Docker (CE version > 18.06) are installed___

Add the following entries to your __host__ file:
```
app.hackteeth.com 127.0.0.1
api.hackteeth.com 127.0.0.1
```
Clone the whole repository
```
git clone https://github.com/ledz1996/hack_my_teeth
```
Navigiate your self to the repository directory and run
```
docker-compose up
```
After a couple of minutes, both java and python web applications should be up and running

