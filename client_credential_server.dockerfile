FROM openjdk:21

COPY client_credential_rest_template_service/build/libs .

EXPOSE 8080
EXPOSE 8090

ENTRYPOINT ["java", "-jar", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8090", "client_credential_server.jar"]