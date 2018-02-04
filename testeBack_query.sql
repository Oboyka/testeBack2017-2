drop database if exists testeback;
create database testeback;
use testeback;

create table tb_customer_account(

	id_customer int unsigned not null primary key,
    cpf_cnpj varchar(14) not null unique,
    nm_customer varchar(64) not null,
    is_active boolean not null,
    vl_total decimal(10,2) not null

);