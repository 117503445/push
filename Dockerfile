FROM maven:3.6.3-openjdk-16 AS MAVEN_BUILD
COPY pom.xml /build/
COPY src /build/src/
# COPY ./src/main/resources/app.yml /build/src/main/resources/application.yml
WORKDIR /build/
RUN mvn package -Dmaven.test.skip=true
FROM openjdk:16-alpine
WORKDIR /root
EXPOSE 80
COPY --from=MAVEN_BUILD /build/target/push.jar /root/push.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/urandom","-jar", "push.jar"]