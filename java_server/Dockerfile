FROM maven:3.6.1-jdk-8
ARG DB_ADDRESS
ARG DB_PORT
RUN mkdir --parents /root/hack_my_teeth
WORKDIR /root/hack_my_teeth
ADD . ./
RUN sh ./modified_db.sh
RUN mvn clean -fn
RUN mvn -Dmaven.test.skip=true install 
CMD ['bash','./wait_for_db.sh']
