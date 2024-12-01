# language: pt
Funcionalidade: API - Pedido

  Cenário: Registrar um novo pedido
    Quando um cliente é registrado
    E produto são registrados
    E um novo pedido é submetido
    Então o pedido é registrado com sucesso

  Cenário: Atualizar a lista de produtos de um produto
    Dado que um pedido já foi registrado
    Quando atualizar a lista de produtos de um produto
    Então a lista de produtos do pedido é atualizada com sucesso

  Cenário: Cancelar um pedido existente
    Dado que um pedido já foi registrado
    Quando cancelar o pedido existente
    Então o pedido é cancelado com sucesso

  Cenário: Confirmar um pedido existente
    Dado que um pedido já foi registrado
    Quando confirmar o pedido existente
    Então o pedido é confirmado com sucesso

  Cenário: Concluir um pedido existente
    Dado que um pedido já foi registrado
    E que um pedido já foi confirmado
    Quando concluir o pedido existente
    Então o pedido é concluido com sucesso
