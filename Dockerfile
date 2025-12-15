FROM gradle:jdk21-corretto AS builder
WORKDIR /workspace
COPY . .
RUN --mount=type=secret,id=FRISBOO_GPR_USER \
    --mount=type=secret,id=FRISBOO_GPR_TOKEN \
    mkdir -p /root/.gradle && \
    echo "frisboo.gpr.user=$(cat /run/secrets/FRISBOO_GPR_USER)" > /root/.gradle/gradle.properties && \
    echo "frisboo.gpr.key=$(cat /run/secrets/FRISBOO_GPR_TOKEN)" >> /root/.gradle/gradle.properties
RUN ./gradlew clean bootJar -x test

FROM amazoncorretto:21-alpine
EXPOSE 8080
RUN mkdir -p /app
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
WORKDIR /app
COPY --from=builder /workspace/build/libs/*.jar /app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
