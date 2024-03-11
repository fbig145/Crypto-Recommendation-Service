FROM maven:3.8.4-openjdk-17 as builder
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:17-jdk-slim
COPY --from=builder /home/app/target/*.jar /usr/local/lib/app.jar
EXPOSE 8020
ENV TZ=UTC
ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]
