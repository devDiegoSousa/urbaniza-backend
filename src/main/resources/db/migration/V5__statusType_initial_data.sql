-- Popular a tabela status_types
INSERT INTO status_types (name) VALUES
('Novo'),
('Em Análise'),
('Resolvendo'),
('Resolvido'),
('Rejeitado'),
('Aprovado')
ON CONFLICT (name) DO NOTHING;