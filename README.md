# 🚀 SIAM — Sistema Inteligente de Auditoria Metrológica

> Sistema desktop em Java com suporte a Inteligência Artificial para otimização de fiscalizações metrológicas.

---

## Stacks

![Java](https://img.shields.io/badge/Java-v21-gray?style=for-the-badge&logo=openjdk&labelColor=red)
![MySQL](https://img.shields.io/badge/MySQL-8.0.45-gray?style=for-the-badge&logo=mysql&labelColor=4479A1&logoColor=white)
![Javax Swing](https://img.shields.io/badge/JavaX%20Swing-v8-gray?style=for-the-badge&logo=openjdk&labelColor=red)
![Gemini API](https://img.shields.io/badge/Gemini%20Api-v1-gray?style=for-the-badge&logo=googlegemini&labelColor=8E75B2&logoColor=white)

---

## 📌 Sobre o Projeto

O **SIAM (Sistema Inteligente de Auditoria Metrológica)** é uma aplicação desktop desenvolvida em **Java**, com interface gráfica em `javax.swing` e banco de dados **MySQL**.

O sistema tem como objetivo **modernizar e automatizar o processo de fiscalização de bombas de combustível**, auxiliando fiscais com apoio de **Inteligência Artificial Generativa (RAG)**.

---

## 🎯 Objetivo

Desenvolver um sistema capaz de:

- Gerenciar fiscalizações
- Armazenar evidências fotográficas
- Controlar usuários e permissões
- Gerar **planos de ação automatizados com IA**

---

## 🌍 Alinhamento com ODS

Este projeto está alinhado com:

- ⚖️ **ODS 16 — Paz, Justiça e Instituições Eficazes**
- 🏗️ **ODS 9 — Indústria, Inovação e Infraestrutura**

---

## 🧠 Diferencial

- 🤖 Integração com IA (Google Gemini)
- 🧩 Arquitetura **RAG (Retrieval-Augmented Generation)**
- 📊 Geração automática de microtarefas de auditoria
- 📚 Base normativa técnica integrada

---

## 🏗️ Arquitetura

O sistema segue o padrão:
**MVC (Model - View - Controller)**

### 🔧 Tecnologias

| Camada        | Tecnologia |
|--------------|-----------|
| Linguagem     | Java 21 |
| Interface     | javax.swing |
| Banco         | MySQL |
| Persistência  | JDBC + DAO |
| IA            | Google Gemini API |
| Build         | Maven |

---

## 📊 📋 Checklists do Projeto

### ✅ Setup Inicial
- [ ] Criar projeto Maven
- [ ] Configurar estrutura MVC
- [ ] Configurar conexão com MySQL
- [ ] Criar `.gitignore`

---

### 🔐 Autenticação
- [ ] Tela de login
- [ ] Criptografia de senha
- [ ] Controle de sessão
- [ ] Controle de acesso por perfil

---

### 👤 Usuários
- [ ] CRUD de usuários
- [ ] Definir tipos (ADMIN / FISCAL)
- [ ] Inserção de admin direto no banco

---

### ⛽ Postos e Bombas
- [ ] CRUD de postos
- [ ] CRUD de bombas
- [ ] Relacionamento entre entidades

---

### 📋 Fiscalização
- [ ] CRUD de fiscalizações
- [ ] Registro de irregularidades
- [ ] Cálculo de erro de medição

---

### 📸 Evidências
- [ ] Upload de imagens
- [ ] Armazenamento local
- [ ] Visualização

---

### 🤖 Inteligência Artificial
- [ ] Integração com API Gemini
- [ ] Implementar RAG
- [ ] Geração de microtarefas

---

### 📊 Dashboard
- [ ] Total de fiscalizações
- [ ] Total de usuários
- [ ] Tipos de irregularidades
- [ ] Reincidência por CNPJ

---

### 🧪 Testes e Finalização
- [ ] Testes manuais
- [ ] Ajustes de performance
- [ ] Preparação para apresentação

---

## 📁 Estrutura do Projeto

```
/src
/main
/java
/model
/view
/controller
/dao
/service
/database
/docs
/assets
README.md
INSTRUCOES.md
PROPOSTA.md
EDITAL.md
```

---

## 🔗 Documentação do Projeto

- 📘 [INSTRUCOES.md](./INSTRUCOES.md)
- 📄 [PROPOSTA.md](./PROPOSTA.md)
- 📚 [EDITAL.md](./EDITAL.md)

---

## ▶️ Como Executar

### ✅ Pré-requisitos

- Java 21
- Maven
- MySQL

### ⚙️ Passos

```bash
git clone <repo-url>
cd siam
mvn clean install
mvn exec:java
```

🔐 Banco de Dados
MySQL
Scripts em /database

⚠️ O usuário ADMIN deve ser criado diretamente no banco.

---

## 🔗 Links Úteis
- [Git Download](https://git-scm.com/install/windows)
- [Java Download - Temurin 21](https://adoptium.net/pt-BR/temurin/releases?version=21&os=any&arch=any)
- [NetBeans Download](https://netbeans.apache.org/front/main/download/)
- [Intellij Download](https://www.jetbrains.com/pt-br/idea/download/?section=windows)
- [Commits Semânticos](https://github.com/iuricode/padroes-de-commits)
- [Guia de uso git](https://github.com/danilocostabento/Git-Commands-just-for-personal-use)

---

## 👥 Equipe
- Danilo Costa Bento
- Letícia de Lima Silva
- Leonardo Lopes Correia
- José Victor Contierro
- Renan dos Santos Souza

---

## 🎓 Disciplina

**Programação de Soluções Computacionais**
Universidade São Judas Tadeu — 2026/1

---

## 🚧 Status do Projeto

![Static Badge](https://img.shields.io/badge/Em%20desenvolvimento-yellow?style=for-the-badge)

---

📜 Licença

Uso acadêmico.
