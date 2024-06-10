## Тестовое задание
#### Компания: AGSR
#### Выполнил: Салов А. А.


### 1. Запуск приложения

- На GitHub уже загружен скомпилированный JAR файлы по пути "build/libs" в каждом модуле
- Для запуска необходимо открыть корень проекта (папку "AGSRTestTaskTest2") в консоли и запустить
  Docker compose файл командой:

  Запуск сервисов авторизации, данных и базы данных

      docker-compose -f auth-res-postgres.docker-compose.yml up -d
- Для остановки сервисов авторизации, данных и базы данных используйте команду:

      docker-compose -f auth-res-postgres.docker-compose.yml up -d down

  Запуск консольного приложения

      docker-compose -f clients.docker-compose.yml up -d
- Для остановки сервисов авторизации, данных и базы данных используйте команду:

      приложение остановится само послесохранения 100 патиентов

### 2. Postman 
  Сохраненная коллекция запросов в файле "AGSRTestTaskTest2.postman_collection.json"
  AGSRTestTaskTest2 - название коллекции после импорта
  [Ссылка](AGSRTestTaskTest2.postman_collection.json)

### 3. Api точки входа
1. Сервис ресурсов для получения пациентов


         port:  8083
         url: http://127.0.0.1:8083/api/v1/patients

  Swagger

         http://127.0.0.1:8083/swagger-ui/index.html
         http://127.0.0.1:8083/v3/api-docs

  Запрос на сохранение


         Role: Practitioner
         Permission: Patient.Write
         POST http://127.0.0.1:8083/api/v1/patients

  Запрос на получение всех пациентов


         Role: Practitioner, Patient
         Permission: Patient.Read         
         GET http://127.0.0.1:8083/api/v1/patients

  Получить пациента по имени


         Role: Practitioner, Patient
         Permission: Patient.Read 
         GET http://127.0.0.1:8083/api/v1/patients/name

  Изменить дату рождения и пол пациента


         Role: Practitioner
         Permission: Patient.Write 
         PUT http://127.0.0.1:8083/api/v1/patients

  Удалить пациента по имени


         Role: Practitioner
         Permission: Patient.Delete        
         DELETE http://127.0.0.1:8083/api/v1/patients/name

2. Сервис авторизации


          port:  8082
          http://127.0.0.1:8082/oauth2/authorize
          http://127.0.0.1:8082/oauth2/token

3. Клиент по сохранению пациентов


          port:  8080
          http://127.0.0.1:8080

4. Общее описание
    На порту 8082 запущен сервис авторизациипо протоколу