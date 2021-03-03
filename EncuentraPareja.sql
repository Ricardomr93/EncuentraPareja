CREATE TABLE `usuario` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `nombre` varchar(30),
  `email` varchar(30),
  `password` varchar(255),
  `activado` boolean DEFAULT false,
  `admin` boolean DEFAULT false
);

CREATE TABLE `perfil` (
  `nick` varchar(30),
  `bio` varchar(255),
  `foto` blob,
  `iduser` int
);

CREATE TABLE `amigo` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `idEnv` int,
  `idRec` int,
  `aceptado` boolean
);

CREATE TABLE `preferencia` (
  `id` int,
  `relacion` varchar(30),
  `artisticos` boolean,
  `deportivos` int,
  `politicos` int,
  `tqhijos` varchar(30),
  `interes` varchar(30)
);

CREATE TABLE `tqhijos` (
  `hijos` varchar(30) PRIMARY KEY NOT NULL
);

CREATE TABLE `relacion` (
  `relacion` varchar(30) PRIMARY KEY NOT NULL
);

CREATE TABLE `interes` (
  `interes` varchar(30) PRIMARY KEY NOT NULL
);

CREATE TABLE `mensaje` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `iduser` int,
  `idrem` int,
  `mensaje` varchar(300)
);

CREATE TABLE `mensajearchivo` (
  `idmensaje` int,
  `idarchivo` int
);

CREATE TABLE `archivo` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `archivo` varchar(200)
);

CREATE TABLE `usuariomensaje` (
  `iduser` int,
  `idmensaje` int
);

ALTER TABLE `usuariomensaje` ADD FOREIGN KEY (`idmensaje`) REFERENCES `mensaje` (`id`);

ALTER TABLE `usuariomensaje` ADD FOREIGN KEY (`iduser`) REFERENCES `usuario` (`id`);

ALTER TABLE `mensajearchivo` ADD FOREIGN KEY (`idmensaje`) REFERENCES `mensaje` (`id`);

ALTER TABLE `mensajearchivo` ADD FOREIGN KEY (`idarchivo`) REFERENCES `archivo` (`id`);

ALTER TABLE `preferencia` ADD FOREIGN KEY (`id`) REFERENCES `usuario` (`id`);

ALTER TABLE `preferencia` ADD FOREIGN KEY (`interes`) REFERENCES `interes` (`interes`);

ALTER TABLE `preferencia` ADD FOREIGN KEY (`relacion`) REFERENCES `relacion` (`relacion`);

ALTER TABLE `preferencia` ADD FOREIGN KEY (`tqhijos`) REFERENCES `tqhijos` (`hijos`);

ALTER TABLE `perfil` ADD FOREIGN KEY (`iduser`) REFERENCES `usuario` (`id`);

ALTER TABLE `amigo` ADD FOREIGN KEY (`idRec`) REFERENCES `usuario` (`id`);

ALTER TABLE `amigo` ADD FOREIGN KEY (`idEnv`) REFERENCES `usuario` (`id`);

INSERT INTO relacion VALUES ("Seria");
INSERT INTO relacion VALUES ("Esporadica");
INSERT INTO tqhijos VALUES ("Tiene hijos");
INSERT INTO tqhijos VALUES ("Quiere hijos");
INSERT INTO tqhijos VALUES ("No tiene hijos");
INSERT INTO tqhijos VALUES ("No quiere hijos");
INSERT INTO interes VALUES ("Hombres");
INSERT INTO interes VALUES ("Mujeres");
INSERT INTO interes VALUES ("Ambos");
INSERT INTO usuario VALUES (1,"Ricardo","ricar@gmail.com","40bd001563085fc35165329ea1ff5c5ecbdbbeef",1,1);