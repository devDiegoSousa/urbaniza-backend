
DO $$
DECLARE
    city_cotia_id INTEGER;
    city_sao_paulo_id INTEGER;

    dept_id INTEGER;
    i INTEGER; -- Mantido para o loop de São Paulo
BEGIN
    -- Popular a tabela cities
    -- Cotia
    INSERT INTO cities (name, uf) VALUES ('Cotia', 'SP')
    ON CONFLICT (name, uf) DO UPDATE SET name = EXCLUDED.name
    RETURNING id INTO city_cotia_id;

    -- São Paulo
    INSERT INTO cities (name, uf) VALUES ('São Paulo', 'SP')
    ON CONFLICT (name, uf) DO UPDATE SET name = EXCLUDED.name
    RETURNING id INTO city_sao_paulo_id;


    -- Popular a tabela regions para Cotia
    INSERT INTO regions (name, city_id) VALUES
    ('Caucaia do Alto', city_cotia_id),
    ('Granja Vianna', city_cotia_id),
    ('Turiguara', city_cotia_id),
    ('Centro', city_cotia_id)
    ON CONFLICT (name, city_id) DO NOTHING;

    -- Popular a tabela regions para São Paulo
    INSERT INTO regions (name, city_id) VALUES
    ('Zona Leste', city_sao_paulo_id),
    ('Zona Sul', city_sao_paulo_id),
    ('Zona Norte', city_sao_paulo_id),
    ('Zona Oeste', city_sao_paulo_id)
    ON CONFLICT (name, city_id) DO NOTHING;

    -- Popular departments e segments para Cotia (COM NOMES ESPECÍFICOS)

    -- 1. Comunicação
    INSERT INTO departments (name, email, city_id) VALUES
    ('Comunicação', 'comunicacao@cotia.sp.gov.br', city_cotia_id)
    ON CONFLICT (name, city_id) DO UPDATE SET name = EXCLUDED.name RETURNING id INTO dept_id;
    INSERT INTO segments (name, department_id) VALUES
    ('Assessoria de Imprensa', dept_id),
    ('Mídias Sociais Oficiais', dept_id),
    ('Publicidade Institucional', dept_id)
    ON CONFLICT (name, department_id) DO NOTHING;

    -- 2. Cultura e Lazer
    INSERT INTO departments (name, email, city_id) VALUES
    ('Cultura e Lazer', 'cultura.lazer@cotia.sp.gov.br', city_cotia_id)
    ON CONFLICT (name, city_id) DO UPDATE SET name = EXCLUDED.name RETURNING id INTO dept_id;
    INSERT INTO segments (name, department_id) VALUES
    ('Eventos Culturais Municipais', dept_id),
    ('Bibliotecas e Espaços de Leitura', dept_id),
    ('Parques e Áreas de Lazer', dept_id)
    ON CONFLICT (name, department_id) DO NOTHING;

    -- 3. Defesa Civil
    INSERT INTO departments (name, email, city_id) VALUES
    ('Defesa Civil', 'defesacivil@cotia.sp.gov.br', city_cotia_id)
    ON CONFLICT (name, city_id) DO UPDATE SET name = EXCLUDED.name RETURNING id INTO dept_id;
    INSERT INTO segments (name, department_id) VALUES
    ('Prevenção de Riscos e Desastres', dept_id),
    ('Monitoramento de Áreas de Risco', dept_id),
    ('Atendimento a Emergências Climáticas', dept_id)
    ON CONFLICT (name, department_id) DO NOTHING;

    -- 4. Desenvolvimento Social & Periferias
    INSERT INTO departments (name, email, city_id) VALUES
    ('Desenvolvimento Social & Periferias', 'social.periferias@cotia.sp.gov.br', city_cotia_id)
    ON CONFLICT (name, city_id) DO UPDATE SET name = EXCLUDED.name RETURNING id INTO dept_id;
    INSERT INTO segments (name, department_id) VALUES
    ('Programas de Assistência Social', dept_id),
    ('Inclusão Produtiva e Emprego', dept_id),
    ('Apoio a Comunidades Vulneráveis', dept_id)
    ON CONFLICT (name, department_id) DO NOTHING;

    -- 5. Educação
    INSERT INTO departments (name, email, city_id) VALUES
    ('Educação', 'educacao@cotia.sp.gov.br', city_cotia_id)
    ON CONFLICT (name, city_id) DO UPDATE SET name = EXCLUDED.name RETURNING id INTO dept_id;
    INSERT INTO segments (name, department_id) VALUES
    ('Gestão de Escolas Municipais', dept_id),
    ('Programas de Alimentação Escolar', dept_id),
    ('Formação Continuada de Professores', dept_id)
    ON CONFLICT (name, department_id) DO NOTHING;

    -- 6. Esportes & Juventude
    INSERT INTO departments (name, email, city_id) VALUES
    ('Esportes & Juventude', 'esportes.juventude@cotia.sp.gov.br', city_cotia_id)
    ON CONFLICT (name, city_id) DO UPDATE SET name = EXCLUDED.name RETURNING id INTO dept_id;
    INSERT INTO segments (name, department_id) VALUES
    ('Fomento ao Esporte Amador', dept_id),
    ('Políticas Públicas para Juventude', dept_id),
    ('Administração de Centros Esportivos', dept_id)
    ON CONFLICT (name, department_id) DO NOTHING;

    -- 7. Gestão e Inovação
    INSERT INTO departments (name, email, city_id) VALUES
    ('Gestão e Inovação', 'gestao.inovacao@cotia.sp.gov.br', city_cotia_id)
    ON CONFLICT (name, city_id) DO UPDATE SET name = EXCLUDED.name RETURNING id INTO dept_id;
    INSERT INTO segments (name, department_id) VALUES
    ('Modernização Administrativa', dept_id),
    ('Tecnologia da Informação Municipal', dept_id),
    ('Processos e Qualidade', dept_id)
    ON CONFLICT (name, department_id) DO NOTHING;

    -- 8. Habitação
    INSERT INTO departments (name, email, city_id) VALUES
    ('Habitação', 'habitacao@cotia.sp.gov.br', city_cotia_id)
    ON CONFLICT (name, city_id) DO UPDATE SET name = EXCLUDED.name RETURNING id INTO dept_id;
    INSERT INTO segments (name, department_id) VALUES
    ('Programas de Moradia Popular', dept_id),
    ('Regularização Fundiária', dept_id),
    ('Fiscalização de Ocupações Irregulares', dept_id)
    ON CONFLICT (name, department_id) DO NOTHING;

    -- 9. Obras e Infraestrutura
    INSERT INTO departments (name, email, city_id) VALUES
    ('Obras e Infraestrutura', 'obras.infra@cotia.sp.gov.br', city_cotia_id)
    ON CONFLICT (name, city_id) DO UPDATE SET name = EXCLUDED.name RETURNING id INTO dept_id;
    INSERT INTO segments (name, department_id) VALUES
    ('Manutenção de Vias Públicas', dept_id),
    ('Projetos de Saneamento Básico', dept_id),
    ('Construção e Reforma de Prédios Públicos', dept_id)
    ON CONFLICT (name, department_id) DO NOTHING;

    -- 10. Relações Institucionais
    INSERT INTO departments (name, email, city_id) VALUES
    ('Relações Institucionais', 'rel.institucionais@cotia.sp.gov.br', city_cotia_id)
    ON CONFLICT (name, city_id) DO UPDATE SET name = EXCLUDED.name RETURNING id INTO dept_id;
    INSERT INTO segments (name, department_id) VALUES
    ('Articulação com Governos (Estadual/Federal)', dept_id),
    ('Convênios e Parcerias Estratégicas', dept_id),
    ('Representação Municipal', dept_id)
    ON CONFLICT (name, department_id) DO NOTHING;

    -- 11. Saúde
    INSERT INTO departments (name, email, city_id) VALUES
    ('Saúde', 'saude@cotia.sp.gov.br', city_cotia_id)
    ON CONFLICT (name, city_id) DO UPDATE SET name = EXCLUDED.name RETURNING id INTO dept_id;
    INSERT INTO segments (name, department_id) VALUES
    ('Atenção Básica (Postos de Saúde)', dept_id),
    ('Vigilância Sanitária e Epidemiológica', dept_id),
    ('Urgência e Emergência (UPA/SAMU)', dept_id)
    ON CONFLICT (name, department_id) DO NOTHING;

    -- 12. Segurança Pública
    INSERT INTO departments (name, email, city_id) VALUES
    ('Segurança Pública', 'seguranca.publica@cotia.sp.gov.br', city_cotia_id)
    ON CONFLICT (name, city_id) DO UPDATE SET name = EXCLUDED.name RETURNING id INTO dept_id;
    INSERT INTO segments (name, department_id) VALUES
    ('Guarda Civil Municipal (GCM)', dept_id),
    ('Monitoramento por Câmeras (COI)', dept_id),
    ('Programas de Prevenção à Violência', dept_id)
    ON CONFLICT (name, department_id) DO NOTHING;

    -- 13. Transportes & Mobilidade
    INSERT INTO departments (name, email, city_id) VALUES
    ('Transportes & Mobilidade', 'transportes.mobilidade@cotia.sp.gov.br', city_cotia_id)
    ON CONFLICT (name, city_id) DO UPDATE SET name = EXCLUDED.name RETURNING id INTO dept_id;
    INSERT INTO segments (name, department_id) VALUES
    ('Gestão do Transporte Público Coletivo', dept_id),
    ('Engenharia de Tráfego e Sinalização', dept_id),
    ('Planejamento de Mobilidade Urbana', dept_id)
    ON CONFLICT (name, department_id) DO NOTHING;

    -- 14. Verde & Meio Ambiente
    INSERT INTO departments (name, email, city_id) VALUES
    ('Verde & Meio Ambiente', 'meioambiente@cotia.sp.gov.br', city_cotia_id)
    ON CONFLICT (name, city_id) DO UPDATE SET name = EXCLUDED.name RETURNING id INTO dept_id;
    INSERT INTO segments (name, department_id) VALUES
    ('Fiscalização Ambiental e Licenciamento', dept_id),
    ('Educação Ambiental e Sustentabilidade', dept_id),
    ('Gestão de Parques e Áreas de Preservação', dept_id)
    ON CONFLICT (name, department_id) DO NOTHING;

    -- Popular departments e segments para São Paulo
    FOR i IN 1..10 LOOP
        INSERT INTO departments (name, email, city_id) VALUES
        ('Departamento ' || i || ' SP', 'dep' || i || '@sp.sp.gov.br', city_sao_paulo_id)
        ON CONFLICT (name, city_id) DO UPDATE SET name = EXCLUDED.name
        RETURNING id INTO dept_id;

        INSERT INTO segments (name, department_id) VALUES
        ('Segmento X do Dept ' || i || ' SP', dept_id),
        ('Segmento Y do Dept ' || i || ' SP', dept_id),
        ('Segmento Z do Dept ' || i || ' SP', dept_id)
        ON CONFLICT (name, department_id) DO NOTHING;
    END LOOP;

END;
$$;