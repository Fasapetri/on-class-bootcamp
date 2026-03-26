CREATE TABLE IF NOT EXISTS capacidad (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(90) NOT NULL
);

CREATE TABLE IF NOT EXISTS bootcamp_capacidad (
    id_bootcamp BIGINT NOT NULL,
    id_capacidad BIGINT NOT NULL,
    PRIMARY KEY (id_bootcamp, id_capacidad),
    CONSTRAINT fk_capacidad FOREIGN KEY (id_capacidad) REFERENCES capacidad(id)
);