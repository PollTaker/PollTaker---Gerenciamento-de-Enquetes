# 🗳️ PollTaker

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-red?style=for-the-badge&logo=openjdk" />
  <img src="https://img.shields.io/badge/Spring_Boot-3.x-green?style=for-the-badge&logo=springboot" />
  <img src="https://img.shields.io/badge/React-18-blue?style=for-the-badge&logo=react" />
  <img src="https://img.shields.io/badge/MySQL-8-orange?style=for-the-badge&logo=mysql" />
  <img src="https://img.shields.io/badge/Status-Em_Desenvolvimento-yellow?style=for-the-badge" />
</p>

<p align="center">
  Sistema de enquetes para decisões coletivas, permitindo criação, votação e visualização de resultados em tempo real.
</p>

---

# 📌 Sobre o Projeto

O **PollTaker** é uma aplicação web desenvolvida para facilitar decisões coletivas através de enquetes simples, rápidas e seguras.

O sistema permite que usuários criem votações com múltiplas opções, registrem votos únicos e acompanhem os resultados parciais enquanto a enquete estiver ativa.

A aplicação foi projetada utilizando arquitetura moderna baseada em:

- **Backend:** Java + Spring Boot
- **Frontend:** React
- **Banco de Dados:** MySQL
- **API REST**
- **Autenticação e validação de regras de negócio**

---

# ✨ Funcionalidades

## 👤 Perfis de Usuário

### Criador
- Criar enquetes
- Encerrar enquetes
- Visualizar resultados

### Votante
- Participar de enquetes
- Visualizar resultados parciais
- Registrar apenas um voto por enquete

---

## ✅ Requisitos Funcionais

- [x] Criação de enquete com múltiplas opções
- [x] Registro de voto do usuário autenticado
- [x] Validação de voto único por usuário
- [x] Visualização dos resultados em tempo real
- [x] Encerramento de enquete pelo criador
- [x] Bloqueio de votos após encerramento

---

# 🏗️ Arquitetura do Sistema

```text
┌───────────────────────┐
│       Frontend        │
│        React          │
└──────────┬────────────┘
           │ HTTP/REST
           ▼
┌───────────────────────┐
│       Backend         │
│   Spring Boot API     │
└──────────┬────────────┘
           │ JPA/Hibernate
           ▼
┌───────────────────────┐
│       MySQL           │
│ Persistência de Dados │
└───────────────────────┘
```

---

# 🛠️ Tecnologias Utilizadas

## Backend
- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA

## Frontend
- React

## Banco de Dados
- MySQL 8

## Ferramentas
- Git
- GitHub

---

# 📂 Estrutura do Projeto

```bash
PollTaker/
│
├── backend/
│   ├── src/main/java
│   ├── src/main/resources
│   └── pom.xml
│
├── frontend/
│   ├── src/
│   ├── public/
│   └── package.json
│
└── README.md
```

---

# 🧠 Regras de Negócio

## 🔒 Regra de voto único
Um usuário só poderá votar **uma única vez** por enquete.

## 🚫 Enquete encerrada
Após o encerramento da enquete:
- novos votos não serão aceitos;
- resultados permanecem disponíveis para consulta.

## 📊 Resultados
Os resultados são exibidos através da contagem total de votos por opção.

---

# 🗃️ Modelo de Entidades

## User
```java
id
name
email
password
```

## Poll
```java
id
title
status
createdAt
createdBy
```

## Option
```java
id
description
pollId
```

## Vote
```java
id
userId
optionId
createdAt
```

---

# 🔗 Relacionamentos

```text
User 1:N Poll
Poll 1:N Option
User 1:N Vote
Option 1:N Vote
```

---

# 🚀 Como Executar o Projeto

## Pré-requisitos

- Java 21+
- Node.js 18+
- MySQL 8+
- Maven

---

## 🔧 Backend

### Clone o repositório

```bash
git clone https://github.com/seu-usuario/PollTaker.git
```

### Acesse a pasta backend

```bash
cd backend
```

### Configure o application.properties

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/polltaker
spring.datasource.username=root
spring.datasource.password=senha

spring.jpa.hibernate.ddl-auto=update
```

### Execute a aplicação

```bash
mvn spring-boot:run
```

Servidor disponível em:

```bash
http://localhost:8080
```

---

## 💻 Frontend

### Acesse a pasta frontend

```bash
cd frontend
```

### Instale as dependências

```bash
npm install
```

### Execute a aplicação

```bash
npm run dev
```

Frontend disponível em:

```bash
http://localhost:5173
```

---

# 📡 Endpoints da API

## Auth

| Método | Endpoint | Descrição |
|--------|----------|------------|
| POST | `/auth/login` | Login do usuário |
| POST | `/auth/register` | Cadastro de usuário |

---

## Polls

| Método | Endpoint | Descrição |
|--------|----------|------------|
| GET | `/polls` | Lista enquetes |
| POST | `/polls` | Cria enquete |
| GET | `/polls/{id}` | Busca enquete |
| PATCH | `/polls/{id}/close` | Encerra enquete |

---

## Votes

| Método | Endpoint | Descrição |
|--------|----------|------------|
| POST | `/votes` | Registra voto |

---

# 🔐 Segurança

O sistema utilizará:

- Autenticação JWT
- Senhas criptografadas com BCrypt
- Controle de acesso por perfil
- Validação de autenticação nas rotas protegidas

---

# 📈 Melhorias Futuras

- [ ] Dashboard administrativo
- [ ] Resultados em tempo real com WebSocket
- [ ] Exportação de resultados
- [ ] Temas dark/light
- [ ] Deploy com Docker
- [ ] CI/CD com GitHub Actions
- [ ] Testes automatizados

---

# 🧪 Testes

Planejamento de testes utilizando:

- JUnit
- Mockito
- React Testing Library

---

# 🐳 Docker (Opcional)

Exemplo de inicialização futura:

```bash
docker-compose up --build
```

---

# 📖 Documentação

A documentação da API poderá ser acessada futuramente via Swagger:

```bash
http://localhost:8080/swagger-ui.html
```

---

# 🤝 Contribuição

Contribuições são bem-vindas!

1. Faça um fork do projeto
2. Crie uma branch:

```bash
git checkout -b feature/minha-feature
```

3. Commit suas alterações:

```bash
git commit -m "feat: minha nova feature"
```

4. Push para sua branch:

```bash
git push origin feature/minha-feature
```

5. Abra um Pull Request

---

# 📄 Licença

Este projeto está sob a licença MIT.

---

# 👨‍💻 Autor

Desenvolvido por **Lucas Chalita**.

---

# ⭐ Considerações Finais

O **PollTaker** foi idealizado para oferecer uma solução simples, intuitiva e segura para tomadas de decisões coletivas, aplicando boas práticas de desenvolvimento fullstack, arquitetura REST e regras robustas de negócio.
