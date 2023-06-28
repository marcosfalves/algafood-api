FROM maven:3-eclipse-temurin-17 AS mvnbuild
WORKDIR /workspace
COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 mvn -B dependency:go-offline
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 mvn -B clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY docker/wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh
COPY --from=mvnbuild /workspace/target/*algafood-api*.jar ./algafood-api.jar
EXPOSE 8081
CMD ["java", "-Xms896m", "-Xmx1024m", "-jar", "algafood-api.jar"]

