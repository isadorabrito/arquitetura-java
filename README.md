# Sistema de Gerenciamento de Doações

Sistema desenvolvido para gerenciar doações, doadores e voluntários, facilitando o processo de doações e sua distribuição.

## Sobre o Projeto

Este é um sistema de gerenciamento de doações desenvolvido em Java com Spring Boot, que permite:

- Cadastro e gerenciamento de doadores
- Cadastro e gerenciamento de voluntários
- Registro e acompanhamento de doações
- Busca de doações por diferentes critérios
- Geração de relatórios estatísticos

## Funcionalidades Principais

- **Gestão de Doadores:**
  - Cadastro completo com validação de dados
  - Histórico de doações por doador
  - Busca de doadores por diferentes critérios

- **Gestão de Voluntários:**
  - Cadastro de voluntários com informações completas
  - Controle de doações recebidas/distribuídas
  - Registro de atividades

- **Gestão de Doações:**
  - Registro detalhado de doações
  - Diferentes tipos de doações suportados
  - Acompanhamento do status da doação
  - Busca avançada por múltiplos critérios

## Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- H2 Database
- Bean Validation
- Lombok

## Executando o Projeto

Para executar o projeto, você precisa ter o Java 17 instalado. Em seguida:

1. Clone o repositório
2. Execute: `./mvnw spring-boot:run`
3. Acesse: `http://localhost:8080`

## API Endpoints

### Doações
- `GET /api/v1/donations` - Lista todas as doações
- `POST /api/v1/donations` - Registra uma nova doação
- `GET /api/v1/donations/{id}` - Busca uma doação específica
- `PUT /api/v1/donations/{id}` - Atualiza uma doação
- `DELETE /api/v1/donations/{id}` - Remove uma doação
- `GET /api/v1/donations/search?start=&end=` - Busca doações por período
- `GET /api/v1/donations/types/{type}` - Lista doações por tipo
- `GET /api/v1/donations/donors/{donorId}` - Lista doações de um doador
- `GET /api/v1/donations/volunteers/{volunteerId}` - Lista doações por voluntário

### Doadores
- `GET /api/v1/donors` - Lista todos os doadores
- `POST /api/v1/donors` - Cadastra um novo doador
- `GET /api/v1/donors/{id}` - Busca um doador específico
- `PUT /api/v1/donors/{id}` - Atualiza um doador
- `DELETE /api/v1/donors/{id}` - Remove um doador
- `PATCH /api/v1/donors/{id}/deactivate` - Desativa um doador

### Voluntários
- `GET /api/v1/volunteers` - Lista todos os voluntários
- `POST /api/v1/volunteers` - Cadastra um novo voluntário
- `GET /api/v1/volunteers/{id}` - Busca um voluntário específico
- `PUT /api/v1/volunteers/{id}` - Atualiza um voluntário
- `DELETE /api/v1/volunteers/{id}` - Remove um voluntário
- `PATCH /api/v1/volunteers/{id}/address` - Atualiza o endereço de um voluntário