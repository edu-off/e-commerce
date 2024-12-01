-- -----------------------------------------------------
-- Table `db-pedido`.`pedido`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db-pedido`.`pedido`;

CREATE TABLE IF NOT EXISTS `db-pedido`.`pedido` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `cliente_id` BIGINT NOT NULL,
    `status` ENUM('EM_ABERTO', 'CONFIRMADO', 'CONCLUIDO', 'CANCELADO') NOT NULL,
    `preco` DOUBLE NOT NULL,
    `produtos` JSON NOT NULL,
    `data_abertura` DATETIME NOT NULL,
    `data_confirmacao` DATETIME NULL,
    `data_cancelamento` DATETIME NULL,
    `data_conclusao` DATETIME NULL,
    PRIMARY KEY (`id`)
);
