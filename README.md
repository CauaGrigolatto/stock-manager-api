# 📦 Stock Manager API

Uma API REST leve em Spring Boot para gerenciar itens e categorias de estoque. Construída com Java e Spring Data JPA. Focada em clareza, extensibilidade e desenvolvimento local rápido.

---

## ✨ DESTAQUES

- Endpoints REST para itens e categorias
- Filtragem, paginação, filtros por data e quantidade
- DTOs e mappers para separar os modelos de API e persistência
- Consultas baseadas em `Specification` para buscas flexíveis

---

## 🚀 QUICK START

### ✅ Requisitos

-   Java 17+ (variável de projeto; Java 21 recomendado)
-   Maven 3.6+
-   MySQL

### 🛠️ Build

```bash
mvn clean package
```

### ▶️ Run

```bash
mvn spring-boot:run
```

ou

```bash
java -jar target/stock-manager-api-*.jar
```

### ⚙️ Configure o banco de dados

Edite `src/main/resources/application.properties` ou use variáveis de ambiente.

**Exemplo:**

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/stock_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

---

## 🌐 ENDPOINTS (visão geral)

### Principais controllers:

-   `ItemsController` — CRUD para itens de estoque, com filtragem e paginação
-   `CategoriesController` — CRUD para categorias

### Notas de comportamento:

-   `POST` deve retornar `201 Created`
-   `GlobalExceptionHandler` centraliza a validação e as respostas de erro
-   Use DTOs nos payloads de requisição/resposta

---

## 📂 ESTRUTURA DO PROJETO

`src/main/java/br/com/codebeans/stockapi`

-   `controller/` — Controladores REST
-   `service/` — Lógica de negócio (limites transacionais)
-   `repository/` — Repositórios Spring Data JPA
-   `model/dto` — DTOs de requisição/resposta e filtros
-   `model/entity` — Entidades JPA
-   `model/mapper/` — Mapeamento entre DTOs e entidades
-   `model/specifications/` — JPA Specifications para filtros avançados

---

## 🤝 CONTRIBUINDO

1.  Faça um Fork do repositório → crie uma branch → abra um PR
2.  Rode os testes localmente antes de abrir o PR
3.  Mantenha as mudanças focadas e documentadas

---

## 📜 LICENÇA

MIT

