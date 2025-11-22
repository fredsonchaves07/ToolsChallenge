# Tools Challenge - Pagamentos - API

[![CD Tools Challenge API](https://github.com/fredsonchaves07/ToolsChallenge/actions/workflows/cd-workflow.yml/badge.svg?branch=main)](https://github.com/fredsonchaves07/ToolsChallenge/actions/workflows/cd-workflow.yml)
## 📌 Conteúdo

- [Sobre](#-sobre)
- [Tecnologias](#-tecnologias)
- [Funcionalidades](#-funcionalidades)
- [Instalação e configuração](#%EF%B8%8F-instalação-e-configuração)
    - [Executando com Docker](#executando-com-docker)
    - [Instalação de Dependências](#instalação-de-dependências)
    - [Executando Testes](#executando-os-testes-da-aplicação)
- [Executando a aplicação](#%EF%B8%8F-executando-a-aplicação)
- [Documentação](#-documentação)
- [Exemplos de Requisições](#-exemplos-de-requisições)
- [Bugs](#-bugs)
- [Contribuição](#-contribuições)
- [Licença](#%EF%B8%8F-licença)

## 🚀 Sobre

Este repositório contém o código-fonte da **API RESTful de gerenciamento de pagamentos e estornos da Tools Challenge**, desenvolvida com foco em boas
práticas de design e arquitetura de software. A API segue os princípios REST e foi construída com **TDD (Test-Driven
Development)**, garantindo cobertura de testes desde a regra de negócio até os endpoints públicos.

A API foi desenvolvida utilizando arquitetura em camadas e design orientado a DDD e padrão Either

🔗 A aplicação também está disponível em ambiente cloud:
> 🌐 https://tools-challenge-api-pagamentos.onrender.com/

## 📝 Análise e Documento de Requisitos

Foi realizada uma **análise detalhada dos requisitos funcionais e não funcionais da API**.
Esse processo permitiu mapear os fluxos essenciais de pagamento, estorno e validações de domínio, garantindo clareza e alinhamento desde o início.
- [Documento de requisitos](https://docs.google.com/document/d/1edx0KnsOvAxLu3ETbqZSVCzbQgYpWxDdV-xTB_hDkR4/edit?usp=sharing)

Este documento serviu como base para:
- Definição das regras de negócio;
- Identificação das entidades e agregados;
- Fluxos principais;
- Estrutura dos retornos no padrão RFC 7807.

## 🏗️ Organização das Atividades com Metodologias Ágeis

Durante o desenvolvimento foi utilizado um fluxo inspirado em Scrum/Kanban realizada através do [GitHub Projects](https://github.com/users/fredsonchaves07/projects/3/views/1)

O quadro foi organizado com colunas como:
- `Backlog`
- `To Do`
- `In Progress`
- `On Hold`
- `Review`
- `Next Release`
- `Done`

Além disso, todas as tarefas estavam vinculadas aos PRs respectivos, garantindo um fluxo claro e auditável.

## 🔄 CI/CD — Integração Contínua e Entrega Contínua
A API foi totalmente automatizada utilizando GitHub Actions com pipelines de CI/CD.

O pipeline executa automaticamente em cada pull request ou push nas branches developer e main.

As etapas incluem:
- Merge automático da branch de destino → PR (com detecção de conflitos)
- Build da aplicação em JDK 21 
- Cache inteligente de dependências Maven 
- Execução de testes unitários com agregação de relatórios 
- Build da imagem Docker 
- Subida de containers para testes end-to-end 
- Análise de vulnerabilidades com Docker Scout 
- Comentários em PR em casos de erros

## 💻 Tecnologias

- [Java 21](https://adoptium.net/temurin/releases/)
- [Maven 3.9.8](https://maven.apache.org/download.cgi)
- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
- [Quarkus](https://pt.quarkus.io/)
- [H2Database](https://www.h2database.com/)
- [Postgres](https://www.postgresql.org/)
- [Flyway](https://www.red-gate.com/products/flyway/community/)
- [JUnit5](https://junit.org/junit5/)
- [Swagger](https://swagger.io/specification/)

## ✨ Funcionalidades

- ✅ Realização e consulta de pagamentos e estornos
- ✅ Validações de domínio aplicadas à entidade `Transacao`, como:
    - Não é possível cadastrar uma transação com status, forma de pagamento, cartão, descrição de valor, dataHora, estabelecimento, nsu, código de autorização, status com dados vazios ou nulos;
    - O número do cartão de crédito não pode ultrapassar 16 dígitos sendo tipo numérico;
    - Nome do estabelecimento não pode ultrapassar 255 caracteres; 
    - O código nsu deve ser do tipo numérico de 10 dígitos; 
    - O código de autorização deve ser do tipo numérico de 9 dígitos; 
    - Não é possível informar quantidade de parcelas com valor 0 ou negativo
- ✅ Retorno de erros no
  formato [RFC 7807 - Problem Details for HTTP APIs](https://datatracker.ietf.org/doc/html/rfc7807)
- ✅ Redirecionamento automático para documentação Swagger
- ✅ Implementação com **TDD**, com testes unitários em todas as camadas
- ✅ CI/CD automatizado utilizando Actions do Github
- ✅ Deploy contínuo com Docker + Render (Cloud)

## 🛠️ Instalação e configuração

Para executar o projeto em ambiente de desenvolvimento, certifique-se de ter as ferramentas listadas na
seção [tecnologias](#-tecnologias) instaladas.

### Executando com Docker

```bash
docker compose up --build
```

Acesse a API pela URL [localhost:8080](localhost:8080).

### Instalação de dependências

```bash
mvn clean install -DskipTests
```

### Executando os testes da aplicação

Todos os testes foram implementados utilizando a abordagem TDD.

```bash
mvn test
```

## ⚙️ Executando a aplicação

Altere as configurações, adicionando as credenciais do seu banco de dados no arquivo `.env`.
Verifique o arquivo exemplo [.env-example](https://github.com/fredsonchaves07/ToolsChallenge/blob/main/.env-example).

Esta aplicação utiliza o Flyway como ferramenta de migração de banco de dados.
Os scripts estão localizados [aqui](https://github.com/fredsonchaves07/ToolsChallenge/tree/main/src/main/resources/db/migration).

Após a instalação das dependências, execute o script abaixo, localizado na raiz do projeto:

```bash
./run-dev.sh
```

Para ambientes Windows, será necessário executar o comando abaixo

```bash
mvn quarkus:dev -Ddebug
```

Ou se preferir, via .jar:

```bash
java -jar target/quarkus-app/quarkus-run.jar 
```

Acesse:

- API: [localhost:8080](http://localhost:8080)
- Swagger: [localhost:8080/docs](http://localhost:8080/docs)

## 📝 Documentação

A API conta com documentação completa via Swagger/OpenAPI.

📄 Swagger UI: http://localhost:8080/docs



## 📦 Exemplos de Requisições

### 🔸 Realizar pagamento

**POST** /pagamentos

```json
{
  "numeroCartao": "1928391049583094",
  "descricaoOperacao": {
    "valor": 50.00,
    "estabelecimento": "PetShop",
    "dataHora": "01/05/2021 18:30:00"
  },
  "formaPagamento": {
    "tipoFormaPagamento": "AVISTA",
    "parcelas": 1
  }
}
```

### 🔸 Realizar estorno

**POST** /estornos

```json
{
  "numeroCartao": "1928391049583094",
  "descricaoOperacao": {
    "valor": 50.00,
    "estabelecimento": "PetShop",
    "dataHora": "01/05/2021 18:30:00"
  },
  "formaPagamento": {
    "tipoFormaPagamento": "AVISTA",
    "parcelas": 1
  }
}
```
### 🔸 Consultar um pagamento

**GET** /pagamentos/{ID}

### 🔸 Consultar um estorno

**GET** /estornos/{ID}

### 🔸 Consultar todos pagamentos

**GET** /pagamentos/

### 🔸 Consultar todos estornos

**GET** /estornos/


## 🐛 Bugs

Se encontrar algum bug ou comportamento inesperado:

Abra uma [issue](https://github.com/fredsonchaves07/api-product-manager/issues)

Ou envie uma PR com a sugestão de correção 🚀

## 🤝 Contribuições

Contribuições são sempre bem-vindas! 💙
Abra uma PR com sugestões de melhoria ou novos recursos.

## ⚖️ Licença

Este projeto utiliza licensa de código aberto, permitindo cópia ou qualquer distribuição sem autorização.

---
Developed 💙 by Fredson Chaves