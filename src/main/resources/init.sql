create database if not exists api_usuarios_teste;
use api_usuarios_teste;
create table if not exists usuarios (
  cpf varchar(100) primary key,
  nome varchar(100) not null,
  ativo boolean,
  criadoEm datetime,
  atualizadoEm datetime
);
create database if not exists api_usuarios;
use api_usuarios;
create table if not exists usuarios (
  cpf varchar(100) primary key,
  nome varchar(100) not null,
  ativo boolean,
  criadoEm datetime,
  atualizadoEm datetime
);