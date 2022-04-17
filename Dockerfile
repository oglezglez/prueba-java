FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /workspace
COPY pricelist-domain pricelist-domain
COPY pricelist-persistence pricelist-persistence
COPY pricelist-app pricelist-app
COPY pom.xml .

RUN mvn clean package -ntp

FROM eclipse-temurin:17-jre-centos7
WORKDIR /
# copy only the built jar and nothing else
COPY --from=build /workspace/pricelist-app/target/pricelist-app-*.jar /app.jar

ENV VERSION=$VERSION
ENV JAVA_OPTS=-Dspring.profiles.active=production

EXPOSE 8080

ENTRYPOINT ["sh","-c","java -jar -Dspring.profiles.active=production /app.jar"]
