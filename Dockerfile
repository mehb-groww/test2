FROM maven:3.5.3-jdk-8 as maven
RUN mkdir --parents /root/hack_my_teeth
WORKDIR /root/hack_my_teeth
ADD pom.xml /root/hack_my_teeth/
RUN mvn verify clean --fail-never
ADD . /root/hack_my_teeth
RUN mvn -Dmaven.test.skip=true install 
CMD ["ls", "/root/hack_my_teeth/target/"]
CMD ["cp","/root/hack_my_teeth/target/HackMyTeeth-0.0.1-SNAPSHOT.jar","/tmp/app.jar"]
