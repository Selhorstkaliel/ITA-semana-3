-- Script de criação do banco de dados coursera
-- Execute este script para criar o banco e a tabela necessários

-- Criar banco de dados (execute como superusuário do PostgreSQL)
-- CREATE DATABASE coursera;

-- Conecte-se ao banco coursera antes de executar o comando abaixo
-- \c coursera

-- Criar tabela usuario
CREATE TABLE IF NOT EXISTS usuario
(
  login text NOT NULL,
  email text,
  nome text,
  senha text,
  pontos integer,
  CONSTRAINT usuario_pkey PRIMARY KEY (login)
);
