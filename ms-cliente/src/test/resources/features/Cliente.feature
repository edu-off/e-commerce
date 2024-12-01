# language: pt
Funcionalidade: API - Cliente

  Cenário: Registrar um novo cliente
    Quando submeter um novo cliente
    Então o cliente é registrado com sucesso

  Cenário: Atualizar um cliente existente
    Dado que um cliente já foi registrado
    Quando atualizar o cliente
    Então o cliente é atualizado com sucesso

  Cenário: Ativar um cliente existente
    Dado que um cliente já foi registrado
    Quando ativar o cliente
    Então o cliente é ativado com sucesso

  Cenário: Inativar um cliente existente
    Dado que um cliente já foi registrado
    Quando inativar o cliente
    Então o cliente é inativado com sucesso

  Cenário: Buscar um cliente existente
    Dado que um cliente já foi registrado
    Quando solicitar a busca do cliente
    Então o cliente é exibido com sucesso

  Cenário: Buscar clientes existente por nome
    Dado que um cliente já foi registrado
    Quando solicitar a busca do cliente por nome
    Então os clientes são exibidos com sucesso

  Cenário: Buscar clientes existente por e-mail
    Dado que um cliente já foi registrado
    Quando solicitar a busca do cliente por e-mail
    Então os clientes são exibidos com sucesso

  Cenário: Buscar clientes existente por status
    Dado que um cliente já foi registrado
    Quando solicitar a busca do cliente por status
    Então os clientes são exibidos com sucesso
