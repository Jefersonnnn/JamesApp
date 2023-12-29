# Aplicação de Gerenciamento de Pagamentos de Pessoas (Vulgo JamesApp)

Esta é uma aplicação com o objetivos de gerenciar pagamentos de pessoas para grupos (boloões por exemplo), 
onde um usuário recebe transferencias de cada pessoa, e esta pessoa pode participar de vários grupos.

## Tecnologias Utilizadas

- Linguagem: Java 17
- Framework: Spring Boot 3.1.4
- Banco de Dados: MySQL
- Bibliotecas Adicionais:

## Principais Funcionalidades

- Registro de usuários
- Registro de clientes (pessoas).
- Criação e gerenciamento de grupos.
- Registro de transações pelos clientes.
- Atualização automática do saldo dos clientes com base nas transações.
- Fechamento dos grupos e registro de participantes pelo periodo definido.

## Principais Rotas

### Auth
- **POST /api/auth/register:** Cria um novo usuário
- **POST /api/auth/login:** Realiza login retornando um token de acesso
### Users
- **GET /api/me:** Retorna o usuário atual
- **GET /api/users:** Retorna a lista de todos os usuários
- **GET /api/users/{id}:** Retorna o usuário do id
### Customers
- **POST /api/customers:** Cria um novo cliente
- **GET /api/customers:** Retorna a lista de todos os cliente.
- **GET /api/customers/{id}:** Retorna o saldo do cliente
### Group Bills
- **GET /api/groupbills:** Retorna a lista de todos os grupos.
- **POST /api/groupsbills:** Cria um novo grupo.
### Transactions
- **GET /api/transactions:** Retorna a lista de todas as transações.
- **POST /api/transactions:** Registra uma nova transação.

## Documentação das Rotas

A documentação detalhada das rotas e seus parâmetros pode ser encontrada [aqui](URL_DA_DOCUMENTAÇÃO).

## Configuração do Banco de Dados

Para armazenar informações de clientes, grupos, faturas e transações. 
Certifique-se de configurar as propriedades de conexão do banco de dados nos arquivos `application-dev.properties` e/ou `application-prod.properties` (é necessário mudar o profile no arquivo `application.properties`) .

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/nome_do_banco
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

## Como Executar
1. Clone o repositório para sua máquina.
2. Configure as propriedades do banco de dados no arquivo application.properties.
3. Execute a aplicação com o comando ./mvnw spring-boot:run (Maven Wrapper) 
ou mvn spring-boot:run (se você tiver o Maven instalado).
4. Acesse a API em http://localhost:8080.
