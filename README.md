# Baozi Store - API REST

API REST desenvolvida em Java com Spring Boot, Spring Data JPA e banco relacional H2, seguindo a arquitetura MVC do Spring, como Atividade Prática da disciplina **Desenvolvimento Web Back-End** (UNINTER).

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 4.2.0** (Spring Web MVC + Spring Data JPA)
- **H2 Database** (banco relacional em memória com console H2 habilitado)
- **Maven** (Maven Wrapper incluído)
- **Postman** (testes dos endpoints REST)

## Arquitetura do Projeto

- `com.baozistore.model`: Entidades JPA (`Cliente`, `Produto`, `Pedido`)
- `com.baozistore.repository`: Repositórios Spring Data JPA (`ClienteRepository`, `ProdutoRepository`, `PedidoRepository`)
- `com.baozistore.controller`: Controladores REST (`ClienteController`, `ProdutoController`, `PedidoController`)

## Endpoints REST

| Entidade | Método | Endpoint | Descrição |
| --- | --- | --- | --- |
| Cliente | `POST` | `/clientes` | Cadastrar cliente |
| Cliente | `GET` | `/clientes` | Listar todos os clientes |
| Cliente | `GET` | `/clientes/{id}` | Consultar cliente por ID |
| Cliente | `PUT` | `/clientes/{id}` | Atualizar cliente por ID |
| Cliente | `DELETE` | `/clientes/{id}` | Apagar cliente por ID |
| Produto | `POST` | `/produtos` | Cadastrar produto |
| Produto | `GET` | `/produtos` | Listar todos os produtos |
| Produto | `GET` | `/produtos/{id}` | Consultar produto por ID |
| Produto | `PUT` | `/produtos/{id}` | Atualizar produto por ID |
| Produto | `DELETE` | `/produtos/{id}` | Apagar produto por ID |
| Pedido | `POST` | `/pedidos` | Registrar pedido |
| Pedido | `GET` | `/pedidos` | Listar todos os pedidos |
| Pedido | `GET` | `/pedidos/{id}` | Consultar pedido por ID |
| Pedido | `PUT` | `/pedidos/{id}` | Atualizar pedido por ID |
| Pedido | `DELETE` | `/pedidos/{id}` | Apagar pedido por ID |

## Como Executar

```bash
# Executar testes automatizados
./mvnw test

# Iniciar a aplicação (porta 8080)
./mvnw spring-boot:run
```

O console do banco H2 fica disponível em `http://localhost:8080/h2-console` (`jdbc:h2:mem:baozistore`, usuário `sa`, senha vazia).

A coleção do Postman encontra-se no arquivo `baozi-store.postman_collection.json`.
