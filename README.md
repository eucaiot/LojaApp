# LojaApp

API REST de uma loja virtual construída com **Spring Boot 2.3.3**, **Spring Security**, **Spring Data JPA**, **H2 Database** e **JWT**.

---

## Sumário

- [Visão Geral](#visão-geral)
- [Tecnologias](#tecnologias)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Modelo de Domínio](#modelo-de-domínio)
- [Enumeradores](#enumeradores)
- [Endpoints da API](#endpoints-da-api)
  - [Index](#index)
  - [Categorias](#categorias)
  - [Produtos](#produtos)
  - [Clientes](#clientes)
  - [Pedidos](#pedidos)
- [Validações](#validações)
- [Tratamento de Erros](#tratamento-de-erros)
- [Segurança](#segurança)
- [Banco de Dados](#banco-de-dados)
- [Dados Iniciais (Seed)](#dados-iniciais-seed)
- [Como Executar](#como-executar)

---

## Visão Geral

O LojaApp é uma API RESTful que gerencia:

| Recurso      | Descrição                                            |
|--------------|------------------------------------------------------|
| Categorias   | Agrupamento de produtos (ex.: Informática, Móveis)   |
| Produtos     | Itens à venda, vinculados a uma ou mais categorias   |
| Clientes     | Pessoas físicas ou jurídicas, com endereços e perfis  |
| Pedidos      | Compras realizadas por clientes, com itens e pagamento|
| Pagamentos   | Boleto ou Cartão de Crédito, vinculados a um pedido   |

---

## Tecnologias

| Tecnologia                      | Versão / Detalhes                        |
|---------------------------------|------------------------------------------|
| Java                            | 11                                       |
| Spring Boot                     | 2.3.3.RELEASE                            |
| Spring Web (starter-web)        | REST controllers                         |
| Spring Data JPA (starter-data-jpa) | ORM + Repositories                   |
| Spring Security (starter-security) | Autenticação / Autorização            |
| Spring Validation (starter-validation) | Bean Validation (javax.validation) |
| H2 Database                     | Banco em arquivo (`~/test`)              |
| JJWT                            | 0.9.1 (geração/validação de tokens JWT)  |
| Jackson                         | Serialização JSON com suporte a herança  |
| Maven Wrapper                   | Build e gerenciamento de dependências    |

---

## Estrutura do Projeto

```
src/main/java/br/com/springboot/lojaapp/
├── LojaAppApplication.java            # Classe principal + CommandLineRunner (seed de dados)
├── config/
│   ├── JacksonConfig.java             # Registro de subtipos de Pagamento no ObjectMapper
│   └── SecurityConfigurations.java    # Configurações de segurança (CORS, CSRF, rotas públicas)
├── controller/
│   ├── IndexController.java           # GET / → retorna "index"
│   ├── CategoriaController.java       # CRUD de Categorias
│   ├── ProdutoController.java         # Busca de Produtos (por id e filtro)
│   ├── ClienteController.java         # CRUD de Clientes
│   ├── PedidoController.java          # Busca e criação de Pedidos
│   └── exception/
│       ├── HandlerExceptionController.java  # @RestControllerAdvice — tratamento global de exceções
│       ├── ErroPadrao.java                  # DTO de erro padrão (status, momento, timestamp, mensagem)
│       ├── ErroValidacao.java               # Herda ErroPadrao + lista de CampoComErro
│       └── CampoComErro.java               # Par (campo, mensagem) para erros de validação
├── dto/
│   ├── CategoriaDto.java              # DTO para Categoria (id, nome) — com validação
│   ├── ProdutoDto.java                # DTO para Produto (id, nome, preco)
│   ├── ClienteDto.java                # DTO para atualização de Cliente (id, nome, email) — com validação
│   └── ClienteNewDto.java             # DTO para criação de Cliente (dados pessoais + endereço + telefones)
├── model/
│   ├── Categoria.java                 # Entidade JPA — ManyToMany com Produto
│   ├── Produto.java                   # Entidade JPA — ManyToMany com Categoria, OneToMany com ItemPedido
│   ├── Cliente.java                   # Entidade JPA — perfis, telefones, endereços, pedidos
│   ├── Endereco.java                  # Entidade JPA — ManyToOne com Cliente e Cidade
│   ├── Cidade.java                    # Entidade JPA — ManyToOne com Estado
│   ├── Estado.java                    # Entidade JPA — OneToMany com Cidade
│   ├── Pedido.java                    # Entidade JPA — OneToOne com Pagamento, ManyToOne com Cliente/Endereco
│   ├── Pagamento.java                 # Entidade JPA abstrata (herança JOINED) — OneToOne com Pedido
│   ├── PagamentoComBoleto.java        # Pagamento por boleto (dataVencimento, dataPagamento)
│   ├── PagamentoComCartao.java        # Pagamento por cartão (numeroDeParcelas)
│   ├── ItemPedido.java                # Entidade JPA — chave composta (EmbeddedId)
│   ├── ItemPedidoPk.java              # Chave composta @Embeddable (pedido_id + produto_id)
│   └── enums/
│       ├── TipoCliente.java           # PESSOA_FISICA(100) | PESSOA_JURIDICA(200)
│       ├── EstadoPagamento.java        # PENDENTE(101) | QUITADO(201) | CANCELADO(301)
│       └── PerfilUsuario.java          # ADMIN(100, ROLE_ADMIN) | CLIENTE(200, ROLE_CLIENTE)
├── repository/
│   ├── CategoriaRepository.java       # JpaRepository<Categoria, Integer>
│   ├── ProdutoRepository.java         # JpaRepository + query customizada buscaFiltrada
│   ├── ClienteRepository.java         # JpaRepository + findByEmail
│   ├── PedidoRepositoty.java          # JpaRepository<Pedido, Integer>
│   ├── PagamentoRepository.java       # JpaRepository<Pagamento, Integer>
│   ├── ItemPedidoRepository.java      # JpaRepository<ItemPedido, Integer>
│   ├── EnderecoRepository.java        # JpaRepository<Endereco, Integer>
│   ├── CidadeRepository.java          # JpaRepository<Cidade, Integer>
│   └── EstadoRepository.java          # JpaRepository<Estado, Integer>
├── service/
│   ├── CategoriaService.java          # Lógica de negócio de Categorias
│   ├── ProdutoService.java            # Lógica de negócio de Produtos (busca filtrada)
│   ├── ClienteService.java            # Lógica de negócio de Clientes
│   ├── PedidoService.java             # Lógica de negócio de Pedidos (criação transacional)
│   ├── PagamentoComBoletoService.java # Atribuição de data de vencimento para boletos
│   ├── exception/
│   │   ├── ObjectNotFoundException.java   # Lançada quando entidade não é encontrada
│   │   └── DataIntegrityException.java    # Lançada quando deleção viola integridade referencial
│   ├── utils/
│   │   └── URI.java                       # Utilitário para decode de strings e parse de listas de IDs
│   └── validation/
│       ├── InserirCliente.java            # Annotation customizada para validação na criação
│       ├── InserirClienteValidator.java   # Valida CPF/CNPJ + email único na criação
│       ├── AtualizarCliente.java          # Annotation customizada para validação na atualização
│       ├── AtualizarClienteValidator.java # Valida email único na atualização (ignora o próprio)
│       └── ValidaCpfCnpj.java             # Algoritmo de validação de CPF e CNPJ
```

---

## Modelo de Domínio

### Relacionamentos entre Entidades

```
Estado 1───* Cidade
Cidade *───1 Endereco
Cliente 1───* Endereco
Cliente 1───* Pedido
Cliente *───* Telefone (ElementCollection)
Cliente *───* Perfil  (ElementCollection)
Pedido 1───1 Pagamento (herança: PagamentoComBoleto | PagamentoComCartao)
Pedido 1───* ItemPedido
Produto 1───* ItemPedido
Produto *───* Categoria (tabela intermediária: produto_categoria)
```

### Detalhes das Entidades

#### Categoria
| Campo     | Tipo          | Detalhes                              |
|-----------|---------------|---------------------------------------|
| id        | Integer       | PK, auto-gerado (IDENTITY)            |
| nome      | String        | Nome da categoria                     |
| produtos  | List\<Produto\> | ManyToMany (mappedBy = "categorias") |

#### Produto
| Campo      | Tipo              | Detalhes                                        |
|------------|-------------------|-------------------------------------------------|
| id         | Integer           | PK, auto-gerado (IDENTITY)                      |
| nome       | String            | Nome do produto                                  |
| preco      | Double            | Preço unitário                                   |
| categorias | List\<Categoria\> | ManyToMany (tabela `produto_categoria`)          |
| pedidos    | Set\<ItemPedido\> | OneToMany (mappedBy = "id.produto") — @JsonIgnore|

#### Cliente
| Campo       | Tipo              | Detalhes                                       |
|-------------|-------------------|-------------------------------------------------|
| id          | Integer           | PK, auto-gerado (IDENTITY)                      |
| nome        | String            | Nome completo                                    |
| email       | String            | E-mail do cliente                                |
| senha       | String            | Senha criptografada (BCrypt) — @JsonIgnore       |
| cpf_Cnpj    | String            | CPF ou CNPJ — `@Column(unique = true)`           |
| tipoCliente | Integer           | Código do enum `TipoCliente` (100 ou 200)        |
| enderecos   | List\<Endereco\>  | OneToMany (cascade ALL)                          |
| telefones   | Set\<String\>     | ElementCollection (tabela `telefone`)            |
| perfis      | Set\<Integer\>    | ElementCollection (tabela `Perfis`)              |
| pedidos     | List\<Pedido\>    | OneToMany — @JsonIgnore                          |

#### Endereco
| Campo       | Tipo    | Detalhes                              |
|-------------|---------|---------------------------------------|
| id          | Integer | PK, auto-gerado (IDENTITY)            |
| logradouro  | String  | Rua/Avenida                           |
| numero      | Integer | Número                                |
| complemento | String  | Complemento (opcional)                |
| bairro      | String  | Bairro                                |
| cep         | String  | CEP                                   |
| cliente     | Cliente | ManyToOne — @JsonIgnore               |
| cidade      | Cidade  | ManyToOne                             |

#### Cidade / Estado
| Cidade   |              | Estado   |              |
|----------|--------------|----------|--------------|
| id       | Integer (PK) | id       | Integer (PK) |
| nome     | String       | nome     | String       |
| estado   | ManyToOne    | cidades  | OneToMany (@JsonIgnore) |

#### Pedido
| Campo             | Tipo              | Detalhes                                     |
|-------------------|-------------------|----------------------------------------------|
| id                | Integer           | PK, auto-gerado (IDENTITY)                    |
| instante          | LocalDateTime     | Data/hora do pedido — formato `dd/MM/yyyy HH:mm` |
| pagamento         | Pagamento         | OneToOne (cascade ALL)                         |
| cliente           | Cliente           | ManyToOne                                      |
| enderecoDeEntrega | Endereco          | ManyToOne                                      |
| itens             | Set\<ItemPedido\> | OneToMany (mappedBy = "id.pedido")             |
| valorTotal        | Double            | Calculado: soma dos subtotais dos itens        |

#### Pagamento (abstrata)
| Campo           | Tipo            | Detalhes                                   |
|-----------------|-----------------|--------------------------------------------|
| id              | Integer         | PK (mesmo id do Pedido — @MapsId)          |
| estadoPagamento | Integer         | Código do enum `EstadoPagamento`           |
| pedido          | Pedido          | OneToOne — @JsonIgnore                     |

Usa herança do tipo **JOINED** e polimorfismo JSON via `@JsonTypeInfo` (propriedade `@type`).

##### PagamentoComBoleto (`@type = "PagamentoComBoleto"`)
| Campo          | Tipo      | Detalhes                                        |
|----------------|-----------|-------------------------------------------------|
| dataVencimento | LocalDate | Formato `dd/MM/yyyy` — ajustada se cai no fim de semana |
| dataPagamento  | LocalDate | Formato `dd/MM/yyyy` — null se ainda não pago   |

**Regra de negócio**: se o vencimento cair no sábado, move para segunda (+2 dias); se domingo, move para segunda (+1 dia).

##### PagamentoComCartao (`@type = "pagamentoComCartao"`)
| Campo            | Tipo    | Detalhes            |
|------------------|---------|---------------------|
| numeroDeParcelas | Integer | Número de parcelas  |

#### ItemPedido
| Campo      | Tipo           | Detalhes                                    |
|------------|----------------|---------------------------------------------|
| id         | ItemPedidoPk   | Chave composta @EmbeddedId — @JsonIgnore    |
| quantidade | Integer        | Quantidade do produto                       |
| desconto   | Double         | Valor do desconto por unidade               |
| preco      | Double         | Preço unitário no momento da compra          |
| subTotal   | Double         | Calculado: `(preco - desconto) * quantidade`|

#### ItemPedidoPk (chave composta @Embeddable)
| Campo   | Tipo    | Detalhes             |
|---------|---------|----------------------|
| pedido  | Pedido  | ManyToOne (pedido_id) |
| produto | Produto | ManyToOne (produto_id)|

---

## Enumeradores

### TipoCliente
| Valor          | Código | Descrição       |
|----------------|--------|-----------------|
| PESSOA_FISICA  | 100    | Pessoa Física   |
| PESSOA_JURIDICA| 200    | Pessoa Jurídica |

### EstadoPagamento
| Valor     | Código | Descrição |
|-----------|--------|-----------|
| PENDENTE  | 101    | Pendente  |
| QUITADO   | 201    | Quitado   |
| CANCELADO | 301    | Cancelado |

### PerfilUsuario
| Valor   | Código | Authority     | Descrição      |
|---------|--------|---------------|----------------|
| ADMIN   | 100    | ROLE_ADMIN    | Administrador  |
| CLIENTE | 200    | ROLE_CLIENTE  | Cliente        |

---

## Endpoints da API

A aplicação roda na porta padrão **8080**.

### Index

| Método | Rota | Descrição           | Autenticação |
|--------|------|---------------------|--------------|
| GET    | `/`  | Retorna string "index" | Pública   |

---

### Categorias

| Método | Rota               | Descrição                        | Autenticação     |
|--------|---------------------|----------------------------------|------------------|
| GET    | `/categorias`       | Lista todas as categorias (DTO)  | Pública          |
| GET    | `/categorias/{id}`  | Busca categoria por ID           | Pública          |
| GET    | `/categorias/page`  | Lista categorias com paginação   | Pública          |
| POST   | `/categorias`       | Cria nova categoria              | Requer autenticação |
| PUT    | `/categorias/{id}`  | Atualiza categoria existente     | Requer autenticação |
| DELETE | `/categorias/{id}`  | Deleta categoria                 | Requer autenticação |

#### GET `/categorias/page` — Parâmetros de Query

| Parâmetro          | Tipo    | Default | Descrição                    |
|--------------------|---------|---------|------------------------------|
| pagina             | Integer | 0       | Número da página (0-based)   |
| elementosPorPagina | Integer | 5       | Quantidade de itens por página|
| direcao            | String  | ASC     | Direção da ordenação (ASC/DESC)|
| ordenarPor         | String  | nome    | Campo para ordenação         |

#### POST `/categorias` — Body (JSON)

```json
{
  "nome": "Eletrônicos"
}
```
**Validações**: `nome` obrigatório, entre 5 e 80 caracteres.

**Resposta**: `201 Created` com a categoria criada e header `Location`.

#### PUT `/categorias/{id}` — Body (JSON)

```json
{
  "nome": "Novo Nome"
}
```
**Validações**: mesmas do POST.

**Resposta**: `204 No Content`.

#### DELETE `/categorias/{id}`

**Regra**: Não permite deletar categoria que contenha produtos vinculados. Lança `DataIntegrityException` → HTTP 400.

**Resposta**: `200 OK` com body `"Categoria Deletada"`.

---

### Produtos

| Método | Rota              | Descrição                    | Autenticação |
|--------|-------------------|------------------------------|--------------|
| GET    | `/produtos/{id}`  | Busca produto por ID         | Pública      |
| GET    | `/produtos`       | Busca filtrada com paginação | Pública      |

#### GET `/produtos` — Parâmetros de Query

| Parâmetro          | Tipo    | Default | Descrição                                      |
|--------------------|---------|---------|-------------------------------------------------|
| nomeProduto        | String  | ""      | Filtro por nome (LIKE %nome%)                   |
| categoriasIds      | String  | ""      | IDs de categorias separados por vírgula (ex: "1,2,3") |
| pagina             | Integer | 0       | Número da página                                |
| elementosPorPagina | Integer | 20      | Itens por página                                |
| direcao            | String  | ASC     | Direção da ordenação                            |
| ordem              | String  | nome    | Campo para ordenação                            |

**Query JPQL customizada**: faz `INNER JOIN` com categorias e filtra por nome (LIKE) e categorias (IN).

---

### Clientes

| Método | Rota              | Descrição                        | Autenticação     |
|--------|-------------------|----------------------------------|------------------|
| GET    | `/clientes`       | Lista todos os clientes (DTO)    | Pública          |
| GET    | `/clientes/{id}`  | Busca cliente por ID             | Pública          |
| GET    | `/clientes/page`  | Lista clientes com paginação     | Pública          |
| POST   | `/clientes`       | Cria novo cliente                | Requer autenticação |
| PUT    | `/clientes/{id}`  | Atualiza cliente existente       | Requer autenticação |
| DELETE | `/clientes/{id}`  | Deleta cliente                   | Requer autenticação |

#### GET `/clientes/page` — Parâmetros de Query

| Parâmetro          | Tipo    | Default | Descrição                    |
|--------------------|---------|---------|------------------------------|
| pagina             | Integer | 0       | Número da página             |
| elementosPorPagina | Integer | 5       | Itens por página             |
| direcao            | String  | ASC     | Direção da ordenação         |
| ordenarPor         | String  | nome    | Campo para ordenação         |

#### POST `/clientes` — Body (JSON)

```json
{
  "nome": "João da Silva",
  "email": "joao@email.com",
  "senha": "minhaSenha123",
  "cpf_Cnpj": "12345678909",
  "tipoCliente": 100,
  "logradouro": "Rua das Flores",
  "numero": 100,
  "complemento": "apto 10",
  "bairro": "Centro",
  "cep": "12345678",
  "telefone1": "11999999999",
  "telefone2": null,
  "telefone3": null,
  "cidadeId": 1
}
```

**Validações na criação (`@InserirCliente`)**:
- `nome`: obrigatório, entre 5 e 50 caracteres
- `email`: obrigatório, formato de e-mail válido, **deve ser único** no sistema
- `senha`: obrigatório
- `cpf_Cnpj`: obrigatório, validado como CPF (11 dígitos) se `tipoCliente = 100` ou CNPJ (14 dígitos) se `tipoCliente = 200`
- `tipoCliente`: obrigatório (100 = Pessoa Física, 200 = Pessoa Jurídica)
- `telefone1`: obrigatório (telefone2 e telefone3 são opcionais)

A senha é criptografada com **BCryptPasswordEncoder** antes de salvar.

**Resposta**: `201 Created` com o cliente criado e header `Location`.

#### PUT `/clientes/{id}` — Body (JSON)

```json
{
  "nome": "João Atualizado",
  "email": "joao.novo@email.com"
}
```

**Validações na atualização (`@AtualizarCliente`)**:
- `nome`: obrigatório, entre 5 e 120 caracteres
- `email`: obrigatório, formato válido, **deve ser único** (ignora o e-mail do próprio cliente sendo atualizado)

**Resposta**: `204 No Content`.

#### DELETE `/clientes/{id}`

**Regra**: Não permite deletar cliente que possua pedidos. Lança `DataIntegrityException` → HTTP 400.

---

### Pedidos

| Método | Rota            | Descrição            | Autenticação        |
|--------|-----------------|----------------------|---------------------|
| GET    | `/pedidos/{id}` | Busca pedido por ID  | Requer autenticação |
| POST   | `/pedidos`      | Cria novo pedido     | Requer autenticação |

#### POST `/pedidos` — Body (JSON)

```json
{
  "cliente": { "id": 1 },
  "enderecoDeEntrega": { "id": 1 },
  "pagamento": {
    "@type": "pagamentoComCartao",
    "numeroDeParcelas": 10
  },
  "itens": [
    {
      "quantidade": 2,
      "produto": { "id": 3 }
    }
  ]
}
```

Exemplo com boleto:
```json
{
  "cliente": { "id": 1 },
  "enderecoDeEntrega": { "id": 2 },
  "pagamento": {
    "@type": "PagamentoComBoleto"
  },
  "itens": [
    {
      "quantidade": 1,
      "produto": { "id": 1 }
    }
  ]
}
```

**Regras de negócio na criação de Pedido**:
1. O `instante` é definido automaticamente como `LocalDateTime.now()`
2. O estado do pagamento é definido como `PENDENTE`
3. Para **PagamentoComBoleto**: a data de vencimento é calculada como `instante + 2 dias`, e ajustada para segunda-feira se cair no fim de semana
4. O `desconto` de cada item é zerado (definido como `0.0`)
5. O `preco` de cada item é obtido do produto cadastrado no banco (preço vigente)
6. A operação é **@Transactional**

**Resposta**: `201 Created` com header `Location`.

---

## Validações

### Validações via Bean Validation (javax.validation)

| DTO              | Campo        | Regra                                          |
|------------------|--------------|-------------------------------------------------|
| CategoriaDto     | nome         | `@NotEmpty`, `@Length(min=5, max=80)`           |
| ClienteDto       | nome         | `@NotEmpty`, `@Length(min=5, max=120)`          |
| ClienteDto       | email        | `@NotEmpty`, `@Email`                           |
| ClienteNewDto    | nome         | `@NotEmpty`, `@Size(min=5, max=50)`             |
| ClienteNewDto    | email        | `@NotEmpty`, `@Email`                           |
| ClienteNewDto    | senha        | `@NotEmpty`                                     |
| ClienteNewDto    | cpf_Cnpj     | `@NotEmpty`                                     |
| ClienteNewDto    | tipoCliente  | `@NotNull`                                      |
| ClienteNewDto    | telefone1    | `@NotEmpty`                                     |

### Validações Customizadas

| Annotation         | Validator                     | Regras                                                     |
|--------------------|-------------------------------|------------------------------------------------------------|
| `@InserirCliente`  | `InserirClienteValidator`     | CPF válido (se PF), CNPJ válido (se PJ), e-mail único     |
| `@AtualizarCliente`| `AtualizarClienteValidator`   | E-mail único (ignora o próprio cliente)                    |

### Validação de CPF/CNPJ (`ValidaCpfCnpj`)

- **CPF**: deve ter exatamente 11 dígitos, não pode ter todos iguais, dígitos verificadores calculados pelo algoritmo padrão
- **CNPJ**: deve ter exatamente 14 dígitos, não pode ter todos iguais, dígitos verificadores calculados pelo algoritmo padrão

---

## Tratamento de Erros

O `HandlerExceptionController` (`@RestControllerAdvice`) intercepta exceções globalmente:

| Exceção                          | HTTP Status | Corpo da Resposta                                   |
|----------------------------------|-------------|------------------------------------------------------|
| `ObjectNotFoundException`        | 404         | `ErroPadrao` (status, momento, timeStamp, mensagem)  |
| `DataIntegrityException`         | 400         | `ErroPadrao`                                         |
| `MethodArgumentNotValidException`| 400         | `ErroValidacao` (ErroPadrao + lista de CampoComErro) |

### Exemplo de resposta de erro (404)
```json
{
  "status": 404,
  "momento": "14:30:22",
  "timeStamp": 1620000000000,
  "mensagem": "Categoria não encontrada. Id: 99. Tipo: br.com.springboot.lojaapp.model.Categoria"
}
```

### Exemplo de resposta de validação (400)
```json
{
  "status": 400,
  "momento": "14:30:22",
  "timeStamp": 1620000000000,
  "mensagem": "Validation failed...",
  "errosList": [
    { "campo": "nome", "mensagem": "Preenchimento obrigatório" },
    { "campo": "cpf_Cnpj", "mensagem": "CPF invalido" }
  ]
}
```

---

## Segurança

Configurada em `SecurityConfigurations`:

| Rota                  | Método | Acesso               |
|-----------------------|--------|-----------------------|
| `/h2-console/**`      | Todos  | Público (permitAll)   |
| `/categorias/**`      | GET    | Público               |
| `/produtos/**`        | GET    | Público               |
| `/clientes/**`        | GET    | Público               |
| Qualquer outra rota   | Todos  | Requer autenticação   |

Configurações adicionais:
- **CSRF** desabilitado
- **CORS** habilitado
- **Session**: `STATELESS` (sem sessões — preparado para JWT)
- **H2 Console**: liberado via `WebSecurity.ignoring()`

### Perfis de Usuário

Todo cliente recebe automaticamente o perfil `CLIENTE` ao ser criado. O perfil `ADMIN` pode ser atribuído manualmente (veja dados de seed).

---

## Banco de Dados

| Propriedade                | Valor                     |
|----------------------------|---------------------------|
| Tipo                       | H2 (arquivo)              |
| URL                        | `jdbc:h2:file:~/test`     |
| Console                    | `/h2-console`             |
| Username                   | `sa`                      |
| Password                   | *(vazio)*                 |

### Configuração Jackson

O `JacksonConfig` registra os subtipos de `Pagamento` (`PagamentoComCartao` e `PagamentoComBoleto`) no ObjectMapper do Jackson para permitir a desserialização polimórfica via a propriedade `@type` no JSON.

---

## Dados Iniciais (Seed)

A classe `LojaAppApplication` implementa `CommandLineRunner` e popula o banco ao iniciar:

### Categorias (10)
| ID | Nome                  |
|----|-----------------------|
| 1  | Informatica           |
| 2  | Escritorio            |
| 3  | Cama, mesa e banho    |
| 4  | Telefone e celulares  |
| 5  | Eletrodomesticos      |
| 6  | Móveis                |
| 7  | Automotivo            |
| 8  | Bebe e criança        |
| 9  | Esportes              |
| 10 | Decoracao             |

### Produtos (11)
| ID | Nome               | Preço    | Categorias       |
|----|--------------------|----------|-------------------|
| 1  | Computador         | 2000.00  | 1 (Info), 4 (Tel) |
| 2  | Impressora         | 800.00   | 1, 2, 4           |
| 3  | Mouse              | 80.00    | 1, 4              |
| 4  | Mesa de Escritorio  | 300.00   | 2                 |
| 5  | Toalha             | 50.00    | 3                 |
| 6  | Colcha             | 200.00   | 3                 |
| 7  | Tv True Color      | 1200.00  | 4                 |
| 8  | Roçadeira          | 300.00   | 5                 |
| 9  | Abajour            | 100.00   | 6                 |
| 10 | Pendente           | 180.00   | 6                 |
| 11 | Shampoo Automotivo | 90.00    | 7                 |

### Estados e Cidades
| Estado      | Cidades                 |
|-------------|-------------------------|
| Minas Gerais| Uberlandia              |
| São Paulo   | São Paulo, Campinas     |

### Clientes (2)
| ID | Nome        | E-mail              | CPF         | Tipo | Perfil  | Senha (plain) |
|----|-------------|----------------------|-------------|------|---------|---------------|
| 1  | Maria Silva | maria@gmail.com      | 12345678911 | PF   | CLIENTE | SenhaSecreta  |
| 2  | Caio Cesar  | caiocesar@gmail.com  | 35444630087 | PF   | ADMIN   | SenhaSecreta  |

### Pedidos (2) e Pagamentos
| Pedido | Cliente | Endereço | Pagamento                              |
|--------|---------|----------|----------------------------------------|
| 1      | Maria   | 1        | Cartão — QUITADO, 6 parcelas          |
| 2      | Maria   | 2        | Boleto — PENDENTE, venc. em 7 dias     |

### Itens dos Pedidos
| Pedido | Produto     | Qtd | Desconto | Preço   |
|--------|-------------|-----|----------|---------|
| 1      | Computador  | 1   | 0.00     | 2000.00 |
| 1      | Mouse       | 2   | 0.00     | 80.00   |
| 2      | Impressora  | 1   | 100.00   | 800.00  |

---

## Como Executar

### Pré-requisitos
- **Java 11** (JDK)
- **Maven** (ou usar o Maven Wrapper incluso)

### Executando

```bash
# Clonar o repositório
git clone https://github.com/eucaiot/LojaApp.git
cd LojaApp

# Executar com Maven Wrapper
./mvnw spring-boot:run

# Ou compilar e executar o JAR
./mvnw clean package
java -jar target/loja-app-1.0.0.jar
```

A API estará disponível em `http://localhost:8080`.

O console H2 estará disponível em `http://localhost:8080/h2-console`.
