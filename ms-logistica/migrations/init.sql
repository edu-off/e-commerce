-- -----------------------------------------------------
-- Table `db-logistica`.`entregador`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db-logistica`.`entregador`;

CREATE TABLE IF NOT EXISTS `db-logistica`.`entregador` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `status` ENUM('DISPONIVEL', 'EM_TRANSITO', 'INATIVO') NOT NULL,
    `nome` VARCHAR(50) NOT NULL,
    `email` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `entregador_status_idx` (`status` ASC) VISIBLE
);

-- -----------------------------------------------------
-- Table `db-logistica`.`entrega`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db-logistica`.`entrega`;

CREATE TABLE IF NOT EXISTS `db-logistica`.`entrega` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `status` ENUM('PENDENTE', 'EM_TRANSITO', 'CANCELADA', 'CONCLUIDA') NOT NULL,
    `pedido_id` BIGINT NOT NULL,
    `cliente_id` BIGINT NOT NULL,
    `destinatario` VARCHAR(50) NULL,
    `ddd` INT NULL,
    `telefone` INT NULL,
    `logradouro` VARCHAR(50) NULL,
    `bairro` VARCHAR(40) NULL,
    `cidade` VARCHAR(40) NULL,
    `uf` VARCHAR(2) NULL,
    `cep` INT NULL,
    `entregador_id` BIGINT NULL,
    PRIMARY KEY (`id`),
    INDEX `entrega_entregador_idx` (`entregador_id` ASC) VISIBLE,
    INDEX `entrega_status_idx` (`status` ASC) VISIBLE,
    CONSTRAINT `fk_entrega_entregador`
    FOREIGN KEY (`entregador_id`)
    REFERENCES `db-logistica`.`entregador` (`id`)
);
