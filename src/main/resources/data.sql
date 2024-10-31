CREATE TABLE stats
(
    id               BIGINT PRIMARY KEY AUTO_INCREMENT,
    count_human_dna  INT,
    count_mutant_dna INT,
    ratio            DECIMAL(4, 2)
);

INSERT INTO stats (count_human_dna, count_mutant_dna, ratio)
VALUES (0, 0, 0);