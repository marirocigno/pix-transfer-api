# PIX Transfer API

API REST em Java/Spring Boot para gerenciamento de contas, chaves Pix e transferências, com validação de regras de negócio e limite diário de transações.

---

## Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- H2 Database
- Maven

---

## Funcionalidades

- **AccountHolder:** CRUD completo. 
- **PixKey:** Criação, listagem e exclusão de chaves Pix..
-  **Transferências:** Registro e consulta.
- Validações de regras de negócio:
    - Cada titular pode ter **até 5 chaves Pix**
    - Os tipos de chave CPF, EMAIL e PHONE só podem existir **uma vez por titular**. Já o tipo de chave RANDOM pode existir mais de uma vez por titular desde que não atinja o limite de 5 chaves totais.
    - Saldo inicial de **R$100,00** se primeira chave Pix for criada
    - Limite diário de transferências: **R$80,00**
    - Saldo insuficiente impede transferência

---

## Estrutura do Projeto

- **entities** → mapeamento das tabelas do banco (AccountHolder, PixKey, Transfer)
- **repositories** → acesso aos dados via Spring Data JPA
- **dto** → objetos de transferência de dados (requests e responses)
- **services** → regras de negócio
- **controllers** → endpoints REST
- **util** → validação de chaves Pix e geração de chave aleatória
- **exception** → tratamento de exceções customizadas

---

## Endpoints

### AccountHolder
| Método | Endpoint | Descrição                                      |
|--------|----------|------------------------------------------------|
| POST | /account-holders | Criar titular                                  |
| GET | /account-holders | Listar todos os titulares                      |
| GET | /account-holders/{id} | Buscar titular por ID                          |
| PUT | /account-holders/{id} | Atualizar nome, e-mail ou telefone do titular. |
| DELETE | /account-holders/{id} | Deletar titular/conta.                         |

### PixKey
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | /pix-keys | Criar chave Pix |
| GET | /pix-keys | Listar todas as chaves |
| GET | /pix-keys/{id} | Buscar chave por ID |
| DELETE | /pix-keys/{id} | Remover chave Pix |

### Transfer
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | /transfers | Realizar transferência |
| GET | /transfers | Listar todas as transferências |
| GET | /transfers/{id} | Buscar transferência por ID |

- Os **endpoints** foram testados utilizando o [Bruno] (https://www.usebruno.com/), uma ferramenta de API client open source.
---

## Próximos Passos

- Impementar testes unitários usando JUnit.
- Adicionar integração com banco de dados real (PostgreSQL).
- Melhorar validação de chaves Pix e CPF.

---

## Repositório

[https://github.com/marirocigno/pix-transfer-api/tree/main/src/main/java/com/marianarocigno/pix_transfer_api]