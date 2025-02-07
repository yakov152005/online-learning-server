FROM maven:3.8.4-openjdk-17 as build
WORKDIR /OnlineLearning
COPY . .
RUN mvn clean package -DskipTests


FROM openjdk:17-jdk-alpine
VOLUME /tmp
COPY --from=build /OnlineLearning/target/OnlineLearning.jar OnlineLearning.jar
ENTRYPOINT ["java", "-jar", "/OnlineLearning.jar"]