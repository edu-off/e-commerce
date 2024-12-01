# language: pt
Funcionalidade: API - Produto

  Cenário: Registrar um novo produto
    Quando submeter um novo produto
    Então o produto é registrado com sucesso

  Cenário: Atualizar um produto existente
    Dado que um produto já foi registrado
    Quando atualizar o produto
    Então o produto é atualizado com sucesso

  Cenário: Incrementar estoque de um produto existente
    Dado que um produto já foi registrado
    Quando incrementar o estoque do produto
    Então o estoque do produto é incrementado com sucesso

  Cenário: Decrementar estoque de um produto existente
    Dado que um produto já foi registrado
    Quando decrementar o estoque do produto
    Então o estoque do produto é decrementado com sucesso

  Cenário: Remover um produto existente
    Dado que um produto já foi registrado
    Quando remover o produto
    Então o produto é removido com sucesso

  Cenário: Buscar um produto existente
    Dado que um produto já foi registrado
    Quando solicitar a busca do produto
    Então o produto é exibido com sucesso

  Cenário: Buscar produtos existente por nome
    Dado que um produto já foi registrado
    Quando solicitar a busca do produto por nome
    Então os produtos são exibidos com sucesso

  Cenário: Buscar produtos existente por descrição
    Dado que um produto já foi registrado
    Quando solicitar a busca do produto por descrição
    Então os produtos são exibidos com sucesso

  Cenário: Buscar produtos existente por categoria
    Dado que um produto já foi registrado
    Quando solicitar a busca do produto por categoria
    Então os produtos são exibidos com sucesso
