# java_server/Dockerfile content
# Base Image as maven with jdk 8
FROM maven:3.6.1-jdk-8
# Make a directory to contains the source code of HackMyTeeth and Maven configuration file
RUN mkdir -p /root/hack_my_teeth
# Set the working directory
WORKDIR /root/hack_my_teeth
# Copy the source code from local directory to the working directory of the docker image
ADD . ./
# Maven cleaning all the files created from previous build by IDE, -fn flag to ignore all the errors, issues
RUN mvn clean -fn
# Let tell Maven to build our project into .jar
RUN mvn -Dmaven.test.skip=true install
# Set the default command for docker run
CMD ["java","-jar","-D/spring.profiles.active=docker","/root/hack_my_teeth/target/HackMyTeeth-0.0.1-SNAPSHOT.jar"]