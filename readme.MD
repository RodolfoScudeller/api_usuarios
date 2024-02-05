# API de Gerenciamento de Usuários

Esta API fornece endpoints para gerenciar informações de usuários em um sistema.

## Endpoints

### 1. Listar Usuários

**Endpoint:** GET /api/v1/usuarios

**Descrição:**
Retorna uma lista de todos os usuários registrados no sistema.

### 2. Listar Usuários Específico

**Endpoint:** GET /api/v1/usuarios/:cpf

**Descrição:**
Retorna um usuários registrados no sistema com base no cpf fornecido.

### 3. Adicionar Usuário

**Endpoint:** POST /api/v1/usuarios

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

### 4. Atualizar Usuário

**Endpoint:** PUT /api/v1/usuarios/:cpf

**Corpo da Solicitação (JSON):**
```json
{
  "name": "Novo Nome do Usuário"
}
```

**Descrição:**
Atualiza as informações de um usuário existente com base no cpf fornecido.

### 5. Excluir Usuário

**Endpoint:** DELETE /api/v1/usuarios/:cpf

**Descrição:**
Exclui um usuário com base no cpf fornecido.

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

## Consumindo a API
A URL base para todas as solicitações é:
- https://apiusuarios-production.up.railway.app/api/v1/usuarios

## Recursos

### Obter Todos os Usuários

#### Request

- Método: `GET`
- Endpoint: `/`
- Descrição: Recupera a lista de todos os usuários cadastrados.

**Resposta (JSON):**
```json
{
  "duracaoEmSegundos": 0.06519756,
  "usuarios": [
    {
      "cpf": 123,
      "nome": "usuario",
      "ativo": true,
      "criadoEm": "2024-02-05",
      "atualizadoEm": "2024-02-05 12:53:16.406804"
    }
  ]
}
```

#### Obter Detalhes de um Usuário
#### Request
- Método: `GET`
- Endpoint: `/{cpf}`
- Descrição: Recupera os detalhes de um usuário específico.

#### Parâmetros de URL
- cpf (string): O cpf do usuário.


**Resposta (JSON):**
```json
{
  "cpf": 123,
  "nome": "usuario",
  "ativo": true,
  "criadoEm": "2024-02-05",
  "atualizadoEm": "2024-02-05 12:53:16.406804"
}
```

#### Criar um Novo Usuário
#### Request
- Método: `POST`
- Endpoint: `/`
- Descrição: Cria um novo usuário.

#### Parâmetros do Corpo (JSON)
- `cpf` (string): cpf do usuário.
- `nome` (string): nome do usuário.
- `ativo` (string): status do usuário.

#### Atualizar Detalhes do Usuário
#### Request
- Método: `PATCH`
- Endpoint: `/{id}`
- Descrição: Atualiza os detalhes de um usuário existente.

#### Parâmetros de URL
- `cpf` (string): O cpf do usuário.

#### Parâmetros do Corpo (JSON)
- `cpf` (string): cpf atualizado do usuário.
- `nome` (string): nome atualizado do usuário.

#### Excluir um Usuário
#### Request
- Método: `DELETE`
- Endpoint: `/{id}`
- Descrição: Exclui um usuário.

#### Parâmetros de URL
- `cpf` (string): O cpf do usuário.