# FutuereH Drone Delivery

## :package: :rocket: Descrição do Projeto
A FutuereH desenvolveu uma nova patente que permite a entrega de pacotes com drones e agora precisa criar um serviço de back-end, chamado Drone Feeder, que fornecerá informações aos drones.

O Drone Feeder é uma aplicação REST em que um sistema Front-end vai exibir algumas informações dos drones, tais como latitude e longitude, data e horário da entrega ou retirada do pacote. Essas informações serão armazenadas no banco de dados MySQL.

O drone, por sua vez, sincronizará informações com o Drone Feeder sempre que ele tiver uma conexão com a internet. O drone ainda vai informar se a entrega foi efetuada junto a data, horário do momento da entrega.

## :hammer_and_wrench: Tecnologias Utilizadas
  * Java 11
  * Spring Boot
  * Maven
  * MySQL
  * Docker/docker-compose

## :round_pushpin: Como Utilizar
### :warning: Pré-requisitos
  * Java 11
  * Docker
  * Maven
  
### :computer: Instalação
  1. Clone o repositório `git clone git@github.com:analuisams99/drone-feeder.git`
  2. Acesse a pasta do projeto via terminal `cd drone-feeder`
  3. Execute o comando `docker-compose up` para subir o banco de dados MySQL

### :triangular_flag_on_post: Endpoints
#### :flying_saucer: Drones
```
GET /drones: lista todos os drones cadastrados
GET /drones/{id}: busca um drone pelo ID
POST /drones: cria um novo drone
PUT /drones/{id}: atualiza um drone existente pelo ID
DELETE /drones/{id}: exclui um drone existente pelo ID 
```

#### :package: Entregas
```
GET /drones/{id}/entregas: lista todas as entregas de um drone
GET /entregas/{id}: busca uma entrega pelo ID
POST /drones/{id}/entregas: cria uma nova entrega associada a um drone
PUT /entregas/{id}: atualiza o status de uma entrega existente pelo ID
DELETE /entregas/{id}: exclui uma entrega existente pelo ID
```

## Associações das Classes de Entidade
### :flying_saucer: :package: Drone - Entrega
  * Uma entrega pode estar associada a um drone
  * Um drone pode ter várias entregas associadas
  
  ---
  
## :busts_in_silhouette: Autores
Desenvolvido por:
  * [Ana Luisa Marques Simões](https://github.com/analuisams99)
  * [Pedro Pereira](https://github.com/pedropereiradev)
  
`Abril - 2023`
