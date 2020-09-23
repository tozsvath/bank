FROM openjdk:12
COPY ./bank/build/libs/bank-1.0-SNAPSHOT.jar /usr/share/app/
WORKDIR /usr/share/app
EXPOSE 8081
ENTRYPOINT exec java $JAVA_OPTS -jar bank-1.0-SNAPSHOT.jar