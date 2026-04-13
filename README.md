# Projeto Inicial KMP + Ktor

Template inicial para a atividade com:

- Kotlin Multiplatform (`shared` para modelos e contratos de repositório)
- Ktor no módulo `server`
- Duas rotas CRUD (`drivers` e `race-results`)
- Swagger/OpenAPI
- PostgreSQL via Docker Compose
- Migrations e seed data com Flyway
- Repositórios usando Exposed (sem SQL direto nos repositórios)

## Estrutura principal

- `shared/src/commonMain/kotlin/com/dev/atv1/shared/model`: modelos compartilhados
- `shared/src/commonMain/kotlin/com/dev/atv1/shared/repository`: interfaces de repositório compartilhadas
- `server/src/main/kotlin/com/dev/atv1/db/table`: tabelas Exposed
- `server/src/main/kotlin/com/dev/atv1/repository`: implementação dos repositórios com Exposed
- `server/src/main/kotlin/com/dev/atv1/routes`: rotas Ktor
- `server/src/main/resources/db/migration`: migrations e seed data
- `server/src/main/resources/openapi/documentation.yaml`: especificação OpenAPI

## Pré-requisitos

- JDK 17+
- Docker e Docker Compose

## Configuração do ambiente

1. Criar arquivo `.env` na raiz do projeto a partir do `.env.example`.
2. Ajustar os valores se necessário.

Exemplo:

```env
SERVER_PORT=8080
DB_HOST=localhost
DB_PORT=5432
DB_NAME=atv1
DB_USER=atv1_user
DB_PASSWORD=atv1_pass
```

## Subir banco de dados

```bash
docker compose up -d postgres
```

Verificar status:

```bash
docker compose ps
```

## Rodar o servidor

- Windows:
  ```powershell
  .\gradlew.bat :server:run
  ```
- macOS/Linux:
  ```bash
  ./gradlew :server:run
  ```

Ao iniciar, o Flyway executa automaticamente:


- `V1__create_tables.sql`
- `V2__seed_f1_data.sql`

## Swagger

- Swagger UI: `http://localhost:8080/swagger`
- OpenAPI: `http://localhost:8080/openapi`

## Endpoints

### Drivers

- `GET /drivers`
- `GET /drivers/{id}`
- `POST /drivers`
- `PUT /drivers/{id}`
- `DELETE /drivers/{id}`

### Race Results

- `GET /race-results`
- `GET /race-results/{id}`
- `POST /race-results`
- `PUT /race-results/{id}`
- `DELETE /race-results/{id}`

## Payloads de exemplo

`POST /drivers`

```json
{
  "name": "Lando Norris",
  "team": "McLaren",
  "nationality": "United Kingdom"
}
```

`POST /race-results`

```json
{
  "season": 2026,
  "grandPrix": "Bahrain Grand Prix",
  "position": 1,
  "points": 25,
  "driverId": 1
}
```

## Comandos úteis

Parar containers:

```bash
docker compose down
```

Parar e remover volumes:

```bash
docker compose down -v
```
