CREATE TABLE IF NOT EXISTS persona (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    edad INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS bootcamp_persona (
    id_persona BIGINT NOT NULL,
    id_bootcamp BIGINT NOT NULL,
    PRIMARY KEY (id_persona, id_bootcamp),
    CONSTRAINT fk_persona FOREIGN KEY (id_persona) REFERENCES persona(id)
);