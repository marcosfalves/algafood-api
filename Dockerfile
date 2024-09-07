FROM eclipse-temurin:17-jdk-alpine AS mvnbuild
WORKDIR /workspace
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw -B dependency:go-offline
COPY src ./src
RUN ./mvnw -B clean package

FROM eclipse-temurin:17-jre-alpine
COPY docker/wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh
RUN addgroup -S application && adduser -S appuser -G application
USER appuser
WORKDIR /app
COPY --from=mvnbuild /workspace/target/*algafood-api*.jar ./algafood-api.jar
EXPOSE 8081
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=80", "-jar", "algafood-api.jar"]

