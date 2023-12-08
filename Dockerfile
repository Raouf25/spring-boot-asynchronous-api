#
# Build stage
#
FROM maven:3.9.5-eclipse-temurin-21-alpine AS MAVEN_BUILD
COPY pom.xml /build/
COPY . /build/
WORKDIR /build/
RUN mvn -f /build/pom.xml clean package

#
# Package stage
#
# base image to build a JRE
FROM eclipse-temurin:21-alpine AS deps

# Identify dependencies
COPY --from=MAVEN_BUILD ./build/app/target/*-SNAPSHOT.jar /app/app.jar
RUN mkdir /app/unpacked && \
    cd /app/unpacked && \
    unzip ../app.jar && \
    cd .. && \
    $JAVA_HOME/bin/jdeps \
    --ignore-missing-deps \
    --print-module-deps \
    -q \
    --recursive \
    --multi-release 17 \
    --class-path="./unpacked/BOOT-INF/lib/*" \
    --module-path="./unpacked/BOOT-INF/lib/*" \
    ./app.jar > /deps.info

# base image to build a JRE
FROM eclipse-temurin:21-alpine AS custome-jre-step

# required for strip-debug to work
RUN apk add --no-cache binutils

# copy module dependencies info
COPY --from=deps /deps.info /deps.info

# Build small JRE image
RUN $JAVA_HOME/bin/jlink \
         --verbose \
         --add-modules $(cat /deps.info) \
         --strip-debug \
         --no-man-pages \
         --no-header-files \
         --compress=2 \
         --output /customjre

# main app image
FROM alpine:latest
ENV JAVA_HOME=/jre
ENV PATH="${JAVA_HOME}/bin:${PATH}"

# copy JRE from the base image
COPY --from=custome-jre-step /customjre $JAVA_HOME

# Add app user
ARG APPLICATION_USER=appuser
RUN adduser --no-create-home -u 1000 -D $APPLICATION_USER

# Configure working directory
RUN mkdir /app && \
    chown -R $APPLICATION_USER /app

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --chown=1000:1000  --from=MAVEN_BUILD /build/app/target/spring-boot-asynchronous-api-app-*.jar  /app/app.jar

# Expose port 8080
EXPOSE 8080

# Run the JAR file as the entrypoint
ENTRYPOINT [ "/jre/bin/java", "-XX:+UseSerialGC","-Xss512k", "-jar", "/app/app.jar" ]
