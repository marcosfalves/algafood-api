FROM eclipse-temurin:17.0.6_10-jre

WORKDIR /app

COPY target/*.jar /app/algafood.jar

EXPOSE 8080

CMD ["java", "-jar", "algafood.jar"]

