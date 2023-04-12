
# Projeto Drone Feeder

A FutuereH desenvolveu uma nova patente que permite a entrega de pacotes com drones e agora precisa criar um serviço de back-end, chamado Drone Feeder, que fornecerá informações aos drones.

O Drone Feeder é uma aplicação REST em que um sistema Front-end vai exibir algumas informações dos drones, tais como latitude e longitude, data e horário da entrega ou retirada do pacote. Essas informações serão armazenadas no banco de dados MySQL.

O drone, por sua vez, sincronizará informações com o Drone Feeder sempre que ele tiver uma conexão com a internet. O drone ainda vai informar se a entrega foi efetuada junto a data, horário do momento da entrega.
## Stack utilizada
- Java
- MySQL
- Maven
- Spring
- Docker

## Funcionalidades

**Drone**
- Criação de um drone
- Listar todos os drones disponíveis
- Buscar drone pelo seu identificador (ID)
- Atualiação da posição geográfica do drone
- Remoção de uma drone existente
- Atribuição de uma entrega à um drone
- Listar todas entregas atribuídas à um drone

**Entrega**
- Criação de uma entrega
- Edição dos dados da entrega
- Listar todas as entregas criadas
- Buscar drone responsável por uma entrega específica de acordo com o identificador da mesma (ID)
- Busca entrega pelo seu identificador (ID)
- Remoção de uma entrega criada
## Rodando localmente

Clone o projeto

```bash
  git clone git@github.com:analuisams99/drone-feeder.git
```

Entre no diretório do projeto

```bash
  cd drone-feeder
```

+ Utilizando Docker:
```bash
docker-compose up -d
```

+ Rodando somente o servidor com Docker:

```bash
  docker run --name drone_feeder -e MYSQL_ROOT_PASSWORD=root 
    -p 3306:3306 -d mysql:5.7
```


```bash
 ./mvnw spring-boot:run
```

+ As requisições podem ser feitas na rota `http://localhost:8080` após estar com a aplicação rodando

## Documentação da API

+ [**Drone**](#drone)
    + [Criar](#criar-drone)
    + [Buscar Todos](#buscar-drones)
    + [Buscar pelo ID](#buscar-drone-pelo-id)
    + [Atualizar localização](#atualizar-localizacao-drone)
    + [Deletar](#deletar-drone)
    + [Adicionar Entrega](#adicionar-entrega-drone)
    + [Listar entregas](#listar-entregas-de-um-drone)

+ [**Entrega**](#entrega)
    + [Criar](#criar-entrega)
    + [Atualizar](#atualizar-entrega)
    + [Buscar Todas](#buscar-entregas)
    + [Buscar pelo ID](#buscar-entrega-pelo-id)
    + [Buscar Drone](#buscar-drone-entrega)
    + [Deletar](#deletar-entrega)

### Drone
#### Criar drone
```http
  POST /drone
```

+ Request (application/json)
    + Body

            {
                "modelo": "Teste",
                "latitudeAtual": 0,
                "longitudeAtual": 0,
            }


+ Response 200 (application/json)
    + Body

            {
                "id": 3,
                "modelo": "DJI Mavick Pro",
                "latitudeAtual": 10.0,
                "longitudeAtual": 20.0,
                "entregas": []
            }

+ Se `modelo` estiver vazio
+ Response 400 (application/json)
    + Body

            {
                 "message": "Insira um modelo para criar um Drone"
            }


#### Buscar drones
```http
  GET /drone
```

+ Response 200 (application/json)
    + Body

            [
                {
                    "id": "1",
                    "modelo": "modelo de teste",
                    "latitudeAtual": 0,
                    "longitudeAtual": 0,
                    "entregas": []
                },
                /* ... */
            ]

#### buscar drone pelo id
```http
  GET /drone/{droneId}
```

| Parametros | Descrição                      |
|:-----------|:-------------------------------|
| `droneId`  | Identificador do drone buscado |


+ Response 200 (application/json)
    + Body

            {
                "id": 1,
                "modelo": "DJI Mavick Pro",
                "latitudeAtual": 10.0,
                "longitudeAtual": 20.0,
                "entregas": []
            }

+ Se não encontrar drone com o id buscado
+ Response 404 (application/json)
    + Body

           {
                "erorr": "Drone não encontrado"
           }

#### Atualizar localizacao drone
``` http
    PUT /drone/{droneId}
```
| Parametros | Descrição                               |
|:-----------|:----------------------------------------|
| `droneId`  | Identificador do drone a ser atualizado |

+ Request (application/json)
    + Body

            {
                "latitudeAtual": 10,
                "longitudeAtual": 15,
            }


+ Response 200 (application/json)
    + Body

            {
                "id": 1,
                "modelo": "DJI Mavick Pro",
                "latitudeAtual": 10.0,
                "longitudeAtual": 15.0,
                "entregas": []
            }

#### Deletar drone
``` http
    DELETE /drone/{droneId}
```
| Parametros | Descrição                             |
|:-----------|:--------------------------------------|
| `droneId`  | Identificador do drone a ser excluído |

+ Response 200 (text)
    + Body

            Drone deletado com sucesso!    

#### Adicionar entrega drone
``` http
    POST /drone/{droneId}/entrega/{entregaId}
```
| Parametros  | Descrição                               |
|:------------|:----------------------------------------|
| `droneId`   | Identificador do drone a ser atualizado |
| `entregaId` | Identificador da entrega a ser indexada |

+ Response 200 (application/json)
    + Body

            {
                "id": 1,
                "modelo": "DJI Mavick Pro",
                "latitudeAtual": 17.0,
                "longitudeAtual": 0.0,
                "entregas": [
                    {
                        "id": 1,
                        "dataHoraRetirada": "11/04/2023 19:38",
                        "dataHoraEntrega": "15:21",
                        "statusDaEntrega": "EM_ANDAMENTO",
                        "latitudeDestino": 0.0,
                        "longitudeDestino": 0.0
                    }
                ]
            }

+ Se nao encontrar drone
+ Response 404 (application/json)
+ Body

        {
            "message": "Drone não encontrado"
        }

+ Se não encontrar entrega
+ Response 404 (application/json)
+ Body

        {
            "message": "Entrega não encontrado"
        }


#### Listar entregas de um drone
``` http
    GET /drone/{droneId}/entregas
```
| Parametros | Descrição                                        |
|:-----------|:-------------------------------------------------|
| `droneId`  | Identificador do drone a ser listado as entregas |

+ Response 200 (application/json)
    + Body

            [
                {
                    "id": 1,
                    "dataHoraRetirada": "11/04/2023 19:38",
                    "dataHoraEntrega": "15:21",
                    "statusDaEntrega": "EM_ANDAMENTO",
                    "latitudeDestino": 0.0,
                    "longitudeDestino": 0.0
                },
                /.../
            ]

+ Se nao encontrar drone
+ Response 404 (application/json)
+ Body

        {
            "message": "Drone não encontrado"
        }

### Entrega
#### Criar entrega
```http
  POST /entrega
```

+ Request (application/json)
    + Body
      {
      "dataHoraRetirada": "10:52",
      "dataHoraEntrega": "15:21",
      "latitudeDestino": 4,
      "longitudeDestino": 15
      }


+ Response 200 (application/json)
    + Body

            {
                "id": 1,
                "dataHoraRetirada": "10:52",
                "dataHoraEntrega": "15:21",
                "statusDaEntrega": "PENDENTE",
                "latitudeDestino": 4.0,
                "longitudeDestino": 15.0
            }

+ Se `dataHoraRetirada` estiver vazio
+ Response 400 (application/json)
    + Body

            {
                 "message": "Não é possível criar uma entrega sem a data de retirada."
            }

+ Se `latitudeDestino` e/ou `longitudeDestino` estiverem vazias
+ Response 400 (application/json)
    + Body

            {
                 "message": "Não é possível criar uma entrega sem as coordenadas de longitude e/ou latitude."
            }

#### Atualizar entrega
``` http
    PUT /entrega/{entregaId}
```
| Parametros | Descrição                                 |
|:-----------|:------------------------------------------|
| `droneId`  | Identificador da entrega a ser atualizado |

+ Request (application/json)
    + Body

            {
                "dataHoraRetirada": "10:52",
                "dataHoraEntrega": "15:21",
                "statusDaEntrega": "ENTREGUE"
                "latitudeDestino": 4,
                "longitudeDestino": 15
            }


+ Response 200 (application/json)
    + Body

            {
                "dataHoraRetirada": "10:52",
                "dataHoraEntrega": "15:21",
                "statusDaEntrega": "ENTREGUE"
                "latitudeDestino": 4,
                "longitudeDestino": 15
            }

+ Se não encontrar entrega
+ Response 404 (application/json)
+ Body

        {
            "message": "Entrega não encontrada"
        }

#### Buscar entregas
```http
  GET /entrega
```

+ Response 200 (application/json)
    + Body

            [
                {
                    "dataHoraRetirada": "10:52",
                    "dataHoraEntrega": "15:21",
                    "statusDaEntrega": "ENTREGUE"
                    "latitudeDestino": 4,
                    "longitudeDestino": 15
                }
                /* ... */
            ]

#### Buscar entrega pelo id
```http
  GET /entrega/{entregaId}
```

| Parametros  | Descrição                        |
|:------------|:---------------------------------|
| `entregaId` | Identificador da entrega buscada |


+ Response 200 (application/json)
    + Body

            {
                "dataHoraRetirada": "10:52",
                "dataHoraEntrega": "15:21",
                "statusDaEntrega": "ENTREGUE"
                "latitudeDestino": 4,
                "longitudeDestino": 15
            }

+ Se não encontrar entrega com o id buscado
+ Response 404 (application/json)
    + Body

           {
                "erorr": "Entrega não encontrada"
           }

#### Buscar drone entrega
```http
  GET /entrega/{entregaId}/drone
```

| Parametros  | Descrição                        |
|:------------|:---------------------------------|
| `entregaId` | Identificador da entrega buscada |


+ Response 200 (application/json)
    + Body

            {
                "id": 1,
                "modelo": "DJI Mavick Pro",
                "latitudeAtual": 17.0,
                "longitudeAtual": 0.0,
                "entregas": [
                    {
                        "id": 1,
                        "dataHoraRetirada": "11/04/2023 19:38",
                        "dataHoraEntrega": "15:21",
                        "statusDaEntrega": "EM_ANDAMENTO",
                        "latitudeDestino": 0.0,
                        "longitudeDestino": 0.0
                    },
                    {
                        "id": 2,
                        "dataHoraRetirada": "11/04/2023 19:39",
                        "dataHoraEntrega": "15:21",
                        "statusDaEntrega": "CANCELADA",
                        "latitudeDestino": 0.0,
                        "longitudeDestino": 0.0
                    },
                    {
                        "id": 3,
                        "dataHoraRetirada": "11/04/2023 19:39",
                        "dataHoraEntrega": "15:21",
                        "statusDaEntrega": "ENTREGUE",
                        "latitudeDestino": 4.0,
                        "longitudeDestino": 15.0
                    }
                ]
            }

+ Se não encontrar entrega com o id buscado
+ Response 404 (application/json)
    + Body

           {
                "erorr": "Entrega não encontrada"
           }

#### Deletar entrega
``` http
    DELETE /drone/{entregaId}
```
| Parametros  | Descrição                               |
|:------------|:----------------------------------------|
| `entregaId` | Identificador da entrega a ser excluída |

+ Response 200 (text)
    + Body

            Entrega deletada com sucesso!    
## Desenvolvido por

- [Pedro Pereira](https://github.com/pedropereiradev)
- [Ana Luisa Marques Simões](https://github.com/analuisams99)
 