# Sistema de Reserva de Hotel - FIAP (CP 2)

Este projeto consiste em uma API REST robusta para a gestão do ciclo de vida de reservas de um hotel, abrangendo desde o cadastro de hóspedes e quartos até a realização de check-in e check-out com cálculos automáticos de diárias. O sistema foi desenvolvido seguindo as melhores práticas de arquitetura em camadas e regras de negócio rigorosas.

## 🏗️ Arquitetura do Sistema

A aplicação utiliza uma arquitetura baseada em **3 Camadas (MVC)** para garantir a separação de responsabilidades e facilitar a manutenção:

1.  **Camada de Apresentação (Controllers):** Expõe os endpoints REST e valida os dados de entrada via DTOs (Data Transfer Objects) e Jakarta Bean Validation.
2.  **Camada de Domínio/Serviço (Services):** Contém toda a lógica de negócio, incluindo a máquina de estados (FSM) da reserva, cálculos de valores e validações de disponibilidade.
3.  **Camada de Acesso a Dados (Repositories):** Interface com o banco de dados utilizando Spring Data JPA para persistência e Flyway para migrações versionadas.

### Diagrama de Arquitetura
![Arquitetura do Sistema](./)
*(Nota: Substitua o link acima pela imagem de arquitetura gerada durante o processo de desenvolvimento)*

## 🛠️ Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Data JPA** (Persistência)
- **H2 Database** (Banco de dados em memória para desenvolvimento)
- **Flyway** (Migração e versionamento de banco)
- **Springdoc OpenAPI (Swagger)** (Documentação da API)
- **Lombok** (Produtividade)
- **Jakarta Bean Validation** (Validação de dados)

## 📋 Regras de Negócio Implementadas

- **Validação de Datas:** O check-out deve ser sempre posterior ao check-in.
- **Disponibilidade:** Impede sobreposição de reservas para o mesmo quarto.
- **Capacidade:** Valida se o número de hóspedes respeita o limite do quarto.
- **Máquina de Estados:** Controla o fluxo `CREATED` ➔ `CHECKED_IN` ➔ `CHECKED_OUT`.
- **Cálculo de Diárias:** No check-out, o sistema calcula o valor final multiplicando as diárias pelo preço base do quarto.
- **Soft Delete:** Quartos com reservas não são excluídos fisicamente, apenas marcados como `INATIVO`.

## 🚀 Como Executar o Projeto

1.  **Clonar o repositório:**
    ```bash
    git clone <url-do-repositorio>
    ```
2.  **Compilar e rodar via Maven:**
    ```bash
    mvn spring-boot:run
    ```
3.  **Acessar a documentação (Swagger UI):**
    Abra o navegador em: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## 👥 Integrantes do Grupo

- **Enzo de Oliveira Rodrigues** - RM [553377]
- **Rafael Cristofali** - RM [553521]
- **Hugo Santos** - RM [553266]
- **Maria Julia** - RM [553384]
- **Gabriel Mediotti** - RM [552632]

---
