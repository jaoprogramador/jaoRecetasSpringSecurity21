-- Eliminar restricciones UNIQUE basándose en la columna
ALTER TABLE categorias DROP CONSTRAINT IF EXISTS categorias_nombre_key;
ALTER TABLE categorias DROP CONSTRAINT IF EXISTS ukqcog8b7hps1hioi9onqwjdt6y;
ALTER TABLE categorias DROP CONSTRAINT IF EXISTS categorias_procedencia_key;
ALTER TABLE categorias DROP CONSTRAINT IF EXISTS ukbbqtcb7pks4y1rr2p9076mpuh;
ALTER TABLE categorias DROP CONSTRAINT IF EXISTS categorias_tipo_alimento_key;
ALTER TABLE categorias DROP CONSTRAINT IF EXISTS ukbudl8r8m8e6wrqk2ma4fvff1g;

-- Por si acaso se crearon mediante un índice único asociado a la columna:
DROP INDEX IF EXISTS ukqcog8b7hps1hioi9onqwjdt6y;
DROP INDEX IF EXISTS ukbbqtcb7pks4y1rr2p9076mpuh;
DROP INDEX IF EXISTS ukbudl8r8m8e6wrqk2ma4fvff1g;