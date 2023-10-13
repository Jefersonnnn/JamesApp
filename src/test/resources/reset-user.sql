-- Desativa a restrição de chave estrangeira temporariamente, se necessário
SET REFERENTIAL_INTEGRITY FALSE;

TRUNCATE TABLE TB_USERS RESTART IDENTITY;

-- Ativa a restrição de chave estrangeira novamente, se desativada
SET REFERENTIAL_INTEGRITY TRUE;