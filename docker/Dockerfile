FROM eclipse-temurin:17.0.6_10-jre

WORKDIR /app

ARG JAR_FILE

COPY target/${JAR_FILE} /app/algafood.jar

EXPOSE 8080

CMD ["java", "-jar", "algafood.jar"]

