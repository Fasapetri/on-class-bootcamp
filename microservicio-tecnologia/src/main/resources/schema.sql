CREATE TABLE IF NOT EXISTS tecnologia (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(90) NOT NULL
);


CREATE TABLE IF NOT EXISTS capacidad_tecnologia (
    id_capacidad BIGINT NOT NULL,
    id_tecnologia BIGINT NOT NULL,
    PRIMARY KEY (id_capacidad, id_tecnologia),
    CONSTRAINT fk_tecnologia FOREIGN KEY (id_tecnologia) REFERENCES tecnologia(id)
);