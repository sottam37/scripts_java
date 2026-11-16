-- Instalação em um banco novo, selecionado no cliente MariaDB.
-- Reexecutar preserva personagens e registros de catálogo já existentes.
SET NAMES utf8mb4;

CREATE TABLE IF NOT EXISTS racas (
    id INT UNSIGNED NOT NULL PRIMARY KEY,
    nome VARCHAR(80) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS classes (
    id INT UNSIGNED NOT NULL PRIMARY KEY,
    nome VARCHAR(80) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS reinos (
    id INT UNSIGNED NOT NULL PRIMARY KEY,
    nome VARCHAR(80) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS armas (
    id INT UNSIGNED NOT NULL PRIMARY KEY,
    nome VARCHAR(80) NOT NULL,
    dano INT NULL CHECK (dano >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS habilidades (
    id INT UNSIGNED NOT NULL PRIMARY KEY,
    nome VARCHAR(80) NOT NULL,
    efeito VARCHAR(100) NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS classe_armas (
    classe_id INT UNSIGNED NOT NULL,
    arma_id INT UNSIGNED NOT NULL,
    PRIMARY KEY (classe_id, arma_id),
    FOREIGN KEY (classe_id) REFERENCES classes(id),
    FOREIGN KEY (arma_id) REFERENCES armas(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS classe_habilidades (
    classe_id INT UNSIGNED NOT NULL,
    habilidade_id INT UNSIGNED NOT NULL,
    PRIMARY KEY (classe_id, habilidade_id),
    FOREIGN KEY (classe_id) REFERENCES classes(id),
    FOREIGN KEY (habilidade_id) REFERENCES habilidades(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS personagens (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(80) NOT NULL CHECK (CHAR_LENGTH(TRIM(nome)) > 0),
    raca_id INT UNSIGNED NOT NULL,
    classe_id INT UNSIGNED NOT NULL,
    habilidade_id INT UNSIGNED NOT NULL,
    arma_id INT UNSIGNED NOT NULL,
    reino_id INT UNSIGNED NULL,
    nivel INT NOT NULL DEFAULT 1 CHECK (nivel >= 1),
    experiencia INT NOT NULL DEFAULT 0 CHECK (experiencia >= 0),
    vida INT NOT NULL DEFAULT 100 CHECK (vida >= 0),
    mana INT NOT NULL DEFAULT 100 CHECK (mana >= 0),
    forca INT NOT NULL DEFAULT 10 CHECK (forca >= 0),
    FOREIGN KEY (raca_id) REFERENCES racas(id),
    FOREIGN KEY (reino_id) REFERENCES reinos(id),
    FOREIGN KEY (classe_id, arma_id) REFERENCES classe_armas(classe_id, arma_id),
    FOREIGN KEY (classe_id, habilidade_id)
        REFERENCES classe_habilidades(classe_id, habilidade_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

START TRANSACTION;

INSERT INTO racas (id, nome) VALUES
    (1, 'Humano'),
    (2, 'Elfo'),
    (3, 'Anão'),
    (4, 'Orc'),
    (5, 'Troll'),
    (6, 'Goblin'),
    (7, 'Dragão'),
    (8, 'Demônio'),
    (9, 'Anjo'),
    (10, 'Vampiro'),
    (11, 'Licantropo'),
    (12, 'Zumbi'),
    (13, 'Esqueleto'),
    (14, 'Gigante'),
    (15, 'Titã'),
    (16, 'Deus')
ON DUPLICATE KEY UPDATE id = VALUES(id);

INSERT INTO classes (id, nome) VALUES
    (1, 'Guerreiro'),
    (2, 'Mago'),
    (3, 'Arqueiro'),
    (4, 'Ladino'),
    (5, 'Paladino'),
    (6, 'Druida'),
    (7, 'Clérigo')
ON DUPLICATE KEY UPDATE id = VALUES(id);

INSERT INTO reinos (id, nome) VALUES
    (1, 'Reino do Fogo'),
    (2, 'Reino do Gelo'),
    (3, 'Reino do Vento'),
    (4, 'Reino da Terra'),
    (5, 'Reino da Luz'),
    (6, 'Reino das Trevas'),
    (7, 'Reino do Mar'),
    (8, 'Reino do Deserto')
ON DUPLICATE KEY UPDATE id = VALUES(id);

INSERT INTO armas (id, nome, dano) VALUES
    (1, 'Espada Longa', NULL),
    (2, 'Machado de Batalha', NULL),
    (3, 'Martelo de Guerra', NULL),
    (4, 'Cajado Arcano', NULL),
    (5, 'Varinha Mágica', NULL),
    (6, 'Grimório', NULL),
    (7, 'Arco Longo', NULL),
    (8, 'Besta', 80),
    (9, 'Arco Curto', NULL),
    (10, 'Adagas Gêmeas', NULL),
    (11, 'Punhal Envenenado', NULL),
    (12, 'Espada Curta', NULL),
    (13, 'Espada', 40),
    (14, 'Machado', 50),
    (15, 'Arco', 30),
    (16, 'Lança', 60),
    (17, 'Adaga', 20),
    (18, 'Cajado', 10),
    (19, 'Martelo', 70),
    (20, 'Punhos', NULL)
ON DUPLICATE KEY UPDATE id = VALUES(id);

INSERT INTO habilidades (id, nome, efeito) VALUES
    (1, 'Golpe Poderoso', NULL),
    (2, 'Escudo Protetor', NULL),
    (3, 'Grito de Guerra', NULL),
    (4, 'Bola de Fogo', 'Dano 50'),
    (5, 'Raio de Gelo', NULL),
    (6, 'Teletransporte', 'Duração de 3 segundos'),
    (7, 'Tiro Certeiro', NULL),
    (8, 'Chuva de Flechas', NULL),
    (9, 'Olhar de Águia', NULL),
    (10, 'Ataque Furtivo', NULL),
    (11, 'Invisibilidade', 'Duração de 10 segundos'),
    (12, 'Roubo Rápido', NULL),
    (13, 'Bola de Gelo', 'Dano 30'),
    (14, 'Furacão de Vento', 'Dano 40'),
    (15, 'Raio', 'Dano 60'),
    (16, 'Regeneração', 'Cura 20'),
    (17, 'Escudo', 'Duração de 5 segundos'),
    (18, 'Invocar Animal', 'Duração de 15 segundos'),
    (19, 'Invocar Demônio', 'Duração de 20 segundos'),
    (20, 'Invocar Elemental', 'Duração de 25 segundos'),
    (21, 'Invocar Dragão', 'Duração de 30 segundos'),
    (22, 'Invocar Fantasma', 'Duração de 35 segundos'),
    (23, 'Invocar Zumbi', 'Duração de 40 segundos'),
    (24, 'Invocar Esqueleto', 'Duração de 45 segundos'),
    (25, 'Invocar Gigante', 'Duração de 50 segundos'),
    (26, 'Invocar Titã', 'Duração de 55 segundos'),
    (27, 'Invocar Deus', 'Duração de 60 segundos'),
    (28, 'Invocar Anjo', 'Duração de 65 segundos'),
    (29, 'Invocar Demônio', 'Duração de 70 segundos'),
    (30, 'Invocar Dragão', 'Duração de 75 segundos'),
    (31, 'Invocar Fantasma', 'Duração de 80 segundos'),
    (32, 'Invocar Zumbi', 'Duração de 85 segundos'),
    (33, 'Invocar Esqueleto', 'Duração de 90 segundos'),
    (34, 'Invocar Gigante', 'Duração de 95 segundos'),
    (35, 'Invocar Titã', 'Duração de 100 segundos'),
    (36, 'Invocar Deus', 'Duração de 105 segundos'),
    (37, 'Invocar Anjo', 'Duração de 110 segundos'),
    (38, 'Invocar Demônio', 'Duração de 115 segundos'),
    (39, 'Invocar Dragão', 'Duração de 120 segundos'),
    (40, 'Invocar Fantasma', 'Duração de 125 segundos'),
    (41, 'Invocar Zumbi', 'Duração de 130 segundos'),
    (42, 'Invocar Esqueleto', 'Duração de 135 segundos'),
    (43, 'Invocar Gigante', 'Duração de 140 segundos'),
    (44, 'Invocar Titã', 'Duração de 145 segundos'),
    (45, 'Invocar Deus', 'Duração de 150 segundos'),
    (46, 'Invocar Anjo', 'Duração de 155 segundos'),
    (47, 'Invocar Demônio', 'Duração de 160 segundos'),
    (48, 'Ataque Básico', NULL)
ON DUPLICATE KEY UPDATE id = VALUES(id);

INSERT INTO classe_armas (classe_id, arma_id) VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (1, 13),
    (1, 14),
    (1, 16),
    (1, 19),
    (2, 4),
    (2, 5),
    (2, 6),
    (2, 18),
    (3, 7),
    (3, 8),
    (3, 9),
    (3, 15),
    (4, 10),
    (4, 11),
    (4, 12),
    (4, 17),
    (5, 13),
    (5, 16),
    (5, 19),
    (5, 20),
    (6, 18),
    (6, 16),
    (6, 20),
    (7, 18),
    (7, 19),
    (7, 20)
ON DUPLICATE KEY UPDATE classe_id = VALUES(classe_id);

INSERT INTO classe_habilidades (classe_id, habilidade_id) VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (1, 17),
    (2, 4),
    (2, 5),
    (2, 6),
    (2, 13),
    (2, 15),
    (2, 19),
    (2, 20),
    (2, 21),
    (2, 24),
    (2, 25),
    (2, 26),
    (2, 27),
    (2, 28),
    (2, 29),
    (2, 30),
    (2, 31),
    (2, 32),
    (2, 33),
    (2, 34),
    (2, 35),
    (2, 36),
    (2, 37),
    (2, 38),
    (2, 39),
    (2, 40),
    (2, 41),
    (2, 42),
    (2, 43),
    (2, 44),
    (2, 45),
    (2, 46),
    (2, 47),
    (3, 7),
    (3, 8),
    (3, 9),
    (4, 10),
    (4, 11),
    (4, 12),
    (5, 2),
    (5, 16),
    (5, 17),
    (5, 48),
    (6, 14),
    (6, 16),
    (6, 18),
    (6, 22),
    (6, 48),
    (7, 16),
    (7, 17),
    (7, 23),
    (7, 48)
ON DUPLICATE KEY UPDATE classe_id = VALUES(classe_id);

COMMIT;
