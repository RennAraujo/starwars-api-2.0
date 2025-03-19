# API Star Wars - Rebeldes

API REST para gerenciamento de rebeldes e recursos durante a guerra contra o Império Galáctico.

## Descrição

Esta API permite o gerenciamento de rebeldes da Aliança Rebelde, incluindo o cadastro de novos membros, atualização de localização, denúncia de traidores e negociação de itens entre rebeldes.

## Funcionalidades

- Cadastro de rebeldes com inventário inicial
- Atualização da localização de rebeldes
- Denúncia de rebeldes como traidores
- Negociação de itens entre rebeldes
- Relatórios estatísticos

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.2.3
- Spring Data JPA
- Banco de dados H2 (em memória)
- Lombok
- Swagger/OpenAPI para documentação

## Pré-requisitos

- Java 17 ou superior
- Maven 3.8 ou superior

## Executando a aplicação

1. Clone o repositório:
```bash
git clone https://github.com/seu-usuario/starwars-api.git
cd starwars-api
```

2. Compile e execute a aplicação:
```bash
mvn spring-boot:run
```

3. A aplicação estará disponível em:
```
http://localhost:9876
```

4. A documentação Swagger estará disponível em:
```
http://localhost:9876/swagger-ui.html
```

5. O console do H2 estará disponível em:
```
http://localhost:9876/h2-console
```
Utilize as credenciais configuradas no `application.properties` para acessar o banco de dados.

## Endpoints da API

### Rebeldes

#### Cadastrar Rebelde
- **POST** `/api/rebeldes` - Cadastra um novo rebelde

#### Listar Rebeldes
- **GET** `/api/rebeldes` - Lista todos os rebeldes

#### Buscar Rebelde
- **GET** `/api/rebeldes/{id}` - Busca um rebelde pelo ID

#### Atualizar Localização
- **PUT** `/api/rebeldes/{id}/localizacao` - Atualiza a localização de um rebelde

#### Denunciar Rebelde
- **POST** `/api/rebeldes/denunciar` - Registra uma denúncia contra um rebelde

#### Negociar Itens
- **POST** `/api/rebeldes/negociar` - Realiza a negociação de itens entre rebeldes

### Relatórios

#### Porcentagem de Traidores
- **GET** `/api/relatorios/porcentagem-traidores` - Retorna a porcentagem de traidores

#### Porcentagem de Rebeldes
- **GET** `/api/relatorios/porcentagem-rebeldes` - Retorna a porcentagem de rebeldes fiéis

#### Média de Recursos
- **GET** `/api/relatorios/media-recursos` - Retorna a média de recursos por rebelde

#### Pontos Perdidos
- **GET** `/api/relatorios/pontos-perdidos` - Retorna os pontos perdidos devido a traidores

## Regras de Negócio

### Pontuação de Itens
- **Arma**: 4 pontos
- **Munição**: 3 pontos
- **Água**: 2 pontos
- **Comida**: 1 ponto

### Negociação de Itens
- Ambas as partes devem oferecer a mesma quantidade de pontos
- Rebeldes marcados como traidores não podem negociar

### Denúncia de Traidores
- Um rebelde é marcado como traidor quando recebe 3 ou mais denúncias
- Traidores não podem:
  - Negociar itens
  - Atualizar sua localização
  - Acessar seu inventário

## Exemplos de Requisições

### Cadastrar Rebelde
```json
POST /api/rebeldes
{
  "nome": "Luke Skywalker",
  "idade": 20,
  "genero": "Masculino",
  "localizacao": {
    "latitude": 23.5505,
    "longitude": 46.6333,
    "nomeBase": "Base Yavin IV"
  },
  "inventario": [
    {
      "tipo": "ARMA",
      "quantidade": 1
    },
    {
      "tipo": "COMIDA",
      "quantidade": 10
    },
    {
      "tipo": "AGUA",
      "quantidade": 5
    }
  ]
}
```

### Atualizar Localização
```json
PUT /api/rebeldes/{id}/localizacao
{
  "localizacao": {
    "latitude": 40.7128,
    "longitude": -74.0060,
    "nomeBase": "Base Hoth"
  }
}
```

### Denunciar Traidor
```json
POST /api/rebeldes/denunciar
{
  "denuncianteId": 1,
  "denunciadoId": 2
}
```

### Negociar Itens
```json
POST /api/rebeldes/negociar
{
  "rebeldeOfertanteId": 1,
  "rebeldeReceptorId": 2,
  "itensOfertados": [
    {
      "tipo": "ARMA",
      "quantidade": 1
    },
    {
      "tipo": "AGUA",
      "quantidade": 1
    }
  ],
  "itensDesejados": [
    {
      "tipo": "COMIDA",
      "quantidade": 6
    }
  ]
}
``` 