[Read in English](README.md)

---

# TaskFlow API (Português)

API RESTful para gerenciamento de tarefas, construída com Spring Boot, Java e PostgreSQL. Este projeto permite aos usuários criar, listar, visualizar detalhes, atualizar e deletar tarefas de forma eficiente.

## Visão Geral

O TaskFlow API é um backend que oferece um conjunto de endpoints para interagir com um sistema de gerenciamento de tarefas. Ele foi desenvolvido como um projeto de aprendizado e portfólio, utilizando boas práticas de desenvolvimento com o ecossistema Spring.

## Funcionalidades Principais

-   [x] Criação de novas tarefas com descrição, status e data de conclusão prevista.
-   [x] Listagem de todas as tarefas cadastradas.
-   [x] Busca de uma tarefa específica pelo seu ID.
-   [x] Atualização completa das informações de uma tarefa existente.
-   [x] Deleção de tarefas.
-   [ ] (Futuro) Validação de dados de entrada.
-   [ ] (Futuro) Tratamento de erros customizado.
-   [ ] (Futuro) Paginação e ordenação para listagem de tarefas.

## Tecnologias Utilizadas

-   **Java:** 21
-   **Spring Boot:** 3.x.x (Ex: 3.3.0 - *Verifique sua versão no `pom.xml`*)
-   **Spring Web:** Para construção de APIs REST.
-   **Spring Data JPA:** Para persistência de dados com o banco.
-   **Hibernate:** Implementação JPA utilizada pelo Spring Data JPA.
-   **PostgreSQL:** Banco de dados relacional (Ex: 16, 17 - *Verifique sua versão*)
-   **Maven:** Gerenciador de dependências e build do projeto.
-   **Lombok:** Para redução de código boilerplate (getters, setters, construtores).
-   **Postman (ou similar):** Para testes da API.

## Pré-requisitos

Antes de começar, garanta que você tem os seguintes softwares instalados em seu sistema:

-   JDK (Java Development Kit) - Versão 21 ou superior.
-   Maven - Versão 3.6 ou superior.
-   PostgreSQL - Servidor de banco de dados PostgreSQL instalado e rodando.
-   Uma IDE de sua preferência (ex: IntelliJ IDEA, Eclipse, VS Code).
-   Postman ou Insomnia (opcional, para testar a API).

## Configuração do Ambiente e Instalação

Siga os passos abaixo para configurar e rodar o projeto localmente:

1.  **Clone o Repositório:**
    ```bash
    git clone <URL_DO_SEU_REPOSITORIO_GITHUB_AQUI>
    cd taskflow # ou o nome da pasta do seu projeto
    ```

2.  **Configuração do Banco de Dados PostgreSQL:**
    * Certifique-se de que o serviço do PostgreSQL está em execução.
    * Crie um novo banco de dados. Usamos o nome `taskflow_db` durante o desenvolvimento:
        ```sql
        -- Conecte-se ao psql como superusuário (ex: postgres)
        CREATE DATABASE taskflow_db;
        ```
    * Crie um novo usuário (role) com uma senha. Usamos `taskflow_user` durante o desenvolvimento:
        ```sql
        CREATE USER taskflow_user WITH PASSWORD '<SUA_SENHA_ESCOLHIDA_AQUI>'; 
        GRANT ALL PRIVILEGES ON DATABASE taskflow_db TO taskflow_user;
        ALTER DATABASE taskflow_db OWNER TO taskflow_user;
        ```
        **Lembre-se da senha que você definiu!**

3.  **Configuração da Aplicação (`application.properties`):**
    * Navegue até `src/main/resources/application.properties`.
    * Configure as propriedades de conexão com o banco de dados de acordo com o que você configurou no PostgreSQL:
        ```properties
        spring.datasource.url=jdbc:postgresql://localhost:5432/taskflow_db
        spring.datasource.username=taskflow_user
        spring.datasource.password=<SUA_SENHA_ESCOLHIDA_AQUI> 
        spring.datasource.driver-class-name=org.postgresql.Driver

        spring.jpa.hibernate.ddl-auto=update
        spring.jpa.show-sql=true
        spring.jpa.properties.hibernate.format_sql=true
        # spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect # Opcional, Spring Boot geralmente detecta
        ```

4.  **Build do Projeto (Opcional, a IDE pode fazer isso):**
    Se você quiser fazer o build via Maven manualmente:
    ```bash
    mvn clean install
    ```

## Como Executar a Aplicação

1.  **Via IDE:**
    * Importe o projeto como um projeto Maven na sua IDE.
    * Encontre a classe principal `TaskflowApplication.java` (em `br.com.davidev.taskflow`).
    * Execute o método `main` desta classe (geralmente clicando com o botão direito > "Run" ou "Debug").

2.  **Via Linha de Comando (Maven):**
    Na raiz do projeto, execute:
    ```bash
    mvn spring-boot:run
    ```

A aplicação estará rodando em `http://localhost:8080` (porta padrão do Tomcat embutido).

## Documentação da API (Endpoints)

A URL base para todos os endpoints é: `http://localhost:8080/api/v1/tasks`

---

### 1. Criar Nova Tarefa

-   **Método HTTP:** `POST`
-   **URL:** `/` (relativo à URL base, ou seja, `http://localhost:8080/api/v1/tasks`)
-   **Descrição:** Cria uma nova tarefa no sistema.
-   **Corpo da Requisição (Request Body - JSON):**
    ```json
    {
        "descricao": "Descrição da nova tarefa",
        "status": "PENDENTE", // Ex: PENDENTE, EM_ANDAMENTO, CONCLUIDA
        "dataConclusaoPrevista": "AAAA-MM-DDTHH:MM:SS" // Ex: "2025-12-31T10:00:00" (Opcional)
    }
    ```
-   **Resposta de Sucesso:**
    -   **Código:** `201 Created`
    -   **Corpo (JSON):** A tarefa recém-criada, incluindo `id` e `dataCriacao`.
        ```json
        {
            "id": 1,
            "descricao": "Descrição da nova tarefa",
            "status": "PENDENTE",
            "dataCriacao": "AAAA-MM-DDTHH:MM:SS.fffffffff", // Gerado automaticamente
            "dataConclusaoPrevista": "2025-12-31T10:00:00",
            "dataFinalizacao": null
        }
        ```

---

### 2. Listar Todas as Tarefas

-   **Método HTTP:** `GET`
-   **URL:** `/`
-   **Descrição:** Retorna uma lista de todas as tarefas cadastradas.
-   **Corpo da Requisição:** Nenhum.
-   **Resposta de Sucesso:**
    -   **Código:** `200 OK`
    -   **Corpo (JSON):** Um array de objetos de tarefa.
        ```json
        [
            {
                "id": 1,
                "descricao": "Descrição da tarefa 1",
                "status": "PENDENTE",
                "dataCriacao": "...",
                "dataConclusaoPrevista": "...",
                "dataFinalizacao": null
            },
            {
                "id": 2,
                "descricao": "Descrição da tarefa 2",
                "status": "EM_ANDAMENTO",
                "dataCriacao": "...",
                "dataConclusaoPrevista": "...",
                "dataFinalizacao": null
            }
        ]
        ```
        (Retornará `[]` se não houver tarefas).

---

### 3. Buscar Tarefa por ID

-   **Método HTTP:** `GET`
-   **URL:** `/{id}` (ex: `/1` para buscar a tarefa com ID 1)
-   **Descrição:** Retorna os detalhes de uma tarefa específica.
-   **Corpo da Requisição:** Nenhum.
-   **Resposta de Sucesso:**
    -   **Código:** `200 OK`
    -   **Corpo (JSON):** O objeto da tarefa encontrada.
        ```json
        {
            "id": 1,
            "descricao": "Descrição da tarefa 1",
            "status": "PENDENTE",
            "dataCriacao": "...",
            "dataConclusaoPrevista": "...",
            "dataFinalizacao": null
        }
        ```
-   **Resposta de Erro:**
    -   **Código:** `404 Not Found` (Se a tarefa com o ID especificado não existir).

---

### 4. Atualizar Tarefa Existente

-   **Método HTTP:** `PUT`
-   **URL:** `/{id}` (ex: `/1` para atualizar a tarefa com ID 1)
-   **Descrição:** Atualiza as informações de uma tarefa existente.
-   **Corpo da Requisição (Request Body - JSON):** Os campos a serem atualizados.
    ```json
    {
        "descricao": "Nova descrição atualizada",
        "status": "CONCLUIDA",
        "dataConclusaoPrevista": "AAAA-MM-DDTHH:MM:SS" // Nova data (Opcional)
    }
    ```
-   **Resposta de Sucesso:**
    -   **Código:** `200 OK`
    -   **Corpo (JSON):** A tarefa atualizada.
        ```json
        {
            "id": 1,
            "descricao": "Nova descrição atualizada",
            "status": "CONCLUIDA",
            "dataCriacao": "...", // Permanece o mesmo
            "dataConclusaoPrevista": "...", // O novo valor, se enviado
            "dataFinalizacao": "..." // Preenchido se o status mudou para CONCLUIDA
        }
        ```
-   **Resposta de Erro:**
    -   **Código:** `404 Not Found` (Se a tarefa com o ID especificado não existir – atualmente, o serviço lança uma exceção que pode resultar em um erro 500; isso pode ser refinado).

---

### 5. Deletar Tarefa

-   **Método HTTP:** `DELETE`
-   **URL:** `/{id}` (ex: `/1` para deletar a tarefa com ID 1)
-   **Descrição:** Remove uma tarefa do sistema.
-   **Corpo da Requisição:** Nenhum.
-   **Resposta de Sucesso:**
    -   **Código:** `204 No Content`
    -   **Corpo:** Nenhum.
-   **Resposta de Erro:**
    -   **Código:** `404 Not Found` (Se a tarefa com o ID especificado não existir e não puder ser deletada – atualmente, o serviço lança uma exceção que pode resultar em um erro 500; isso pode ser refinado).

---

## Como Testar a API

Recomenda-se o uso de ferramentas como [Postman](https://www.postman.com/) ou [Insomnia](https://insomnia.rest/) para enviar requisições HTTP para os endpoints listados acima e verificar as respostas.

## Próximas Funcionalidades Planejadas (Exemplos)

-   Melhorar o tratamento de erros e validações.
-   Implementar DTOs (Data Transfer Objects).
-   Adicionar paginação e ordenação aos resultados.
-   Implementar autenticação e autorização de usuários.
-   Desenvolver um frontend para interagir com a API.

---

**Autor**

Davi <SEU_SOBRENOME_AQUI>

* GitHub: `https://github.com/<SEU_USUARIO_GITHUB_AQUI>`
* LinkedIn: `https://linkedin.com/in/<SEU_PERFIL_LINKEDIN_AQUI>` (Se tiver)

---
