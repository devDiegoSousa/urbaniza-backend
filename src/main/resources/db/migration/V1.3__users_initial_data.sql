BEGIN;

-- Popular a tabela users (iniciais)
INSERT INTO users (email, password, first_name, last_name, role) VALUES
('citizenteste@urbaniza.com', 'senha123forte', 'Citizen', 'teste', 'CITIZEN'),
('admin.master@example.com', 'senha123admin', 'Admin', 'Master', 'CITIZEN'),
ON CONFLICT (email) DO NOTHING; -- Evita erro se os usuários já existirem

-- Popular usuários para cada departamento de Cotia
INSERT INTO users (email, password, first_name, last_name, role) VALUES
('comunicacao.cotia@example.com', 'senhaDeptCotia123', 'Usuário Dept.', 'Comunicação Cotia', 'DEPARTMENT'),
('cultura.lazer.cotia@example.com', 'senhaDeptCotia123', 'Usuário Dept.', 'Cultura e Lazer Cotia', 'DEPARTMENT'),
('defesacivil.cotia@example.com', 'senhaDeptCotia123', 'Usuário Dept.', 'Defesa Civil Cotia', 'DEPARTMENT'),
('social.periferias.cotia@example.com', 'senhaDeptCotia123', 'Usuário Dept.', 'Desenv. Social & Periferias Cotia', 'DEPARTMENT'),
('educacao.cotia@example.com', 'senhaDeptCotia123', 'Usuário Dept.', 'Educação Cotia', 'DEPARTMENT'),
('esportes.juventude.cotia@example.com', 'senhaDeptCotia123', 'Usuário Dept.', 'Esportes & Juventude Cotia', 'DEPARTMENT'),
('gestao.inovacao.cotia@example.com', 'senhaDeptCotia123', 'Usuário Dept.', 'Gestão e Inovação Cotia', 'DEPARTMENT'),
('habitacao.cotia@example.com', 'senhaDeptCotia123', 'Usuário Dept.', 'Habitação Cotia', 'DEPARTMENT'),
('obras.infra.cotia@example.com', 'senhaDeptCotia123', 'Usuário Dept.', 'Obras e Infraestrutura Cotia', 'DEPARTMENT'),
('rel.institucionais.cotia@example.com', 'senhaDeptCotia123', 'Usuário Dept.', 'Relações Institucionais Cotia', 'DEPARTMENT'),
('saude.cotia@example.com', 'senhaDeptCotia123', 'Usuário Dept.', 'Saúde Cotia', 'DEPARTMENT'),
('seguranca.publica.cotia@example.com', 'senhaDeptCotia123', 'Usuário Dept.', 'Segurança Pública Cotia', 'DEPARTMENT'),
('transportes.mobilidade.cotia@example.com', 'senhaDeptCotia123', 'Usuário Dept.', 'Transportes & Mobilidade Cotia', 'DEPARTMENT'),
('meioambiente.cotia@example.com', 'senhaDeptCotia123', 'Usuário Dept.', 'Verde & Meio Ambiente Cotia', 'DEPARTMENT')
ON CONFLICT (email) DO NOTHING;

COMMIT;