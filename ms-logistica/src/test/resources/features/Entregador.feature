# language: pt
Funcionalidade: API - Entregador

  Cenário: Registrar um novo entregador
    Quando submeter um novo entregador
    Então o entregador é registrado com sucesso

  Cenário: Atualizar um entregador existente
    Dado que um entregador já foi registrado
    Quando atualizar o entregador
    Então o entregador é atualizado com sucesso

  Cenário: Atualizar um entregador inexistente
    Quando atualizar um entregador inexistente
    Então um erro de entregador não encontrado é retornado
