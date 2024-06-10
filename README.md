## Тестовое задание
#### Компания: AGSR
#### Выполнил: Салов А. А.


### 1. Запуск приложения

- На GitHub уже загружен скомпилированный JAR task_25_04_2024.jar по пути "build/libs" ([ссылка](build/libs))
- Для запуска необходимо открыть корень проекта (папку "task_25_04_2024") в консоли и запустить
  Docker compose файл командой:

      docker-compose -f dev-postgres.docker-compose.yml up -d
- Для остановки запущенного контейнера используйте команду:

      docker-compose -f dev-postgres.docker-compose.yml down

### 2. Api точки входа
- Порты

      8081 - Rest API
      8087 - порт для удаленного дебага (remote debug)
- Получить всех пользователей с базы данных с их счетами и историей операций можно по адресу

  Номера паспортов и водительских прав не передаются в контроллер и не передаются - из соображений безопасности.

      GET request:
      http://localhost:8081/api/v1/all
<details>
    <summary>Пример ответа "Получить всех пользователей" </summary>

    [
        {
            "name": "user2",
            "documentType": "DRIVER_LICENSE",
            "createdAt": "2024-02-29T00:00:39.412+00:00",
            "accounts": [
                {
                    "id": 5,
                    "userId": 2,
                    "balance": 1000.00,
                    "currencyType": "EUR",
                    "createdAt": "2024-05-29T00:05:39.412+00:00",
                    "transactions": [
                        {
                            "transactionType": "DEPOSIT",
                            "amount": 1000.00,
                            "currencyType": "EUR",
                            "createdAt": "2024-10-29T00:02:39.412+00:00"
                        }
                    ]
                },
                {
                    "id": 4,
                    "userId": 2,
                    "balance": 1000.00,
                    "currencyType": "BYN",
                    "createdAt": "2024-04-29T00:04:39.412+00:00",
                    "transactions": [
                        {
                            "transactionType": "DEPOSIT",
                            "amount": 1000.00,
                            "currencyType": "BYN",
                            "createdAt": "2024-10-29T00:01:39.412+00:00"
                        }
                    ]
                },
                {
                    "id": 6,
                    "userId": 2,
                    "balance": 1000.00,
                    "currencyType": "USD",
                    "createdAt": "2024-06-29T00:06:39.412+00:00",
                    "transactions": [
                        {
                            "transactionType": "DEPOSIT",
                            "amount": 1000.00,
                            "currencyType": "USD",
                            "createdAt": "2024-10-29T00:03:39.412+00:00"
                        }
                    ]
                }
            ]
        },
        {
            "name": "user3",
            "documentType": "PASSPORT",
            "createdAt": "2024-03-29T00:00:39.412+00:00",
            "accounts": [
                {
                    "id": 7,
                    "userId": 3,
                    "balance": 300.00,
                    "currencyType": "BYN",
                    "createdAt": "2024-07-29T00:07:39.412+00:00",
                    "transactions": [
                        {
                            "transactionType": "DEPOSIT",
                            "amount": 100.00,
                            "currencyType": "BYN",
                            "createdAt": "2024-10-29T00:06:39.412+00:00"
                        },
                        {
                            "transactionType": "DEPOSIT",
                            "amount": 100.00,
                            "currencyType": "BYN",
                            "createdAt": "2024-10-29T00:05:39.412+00:00"
                        },
                        {
                            "transactionType": "DEPOSIT",
                            "amount": 100.00,
                            "currencyType": "BYN",
                            "createdAt": "2024-10-29T00:04:39.412+00:00"
                        }
                    ]
                }
            ]
        },
        {
            "name": "user4",
            "documentType": "DRIVER_LICENSE",
            "createdAt": "2024-04-29T00:00:39.412+00:00",
            "accounts": [
                {
                    "id": 8,
                    "userId": 4,
                    "balance": 400.00,
                    "currencyType": "EUR",
                    "createdAt": "2024-08-29T00:08:39.412+00:00",
                    "transactions": [
                        {
                            "transactionType": "DEPOSIT",
                            "amount": 200.00,
                            "currencyType": "EUR",
                            "createdAt": "2024-10-29T00:08:39.412+00:00"
                        },
                        {
                            "transactionType": "DEPOSIT",
                            "amount": 200.00,
                            "currencyType": "EUR",
                            "createdAt": "2024-10-29T00:07:39.412+00:00"
                        }
                    ]
                }
            ]
        },
        {
            "name": "user5",
            "documentType": "PASSPORT",
            "createdAt": "2024-05-29T00:00:39.412+00:00",
            "accounts": [
                {
                    "id": 9,
                    "userId": 5,
                    "balance": 500.00,
                    "currencyType": "USD",
                    "createdAt": "2024-09-29T00:09:39.412+00:00",
                    "transactions": [
                        {
                            "transactionType": "DEPOSIT",
                            "amount": 500.00,
                            "currencyType": "USD",
                            "createdAt": "2024-10-29T00:09:39.412+00:00"
                        }
                    ]
                }
            ]
        },
        {
            "name": "user1",
            "documentType": "PASSPORT",
            "createdAt": "2024-01-29T00:00:39.412+00:00",
            "accounts": [
                {
                    "id": 3,
                    "userId": 1,
                    "balance": 0.00,
                    "currencyType": "USD",
                    "createdAt": "2024-03-29T00:03:39.412+00:00",
                    "transactions": []
                },
                {
                    "id": 2,
                    "userId": 1,
                    "balance": 0.00,
                    "currencyType": "EUR",
                    "createdAt": "2024-02-29T00:02:39.412+00:00",
                    "transactions": []
                },
                {
                    "id": 1,
                    "userId": 1,
                    "balance": 0.00,
                    "currencyType": "BYN",
                    "createdAt": "2024-01-29T00:01:39.412+00:00",
                    "transactions": []
                }
            ]
        }
    ]
</details>

- Пополнить счет пользователя можно по адресу.

      POST request в формате JSON
      http://localhost:8081/api/v1/deposit
Пользователь ищется в БД по уникальному номеру документа,
после чего тип документа в запросе сравнивается с тем что в базе, совпадают ли они.

Поля в JSON номер документа и тип: "documentNumber", "documentType", соответственно.

Сумма операции указывается в поле "amount".

Тип операции указывается в поле "transactionType".

У каждого пользователя есть несколько счетов,
поэтому нужно указать "accountId"

Список пользователей в базе для выполнения запросов:

    Имя пользователя    Номера документов    Тип документа      Id Аккаунтов     
    user1               1111111A001PB9       PASSPORT           1, 2, 3
    user2               2222222A002PB9       DRIVER_LICENSE     4, 5, 6
    user3               3333333A003PB9       PASSPORT           7
    user4               4444444A004PB9       DRIVER_LICENSE     8
    user5               5555555A005PB9       PASSPORT           9




<details >
    <summary>Пример запросов "Пополнить счет"</summary>

    Пополнить счет пользователя "user3" c ID счета 7  
    {   
        "name": "user3",
        "documentNumber": "3333333A003PB9",
        "accountId": 7,
        "documentType": "PASSPORT",
        "amount": 1000.11,
        "transactionType": "DEPOSIT"
    }

    Пополнить счет пользователя "user2" c ID счета 4 
    {
        "name": "user2",
        "documentNumber": "2222222A002PB9",
        "accountId": 4,
        "documentType": "DRIVER_LICENSE",
        "amount": 1000.11,
        "transactionType": "DEPOSIT" 
    }
    Пополнить счет пользователя "user2" c ID счета 6 
    {
        "name": "user2",
        "documentNumber": "2222222A002PB9",
        "accountId": 6,
        "documentType": "DRIVER_LICENSE",
        "amount": 1000.11,
        "transactionType": "DEPOSIT" 
    }
</details>

В ответе мы получим JSON c информацией о пользователе его счетах и историей операций
<details >
        <summary>Пример ответа "Пополнить счет"</summary>

        {
            "name": "user3",
            "documentType": "PASSPORT",
            "createdAt": "2024-03-29T00:00:39.412+00:00",
            "accounts": [
                {
                    "id": 7,
                    "userId": 3,
                    "balance": 1300.11,
                    "currencyType": "BYN",
                    "createdAt": "2024-07-29T00:07:39.412+00:00",
                    "transactions": [
                        {
                            "transactionType": "DEPOSIT",
                            "amount": 1000.11,
                            "currencyType": "BYN",
                            "createdAt": null
                        },
                        {
                            "transactionType": "DEPOSIT",
                            "amount": 100.00,
                            "currencyType": "BYN",
                            "createdAt": "2024-10-29T00:04:39.412+00:00"
                        },
                        {
                            "transactionType": "DEPOSIT",
                            "amount": 100.00,
                            "currencyType": "BYN",
                            "createdAt": "2024-10-29T00:05:39.412+00:00"
                        },
                        {
                            "transactionType": "DEPOSIT",
                            "amount": 100.00,
                            "currencyType": "BYN",
                            "createdAt": "2024-10-29T00:06:39.412+00:00"
                        }
                    ]
                }
            ]
        }
</details>

- Swagger UI с описанием точек входа

      http://127.0.0.1:8081/swagger-ui/index.html

- Open Api 3.0

      http://127.0.0.1:8081/v3/api-docs

- Удаленное тестирование

      http://127.0.0.1:8087/

- Actuator где можно проверить работает ли приложение

      http://127.0.0.1:8081/actuator/health



### 3. Описание структуры базы данных, включая таблицы и поля в них:
Скрипты миграции в папке "src/main/resources/db/changelog/scripts" ([ссылка](build/libs))
#### 1. Таблица Users

Эта таблица будет хранить основные данные о пользователях.
Поля:
- user_id (INT, PRIMARY KEY, AUTO_INCREMENT) - уникальный идентификатор пользователя.
- name (VARCHAR) - имя пользователя.
- document_type (ENUM) - тип документа (passport, driver_license).
- document_number (VARCHAR) - уникальный номер документа.
- created_at (TIMESTAMP) - время создания пользователя.

#### 2. Таблица Accounts

Эта таблица будет хранить информацию о счетах пользователей.
Поля:
- account_id (INT, PRIMARY KEY, AUTO_INCREMENT) - уникальный идентификатор счета.
- user_id (INT, FOREIGN KEY) - идентификатор пользователя, связь с таблицей Users.
- balance (DECIMAL(10,2)) - баланс счета с двумя знаками после запятой.
- currency_type (VARCHAR) - валюта счета (например, USD, EUR).
- created_at (TIMESTAMP) - время создания счета.

#### 3. Таблица Transactions

Эта таблица будет хранить информацию о всех операциях по счетам.
Поля:
- transaction_id (INT, PRIMARY KEY, AUTO_INCREMENT) - уникальный идентификатор транзакции.
- account_id (INT, FOREIGN KEY) - идентификатор счета, связь с таблицей Accounts.
- transaction_type (ENUM) - тип операции (deposit, withdrawal).
- amount (DECIMAL(10,2)) - сумма транзакции.
- currency_type (VARCHAR) - валюта транзакции.
- timestamp (TIMESTAMP) - время проведения операции.
- created_at (TIMESTAMP) - время проведения транзакции.


#### Связи между таблицами:

- Users и Accounts связаны через user_id, где каждый пользователь может иметь один или несколько счетов.
- Accounts и Transactions связаны через account_id, позволяя отслеживать все операции, проведенные по каждому счету.

### 4. Логирование:
Логирование производится в папку "src/main/resources/logs" ([ссылка](build/libs))

### 5. Обработка параллельных запросов на пополнение счета:
Реализована стандартным Spring @Async. Пул потоков реализован через AsyncConfigurer.

### 6. Обработка исключений
Исключения обрабатываются с помощью @RestControllerAdvice
<details >
    <summary>Пример JSON ошибки"</summary>

    Пополнить счет пользователя "user2" c ID счета 10 - такого ID счета не существует  
    {
        "statusCode": 500,
        "message": "Account not found"
    }
</details>

Асинхронные исключения обрабатываются с помощью AsyncUncaughtExceptionHandler
и выводятся в консоль



    
    
    