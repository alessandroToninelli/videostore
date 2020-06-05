# Complete Kotlin server-side application with Ktor 

Server side application written in kotlin using the Ktor framework and following the concept of [*Dependency Rules*](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)

Description
-----
Application that represents the server side of a videostore. Each request returns a JSON

The [**Koin**](https://insert-koin.io/) library is used for dependency injection

Usage
-----
* Download or clone the repo
* Add to resource folder the file **hikari.properties** where there must be all the information to connect the database (e.g. Postgresql)

```
dataSourceClassName=org.postgresql.ds.PGSimpleDataSource
dataSource.user=dbUser
dataSource.password=dbPassword
dataSource.databaseName=dbName
dataSource.portNumber=5432
dataSource.serverName=192.168.1.200
```

* run with `./gradlew run` or through the *MainModule*


License
=======

    Copyright 2017 Alessandro Toninelli

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
