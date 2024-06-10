FROM openjdk:21

COPY resource_server/build/libs .

EXPOSE 8083
EXPOSE 8093

ENTRYPOINT ["java", "-jar", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8093", "resource_server.jar"]