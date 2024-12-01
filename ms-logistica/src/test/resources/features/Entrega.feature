# language: pt
Funcionalidade: API - Entrega

  Cenário: Registrar uma nova entrega
    Quando um cliente é registrado
    E um produto é registrado
    E um pedido for criado
    E o mesmo pedido for confirmado
    E submeter uma nova entrega
    Então a entrega é registrada com sucesso

  Cenário: Cancelar uma entrega existente
    Dado que uma entrega já foi registrada
    Quando cancelar a entrega existente
    Então a entrega é cancelada com sucesso