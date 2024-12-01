# Sistema de E-Commerce - Tech Challenge Pós Tech FIAP

Tech Challenge Pós Tech FIAP - Back end de aplicação que controla ações de um e-commerce

## **Microsserviços:** 

* Gestão de clientes: [clique aqui.](https://github.com/edu-off/e-commerce/tree/main/ms-cliente)
* Gestão de produtos: [clique aqui.](https://github.com/edu-off/e-commerce/tree/main/ms-produto)
* Gestão de pedidos: [clique aqui.](https://github.com/edu-off/e-commerce/tree/main/ms-pedido)
* Gestão de logistica: [clique aqui.](https://github.com/edu-off/e-commerce/tree/main/ms-logistica)

## **Subir microsservicos e bancos de dados via Makefile**

* Para subir tdodos os containeres e bancos de dados da aplicação via Makefile:
```shell
make docker-start
```

## **Subir microsservicos e bancos de dados via terminal**

* Para subir container da aplicação e servidor de banco de dados via docker compose:
```shell
docker-compose -f docker-compose.yml up -d
```