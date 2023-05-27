create database csgo;
use csgo;

create table skin(
    id int primary key auto_increment,
    nome varchar(200),
    arma varchar(100),
    preco int,
    raridade varchar(100),
    imagem varchar(100)
);

create table user(
    id int primary key auto_increment,
    nome varchar(200) unique,
    cargo varchar(50),
    pontos int,
    email varchar(100) unique,
    senha varchar(100)
);

create table movement(
    id_venda int primary key auto_increment,
    id_vendedor int,
    id_comprador int,
    id_skin int,
    estado_venda tinyint,
    pontos int,
    CONSTRAINT fk_cl_vendedor FOREIGN KEY (id_vendedor) REFERENCES user(id),
    CONSTRAINT fk_cl_comprador FOREIGN KEY (id_comprador) REFERENCES user(id),
    CONSTRAINT fk_cl_skin FOREIGN KEY (id_skin) REFERENCES skin(id)
);

create table skinsuser(
    id_user int,
    id_skin int,
    CONSTRAINT fk_skin FOREIGN KEY (id_skin) REFERENCES skin(id),
    CONSTRAINT fk_user FOREIGN KEY (id_user) REFERENCES user(id)
);

/*Inserts da tabela */

insert into user(nome,cargo,pontos,email,senha) values("Carlos","cliente",200,"ca@gmail","9090");
insert into user(nome,cargo,pontos,email,senha) values("Administrador","admin",1000000000,"admin@admin.com","1090");

insert into skin(nome,arma,preco,raridade,imagem) values
('Dragon Lore', 'AWP', 10000, 'Factory New','AWP_Dragon_Lore.png'),
('Cobalt Quartz', 'Dual Berettas', 1500, 'Field-Tested','Dual_Berettas_Cobalt_Quartz.png'),
('Cyrex', 'M4A1-S', 7000, 'Minimal Wear','M4A1-S_Cyrex.png'),
('Hot Rod', 'M4A1-S', 6000, 'Factory New','M4A1-S_Hot_Rod.png'),
('Bloodsport', 'SCAR-20', 1000, 'Well-Worn','SCAR-20_Bloodsport.png'),
('Splash Jam', 'SCAR-20', 1400, 'Battle-Scarred','SCAR-20_Splash_Jam.png'),
('Integrale', 'SG 553', 4000, 'Factory New','SG_553_Integrale.png'),
('Hazard Pay', 'SG 553', 3000, 'Minimal Wear','SG_553_Hazard_Pay.png'),
('Chatterbox', 'Galil AR', 5000, 'Field-Tested','Galil_AR_Chatterbox.png'),
('Sugar Rush', 'Galil AR', 2500, 'Well-Worn','Galil_AR_Sugar_Rush.png'),
('Roll Cage', 'FAMAS', 3000, 'Field-Tested','FAMAS_Roll_Cage.png'),
('Commemoration', 'FAMAS', 2500, 'Minimal Wear','FAMAS_Commemoration.png'),
('Fire Serpent', 'AK-47', 9500, 'Minimal Wear','AK-47_Fire_Serpent.png'),
('Vulcan', 'AK-47', 8000, 'Factory New','AK-47_Vulcan.png'),
('Asiimov', 'M4A4', 6000, 'Field-Tested','M4A4_Asiimov.png'),
('Poseidon', 'M4A4', 4000, 'Minimal Wear','M4A4_Poseidon.png'),
('Decimator', 'Tec-9', 3000, 'Factory New','Tec-9_Decimator.png'),
('Avalanche', 'Tec-9', 2000, 'Field-Tested','Tec-9_Avalanche.png'),
('Vogue', 'Glock-18', 5000, 'Factory New','Glock-18_Vogue.png'),
('Moonrise', 'Glock-18', 2000, 'Field-Tested','Glock-18_Moonrise.png');

insert into skinsuser(id_user, id_skin) values(1,1);
insert into skinsuser(id_user, id_skin) values(1,3);

insert into movement(id_vendedor,id_skin, estado_venda, pontos) values (1,3,false,7000);

/*Selects das tabelas*/

select * from user;
select * from user where user.id = 2;
select * from user where email="lalala";
select * from user where email="ca@gmail" or nome="Lucas";

select * from skin;

select * from movement;

select * from skinsuser;
SELECT * FROM skinsuser WHERE id_user=1;

SELECT m.id_venda,uc.nome AS nome_comprador, uv.nome AS nome_vendedor, s.nome AS nome_skin, m.pontos, m.estado_venda
FROM movement AS m
LEFT JOIN user AS uc ON m.id_comprador = uc.id
JOIN user AS uv ON m.id_vendedor = uv.id
JOIN skin AS s ON m.id_skin = s.id
ORDER BY m.id_venda;


/*Updates*/

update user set senha="456" where id=1;

/*Deletes da tabela*/
delete from skin where id=4;

delete from skinsuser where id_skin = 3 and id_user=1;

delete from user where id=6;

/*Drops*/

drop table skinsuser;
drop table movement;
drop table skin;
drop table user;