DO $$
BEGIN
    -- Adicionar 'DEPARTMENT' SE NÃO EXISTIR.
    IF NOT EXISTS (SELECT 1 FROM pg_enum WHERE enumtypid = 'user_role'::regtype AND enumlabel = 'DEPARTMENT') THEN
        ALTER TYPE user_role ADD VALUE 'DEPARTMENT';
    END IF;

    -- Adicionar 'CITIZEN' SE NÃO EXISTIR.
    IF NOT EXISTS (SELECT 1 FROM pg_enum WHERE enumtypid = 'user_role'::regtype AND enumlabel = 'CITIZEN') THEN
        ALTER TYPE user_role ADD VALUE 'CITIZEN';
    END IF;
END
$$;