FROM openjdk:12
COPY ./build/libs/bankApp-1.0-SNAPSHOT.jar /usr/share/app/
WORKDIR /usr/share/app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "bankApp-1.0-SNAPSHOT.jar"]