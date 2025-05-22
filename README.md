# 🌆 Urbaniza

**Urbaniza** é uma plataforma de registro e denúncia de problemas urbanos, conectando cidadãos e órgãos públicos municipais para melhorar a gestão e manutenção das cidades.

---

## 🚀 Como executar o projeto localmente

Siga os passos abaixo para clonar, configurar e executar o projeto em seu ambiente local:

### ✅ Pré-requisitos

- [Git](https://git-scm.com/)
- [Java 17+](https://www.oracle.com/br/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven](https://maven.apache.org/)
- [Docker e Docker Compose](https://docs.docker.com/get-docker/)
- Uma IDE compatível com Java (ex: IntelliJ IDEA, Eclipse, VS Code)

---

### 📦 Clonando o repositório

```
> git clone https://github.com/devDiegoSousa/urbaniza-backend.git
> cd urbaniza-backend
```

### 🔧 Preparando o projeto Java

1. Abra o projeto clonado em sua IDE de preferência.
2. No terminal da IDE ou do sistema, execute os seguintes comandos:

```
# Limpa dependências e builds anteriores
> mvn clean
```
``` 
# Compila o projeto e gera o .jar (pulando os testes)
>mvn package -DskipTests
```
ou utilize somente:
```
> mvn clean package -DskipTests
```

### 🐳 Subindo os containers com Docker

Com a aplicação empacotada, execute o comando abaixo para iniciar o banco de dados, pgAdmin e o backend da aplicação:

```
> docker-compose up --build
```

Isso criará e iniciará os containers para:
- PostgreSQL (porta 5433)
- pgAdmin (porta 8083)
- Urbaniza backend (porta 8282)

## 📂 Acessos úteis

- Backend: http://localhost:8282
- pgAdmin: http://localhost:8083
    - Login: admin@urbaniza.com
    - Senha: admin

No primeiro acesso ao pgAdmin, use as credenciais acima e configure a conexão com o servidor Postgres se necessário.
As credenciais do banco são:
- Banco: urbaniza-db
- Host: urbdb
- Porta: 5432
- Usuário: urbanizapostgres
- Senha: urbanizapassword


## 📘 API - Documentação

A API REST da Urbaniza expõe endpoints para registro de denúncias, gerenciamento de usuários, autenticação e mais.

### 🔐 Autenticação

#### POST `/auth/signup`
Cria o registro de um novo usuário no banco de dados

##### Request Body:
```
{
    "email": "teste@urbaniza.com",
    "password": "testes",
    "firstName": "usuário",
    "lastName": "teste",
    "role": "DEPARTMENT"(DEPARTMENT ou CITIZEN)
}
```
##### Response Body:

    Usuário registrado com sucesso.

#### POST `/auth/signin`
Autentica um usuário e retorna o token JWT.

##### Request Body:
```    
{
  "email": "usuario@email.com",
  "senha": "senha123"
}
```

##### Response Body:
```
{
    "accessToken": "eyJh...",
    "refreshToken": "eyJhb...",
    "expTime": 1747944421000
}
```

#### POST `/auth/refresh-token`
Utiliza o refreshToken para criar um novo accessToken

##### Request Body

```
{
  "refreshToken": "insira um token válido"
}
```

## 🧪 Testando a API
Você pode usar ferramentas como Postman ou Insomnia para interagir com a API, entretanto,você pode realizar solicitações diretamente do [front-end](https://github.com/devDiegoSousa/urbaniza-frontend) da aplicação.

## 🧹 Encerrando os containers
Para parar a aplicação e remover os containers:
```
docker-compose down
```