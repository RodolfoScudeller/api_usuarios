create table if not exists usuarios (
    cpf varchar(100) primary key,
    nome varchar(100) not null,
    ativo boolean,
    criadoEm datetime,
    atualizadoEm datetime
);