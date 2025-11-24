FROM maven:3.9.8-eclipse-temurin-21-alpine AS build

WORKDIR /build

COPY pom.xml ./

RUN --mount=type=cache,target=/root/.m2 \
    mvn dependency:go-offline -B -DskipTests

COPY ./src ./src

RUN --mount=type=cache,target=/root/.m2 \
    mvn package -B -DskipTests

FROM eclipse-temurin:21-jre-alpine AS final

ARG BUILD_DATE

ARG PROJECT_VERSION

ARG GIT_COMMIT

ARG ENVIRONMENT

LABEL sync.title="Tools Challenge - Pagamentos API"

LABEL sync.authors="Tools Challenge <fredsonchaves07@gmail.com>"

LABEL sync.created="${BUILD_DATE}"

LABEL sync.version="${PROJECT_VERSION}"

LABEL sync.revision="${GIT_COMMIT}"

LABEL sync.environment="${ENVIRONMENT}"

ARG UID=10001

RUN adduser -D -H -s /sbin/nologin -u "${UID}" tools-challenge

COPY --from=build /build/target/quarkus-app/ ./tools-challenge/

RUN mv tools-challenge/quarkus-run.jar tools-challenge/tools-challenge-app.jar

WORKDIR /tools-challenge

RUN chown -R tools-challenge:tools-challenge .

USER tools-challenge

EXPOSE 8080

ENV JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom"

CMD ["java", "-jar", "./tools-challenge-app.jar"]