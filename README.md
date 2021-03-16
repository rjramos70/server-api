# server-api
API server project in which user should be able to register multiple database connections and browse their data and structure

### About The Project

It is about implementing a web-based database browser (similar to desktop application DBeaver ) with basic functionality and for a single database vendor only. 

The browser should be able to register multiple database connections and browse their data and structure.

The result should be a RESTful service with its own database.


### Built With

In this section we list the main frameworks used in the development of this project
* [Java](https://docs.oracle.com/en/java/)
* [Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
* [Open API](https://swagger.io/specification/)
* [SpringFox](https://springfox.github.io/springfox/docs/current/)
* [Swagger UI](https://swagger.io/docs/)


### Getting Started

This is an example of how you may give instructions on setting up your project locally.
To get a local copy up and running follow these simple example steps.

#### Prerequisites

* NPM instance should be up and running before running our service.

  ```sh
  npm install npm@latest -g
  ```
* A local instance of MySQL should be up and running before running our service.
* Java 11 or up runtime version up and running.
* Internet access to download the packages from the project.
* Local installation of an IDE like Eclipse, Spring Tool Suite to open and execute the project.
* By default, the `afterMigration.sql` file already registers a MySQL database connection drive type, which is a prerequisite for registering any Connector.

#### Installation

1. Access the project repository in [rjramos70/server-api](https://github.com/rjramos70/server-api)
2. Clone the repo
   ```sh
   git clone https://github.com/rjramos70/server-api.git
   ```
3. Open the project in a local IDE.

4. Open a terminal in the project main folder and install NPM packages
   ```sh
   npm install
   ```
5. Run the project by clicking on the "Boot Dashboard" tab, in the case of the IDE Spring Tool Suite 4, choose the `server-api` project, and with the direct button choose to start.

### Usage

This project was developed with the OpenAPI framework. After executing our project, the client can have access to all project documentation, its endpoints, resources, among others.

_For more examples, please refer to the [http://localhost:9090/swagger-ui.html#/](http://localhost:9090/swagger-ui.html#/)_


At this time, you have a RESTful API server running at `http://127.0.0.1:9090`. It provides the following endpoints:

* `GET /actuator/health`: checks the health of the project, whether it is running or not


--------------------------------------------------
### Glossary
--------------------------------------------------


* `Database` 


`DatabaseType` - Driver data with the respective database type. 

***Example:*** 

```
{
  "id": 1,
  "description": "MySQL database instance",
  "type": "MySQL",
  "jdbcPrefix": "jdbc:mysql://",
  "jdbcDriver": "com.mysql.cj.jdbc.Driver"
} 
```

`Connector` - It is the instance with the necessary data to connect to a database, for that we need to fill in the following data: name, hostName, port, databaseName, username, password, databasetype. 

***Example:*** 

```
{
    "name": "algafoodDB44444",
    "hostName": "localhost",
    "port": 3306,
    "databaseName": "algafood",
    "username": "root",
    "password": "root",
    "databasetype": {
        "id": 1
    }
} 
```

`Database` - Shows schemas and their details. 

***Example:*** 

```
{
  "id": 1,
  "description": "MySQL database instance",
  "type": "MySQL",
  "jdbcPrefix": "jdbc:mysql://",
  "jdbcDriver": "com.mysql.cj.jdbc.Driver"
} 
```

--------------------------------------------------
### Database Type
--------------------------------------------------

#### Lists all database type connections

**Request**

`GET /databasetypes`
````
curl -X GET "http://localhost:9090/databasetypes" -H "accept: application/json"
````
**Response**

`200 OK`

```
 cache-control: max-age=20, public 
 content-length: 134 
 content-type: application/json 
 date: Mon, 15 Mar 2021 23:36:44 GMT 
 etag: "0aac6b46784afb38c40bd1c424ff40d1c" 

[
  {
    "id": 1,
    "description": "MySQL database instance",
    "type": "MySQL",
    "jdbcPrefix": "jdbc:mysql://",
    "jdbcDriver": "com.mysql.cj.jdbc.Driver"
  }
]

```

--------------------------------------------------

#### Insert a new DatabaseType representation

**Request**

`POST /databasetypes`
````
curl -X POST "http://localhost:9090/databasetypes" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"description\": \"Oracle database instance\", \"type\": \"Oracle\", \"jdbcPrefix\": \"jdbc:oracle://\", \"jdbcDriver\": \"com.oracle.cj.jdbc.Driver\"}"
````
**Response**

`201 DatabaseType createad`

```
 connection: keep-alive 
 content-length: 136 
 content-type: application/json 
 date: Mon, 15 Mar 2021 23:40:24 GMT 
 keep-alive: timeout=60 

{
  "id": 2,
  "description": "Oracle database instance",
  "type": "Oracle",
  "jdbcPrefix": "jdbc:oracle://",
  "jdbcDriver": "com.oracle.cj.jdbc.Driver"
}

```

--------------------------------------------------

#### Get a DatabaseType representation based on an ID

**Request**

`GET /databasetypes/{databaseTypeId}`
````
curl -X GET "http://localhost:9090/databasetypes/1" -H "accept: application/json"
````
**Response**

`200 OK`

```
 cache-control: max-age=20, public 
 content-length: 132 
 content-type: application/json 
 date: Mon, 15 Mar 2021 23:42:21 GMT 
 etag: "0adf3c37f65f3e6b06a561c579f7c73a9" 

{
  "id": 1,
  "description": "MySQL database instance",
  "type": "MySQL",
  "jdbcPrefix": "jdbc:mysql://",
  "jdbcDriver": "com.mysql.cj.jdbc.Driver"
}

```

--------------------------------------------------

#### Update a DatabaseType with the new data

**Request**

`PUT /databasetypes/{databaseTypeId}`
````
curl -X PUT "http://localhost:9090/databasetypes/2" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"description\": \"New Oracle database instance\", \"type\": \"Oracle\", \"jdbcPrefix\": \"jdbc:oracle://\", \"jdbcDriver\": \"com.oracle.cj.jdbc.Driver\"}"
````
**Response**

`200 OK`

```
 connection: keep-alive 
 content-length: 140 
 content-type: application/json 
 date: Mon, 15 Mar 2021 23:45:03 GMT 
 keep-alive: timeout=60 

{
  "id": 2,
  "description": "New Oracle database instance",
  "type": "Oracle",
  "jdbcPrefix": "jdbc:oracle://",
  "jdbcDriver": "com.oracle.cj.jdbc.Driver"
}

```

--------------------------------------------------

#### Delete a DatabaseType based on an valid ID

**Request**

`DELETE /databasetypes/{databaseTypeId}`
````
curl -X DELETE "http://localhost:9090/databasetypes/2" -H "accept: application/json"
````
**Response**

`204 DatabaseType deleted`

```
 connection: keep-alive 
 date: Mon, 15 Mar 2021 23:46:46 GMT 
 keep-alive: timeout=60 

```
--------------------------------------------------
### Connector
--------------------------------------------------

#### List all connectors

**Request**

`GET /connectors`
````
curl -X GET "http://localhost:9090/connectors" -H "accept: application/json"
````
**Response**

`200 OK`

```
 cache-control: max-age=20, public 
 connection: keep-alive 
 content-type: application/json 
 date: Mon, 15 Mar 2021 21:00:00 GMT 
 etag: "1615801766" 
 keep-alive: timeout=60 
 transfer-encoding: chunked 

[
  {
    "id": 1,
    "name": "db0001",
    "hostName": "localhost",
    "port": 3306,
    "databaseName": "db0001",
    "username": "root",
    "password": "xpto",
    "databasetype": {
      "id": 1
    }
  },
  {
    "id": 2,
    "name": "db0002",
    "hostName": "localhost",
    "port": 3306,
    "databaseName": "db0002",
    "username": "root",
    "password": "abcd",
    "databasetype": {
      "id": 1
    }
  }
]

```

--------------------------------------------------
#### Register a new connector
**Request**

`POST /connectors`
````
curl -X POST "http://localhost:9090/connectors" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"databasetype\": { \"id\": 1 }, \"name\": \"db0003\", \"hostName\": \"localhost\", \"port\": 3306, \"databaseName\": \"mydatabase\", \"username\": \"root\", \"password\": \"zklp\"}"
````
**Response**

`201 Connector createad`

```
 connection: keep-alive 
 content-length: 147 
 content-type: application/json 
 date: Mon, 15 Mar 2021 21:15:36 GMT 
 keep-alive: timeout=60 

{
  "id": 6,
  "name": "db0003",
  "hostName": "localhost",
  "port": 3306,
  "databaseName": "mydatabase",
  "username": "root",
  "password": "zklp",
  "databasetype": {
    "id": 1
  }
}
```
--------------------------------------------------

#### Get a connector based on an ID
**Request**

`GET /connectors/{connectorId}`
````
curl -X GET "http://localhost:9090/connectors/1" -H "accept: application/json"
````
**Response**

`200 OK`

```
 cache-control: max-age=20, public 
 connection: keep-alive 
 content-length: 216 
 content-type: application/json 
 date: Mon, 15 Mar 2021 23:25:20 GMT 
 etag: "0355df10284d259af8f76b0b5ed9f53a8" 
 keep-alive: timeout=60 

{
  "id": 1,
  "name": "db0001",
  "hostName": "localhost",
  "port": 3306,
  "databaseName": "db0001",
  "username": "root",
  "password": "xpto",
  "databasetype": {
    "id": 1
  },
  "registered": "2021-03-12T00:33:30",
  "updated": "2021-03-12T00:33:30"
}
```

--------------------------------------------------
#### Update a connector based on an ID
**Request**

`PUT /connectors/{connectorId}`
````
curl -X PUT "http://localhost:9090/connectors/2" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"databasetype\": { \"id\": 1 }, \"name\": \"db0002\", \"hostName\": \"localhost\", \"port\": 3306, \"databaseName\": \"db0002\", \"username\": \"root\", \"password\": \"abcd\"}"
````
**Response**

`200 OK`

```
 connection: keep-alive 
 content-length: 217 
 content-type: application/json 
 date: Mon, 15 Mar 2021 23:29:40 GMT 
 keep-alive: timeout=60 

{
  "id": 2,
  "name": "db0002",
  "hostName": "localhost",
  "port": 3306,
  "databaseName": "db0002",
  "username": "root",
  "password": "abcd",
  "databasetype": {
    "id": 1
  },
  "registered": "2021-03-13T19:01:40",
  "updated": "2021-03-15T20:29:40.308728"
}
```

--------------------------------------------------
#### Remove a connector based on an valid ID
**Request**

`DELETE /connectors/{connectorId}` 
````
curl -X DELETE "http://localhost:9090/connectors/2" -H "accept: application/json"
````
**Response**

`204 Connector deleted`

```
 connection: keep-alive 
 date: Mon, 15 Mar 2021 23:33:45 GMT 
 keep-alive: timeout=60 
```

--------------------------------------------------
### Database
--------------------------------------------------

#### Lists all Schemas and their respective 

**Request**

`GET /databases/{connectorId}`
````
curl -X GET "http://localhost:9090/databases/1" -H "accept: application/json"
````
**Response**

`200 OK`

```
 cache-control: max-age=20, public 
 connection: keep-alive 
 content-type: application/json 
 date: Mon, 15 Mar 2021 21:00:00 GMT 
 etag: "1615801766" 
 keep-alive: timeout=60 
 transfer-encoding: chunked 

[
  {
    "schemaName": "db0003",
    "numberOfTables": 2,
    "tables": [{
		  "name": "kitchen",
		  "tableType": "TABLE",
		  "primaryKey": "id",
		  "numberOfColumns": 2,
		  "columns": [{
				"name": "id",
				"type": "BIGINT"
			      },{
				"name": "nome",
				"type": "VARCHAR"
			      }]
		},{
		   "name": "state",
		   "tableType": "TABLE",
		   "primaryKey": "id",
		   "numberOfColumns": 8,
		   "columns": [{
				 "name": "id",
				 "type": "BIGINT"
			        },{
				 "name": "nome",
				 "type": "VARCHAR"
				},{
				 "name": "CodEstadoIBGE",
				 "type": "INT"
				},{
				 "name": "NomeEstado",
				 "type": "VARCHAR"
				},{
				 "name": "SiglaEstado",
				 "type": "CHAR"
				},{
				 "name": "Regiao",
				 "type": "VARCHAR"
				},{
				 "name": "id",
				 "type": "BIGINT"
				},{
				 "name": "nome",
				 "type": "VARCHAR"
				}]
			}]
   }
]
```

--------------------------------------------------

#### Returns a single representation of Schema
**Request**

`GET /databases/{connectorId}/{schemaName}`
````
curl -X GET "http://localhost:9090/databases/1/ataccama" -H "accept: application/json"
````
**Response**

`200 OK

```
 cache-control: max-age=20, public 
 connection: keep-alive 
 content-length: 25 
 content-type: application/json 
 date: Tue, 16 Mar 2021 12:45:31 GMT 
 etag: "0dfbd6c5123764abffa8da6265b56834d" 
 keep-alive: timeout=60 

{
  "schemaName": "ataccama"
}
```

--------------------------------------------------


#### Lists all columns in a Schema table based on a connector ID, Schema name and table name
**Request**

`GET /databases/{connectorId}/{schemaName}/{tableName}/columns`
````
curl -X GET "http://localhost:9090/databases/1/ataccama/databasetype/columns" -H "accept: application/json"
````
**Response**

`200 OK

```
 cache-control: max-age=20, public 
 connection: keep-alive 
 content-length: 259 
 content-type: application/json 
 date: Tue, 16 Mar 2021 00:14:59 GMT 
 etag: "029766f606866a26ababd96de2f7aec72" 
 keep-alive: timeout=60 

[
  {
    "name": "id",
    "type": "BIGINT"
  },
  {
    "name": "description",
    "type": "VARCHAR"
  },
  {
    "name": "type",
    "type": "VARCHAR"
  },
  {
    "name": "jdbcprefix",
    "type": "VARCHAR"
  },
  {
    "name": "jdbcdriver",
    "type": "VARCHAR"
  },
  {
    "name": "registered",
    "type": "DATETIME"
  },
  {
    "name": "updated",
    "type": "DATETIME"
  }
]
```

--------------------------------------------------

#### Lists all data from a Schema table based on a connector ID, Schema name and table name
**Request**

`GET /databases/{connectorId}/{schemaName}/{tableName}/data`
````
curl -X GET "http://localhost:9090/databases/1/algafood/restaurante/columns/statistic" -H "accept: application/json"
````
**Response**

`200 OK`

```
 cache-control: max-age=20, public 
 connection: keep-alive 
 content-length: 1300 
 content-type: application/json 
 date: Tue, 16 Mar 2021 00:21:30 GMT 
 etag: "0fdaafe03ee7759a09aad2f5ea10ac040" 
 keep-alive: timeout=60 
```

--------------------------------------------------

#### Get statistics for the columns of a table based on the connector ID, Schema name and table name
**Request**

`GET /databases/{connectorId}/{schemaName}/{tableName}/columns/statistic`
````
curl -X GET "http://localhost:9090/databases/1/algafood/restaurante/columns/statistic" -H "accept: application/json"
````
**Response**

`200 OK`

```
 cache-control: max-age=20, public 
 connection: keep-alive 
 content-length: 1300 
 content-type: application/json 
 date: Tue, 16 Mar 2021 00:21:30 GMT 
 etag: "0fdaafe03ee7759a09aad2f5ea10ac040" 
 keep-alive: timeout=60 

[
  {
    "columnName": "id",
    "columnType": "BIGINT",
    "min": 1,
    "avg": 5.5,
    "median": 6,
    "max": 10
  },
  {
    "columnName": "cozinha_id",
    "columnType": "BIGINT",
    "min": 1,
    "avg": 1.8,
    "median": 1,
    "max": 4
  },
  {
    "columnName": "nome",
    "columnType": "VARCHAR",
    "minLength": 6,
    "avgLength": 13.9,
    "medianLength": 14,
    "maxLength": 22
  },
  {
    "columnName": "taxa_frete",
    "columnType": "DECIMAL",
    "min": 5.75,
    "avg": 10.462,
    "median": 11,
    "max": 15
  },
  {
    "columnName": "data_atualizacao",
    "columnType": "DATETIME",
    "min": "2021-03-02 21:35:17",
    "max": "2021-03-02 21:37:00"
  },
  {
    "columnName": "data_cadastro",
    "columnType": "DATETIME",
    "min": "2021-03-02 21:35:17",
    "max": "2021-03-02 21:37:00"
  },
  {
    "columnName": "endereco_cidade_id",
    "columnType": "BIGINT",
    "min": 1,
    "avg": 1,
    "median": 1,
    "max": 1
  },
  {
    "columnName": "endereco_cep",
    "columnType": "VARCHAR",
    "minLength": 9,
    "avgLength": 9,
    "medianLength": 9,
    "maxLength": 9
  },
  {
    "columnName": "endereco_logradouro",
    "columnType": "VARCHAR",
    "minLength": 17,
    "avgLength": 17,
    "medianLength": 17,
    "maxLength": 17
  },
  {
    "columnName": "endereco_numero",
    "columnType": "VARCHAR",
    "minLength": 4,
    "avgLength": 4,
    "medianLength": 4,
    "maxLength": 4
  },
  {
    "columnName": "endereco_complemento",
    "columnType": "VARCHAR",
    "minLength": null,
    "avgLength": null,
    "medianLength": null,
    "maxLength": null
  },
  {
    "columnName": "endereco_bairro",
    "columnType": "VARCHAR",
    "minLength": 6,
    "avgLength": 6,
    "medianLength": 6,
    "maxLength": 6
  }
]
```

--------------------------------------------------

#### Lists all details for a given Schema based on the ID of a connector and the name of the Schema
**Request**

`GET /databases/{connectorId}/{schemaName}/detail`
````
curl -X GET "http://localhost:9090/databases/1/ataccama/detail" -H "accept: application/json"
````
**Response**

`200 OK`

```
 cache-control: max-age=20, public 
 connection: keep-alive 
 content-length: 3283 
 content-type: application/json 
 date: Tue, 16 Mar 2021 12:41:23 GMT 
 etag: "0d8342ee5b177d42e9bd188c997ea10b3" 
 keep-alive: timeout=60 

[
  {
    "name": "connector",
    "tableType": "TABLE",
    "primaryKey": "id",
    "numberOfColumns": 10,
    "columns": [
      {
        "name": "id",
        "type": "BIGINT"
      },
      {
        "name": "name",
        "type": "VARCHAR"
      },
      {
        "name": "hostname",
        "type": "VARCHAR"
      },
      {
        "name": "port",
        "type": "BIGINT"
      },
      {
        "name": "databasename",
        "type": "VARCHAR"
      },
      {
        "name": "username",
        "type": "VARCHAR"
      },
      {
        "name": "password",
        "type": "VARCHAR"
      },
      {
        "name": "databasetype_id",
        "type": "BIGINT"
      },
      {
        "name": "registered",
        "type": "DATETIME"
      },
      {
        "name": "updated",
        "type": "DATETIME"
      }
    ]
  },
  {
    "name": "databasetype",
    "tableType": "TABLE",
    "primaryKey": "id",
    "numberOfColumns": 7,
    "columns": [
      {
        "name": "id",
        "type": "BIGINT"
      },
      {
        "name": "description",
        "type": "VARCHAR"
      },
      {
        "name": "type",
        "type": "VARCHAR"
      },
      {
        "name": "jdbcprefix",
        "type": "VARCHAR"
      },
      {
        "name": "jdbcdriver",
        "type": "VARCHAR"
      },
      {
        "name": "registered",
        "type": "DATETIME"
      },
      {
        "name": "updated",
        "type": "DATETIME"
      }
    ]
  }
]
```

--------------------------------------------------

* `GET /databases/{connectorId}/schemas`: return a lists all schemes based on the connector's ID



Try the URL `localhost:9090/actuator/health` in a browser, and you should see something like `{
    "status": "UP"
}` displayed.

### API overview
--------------------------------------------------

The API is generally RESTFUL and returns results in JSON. Every request requires 'Accept' to be set in the header with 'application / json'. 

The API supports HTTP only. Examples here are provided using HTTP.

You should always url-encode DOIs and parameter values when using the API. DOIs are notorious for including characters that break URLs (e.g. semicolons, hashes, slashes, ampersands, question marks, etc.).


### Result types
--------------------------------------------------

All results are returned in JSON. There are three general types of results:

- Singletons
- Headers-only
- Lists

### Future improvements
--------------------------------------------------

Due to the fact that it is an MVP and due to the short time for delivery, this project was developed using a methodology of continuous evolution and the following functionalities (requirements) were pending:

* Implementation of unit tests.
* Implementation of logs in files or databases.
* Implementation of web interfaces to facilitate the experience with the end user.

### License
--------------------------------------------------

This project was developed without using any associated license.

### Contact
--------------------------------------------------

Renato J Ramos - [Github](https://github.com/rjramos70) - [Linkedin](https://www.linkedin.com/in/rjramos70/) - [Gmail](mailto:rjramos70@gmail.com)

Project Link: [https://github.com/rjramos70/server-api](https://github.com/rjramos70/server-api)







