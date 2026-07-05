# LojaApp

Projeto básico utilizando ApiRest.

## Stack

- Spring Boot 3.3.5
- Java 21
- MySQL 8.x (runtime)
- H2 in-memory (testes)

## Banco de dados

A aplicação usa **MySQL** em runtime. A `application.properties` lê a configuração via variáveis de ambiente com defaults:

| Variável         | Default     |
|------------------|-------------|
| `MYSQL_HOST`     | `localhost` |
| `MYSQL_PORT`     | `3306`      |
| `MYSQL_DATABASE` | `lojaapp`   |
| `MYSQL_USER`     | `root`      |
| `MYSQL_PASSWORD` | *(vazio)*   |

O schema é recriado a cada boot (`spring.jpa.hibernate.ddl-auto=create-drop`) e os dados de seed são carregados a partir de `src/main/resources/data.sql` (`spring.sql.init.mode=always`, com `spring.jpa.defer-datasource-initialization=true`).

### Subindo MySQL local com Docker

```bash
docker run -d --name lojaapp-mysql \
  -e MYSQL_ROOT_PASSWORD=root \
  -e MYSQL_DATABASE=lojaapp \
  -p 3306:3306 \
  mysql:8.4
```

### Rodando a aplicação

```bash
MYSQL_PASSWORD=root ./mvnw spring-boot:run
```

### Rodando os testes

Os testes usam **H2 em memória** (modo MySQL) — não exigem MySQL rodando:

```bash
./mvnw test
```

## IDs

Todas as entidades usam `UUID` como chave primária (`@GeneratedValue(strategy = GenerationType.UUID)`), armazenados como `CHAR(36)`.

## Header Authorization

A aplicação não usa Spring Security. Existe um filtro simples (`AuthorizationHeaderFilter`) que apenas lê o header `Authorization` da requisição e a deixa seguir adiante.
