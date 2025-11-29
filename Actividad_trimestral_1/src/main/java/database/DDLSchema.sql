create database almacen;

CREATE TABLE empleados
(
    id     INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    puesto VARCHAR(100)
);

CREATE TABLE productos
(
    id_producto          INT NOT NULL PRIMARY KEY,
    nombre_producto      VARCHAR(150),
    descripcion_producto VARCHAR(1000),
    cantidad             INT,
    precio               DOUBLE
);

CREATE TABLE pedidos
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    id_producto INT NOT NULL,
    id_empleado INT NOT NULL,
    cantidad    INT,
    fecha       DATE,
    FOREIGN KEY (id_producto) REFERENCES productos (id_producto),
    FOREIGN KEY (id_empleado) REFERENCES empleados (id)
);

CREATE INDEX idx_pedidos_empleado ON pedidos (id_empleado);
CREATE INDEX idx_pedidos_producto ON pedidos (id_producto);

CREATE TABLE productos_fav
(
    id_producto_fav INT AUTO_INCREMENT PRIMARY KEY,
    id_producto     INT NOT NULL,
    FOREIGN KEY (id_producto) REFERENCES productos (id_producto)
);