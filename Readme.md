# Spring-Boot-Asynchronous-API
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Raouf25_spring-boot-asynchronous-api&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Raouf25_spring-boot-asynchronous-api)


## Overview
This project is a sample of an asynchronous API built in Spring Boot. It provides asynchronous endpoints that are called asynchronously and can be used as a starting point to build more complex systems.

## Prerequisites
* Java 8
* Maven 3.3.9
* Spring Boot 2.1.3

## Getting Started
```bash
git clone https://github.com/Raouf25/spring-boot-asynchronous-api.git
```
Once you've cloned the project, you can run it with the following command:

```bash
mvn spring-boot:run
```
Once the server is up and running, you can access the web service at http://localhost:8080/.
 
## how run nrt locally:
```shell
mvn clean test  -D"skip.tests"=false -Plocal -Dapi.url=http://localhost:8080/api/v1
```

## Github Actions
Github Actions is a CI/CD platform that allows developers to automate their workflow with custom scripts. This project contains a Github Action workflow that executes the following steps:

1. Checkout the source code
2. Build the application
3. Run unit & integration tests
   - ✅ [code coverage in sonar](https://sonarcloud.io/summary/new_code?id=Raouf25_spring-boot-asynchronous-api)
4. Deploy in Dev environment
   - ✅ [swagger documentation](https://spring-boot-asynchronous-api.fly.dev/swagger-ui/index.html)
5. Run non regression tests & publish the generated reports
   - ✅ [karate report]( https://raouf25.github.io/spring-boot-asynchronous-api/karate-summary.html )
   
The workflow also runs non-regression tests as part of the build process. The reports are published to the `reports` directory and can be accessed from the Github Actions UI. The reports can also be generated locally using the `mvn test` command.

## Documentation
For more information, please refer to the :
- [Testing a Java Spring Boot REST API with Karate](https://semaphoreci.com/community/tutorials/testing-a-java-spring-boot-rest-api-with-karate)
- [jacoco-multi-module-sample](https://medium.com/javarevisited/merging-integration-unit-and-functional-test-reports-with-jacoco-de5cde9b56e1)
- [jacoco and sonar](https://www.baeldung.com/sonarqube-jacoco-code-coverage)
- r2dbc-pagination  https://www.vinsguru.com/r2dbc-pagination/
- https://www.vinsguru.com/spring-data-r2dbc/
- https://www.vinsguru.com/category/spring/spring-webflux/
- https://www.vinsguru.com/choreography-saga-pattern-with-spring-boot/
- https://www.vinsguru.com/spring-webflux-websocket/
- https://www.vinsguru.com/orchestration-saga-pattern-with-spring-boot/
- https://www.vinsguru.com/spring-webclient-with-feign/
- https://www.vinsguru.com/spring-data-r2dbc-query-by-example/
- https://www.vinsguru.com/spring-boot-graalvm-native-image/

---------------
## Steps of project building:
1. ✅ Rest API implementation: [swagger documentation](https://spring-boot-asynchronous-api.fly.dev/swagger-ui/index.html)
2. ✅ Non Regression Test: [karate report]( https://raouf25.github.io/spring-boot-asynchronous-api/karate-summary.html )
3. ✅ Code coverage (sonar & jacoco): [code coverage in sonar](https://sonarcloud.io/summary/new_code?id=Raouf25_spring-boot-asynchronous-api)
4. 🚧 CI/CD releasing 

