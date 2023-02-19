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
 

## Github Actions
Github Actions is a CI/CD platform that allows developers to automate their workflow with custom scripts. This project contains a Github Action workflow that executes the following steps:

1. Checkout the source code
2. Build the application
3. Run unit & integration tests
   - ✅ [code coverage in sonar](https://sonarcloud.io/summary/new_code?id=Raouf25_spring-boot-asynchronous-api)
4. Deploy in Dev environment
5. Run non regression tests
6. Publish the generated reports
   - ✅ [karate report]( https://raouf25.github.io/spring-boot-asynchronous-api/karate-summary.html )
   
The workflow also runs non-regression tests as part of the build process. The reports are published to the `reports` directory and can be accessed from the Github Actions UI. The reports can also be generated locally using the `mvn test` command.

## Documentation
For more information, please refer to the [official documentation](https://www.baeldung.com/spring-async).


---------------
## Steps of project building:
1. ✅ Rest API implementation 
2. ✅ Non Regression Test:  [karate report]( https://raouf25.github.io/spring-boot-asynchronous-api/karate-summary.html )
3. ✅ Code coverage (sonar & jacoco)
4. 🚧 CICD releasing 

## how run nrt locally:
```shell
 mvn clean test -DskipTests=false -Dapi.url=http://localhost:8080 
```

## Maven release
steps: 
- checkout code from repository
- get actual version 
- push image in docker 
- deploy 
- generate changelog 
- bump version in main branch 

1. increase version
```shell
mvn -q build-helper:parse-version versions:set -DnewVersion=\${parsedVersion.majorVersion}.\${parsedVersion.nextMinorVersion}.0 versions::commit
```

## References:
- [Testing a Java Spring Boot REST API with Karate](https://semaphoreci.com/community/tutorials/testing-a-java-spring-boot-rest-api-with-karate)
- [jacoco-multi-module-sample](https://medium.com/javarevisited/merging-integration-unit-and-functional-test-reports-with-jacoco-de5cde9b56e1)
- [jacoco and sonar](https://www.baeldung.com/sonarqube-jacoco-code-coverage)


------------
set up jacoco
https://github.com/vidhya03/http-patch-jax-rs/blob/master/pom.xml
https://www.youtube.com/watch?v=t9O4bYSnB74&ab_channel=Mukeshotwani

