# API de Gerenciamento de Usuários

Esta API fornece endpoints para gerenciar informações de usuários em um sistema.

## Endpoints

### 1. Listar Usuários

**Endpoint:** GET /users

**Descrição:**
Retorna uma lista de todos os usuários registrados no sistema.

### 2. Adicionar Usuário

**Endpoint:** POST /users

**Corpo da Solicitação (JSON):**
```json
{
  "name": "Nome do Usuário",
  "cpf": "123456789-85",
  "active": true
}
```

**Descrição:**
Adiciona um novo usuário ao sistema.

### 3. Atualizar Usuário

**Endpoint:** PUT /users/:id

**Corpo da Solicitação (JSON):**
```json
{
  "name": "Novo Nome do Usuário"
}
```

**Descrição:**
Atualiza as informações de um usuário existente com base no ID fornecido.

### 4. Excluir Usuário

**Endpoint:** DELETE /users/:id

**Descrição:**
Exclui um usuário com base no ID fornecido.

## Pré-requisitos
Certifique-se de ter os seguintes requisitos instalados antes de começar:

- [Maven](https://downloads.apache.org/maven/): Certifique-se de ter o Maven instalado. Se não tiver, você pode baixá-lo [aqui](https://downloads.apache.org/maven/) e seguir as instruções de instalação para o seu sistema operacional.

- [Docker](https://www.docker.com/products/docker-desktop/): Verifique se o Docker está instalado em sua máquina. Caso contrário, você pode baixá-lo [aqui](https://www.docker.com/products/docker-desktop/) e seguir as instruções de instalação.

## Executando Localmente
- Clone o repositório: git clone https://github.com/RodolfoScudeller/api_usuarios
- Instale as dependências: mvn clean install
- Inicie o Docker: docker-compose up -d
- Execute a aplicação: mvn spring-boot:run
- Execute os teste: mvn test

