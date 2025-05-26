-- Criação do ENUM para user_role
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'user_role') THEN
        CREATE TYPE user_role AS ENUM ('citizen', 'admin', 'manager');
    END IF;
END
$$;

-- Usuário
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(150) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    role user_role NOT NULL
);

-- Cidade
CREATE TABLE IF NOT EXISTS cities (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    uf VARCHAR(2) NOT NULL
);

-- Região
CREATE TABLE IF NOT EXISTS regions (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    city_id INTEGER NOT NULL REFERENCES cities(id) ON DELETE CASCADE
);

-- Departamento
CREATE TABLE IF NOT EXISTS departments (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(50),
    city_id INTEGER NOT NULL REFERENCES cities(id) ON DELETE CASCADE
);

-- Segmento
CREATE TABLE IF NOT EXISTS segments (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    department_id INTEGER NOT NULL REFERENCES departments(id) ON DELETE CASCADE
);

-- Tipos de status
CREATE TABLE IF NOT EXISTS status_types (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);

-- Denúncia
CREATE TABLE IF NOT EXISTS reports (
    id SERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description TEXT NOT NULL,
    latitude DECIMAL(9,6) NOT NULL,
    longitude DECIMAL(9,6) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    photo_url VARCHAR(255),
    photo_public_id VARCHAR(255),
    anonymous BOOLEAN NOT NULL,
    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    status_id INTEGER NOT NULL REFERENCES status_types(id) ON DELETE CASCADE,
    segment_id INTEGER NOT NULL REFERENCES segments(id) ON DELETE CASCADE
);

-- Histórico de Status da Denúncia
CREATE TABLE IF NOT EXISTS status_history (
    id SERIAL PRIMARY KEY,
    modified_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    report_id INTEGER NOT NULL REFERENCES reports(id) ON DELETE CASCADE,
    current_status INTEGER NOT NULL REFERENCES status_types(id) ON DELETE CASCADE,
    previous_status INTEGER NOT NULL REFERENCES status_types(id) ON DELETE CASCADE,
    modified_by INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE
);
