CREATE TABLE IF NOT EXISTS bootcamp (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(90) NOT NULL,
    fecha_lanzamiento DATE NOT NULL,
    duracion INTEGER NOT NULL
);