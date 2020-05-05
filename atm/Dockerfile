FROM openjdk:12
COPY ./build/libs/atm-1.0-SNAPSHOT.jar /usr/share/app/
WORKDIR /usr/share/app
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "atm-1.0-SNAPSHOT.jar"]