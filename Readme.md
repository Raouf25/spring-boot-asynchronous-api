# Spring-boot-asynchronous-api
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Raouf25_spring-boot-asynchronous-api&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Raouf25_spring-boot-asynchronous-api)

## Steps for project building:
1. ✅ Rest API implementation 
2. ✅ Non Regression Test:  [karate report]( https://raouf25.github.io/spring-boot-asynchronous-api/karate-summary.html )
3. ✅ Code coverage (sonar & jacoco)
4. 🚧 CICD releasing 


 

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

Testing a Java Spring Boot REST API with Karate
https://semaphoreci.com/community/tutorials/testing-a-java-spring-boot-rest-api-with-karate

jacoco-multi-module-sample: 
https://github.com/PraveenGNair/jacoco-multi-module-sample

set up jacoco
https://github.com/vidhya03/http-patch-jax-rs/blob/master/pom.xml
https://www.youtube.com/watch?v=t9O4bYSnB74&ab_channel=Mukeshotwani

jacoco and sonar 
https://www.baeldung.com/sonarqube-jacoco-code-coverage
