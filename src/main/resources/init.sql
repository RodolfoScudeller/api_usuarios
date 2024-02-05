create database api_usuarios_teste;
use api_usuarios_teste;
create table usuarios (
    cpf varchar(100) primary key,
    nome varchar(100) not null,
    ativo boolean,
    criadoEm datetime,
    atualizadoEm datetime
);
create database api_usuarios;
use api_usuarios;
create table usuarios (
  cpf varchar(100) primary key,
  nome varchar(100) not null,
  ativo boolean,
  criadoEm datetime,
  atualizadoEm datetime
);