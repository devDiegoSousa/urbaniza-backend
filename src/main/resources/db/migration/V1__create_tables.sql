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
    uf VARCHAR(2) NOT NULL,
    UNIQUE(name, uf)
);

-- Região
CREATE TABLE IF NOT EXISTS regions (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    city_id INTEGER NOT NULL REFERENCES cities(id) ON DELETE CASCADE,
    UNIQUE(name, city_id)
);
CREATE INDEX IF NOT EXISTS idx_regions_city_id ON regions(city_id);

-- Departamento
CREATE TABLE IF NOT EXISTS departments (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(50),
    city_id INTEGER NOT NULL REFERENCES cities(id) ON DELETE RESTRICT,
    UNIQUE(name, city_id)
);
CREATE INDEX IF NOT EXISTS idx_departments_city_id ON departments(city_id);

-- Segmento
CREATE TABLE IF NOT EXISTS segments (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    department_id INTEGER NOT NULL REFERENCES departments(id) ON DELETE CASCADE,
    UNIQUE(name, department_id)
);
CREATE INDEX IF NOT EXISTS idx_segments_department_id ON segments(department_id);

CREATE TABLE IF NOT EXISTS status_types (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) UNIQUE NOT NULL
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
    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE RESTRICT,
    status_id INTEGER NOT NULL REFERENCES status_types(id) ON DELETE RESTRICT,
    segment_id INTEGER NOT NULL REFERENCES segments(id) ON DELETE RESTRICT
);
CREATE INDEX IF NOT EXISTS idx_reports_user_id ON reports(user_id);
CREATE INDEX IF NOT EXISTS idx_reports_status_id ON reports(status_id);
CREATE INDEX IF NOT EXISTS idx_reports_segment_id ON reports(segment_id);
CREATE INDEX IF NOT EXISTS idx_reports_created_at ON reports(created_at);

-- Histórico de Status da Denúncia
CREATE TABLE IF NOT EXISTS status_history (
    id SERIAL PRIMARY KEY,
    modified_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    report_id INTEGER NOT NULL REFERENCES reports(id) ON DELETE CASCADE,
    current_status INTEGER NOT NULL REFERENCES status_types(id) ON DELETE RESTRICT,
    previous_status INTEGER NOT NULL REFERENCES status_types(id) ON DELETE RESTRICT,
    modified_by INTEGER NOT NULL REFERENCES users(id) ON DELETE RESTRICT
);
CREATE INDEX IF NOT EXISTS idx_status_history_report_id ON status_history(report_id);
CREATE INDEX IF NOT EXISTS idx_status_history_current_status ON status_history(current_status);
CREATE INDEX IF NOT EXISTS idx_status_history_previous_status ON status_history(previous_status);
CREATE INDEX IF NOT EXISTS idx_status_history_modified_by ON status_history(modified_by);