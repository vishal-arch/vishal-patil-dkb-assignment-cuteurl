FROM amazoncorretto:17.0.3
COPY ./target/miniurl-0.0.1-SNAPSHOT.jar /opt/

ENTRYPOINT ["java", "-jar", "/opt/miniurl-0.0.1-SNAPSHOT.jar"]
USER nobody