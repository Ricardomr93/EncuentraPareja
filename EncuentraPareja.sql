/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 100129
 Source Host           : localhost:3306
 Source Schema         : encuentrapareja

 Target Server Type    : MySQL
 Target Server Version : 100129
 File Encoding         : 65001

 Date: 10/03/2021 23:21:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for amigo
-- ----------------------------
DROP TABLE IF EXISTS `amigo`;
CREATE TABLE `amigo`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `idEnv` int NULL DEFAULT NULL,
  `idRec` int NULL DEFAULT NULL,
  `aceptado` tinyint(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idRec`(`idRec`) USING BTREE,
  INDEX `idEnv`(`idEnv`) USING BTREE,
  CONSTRAINT `amigo_ibfk_1` FOREIGN KEY (`idRec`) REFERENCES `usuario` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `amigo_ibfk_2` FOREIGN KEY (`idEnv`) REFERENCES `usuario` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for archivo
-- ----------------------------
DROP TABLE IF EXISTS `archivo`;
CREATE TABLE `archivo`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `archivo` varchar(200) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for interes
-- ----------------------------
DROP TABLE IF EXISTS `interes`;
CREATE TABLE `interes`  (
  `interes` varchar(30) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  PRIMARY KEY (`interes`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for mensaje
-- ----------------------------
DROP TABLE IF EXISTS `mensaje`;
CREATE TABLE `mensaje`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `iduser` int NULL DEFAULT NULL,
  `idrem` int NULL DEFAULT NULL,
  `mensaje` varchar(300) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for mensajearchivo
-- ----------------------------
DROP TABLE IF EXISTS `mensajearchivo`;
CREATE TABLE `mensajearchivo`  (
  `idmensaje` int NOT NULL,
  `idarchivo` int NOT NULL,
  PRIMARY KEY (`idmensaje`, `idarchivo`) USING BTREE,
  INDEX `idarchivo`(`idarchivo`) USING BTREE,
  CONSTRAINT `mensajearchivo_ibfk_1` FOREIGN KEY (`idmensaje`) REFERENCES `mensaje` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `mensajearchivo_ibfk_2` FOREIGN KEY (`idarchivo`) REFERENCES `archivo` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for perfil
-- ----------------------------
DROP TABLE IF EXISTS `perfil`;
CREATE TABLE `perfil`  (
  `nick` varchar(30) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `bio` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `foto` blob NULL,
  `iduser` int NOT NULL,
  PRIMARY KEY (`iduser`) USING BTREE,
  CONSTRAINT `perfil_ibfk_1` FOREIGN KEY (`iduser`) REFERENCES `usuario` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for preferencia
-- ----------------------------
DROP TABLE IF EXISTS `preferencia`;
CREATE TABLE `preferencia`  (
  `id` int NOT NULL,
  `relacion` varchar(30) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `artisticos` tinyint(1) NULL DEFAULT NULL,
  `deportivos` int NULL DEFAULT NULL,
  `politicos` int NULL DEFAULT NULL,
  `tqhijos` varchar(30) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `genero` varchar(30) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `interes` varchar(30) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `interes`(`interes`) USING BTREE,
  INDEX `relacion`(`relacion`) USING BTREE,
  INDEX `tqhijos`(`tqhijos`) USING BTREE,
  CONSTRAINT `preferencia_ibfk_1` FOREIGN KEY (`id`) REFERENCES `usuario` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `preferencia_ibfk_2` FOREIGN KEY (`interes`) REFERENCES `interes` (`interes`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `preferencia_ibfk_3` FOREIGN KEY (`relacion`) REFERENCES `relacion` (`relacion`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `preferencia_ibfk_4` FOREIGN KEY (`tqhijos`) REFERENCES `tqhijos` (`hijos`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for relacion
-- ----------------------------
DROP TABLE IF EXISTS `relacion`;
CREATE TABLE `relacion`  (
  `relacion` varchar(30) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  PRIMARY KEY (`relacion`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for tqhijos
-- ----------------------------
DROP TABLE IF EXISTS `tqhijos`;
CREATE TABLE `tqhijos`  (
  `hijos` varchar(30) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  PRIMARY KEY (`hijos`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for usuario
-- ----------------------------
DROP TABLE IF EXISTS `usuario`;
CREATE TABLE `usuario`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `email` varchar(30) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `activado` tinyint(1) NULL DEFAULT 0,
  `admin` tinyint(1) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for usuariomensaje
-- ----------------------------
DROP TABLE IF EXISTS `usuariomensaje`;
CREATE TABLE `usuariomensaje`  (
  `iduser` int NOT NULL,
  `idmensaje` int NOT NULL,
  PRIMARY KEY (`iduser`, `idmensaje`) USING BTREE,
  INDEX `idmensaje`(`idmensaje`) USING BTREE,
  CONSTRAINT `usuariomensaje_ibfk_1` FOREIGN KEY (`idmensaje`) REFERENCES `mensaje` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `usuariomensaje_ibfk_2` FOREIGN KEY (`iduser`) REFERENCES `usuario` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
