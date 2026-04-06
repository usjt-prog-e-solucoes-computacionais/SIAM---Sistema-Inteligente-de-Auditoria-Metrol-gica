# 📦 Sprint 1 — Foundation (Base do Sistema)

## 🎯 Objetivo

Estabelecer a base técnica do sistema SIAM, incluindo configuração do ambiente, arquitetura inicial, conexão com banco de dados e implementação do fluxo de autenticação de usuários.

---

## 📖 Visão Geral

A Sprint 1 foi responsável por estruturar todo o projeto, garantindo que o sistema estivesse pronto para evoluir com novas funcionalidades.

O foco principal foi a construção de uma base sólida, com separação de responsabilidades, integração com banco de dados e um fluxo de autenticação funcional.

---

## 🏗️ Entregas Realizadas

### ⚙️ Configuração do Projeto

- [x] Criação do projeto Maven
- [x] Configuração do `pom.xml`
- [x] Inclusão do driver MySQL
- [x] Organização da estrutura MVC

#### 📁 Estrutura inicial
```txt
src/main/java
├── controller
├── model
├── view
├── dao
├── service
└── connection
```

---

### 🗄️ Banco de Dados

#### Estrutura da tabela `users`

```sql
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    login VARCHAR(120) UNIQUE,
    registration VARCHAR(20) UNIQUE,
    password_hash VARCHAR(255),
    user_type ENUM('ADMIN', 'FISCAL')
);
```

*Configuração de conexão*
- JDBC utilizado
- Conexão centralizada em classe `DatabaseConnection`
- Uso de `DriverManager`

---

### 🔐 Autenticação

#### Fluxo de autenticação
`LoginVIew -> LoginController -> UserService -> UserDAO -> Database`

#### Regras implementadas
- login por email OU matrícula
- validação via banco de dados
- retorno com `Optional<User>`
- separação entre camadas

---

### 🧩 Camadas implementadas

#### Model
- `User`

#### DAO
- `UserDAO`
- `UserDAOImpl`

#### Service
- `UserService`

#### Controller
- `LoginController`

#### View
- `LoginView`

---

### 🖥️ Interface Gráfica

#### Tela de Login
Funcionalidades:
- campo de identificador (email ou matricula)
- campo de senha
- botão de login
- botão de aceite de termos de uso
- feedback visual de erro/sucesso

#### Comportamento
- valida entrada do usuário
- consulta banco
- retorna sucesso ou erro

---

## 🧠 Decisões Técnicas

### Arquitetura
- Padrão MVC aplicado
- separação clara entre camadas

### Persistência
- uso de JDBC direto
- DAO Pattern para isolamento de queries

### Autenticação
- suporte a múltiplos identificadores
- uso de `Optional` para evitar null

### Build
- Maven para gerenciamento de dependências

---

## ⚠️ Limitações da Sprint
- senhas não criptografadas (uso temporário de texto simples)
- ausência de controle de sessão
- ausência de logs estruturados
- UI ainda não refinada
- ausência de tratamento global de excessões

---

## 🚀 Preparação para Sprint 2

A base permite:
- criação da MainView
- listagem de dados do banco
- controle de acesso por tipo de usuário
- expansão para novas entidades (Inspection, Posto, etc.)

---

## 📊 Resultado da Sprint

✔️ Projeto estruturado

✔️ Banco conectado

✔️ Login funcional

✔️ Arquitetura definida

✔️ Base pronta para evolução

---

## 📌 Status

🟢 Concluída
