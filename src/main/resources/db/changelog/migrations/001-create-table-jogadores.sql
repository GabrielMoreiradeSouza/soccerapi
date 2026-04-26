--liquibase formatted sql

--changeset gabriel:1
CREATE TABLE jogadores (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    posicao VARCHAR(255) NOT NULL,
    idade INT NOT NULL,
    time VARCHAR(255) NOT NULL
);
