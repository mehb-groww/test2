#!/bin/bash

echo "Waiting for mysql"
until (echo > /dev/tcp/$DB_ADDRESS/$DB_PORT) >/dev/null 2>&1
do
  printf "."
  sleep 1
done

echo -e "\nmysql ready"
sed -i 's#to_be_replaced#'"$DB_ADDRESS\:$DB_PORT"'#g' /root/hack_my_teeth/src/main/resources/application.properties
java -jar -D/spring.profiles.active=docker /root/hack_my_teeth/target/HackMyTeeth-0.0.1-SNAPSHOT.jar