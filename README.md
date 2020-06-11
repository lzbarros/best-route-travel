# Bexs Travel API

##Sonar metrics
![Alt text](sonar-metrics.png?raw=true "Sonar")

## Descrição
Esse projeto tem como objetivo encontrar a rota mais econômica entre o ponto de partida ABC até o ponto de chegada XYZ.

## Pré-requisitos do projeto

- Java versão 8 (Instalação windows: https://www.java.com/pt_BR/download/help/windows_manual_download.xml)
- Maven (Instalação windows: https://maven.apache.org/guides/getting-started/windows-prerequisites.html)
- Git (Instalação windows: https://git-scm.com/download/win)

## Clonando o repositório e executando a aplicação

Execute o comando no terminal ou importe pela IDE:

```sh
$ git clone https://github.com/lzbarros/bexs-travel-api.git
```

Após isso, execute os comandos:

```sh
$ cd bexs-travel-api
$ git fetch
$ git branch master
$ git pull origin master
$ mvn clean install
```

Para executar a aplicação:

```sh
$ cd target
$ java -jar bexs-travel-api-0.0.1-SNAPSHOT.jar
```
O arquivo com as rotas deve estar no formato .csv (com o nome input-routes.csv) e deve estar no mesmo diretório do jar da aplicação.

## Exemplo de conteúdo do arquivo csv

```csv
GRU,BRC,10
BRC,SCL,5
GRU,CDG,75
GRU,SCL,20
GRU,ORL,56
ORL,CDG,5
SCL,ORL,20
```

## Shell Interface

Após executar a aplicação, duas interfaces estarão disponíveis: shell e rest.

Para pesquisar uma rota usando o shell (Que estará disponível no mesmo terminal de execução do comando java -jar) deve se usar o comando find-best-route, ex:
find-best-route GRU-CDG

Caso queira mais detalhes dos comandos em shell disponíveis na aplicação, execute o comando "help", ex. de saída do comando:

```sh
shell:>help
AVAILABLE COMMANDS

Built-In Commands
        clear: Clear the shell screen.
        exit, quit: Exit the shell.
        help: Display help about available commands.
        script: Read and execute commands from a file.
        stacktrace: Display the full stacktrace of the last error.

Routes
        find-best-route: Find best route. Please type "find-best-route" and "Route from"-"Route To" ex.: find-best-route ABC-XYZ
```

## Rest Interface 

```
	http://localhost:8080/travel/routes
	
	POST Method:
		JSON Request: 
			{
				"routeFrom": "ABC",
				"routeTo": "XYZ",
				"value": 10,
			}
		
		JSON Response (IF OK): 
			{
				"routeFrom": "ABC",
				"routeTo": "XYZ",
				"value": 10,
			}
			
		HTTP Status code: 201 CREATED
		
		-----------------------------
		
		JSON Response (IF NOK): 
			"Route [ABC-XYZ] already exists"		
			
		HTTP Status code: 401 CONFLICT		
		
		------------------------------
	
	GET Method
		http://localhost:8080/travel/routes/ABC/XYZ
		
		JSON Response (IF OK): 		
			"ABC > XYZ > $10"
		
		HTTP Status code: 200 OK
		
		-------------------------------
		
		JSON Response (IF NOK): 		
			"Route from ABC not found"
			OR
			"Route to ABC not found"
			OR
			"Route [ABC-XYZ] not found"
		
		HTTP Status code: 404 NOT FOUND			
		
		-------------------------------
```


## Estrutura de pacotes
```
./counter
├── com.bexs.travel
│   └── application
│       ├── infrastructure
│       ├── usecases
│   └── domain
│       ├── entities
│       ├── exceptions
│       └── services
│   └── framework
│       ├── interfaces
│	│   ├── commandline
│	│   ├── rest
│       ├── presenters
│       └── requests
```

## Arquitetura/Design adotada

A aplicação foi desenvolvida seguindo o modelo de arquitetura hexagonal, sendo assim a camada de domínio (core) está totalmente desacoplada 
das interfaces (command line e rest (web)) e do repositório (fonte de dados).

Dessa forma, a aplicação está desenhada e preparada para absorver uma outra fonte de dados (diferente de CSV) sem que seja necessário alterar a
camada de domínio, o mesmo é possível em relação a interface.

As interfaces shell e rest (web) compartilham da mesma fachada para realizar a solicitação desejada pelo usuário, ou poderiam facilmente utilizarem
fachadas diferentes de acordo com a regra estabelecida para cada uma.

Outra decisão tomada foi a criação de INTERFACES em todas as camadas da aplicação, ou seja, todas elas seguem um contrato estabelecido onde se for
necessário a troca da implementação, a comunicação entre as camadas não será afetada.

A camada de domínio também está totalmente desacoplada do framework utilizado (Spring), onde somente há a incidência do framework na camada 
da aplicação "framework" para a criação das interfaces shell e rest (web).

Outro detalhe como a injeção de dependência, foi totalmente separada do framework utilizando anotações como "@Inject, @Named" (Java CDI) e não as anotações
"@Service, @Component" do Spring.


