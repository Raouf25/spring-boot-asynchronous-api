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
