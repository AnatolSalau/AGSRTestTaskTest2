1. Resource server to work with patients


         port:  8083
         url: http://127.0.0.1:8083/api/v1/patients
         
   Swagger
         
         http://127.0.0.1:8083/swagger-ui/index.html
         http://127.0.0.1:8083/v3/api-docs
   To create patient
         

         Role: Practitioner
         Permission: Patient.Write
         POST http://127.0.0.1:8083/api/v1/patients

   To get all patients


         Role: Practitioner, Patient
         Permission: Patient.Read         
         GET http://127.0.0.1:8083/api/v1/patients

   To get particular patient 


         Role: Practitioner, Patient
         Permission: Patient.Read 
         GET http://127.0.0.1:8083/api/v1/patients/name

   To update particular patient.


         Role: Practitioner
         Permission: Patient.Write 
         PUT http://127.0.0.1:8083/api/v1/patients

   To delete particular patient


         Role: Practitioner
         Permission: Patient.Delete        
         DELETE http://127.0.0.1:8083/api/v1/patients/<id>

2. Authorization server 


         port: 8082
         Swagger UI с описанием точек входа

         http://127.0.0.1:8082/swagger-ui/index.html
         http://127.0.0.1:8082/v3/api-docs


3. Client "client credential flow"

         Role: Practitioner
         Permission: Patient.Write
         port: 8080

4. Docker
   
   Build image authorization service

        docker build -t authorization_server -f authorization_server.dockerfile .
   Run image authorization service

        docker run -d -p 8082:8082 -p 8092:8092 --name=authorization_server authorization_server
         
   Build image client credential server

        docker build -t client_credential_server -f client_credential_server.dockerfile . 

   Run image client credential server

        docker run -d -p 8080:8080 -p 8090:8090 --name=client_credential_server client_credential_server

   Build image resource service

        docker build -t resource_server -f resource_server.dockerfile .  
   Run image resource service

        docker run -d -p 8083:8083 -p 8093:8093 --name=resource_server resource_server


   Run docker yml with postgres

        docker-compose -f dev-postgres.docker-compose.yml up -d

   Shutdown docker yml with postgres

        docker-compose -f dev-postgres.docker-compose.yml down

http://127.0.0.1:8082/.well-known/openid-configuration

docker-compose -f auth-res-postgres.docker-compose.yml up -d

docker-compose -f clients.docker-compose.yml up -d