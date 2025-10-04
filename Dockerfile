FROM openjdk:17-jdk-slim
EXPOSE 8081
ADD target/conditionalapp-0.0.1-SNAPSHOT.jar myapp.jar
ENTRYPOINT ["java","-jar","/myapp.jar"]