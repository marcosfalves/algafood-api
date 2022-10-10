<p align="center">
   <img src="http://img.shields.io/static/v1?label=STATUS&message=EM%20DESENVOLVIMENTO&color=RED&style=for-the-badge"/>
</p>

# algafood-api
REST API de delivery de comida desenvolvida com Spring Boot seguindo as melhores práticas do mercado durante o curso Especialista Spring REST (ESR) da Algaworks

Neste curso partimos do básico como os CRUDS, e vamos muito além, com tópicos avançados como: Segurança (JWT Oauth2), upload de arquivos, envio de mails, caches, HATEOAS, documentação com Swagger, testes de integração, projeções com JPA, DDD, Domain Events, Oauth2 para segurança, migração e versionamento de base dados (Flyway), e muito mais!

## Desenvolvido com
* [Spring Framework](https://spring.io/projects/spring-framework)
* [Spring Data](https://spring.io/projects/spring-data)
* [Spring Security](https://spring.io/projects/spring-security)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [MySQL](https://www.mysql.com/)
* [Lombok](https://projectlombok.org/)
* [Flyway](https://flywaydb.org/)
* [ModelMapper](http://modelmapper.org/)
* [Jasper Reports](https://community.jaspersoft.com/)
* [AWS JDK S3](https://docs.aws.amazon.com/sdk-for-java/index.html)

## Execução Local
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
2. Abra em sua IDE preferida
3. Inicie o ambiente de desenvolvimento com o docker
   ```sh
   docker-compose up -d
   ```
4. Execute a aplicação em sua IDE
    - Iniciar o método main da classe [AlgafoodApiApplication](./src/main/java/com/algaworks/algafood/AlgafoodApiApplication.java)

### Diagrama de Classes
![alt Diagrama de Classes do projeto](./doc/ESR%20-%20Diagrama%20de%20classes.png)