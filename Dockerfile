FROM eclipse-temurin:17.0.6_10-jre

WORKDIR /app

ARG JAR_FILE

COPY target/${JAR_FILE} /app/algafood.jar
COPY docker/wait-for-it.sh /wait-for-it.sh

RUN chmod +x /wait-for-it.sh

EXPOSE 8080

CMD ["java", "-jar", "algafood.jar"]

