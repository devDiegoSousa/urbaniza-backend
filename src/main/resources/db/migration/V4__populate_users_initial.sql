-- Popular a tabela users para testes
INSERT INTO users (email, password, first_name, last_name, role) VALUES
('testecitizen@urbaniza.com', '123456', 'Cidadão', 'Silva', 'CITIZEN'),
('admin.master@example.com', '123456', 'Admin', 'Master', 'DEPARTMENT')
ON CONFLICT (email) DO NOTHING; -- Evita erro se os usuários já existirem

-- Popular usuários para cada departamento de Cotia
INSERT INTO users (email, password, first_name, last_name, role) VALUES
('comunicacao@cotia.sp.gov.br', 'senhaDeptCotia123', 'Usuário Dept.', 'Comunicação Cotia', 'DEPARTMENT'),
('cultura.lazer@cotia.sp.gov.br', 'senhaDeptCotia123', 'Usuário Dept.', 'Cultura e Lazer Cotia', 'DEPARTMENT'),
('defesacivil@cotia.sp.gov.br', 'senhaDeptCotia123', 'Usuário Dept.', 'Defesa Civil Cotia', 'DEPARTMENT'),
('social.periferias@cotia.sp.gov.br', 'senhaDeptCotia123', 'Usuário Dept.', 'Desenv. Social & Periferias Cotia', 'DEPARTMENT'),
('educacao@cotia.sp.gov.br', 'senhaDeptCotia123', 'Usuário Dept.', 'Educação Cotia', 'DEPARTMENT'),
('esportes.juventude@cotia.sp.gov.br', 'senhaDeptCotia123', 'Usuário Dept.', 'Esportes & Juventude Cotia', 'DEPARTMENT'),
('gestao.inovacao@cotia.sp.gov.br', 'senhaDeptCotia123', 'Usuário Dept.', 'Gestão e Inovação Cotia', 'DEPARTMENT'),
('habitacao@cotia.sp.gov.br', 'senhaDeptCotia123', 'Usuário Dept.', 'Habitação Cotia', 'DEPARTMENT'),
('obras.infra@cotia.sp.gov.br', 'senhaDeptCotia123', 'Usuário Dept.', 'Obras e Infraestrutura Cotia', 'DEPARTMENT'),
('rel.institucionais@cotia.sp.gov.br', 'senhaDeptCotia123', 'Usuário Dept.', 'Relações Institucionais Cotia', 'DEPARTMENT'),
('saude@cotia.sp.gov.br', 'senhaDeptCotia123', 'Usuário Dept.', 'Saúde Cotia', 'DEPARTMENT'),
('seguranca.publica@cotia.sp.gov.br', 'senhaDeptCotia123', 'Usuário Dept.', 'Segurança Pública Cotia', 'DEPARTMENT'),
('transportes.mobilidade@cotia.sp.gov.br', 'senhaDeptCotia123', 'Usuário Dept.', 'Transportes & Mobilidade Cotia', 'DEPARTMENT'),
('meioambiente@cotia.sp.gov.br', 'senhaDeptCotia123', 'Usuário Dept.', 'Verde & Meio Ambiente Cotia', 'DEPARTMENT')
ON CONFLICT (email) DO NOTHING;