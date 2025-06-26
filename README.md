# 🌆 Urbaniza

**Urbaniza** é uma plataforma de registro e denúncia de problemas urbanos, conectando cidadãos e órgãos públicos municipais para melhorar a gestão e manutenção das cidades.

---

## 🚀 Como executar o projeto localmente

Siga os passos abaixo para clonar, configurar e executar o projeto em seu ambiente local:

### ✅ Pré-requisitos

- [Git](https://git-scm.com/)
- [Docker e Docker Compose](https://docs.docker.com/get-docker/)
- Uma IDE compatível com Java (ex: IntelliJ IDEA, Eclipse, VS Code)

---

### 📦 Clonando o repositório

```
> git clone https://github.com/devDiegoSousa/urbaniza-backend.git
> cd urbaniza-backend
```

### 🐳 Subindo os containers com Docker
1. Abra o Docker
2. Acesse o terminal Docker
3. Acesse dentro do terminal a pasta raiz do projeto (Repositório clonado)
4. Utilize o seguinte comando:

```
> docker compose up
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

##### Request Body example:
```
{
    "email": "teste@urbaniza.com",
    "password": "senhacom6caracteres",
    "firstName": "usuário",
    "lastName": "teste",
}
```
##### Response Body example:

    Usuário registrado com sucesso.

##### Validação de email
1. Abra seu email
2. Procure o email enviado por nicolasalcantaradev@gmail.com
3. Acesse o link para validar o email

#### POST `/auth/signin`
Autentica um usuário e retorna o token JWT.

##### Request Body example:
```    
{
  "email": "usuario@email.com",
  "senha": "senha123"
}
```

##### Response Body example:
```
{
    "accessToken": "eyJh...",
    "refreshToken": "eyJhb...",
    "expTime": 1747944421000
}
```

#### POST `/auth/refresh-token`
Utiliza o refreshToken para criar um novo accessToken

##### Request Body example

```
{
  "refreshToken": "eyJhb..."
}
```

## 🧪 Testando a API
Você pode usar ferramentas como Postman ou Insomnia para interagir com a API, entretanto, pode-se realizar solicitações diretamente do [front-end](https://github.com/devDiegoSousa/urbaniza-frontend) da aplicação.

## 🧹 Encerrando os containers
Para parar a aplicação e remover os containers:

1. Dentro da pasta raiz do projeto dentro do terminal Docker, utilize o comando a seguir:
```
docker-compose down
```
