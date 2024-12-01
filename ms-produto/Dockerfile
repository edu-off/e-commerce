FROM maven:3.8.3-amazoncorretto-17 as builder
COPY ms-produto/src /app/src
COPY ms-produto/pom.xml /app
WORKDIR /app
RUN mvn clean package -DskipTests]

FROM amazoncorretto:17-alpine-jdk
WORKDIR /app
RUN addgroup --system javauser && adduser -S -s /usr/sbin/nologin -G javauser javauser
COPY --from=builder /app/target/*.jar /app/app.jar
RUN chown -R javauser:javauser .
USER javauser
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "/app/app.jar" ]