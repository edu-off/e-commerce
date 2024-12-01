-- -----------------------------------------------------
-- Table `db-cliente`.`cliente`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db-cliente`.`cliente`;

CREATE TABLE IF NOT EXISTS `db-cliente`.`cliente` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `nome` VARCHAR(50) NOT NULL,
    `status` ENUM('ATIVO', 'INATIVO') NOT NULL,
    `email` VARCHAR(100) NOT NULL,
    `ddd` INT NULL,
    `telefone` INT NULL,
    PRIMARY KEY (`id`),
    INDEX `cliente_nome_idx` (`nome` ASC) VISIBLE,
    INDEX `cliente_status_idx` (`status` ASC) VISIBLE,
    INDEX `cliente_email_idx` (`email` ASC) VISIBLE
);

-- -----------------------------------------------------
-- Table `db-cliente`.`endereco`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db-cliente`.`endereco`;

CREATE TABLE IF NOT EXISTS `db-cliente`.`endereco` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `logradouro` VARCHAR(50) NOT NULL,
    `bairro` VARCHAR(20) NOT NULL,
    `cidade` VARCHAR(20) NOT NULL,
    `uf` VARCHAR(2) NOT NULL,
    `cep` INT NOT NULL,
    `cliente_id` BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_endereco_cliente`
    FOREIGN KEY (`cliente_id`)
    REFERENCES `db-cliente`.`cliente` (`id`)
);