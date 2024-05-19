#
# Build stage
#
FROM maven:3.8.4-openjdk-17 as BUILD
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17
COPY --from=build target/chat-server-0.0.1-SNAPSHOT.jar chat-server-0.0.1-SNAPSHOT.jar

# ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java","-jar","chat-server-0.0.1-SNAPSHOT.jar"]
