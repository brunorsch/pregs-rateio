# Pregs Rateio - Backend

Este repositório contém o backend do Pregs Rateio. Este README explica a estrutura do projeto, onde estão os
principais pacotes e convenções utilizadas.

## Visão geral

- Tecnologia principal: Java + Spring Boot
- Build: Gradle
- Estrutura principal do código-fonte: `src/main/java` e `src/main/resources`
- Testes: `src/test/java`

## Estrutura do repositório

Na raiz:
- `build.gradle.kts` - script de build do Gradle (Kotlin DSL).
- `docker-compose.yml` - para executar dependências para desenvolvimento local.

Diretório `src/main/java/br/app/pregsrateio/` (pacote base):

- `PregsrateioApplication.java` - classe principal que inicializa a aplicação Spring Boot.

Pacote `common` - utilitários e configurações transversais:
- `aop` - aspectos e utilitários AOP.
- `auditoria` - classes relacionadas à auditoria (eventos, histórico e auditoria Spring Security).
- `config` - configurações da aplicação (Auditing, Cache, Security, Swagger).
- `erros` - tratamento de erros e exceções globais.
- `mapper` - mappers comuns
- `security` - classes de segurança.
- `validation` - validadores customizados (ex.: validação de ID Mongo, validação de telefone).

Pacotes das features (ex: `rateio`, `usuario`):
- `controller` - mapeamento de endpoints REST.
- `data` - entidades / repositórios (ex.: `Rateio`, `RateioRepository`).
- `service` - serviços que implementam a lógica de negócio (ex.: `CrudRateioService`).

Configurações (Pasta `src/main/resources/`):
- `application.yml` - arquivo de configuração principal.
- `application-dev.yml`, `application-prod.yml` - perfis de ambiente (dev/prod).
- `logback-spring.xml` - configuração de logging.

Testes:
- `src/test/java` - testes da aplicação (ex.: `PregsrateioApplicationTests.java`).

## Perfis e variáveis de ambiente

A aplicação usa perfis Spring definidos em `application-*.yml`. Para ativar um perfil localmente, exporte a 
variável `SPRING_PROFILES_ACTIVE` ou passe `-Dspring.profiles.active=` como argumento JVM.

Exemplo:
```sh
SPRING_PROFILES_ACTIVE=dev ./gradlew bootRun
```

Configurações sensíveis (ex.: credenciais, URIs de serviços) devem ser definidas via variáveis de ambiente ou um 
mecanismo seguro de gestão de segredos; evite commitar segredos em `application-*.yml`.