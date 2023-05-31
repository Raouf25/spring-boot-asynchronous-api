# Spring-Boot-Asynchronous-API
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Raouf25_spring-boot-asynchronous-api&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Raouf25_spring-boot-asynchronous-api)


## Overview
Spring-Boot-Asynchronous-API is a sample project showcasing the implementation of an asynchronous API built using Spring Boot. It offers asynchronous endpoints, allowing for concurrent and non-blocking execution, making it an excellent foundation for developing more complex systems.

## Prerequisites
* Java 19
* Maven 3.3.9
* Spring Boot 3.1.0

## Getting Started
```bash
git clone https://github.com/Raouf25/spring-boot-asynchronous-api.git
```
Once you've cloned the project, you can run it with the following command:

```bash
mvn spring-boot:run
```
Once the server is up and running, you can access the web service at http://localhost:8080/.

## Running Non-Regression Tests Locally:
You can run non-regression tests locally with the following command:
```shell
mvn clean test  -D"skip.tests"=false -Plocal -Dapi.url=http://localhost:8080 
```

## Running Gatling Loading Tests Locally:
To run Gatling loading tests locally, navigate to the loading directory and use the following command:
```shell
cd loading
mvn -Dgatling.simulation.name=HttpSimulation4 clean gatling:test
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
   - ✅ [karate report](https://raouf25.github.io/spring-boot-asynchronous-api/karate/karate-summary.html)

   The workflow also runs non-regression tests as part of the build process. The reports are published to the `reports` directory and can be accessed from the Github Actions UI. The reports can also be generated locally using the `mvn test` command.
6. Run loading tests & publish the generated reports
   - ✅ [gatling report](https://raouf25.github.io/spring-boot-asynchronous-api/gatling/summary.html)

## Documentation
For more information, please refer to the :
- [Testing a Java Spring Boot REST API with Karate](https://semaphoreci.com/community/tutorials/testing-a-java-spring-boot-rest-api-with-karate)
- [jacoco-multi-module-sample](https://medium.com/javarevisited/merging-integration-unit-and-functional-test-reports-with-jacoco-de5cde9b56e1)
- [jacoco and sonar](https://www.baeldung.com/sonarqube-jacoco-code-coverage)
- [gatling loading test](https://github.com/krizsan/gatling-examples)

---------------
## Project Building Steps:
The project building steps are as follows:
1. ✅ Implement the REST API. You can access the [Swagger documentation](https://spring-boot-asynchronous-api.fly.dev/swagger-ui/index.html)
2. ✅ Perform Non-Regression Testing. Review [Karate report](https://raouf25.github.io/spring-boot-asynchronous-api/karate/karate-summary.html)
3. ✅ Monitor and Improve Code Coverage. View [code coverage in sonar](https://sonarcloud.io/summary/new_code?id=Raouf25_spring-boot-asynchronous-api)
4. ✅ Run Loading Tests. Explore the [Gatling report](https://raouf25.github.io/spring-boot-asynchronous-api/gatling/summary.html)
5. 🚧 Ongoing work on CI/CD and releases.
