# Sistema Escalável para Análise de Ocorrência

#### Desafio proposto
Construir uma aplicação, disponibilizando um endpoint REST que receba um parâmetro (do tipo texto). A aplicação deverá contar o número de ocorrências do parâmetro recebido nos arquivos de amostras enviados juntos. O retorno deve ser o número de ocorrências do termo nos arquivos e o tempo total gasto na pesquisa.  [**=> Confira Aqui**](#solução-proposta)

#### Pré requisitos:
- [X] 1. A solução deverá utilizar docker;
- [X] 2. A solução deve ser escalável;
- [X] 3. A arquitetura e tecnologias são livres, de escolha do candidato.

#### Da solução:
1. Explique o porquê do modelo de arquitetura e tecnologias adotadas;  [**=> Confira Aqui**](#arquiteturatecnologias-utilizadas)
2. Como a solução poderia ser escalada (o que se espera da sua soluçãose tiver 1 milhão de arquivos);  [**=> Confira Aqui**](#escalabilidade)
3. Como seria o procedimento de build, deploy [**=> Confira Aqui**](#documentação)

## 

# Solução Proposta

A solução aqui proposta atendeu a solicitação, porém foi um pouco além, pois acreditei ser uma boa oportunidade para explanar melhor meu conhecimento.
As features implementadas foram:
1. Recebimento do arquivo e do parametro, realizando a contagem das ocorrências e retornando: O termo enviado, número de ocorrências, tempo de processamento e um link para download do arquivo enviado.
2. Após o envio de um arquivo, o mesmo fica armazenado para futuras contagens, sem a necessidade de enviar um novo arquivo. Porém, caso um novo arquivo seja enviado, o anterior será sobrescrito.
3. O ultimo arquivo enviado pode ser baixado a qualquer momento.
4. Foi implementada um controle de acesso afim de aumentar o nível de segurança, apenas usuários que realizarem login podem utilizar a API

## 

# Arquitetura/Tecnologias Utilizadas

1. Repository Pattern, Value Object (VO) e Model View Controll (MVC)
2. Java e Spring Boot
3. Maven
4. Swagger2
5. Docker Compose
6. JPA/Hibernate
7. MySQL/FlyWay
8. JSON Web Tokens (JWT)

Com relação as arquiteturas, existem diversas razões para eu ter escolhido desenvolver desta forma, mas em geral eu me sinto mais confortável assim devido a clareza da arquitetura **MVC**, o código fica bem organizado, cada entidade e serviço tem seu papel bem definido! Já a utilização do **VO** traz uma segurança ainda maior por não permitir que as entidades sejam expostas e o **Pattern** permite um emcapsulamento da lógica de acesso aos dados, desta forma, acredito que estou trabalhando da forma extremamente segura e clara, não só para o programador que está desenvolvendo, mas também pro programador que virá a dar supporte nessa aplicação no futuro.
Ademais, sobre as tecnologias, cada uma deve um papel que julgo muito imporante e muito prático durante o desenvolvimento: o **maven** é quem possibilita trazer todas as dependencias de forma simples; o **Spring** permite que tudo ocorra da forma mais rápida possível criando toda a estrutura da aplicação permitindo inicializar sem grandes complexidades; **JPA** e o **Hibernate** me auxiliam no mapeamento das entidades e a comunicação como banco; o **MySQL** é um dos bancos de dados relacionais mais conhecidos, estáveis e confiáveis; já o **FlyWay** é o que permite o versionamento do banco, pensando em possíveis crescimentos e modificações futuras; **JWT** me permite manipular os tokens de segurança e garantir o controle de acesso da aplicação; o **Swagger** é uma das melhores ferramentas para documentar uma API, deixando clara cada uma das chamadas; e por fim, mas um dos mais importantes, o **Docker** Compose é a forma mais simples de criar imagens das aplicações para subir no docker, permitindo assim fácil transportabilidade e escalabilidade.

## 

# Escalabilidade

Essa API Rest foi estruturada para suportar crescimento tanto na vertical, quanto na horizontal. O código está pronto para possíveis versionamentos, tanto no Java, quanto no MySQL, permitindo assim o crescimento vertical com mais entidades, classes, controllers e afins. Porém também é possível crescer horizontalmente por estar em um container, é possível aumentarmos a capacidade ou as instâncias e conexões conforme necessidade.

## 

# Documentação
## Deploy
Para subir uma instância dessa API não á grande complexidade, apenas abra o PowerShell na pasta que foi realizado o git clone, onde consta o arquivo `docker-compose.yml`, desta forma siga abaixo:
1. Digite o comando `docker-compose build`, nesse momento será construida a imagem e pode demorar um pouco, pois irá realizar o download do Maven e do MySQL.
2. Digite o comando `docker-compose up -d` e aguarde alguns segundos enquanto os containers inicializam.
3. Caso queira verificar o progresso você pode verificar as logs digitado `docker logs -f <nome_container>`, o nome do contaner pode veriar, então sempre verifique o nome com `docker ps`
4. Pronto, aplicação UP e pronta para conexões.

## Utilização
Após a inicialização da aplicação, você pode conferir como utilizar a API de duas formas:
1. Acessando a documentação Swagger em http://localhost:8080/swagger-ui.html
2. Utilizar o **PostMan** para importar as configurações disponibilidas aqui neste repositório em 'postman.collections'
 
Recomendo a utilização da segunda opção, pois ja está devidamente configurado os acessos para Login com usuário e senha, basta apenas importar os dois arquivos `B2W REST API Spring Boot v2.1.3.postman_collection.json` e `B2W TEST ENVIRONMENT.postman_environment.json`, desta forma fica tudo pronto para conexão e teste. Caso haja alguma duvida, estou a disposição!
De todo modo, caso queiram acessar a API de outra forma, existem dois usuários cadastrados:

- 1. usuário: **b2w_user** / senha: **b2w_passwd**
- 2. usuário: **felipe_user** / senha: **felipe_passwd**

## 

# Comentários Finais
Gostaria de agradecer a oportunidade desde já, procurei ser transparente no meu código e o mais claro possível em minhas explicações aqui, tem bastante coisa bacana que seria possível fazer com essa proposta! 
No mais, eu estou a disposição para possíveis entrevistas ou apresentações, caso haja enteresse.

Aguardo um feedback, abraços.


