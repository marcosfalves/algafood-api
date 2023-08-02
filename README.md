<h1 align="center">algafood-api</h1>
<p align="center">
<img src="https://img.shields.io/badge/Spring_Boot-3.1.1-informational">
<img src="https://img.shields.io/badge/Java-17-informational">
</p>

<p align="center">
<img src="https://sonarcloud.io/api/project_badges/measure?project=marcosfalves_algafood-api&metric=alert_status">
<img src="https://sonarcloud.io/api/project_badges/measure?project=marcosfalves_algafood-api&metric=bugs">
<img src="https://sonarcloud.io/api/project_badges/measure?project=marcosfalves_algafood-api&metric=vulnerabilities">
</p>  

# Introdução
REST API de delivery de comida desenvolvida com Spring Boot seguindo as melhores práticas do mercado durante o curso Especialista Spring REST (ESR) da Algaworks

Neste curso partimos do básico como os CRUDS, e vamos muito além, com tópicos avançados como: Segurança (JWT Oauth2), upload de arquivos, envio de mails, caches, HATEOAS, documentação com Swagger, testes de integração, projeções com JPA, DDD, Domain Events, Oauth2 para segurança, migração e versionamento de base dados (Flyway), e muito mais!

## Tecnologias utilizadas
* [Spring](https://spring.io/)
  * [Spring Boot 3](https://spring.io/projects/spring-boot)
  * [Spring Framework 6](https://spring.io/projects/spring-framework)
  * [Spring Data](https://spring.io/projects/spring-data)
  * [Spring Security 6](https://spring.io/projects/spring-security)
  * [Spring Authorization Server 1.0.1](https://spring.io/projects/spring-authorization-server)
  * [Spring Session (Data Redis)](https://spring.io/projects/spring-session)
  * [Spring HATEOAS](https://spring.io/projects/spring-hateoas)
* [SpringDoc v2 (OpenAPI 3)](https://springdoc.org/v2/)
* [REST Assured](https://rest-assured.io/)
* [Testcontainers](https://testcontainers.com/guides/getting-started-with-testcontainers-for-java)
* [Logback](https://logback.qos.ch/)
  * [Loggly](https://www.loggly.com/)
* [ModelMapper](http://modelmapper.org/)
* [Lombok](https://projectlombok.org/)
* [MySQL](https://www.mysql.com/)
* [Flyway](https://flywaydb.org/)
* [Jasper Reports](https://community.jaspersoft.com/)
* [AWS JDK S3](https://docs.aws.amazon.com/sdk-for-java/index.html)
* [LocalStack](https://localstack.cloud/)

## Qualidade
[![Automated Testing](https://github.com/marcosfalves/algafood-api/actions/workflows/automated-testing.yml/badge.svg)](https://github.com/marcosfalves/algafood-api/actions/workflows/automated-testing.yml)
[![SonarCloud](https://github.com/marcosfalves/algafood-api/actions/workflows/sonar-cloud-analysis.yml/badge.svg)](https://github.com/marcosfalves/algafood-api/actions/workflows/sonar-cloud-analysis.yml)

A qualidade da aplicação é garantida através dos testes unitários e integrados.
Para validação de cobertura mínima de código é utilizado o [JaCoCo](https://www.jacoco.org/) e para análise estática o [SonarCloud](https://sonarcloud.io/project/overview?id=marcosfalves_algafood-api).

## CI

A integração de modificações na branch master é realizada através de abertura de Pull Request.
Uma Pull Request só poderá ser mergeada em caso de sucesso no Status check, que envolve build, execução dos testes automatizados e verificações de qualidade de código com o SonarCloud.

## CD
[![Deploy to Amazon ECS](https://github.com/marcosfalves/algafood-api/actions/workflows/aws-deploy.yml/badge.svg)](https://github.com/marcosfalves/algafood-api/actions/workflows/aws-deploy.yml)
- O deploy em produção é realizado na AWS utilizando o ECS Fargate.
- Após realizar o merge da Pull Request na master é executado uma Action do Github que gera a imagem docker, faz upload para o Amazon ECR e finaliza o deploy com a atualização da task definition.


## Ambiente de desenvolvimento
### Pré-requisitos
- ⚫ [Git](https://git-scm.com/)
- 🐋 [Docker](https://docs.docker.com/engine/install/)
- ☕ [Java 17 ou superior](https://openjdk.org/projects/jdk/)
    - Sugestão: Utilizar SDKMan para instalar o java:
        - [Instalação do SDKMan](https://sdkman.io/install)
        - [Instalação do Java utilizando o SDKMan](https://sdkman.io/usage)

### Instruções

1. Clone o repositório
   ```sh
   git clone https://github.com/marcosfalves/algafood-api.git
   ```
2. Inicie o ambiente de desenvolvimento com o docker
   ```sh
   docker-compose up -d
   ```
3. Execute a aplicação
    ```sh
    ./mvnw spring-boot:run
   ```
   ou na IDE inicie o método main da classe [AlgafoodApiApplication](./src/main/java/com/algaworks/algafood/AlgafoodApiApplication.java)
---
## Deploy em ambiente local
- O deploy em ambiente local permite validar o build e a construção da imagem docker do projeto e também validar o funcionamento com mais de uma instância da aplicação em execução.

### Instruções
1. Inicie o ambiente de desenvolvimento com o docker:
   ```sh
   cd docker
   docker-compose up -d --build --force-recreate --scale algafood-api=2
   ```
2. As requisições devem ser realizadas diretamente para http://localhost/
   * Não informar a porta pois está utilizando NGINX para proxy reverso, assim também é possível utilizar o balanceador de carga do Docker. 
3. Verificar os logs dos containers:
   ```sh
   cd docker
   docker-compose logs -f
   ```
4. Acessar OpenAPI
    * http://127.0.0.1/swagger-ui/index.html
      * Para acessar a doc deve ser utilizado o IP do localhost devido regras de segurança para redirecionamento da autenticação.
    * Ao realizar a autorização no Swagger, utilizar as credenciais já preenchidas e marcar os escopos.
    * Para login na aplicação utilizar:
      * e-mail: ```joao.ger@algafood.com```
      * senha: ```123```
---
### Diagrama de Classes
![alt Diagrama de Classes do projeto](./doc/ESR%20-%20Diagrama%20de%20classes.png)
