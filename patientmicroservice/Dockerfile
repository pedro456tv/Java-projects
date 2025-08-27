FROM openjdk:24-jdk as builder
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

FROM openjdk:24-jdk
WORKDIR /app
COPY --from=builder /app/target/patientmicroservice-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
