-- 1. Insertar Categorías (respetando los constraints unique=true)
INSERT INTO categorias (nombre, procedencia, tipoAlimento) VALUES
                                                               ('Italiana', 'Italia', 'Pastas y Masas'),
                                                               ('Mexicana', 'Mexico', 'Antojitos'),
                                                               ('Japonesa', 'Japon', 'Pescados y Mariscos');
INSERT INTO categorias (nombre, procedencia, tipoAlimento) VALUES
                                                               ('Coreana', 'Corea', 'Pastas'),
                                                               ('Alemana', 'Alemania', 'Hamburguesas'),
                                                               ('China', 'China', 'Arroces'),
                                                               ('Española', 'Vasca', 'Pescados'),
                                                               ('Española', 'Valencia', 'Arroces');

-- 2. Insertar Recetas asociadas a las categorías anteriores
INSERT INTO recetas (categoria_id, titulo, descripcion, tiempo_preparacion_minutos, porciones, ingredientes, instrucciones, fecha_alta, fecha_modificacion) VALUES
                                                                                                                                                                (
                                                                                                                                                                    1,
                                                                                                                                                                    'Spaghetti alla Carbonara',
                                                                                                                                                                    'Auténtica receta italiana de pasta carbonara cremosa sin crema de leche.',
                                                                                                                                                                    25,
                                                                                                                                                                    2,
                                                                                                                                                                    ARRAY['Spaghetti', 'Panceta o Guanciale', 'Yemas de huevo', 'Queso Pecorino Romano', 'Pimienta negra'],
                                                                                                                                                                    '1. Cocer la pasta al dente. 2. Dorar el guanciale. 3. Mezclar yemas con queso y pimienta. 4. Unir todo fuera del fuego con un poco de agua de cocción.',
                                                                                                                                                                    NOW(),
                                                                                                                                                                    NOW()
                                                                                                                                                                ),
                                                                                                                                                                (
                                                                                                                                                                    2,
                                                                                                                                                                    'Tacos al Pastor',
                                                                                                                                                                    'Tradicionales tacos mexicanos de cerdo marinado con achiote y piña.',
                                                                                                                                                                    45,
                                                                                                                                                                    4,
                                                                                                                                                                    ARRAY['Carne de cerdo', 'Achiote', 'Chiles guajillo', 'Piña', 'Tortillas de maíz', 'Cebolla y cilantro'],
                                                                                                                                                                    '1. Marinar la carne con la pasta de achiote y chiles. 2. Cocinar la carne finamente picada. 3. Servir en tortillas con piña, cilantro y cebolla.',
                                                                                                                                                                    NOW(),
                                                                                                                                                                    NOW()
                                                                                                                                                                ),
                                                                                                                                                                (
                                                                                                                                                                    3,
                                                                                                                                                                    'Sushi Roll California',
                                                                                                                                                                    'Rollito de sushi clásico relleno de aguacate, pepino y surimi.',
                                                                                                                                                                    30,
                                                                                                                                                                    3,
                                                                                                                                                                    ARRAY['Arroz para sushi', 'Alga Nori', 'Surimi', 'Aguacate', 'Pepino', 'Semillas de sésamo'],
                                                                                                                                                                    '1. Extender el arroz sobre el alga nori. 2. Voltear y colocar el relleno. 3. Enrollar firmemente con ayuda de la esterilla y cortar en porciones.',
                                                                                                                                                                    NOW(),
                                                                                                                                                                    NOW()
                                                                                                                                                                );