##
## Build stage
##
#FROM maven:3.9.5-eclipse-temurin-21-alpine AS MAVEN_BUILD
#COPY pom.xml /build/
#COPY app /build/app/
#WORKDIR /build/
#RUN mvn -f /build/app/pom.xml clean package
#
##
## Package stage
##
## base image to build a JRE
#FROM eclipse-temurin:21-alpine AS deps
#
## Identify dependencies
#COPY --from=MAVEN_BUILD ./build/app/target/*-SNAPSHOT.jar /app/app.jar
#RUN mkdir /app/unpacked && \
#    cd /app/unpacked && \
#    unzip ../app.jar && \
#    cd .. && \
#    $JAVA_HOME/bin/jdeps \
#    --ignore-missing-deps \
#    --print-module-deps \
#    -q \
#    --recursive \
#    --multi-release 17 \
#    --class-path="./unpacked/BOOT-INF/lib/*" \
#    --module-path="./unpacked/BOOT-INF/lib/*" \
#    ./app.jar > /deps.info
#
## base image to build a JRE
#FROM eclipse-temurin:21-alpine AS custome-jre-step
#
## required for strip-debug to work
#RUN apk add --no-cache binutils
#
## copy module dependencies info
#COPY --from=deps /deps.info /deps.info
#
## Build small JRE image
#RUN $JAVA_HOME/bin/jlink \
#         --verbose \
#         --add-modules $(cat /deps.info) \
#         --strip-debug \
#         --no-man-pages \
#         --no-header-files \
#         --compress=2 \
#         --output /customjre
#
## main app image
#FROM alpine:latest
#ENV JAVA_HOME=/jre
#ENV PATH="${JAVA_HOME}/bin:${PATH}"
#
## copy JRE from the base image
#COPY --from=custome-jre-step /customjre $JAVA_HOME
#
## Add app user
#ARG APPLICATION_USER=appuser
#RUN adduser --no-create-home -u 1000 -D $APPLICATION_USER
#
## Configure working directory
#RUN mkdir /app && \
#    chown -R $APPLICATION_USER /app
#
## Set the working directory
#WORKDIR /app
#
## Copy the built JAR file from the build stage
#COPY --chown=1000:1000  --from=MAVEN_BUILD /build/app/target/spring-boot-asynchronous-api-app-*.jar  /app/app.jar
#
## Expose port 8080
#EXPOSE 8080
#
## Run the JAR file as the entrypoint
#ENTRYPOINT [ "/jre/bin/java", "-XX:+UseSerialGC","-Xss512k", "-jar", "/app/app.jar" ]






# ------------------------------------
#
## Simple Dockerfile adding Maven and GraalVM Native Image compiler to the standard
## https://github.com/graalvm/container/pkgs/container/graalvm-ce image
#FROM ghcr.io/graalvm/graalvm-ce:22.3.3 AS build
#
## For SDKMAN to work we need unzip & zip
#RUN microdnf -y install unzip zip \
# && gu install native-image
#
##RUN \
##    # Install SDKMAN
##    curl -s "https://get.sdkman.io" | bash; \
##    source "$HOME/.sdkman/bin/sdkman-init.sh"; \
##    sdk install maven; \
##    # Install GraalVM Native Image
##    gu install native-image;
##
##RUN source "$HOME/.sdkman/bin/sdkman-init.sh" && mvn --version
#
#ARG MAVEN_VERSION=3.9.4
#
#RUN curl https://dlcdn.apache.org/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz --output /opt/maven.tar.gz \
# && tar xvzf /opt/maven.tar.gz \
# && mv apache-maven-$MAVEN_VERSION /opt/maven
#
#
#RUN native-image --version
#
## Set environment variable to increase Maven memory
#ENV MAVEN_OPTS="-Xmx2g -Xms512m"
#
## Copy the application source code
#WORKDIR /build
#COPY . .
#
## Download dependencies
##RUN source "$HOME/.sdkman/bin/sdkman-init.sh" && mvn dependency:go-offline
#
## Check: https://github.com/tratif/specification-arg-resolver-example-springboot3
##RUN source "$HOME/.sdkman/bin/sdkman-init.sh" && mvn -Pnative native:compile -DskipTests -f ./app/pom.xml
#RUN /opt/maven/bin/mvn  -Pnative native:compile -DskipTests -f ./app/pom.xml
#
## Second stage: Lightweight debian-slim image
#FROM debian:bookworm-slim
#
#WORKDIR /app
#
## Copy the native binary from the build stage
#COPY --from=build /build/app/target/spring-boot-asynchronous-api-app /app/
#
## Run the application
#CMD ["./spring-boot-asynchronous-api-app"]



# ----------------
# Using Oracle GraalVM for JDK 21
FROM container-registry.oracle.com/graalvm/native-image:21-ol8 AS builder

# Set the working directory to /home/app
WORKDIR /build

# Copy the source code into the image for building
COPY . /build

# Build
RUN ./mvnw --no-transfer-progress native:compile -Pnative  -DskipTests -f ./app/pom.xml

# The deployment Image
FROM container-registry.oracle.com/os/oraclelinux:8-slim

EXPOSE 8080

# Copy the native executable into the containers
COPY --from=builder /build/app/target/spring-boot-asynchronous-api-app app
ENTRYPOINT ["/app"]
