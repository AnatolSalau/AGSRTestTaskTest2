FROM openjdk:21

COPY authorization_server/build/libs .

EXPOSE 8082
EXPOSE 8092

ENTRYPOINT ["java", "-jar", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8092", "authorization_server.jar"]