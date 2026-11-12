INSERT INTO categorias
(id, nombre, procedencia, tipo_alimento)
VALUES(1, 'Italiana', 'Italia', 'Pastas y Masas');
INSERT INTO categorias
(id, nombre, procedencia, tipo_alimento)
VALUES(2, 'Mexicana', 'Mexico', 'Antojitos');
INSERT INTO categorias
(id, nombre, procedencia, tipo_alimento)
VALUES(19, 'Italiana', 'Sicilia', 'Postre');
INSERT INTO categorias
(id, nombre, procedencia, tipo_alimento)
VALUES(3, 'Japonesa', 'Japon', 'Pescados y Mariscos');
INSERT INTO categorias
(id, nombre, procedencia, tipo_alimento)
VALUES(14, 'Coreana', 'Corea', 'Pastas');
INSERT INTO categorias
(id, nombre, procedencia, tipo_alimento)
VALUES(15, 'Alemana', 'Alemania', 'Hamburguesas');
INSERT INTO categorias
(id, nombre, procedencia, tipo_alimento)
VALUES(16, 'China', 'China', 'Arroces');
INSERT INTO categorias
(id, nombre, procedencia, tipo_alimento)
VALUES(17, 'Española', 'Bilbao', 'Pescado');
INSERT INTO categorias
(id, nombre, procedencia, tipo_alimento)
VALUES(18, 'Valenciana', 'Valencia', 'Arroces1');

--
INSERT INTO recetas
(id, categoria_id, descripcion, fecha_alta, fecha_modificacion, ingredientes, instrucciones, porciones, tiempo_preparacion_minutos, titulo, activo)
VALUES(3, 3, 'Rollito de sushi clásico relleno de aguacate, pepino y surimi.', '2026-10-09 11:24:49.058',
       '2026-10-09 11:24:49.058', '{"Arroz para sushi","Alga Nori",Surimi,Aguacate,Pepino,"Semillas de sésamo"}',
       '1. Extender el arroz sobre el alga nori. 2. Voltear y colocar el relleno. 3. Enrollar firmemente con ayuda de la esterilla y cortar en porciones.',
       3, 30, 'Sushi Roll California', 'S');
INSERT INTO recetas
(id, categoria_id, descripcion, fecha_alta, fecha_modificacion, ingredientes, instrucciones, porciones, tiempo_preparacion_minutos, titulo, activo)
VALUES(5, 2, 'Tradicionales tacos mexicanos de cerdo marinado con achiote y piña.', '2026-10-13 11:08:22.678', '2026-10-13 11:08:22.678', '{"Carne de cerdo",Achiote,"Chiles guajillo",Piña,"Tortillas de maíz","Cebolla y cilantro"}', '1. Marinar la carne con la pasta de achiote y chiles. 2. Cocinar la carne finamente picada. 3. Servir en tortillas con piña, cilantro y cebolla.', 4, 45, 'Tacos al Pastor', 'S');