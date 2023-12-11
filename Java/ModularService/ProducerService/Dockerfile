FROM alpine as BUILDER
LABEL authors="Bistik"

COPY target/ target/
#COPY target/ProducerService-0.0.1-SNAPSHOT.jar .
RUN apk add openjdk17

#ENTRYPOINT ["java", "-jar", "ProducerService-0.0.1-SNAPSHOT.jar"]

RUN jdeps --print-module-deps --multi-release 17 --ignore-missing-deps --class-path="target/dependency/*" --module-path="target/dependency/*" target/ProducerService-0.0.1-SNAPSHOT.jar > jre-deps.info
RUN jlink --strip-java-debug-attributes --no-header-files --no-man-pages --output jre --add-modules $(cat jre-deps.info)

FROM alpine

COPY --from=BUILDER target/ProducerService-0.0.1-SNAPSHOT.jar /
COPY --from=BUILDER jre/ jre/

ENTRYPOINT ["jre/bin/java", "-jar", "ProducerService-0.0.1-SNAPSHOT.jar"]