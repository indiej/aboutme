FROM java:8
VOLUME /tmp

ADD /target/VATLab-java-ms-Appan-1.0.0.jar app.jar

# Expose ports.
EXPOSE 8080

RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]