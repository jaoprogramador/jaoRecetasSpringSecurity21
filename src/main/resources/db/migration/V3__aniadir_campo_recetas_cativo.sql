-- Añadir la columna solo si no existe
ALTER TABLE recetas ADD COLUMN IF NOT EXISTS activo VARCHAR(1) DEFAULT 'S';

-- Actualizar por seguridad los nulos que pudieran haber quedado
UPDATE recetas SET activo = 'S' WHERE activo IS NULL;