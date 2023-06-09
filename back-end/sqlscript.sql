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

/*create table skinsuser(
    id_user int,
    id_skin int,
    CONSTRAINT fk_skin FOREIGN KEY (id_skin) REFERENCES skin(id),
    CONSTRAINT fk_user FOREIGN KEY (id_user) REFERENCES user(id)
);*/

/*Inserts da tabela */

insert into user(nome,cargo,pontos,email,senha) values("Carlos","cliente",200,"ca@gmail","9090"), 
("Administrador","admin",100000,"admin@admin.com","admin");
insert into user(id,nome,cargo,pontos,email,senha) values (3,"EstoqueDinamico","admin",100000,"estoqued@admin.com","admin"),
(4,"EstoqueEstatico","admin",100000,"estoques@admin.com","admin");



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
('Moonrise', 'Glock-18', 2000, 'Field-Tested','Glock-18_Moonrise.png'),

("Mehndi", "P250", 2000,"Minimal Wear",'P250_Mehndi.png'),
("Muertos", "P250", 3000,"Well-Worn",'P250_Muertos.png'),
("Triumvirate", "Five-SeveN", 4500,"Field-Tested",'Five-SeveN_Triumvirate.png'),
("Angry Mob", "Five-SeveN", 5000,"Well-Worn",'Five-SeveN_Angry_Mob.png'),
("Kill Confirmed", "USP-S", 8000,"Factory New",'USP-S_Kill_Confirmed.png'),
("The Traitor", "USP-S", 5000,"Field-Tested",'USP-S_The_Traitor.png'),
("Fade", "R8 Revolver", 4000,"Battle-Scarred",'R8_Revolver_Fade.png'),
("Skull Crusher", "R8 Revolver", 2000,"Well-Worn",'R8_Revolver_Skull_Crusher.png'),
("Golden Koi", "Desert Eagle", 6000,"Battle-Scarred",'Desert_Eagle_Golden_Koi.png'),
("Code Red", "Desert Eagle", 3000,"Field-Tested",'Desert_Eagle_Code_Red.png'),
("Bloodsport", "MP7", 5000,"Factory New",'MP7_Bloodsport.png'),
("Impire", "MP7", 1500,"Field-Tested",'MP7_Impire.png'),
("Whiteout", "MP7", 2000,"Well-Worn",'MP7_Whiteout.png');


insert into user_skins_user(user_id, skins_user_id) values(1,1), (1,3), 
(2,4), (2,5), (2,6), (2,7), (2,8), (2,9), (2,10),
(3,11), (3,12), (3,13), (3,14), (3,15), (3,16), (3,17), (3,18), (3,19), (3,20),
(4,21),(4,22),(4,23),(4,24),(4,25),(4,26),(4,27),(4,28),(4,29),(4,30),(4,31),(4,32),(4,33);

insert into movement(id_vendedor,id_skin, estado_venda, pontos) values (1,3,false,7000), (1,1,false,10000), (2,4,false,6000);

/*Selects das tabelas*/

select * from user;
select * from user where user.id = 2;
select * from user where email="lalala";
select * from user where email="ca@gmail" or nome="Lucas";

select * from skin;

select * from movement;

SELECT * FROM user_skins_user WHERE id_user=1;

SELECT m.id_venda, uc.nome AS nome_comprador, uv.nome AS nome_vendedor, CONCAT(s.arma, ' ', s.nome) AS nome_skin, m.pontos, m.estado_venda
FROM movement AS m
LEFT JOIN user AS uc ON m.id_comprador = uc.id
LEFT JOIN user AS uv ON m.id_vendedor = uv.id
JOIN skin AS s ON m.id_skin = s.id
ORDER BY m.id_venda;


SELECT s.id AS id_skin, s.nome, s.arma, s.preco, s.raridade, s.imagem,
    CASE
        WHEN EXISTS (SELECT 1 FROM movement WHERE id_skin = s.id) THEN
            CASE
                WHEN (SELECT estado_venda FROM movement WHERE id_skin = s.id LIMIT 1) = true THEN false
                ELSE true
            END
        ELSE false
    END AS is_in_movement,
    m.id_venda
FROM skin s
JOIN user_skins_user su ON s.id = su.skins_user_id
LEFT JOIN movement m ON s.id = m.id_skin
WHERE su.user_id = 4;

select * from user_skins_user;

/*Updates*/

update user set senha="456" where id=1;

/*Deletes da tabela*/
delete from skin where id=4;

delete from user_skins_user where skins_user_id = 3 and id_user=1;

delete from user where id=6;

/*Drops*/

drop table user_skins_user;
drop table movement;
drop table skin;
drop table user;