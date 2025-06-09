-- Popular a tabela users para testes
INSERT INTO users (email, password, first_name, last_name, role, email_confirmed) VALUES
('testecitizen@urbaniza.com', '123456', 'Cidadão', 'Silva', 'CITIZEN', True),
('admin.master@example.com', '123456', 'Admin', 'Master', 'DEPARTMENT',  True)
ON CONFLICT (email) DO NOTHING; -- Evita erro se os usuários já existirem

-- Popular usuários para cada departamento de Cotia
INSERT INTO users (email, password, first_name, last_name, role, email_confirmed) VALUES
('comunicacao@cotia.sp.gov.br', 'senhaDeptCotia123', 'Usuário Dept.', 'Comunicação Cotia', 'DEPARTMENT',  True),
('cultura.lazer@cotia.sp.gov.br', 'senhaDeptCotia123', 'Usuário Dept.', 'Cultura e Lazer Cotia', 'DEPARTMENT', True),
('defesacivil@cotia.sp.gov.br', 'senhaDeptCotia123', 'Usuário Dept.', 'Defesa Civil Cotia', 'DEPARTMENT',  True),
('social.periferias@cotia.sp.gov.br', 'senhaDeptCotia123', 'Usuário Dept.', 'Desenv. Social & Periferias Cotia', 'DEPARTMENT',  True),
('educacao@cotia.sp.gov.br', 'senhaDeptCotia123', 'Usuário Dept.', 'Educação Cotia', 'DEPARTMENT',  True),
('esportes.juventude@cotia.sp.gov.br', 'senhaDeptCotia123', 'Usuário Dept.', 'Esportes & Juventude Cotia', 'DEPARTMENT',  True),
('gestao.inovacao@cotia.sp.gov.br', 'senhaDeptCotia123', 'Usuário Dept.', 'Gestão e Inovação Cotia', 'DEPARTMENT',  True),
('habitacao@cotia.sp.gov.br', 'senhaDeptCotia123', 'Usuário Dept.', 'Habitação Cotia', 'DEPARTMENT',  True),
('obras.infra@cotia.sp.gov.br', 'senhaDeptCotia123', 'Usuário Dept.', 'Obras e Infraestrutura Cotia', 'DEPARTMENT',  True),
('rel.institucionais@cotia.sp.gov.br', 'senhaDeptCotia123', 'Usuário Dept.', 'Relações Institucionais Cotia', 'DEPARTMENT',  True),
('saude@cotia.sp.gov.br', 'senhaDeptCotia123', 'Usuário Dept.', 'Saúde Cotia', 'DEPARTMENT',  True),
('seguranca.publica@cotia.sp.gov.br', 'senhaDeptCotia123', 'Usuário Dept.', 'Segurança Pública Cotia', 'DEPARTMENT', True),
('transportes.mobilidade@cotia.sp.gov.br', 'senhaDeptCotia123', 'Usuário Dept.', 'Transportes & Mobilidade Cotia', 'DEPARTMENT',  True),
('meioambiente@cotia.sp.gov.br', 'senhaDeptCotia123', 'Usuário Dept.', 'Verde & Meio Ambiente Cotia', 'DEPARTMENT',  True)
ON CONFLICT (email) DO NOTHING;