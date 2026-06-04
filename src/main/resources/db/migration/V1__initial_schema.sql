-- ==========================================================
-- CONTEXT
-- ==========================================================
CREATE TABLE context
(
    id         TEXT PRIMARY KEY,
    name       TEXT      NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL
);

-- ==========================================================
-- USER ACCOUNT
-- ==========================================================
CREATE TABLE user_account
(
    id         TEXT PRIMARY KEY,
    name       TEXT      NOT NULL,
    email      TEXT      NOT NULL UNIQUE,
    password   TEXT      NOT NULL,
    ind_ativo  TEXT      NOT NULL DEFAULT 'S',
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT ck_user_account_ind_ativo
        CHECK (ind_ativo IN ('S', 'N'))
);

CREATE INDEX idx_user_account_email
    ON user_account (email);

-- ==========================================================
-- ROLE
-- ==========================================================
CREATE TABLE role
(
    id          TEXT PRIMARY KEY,
    context_id  TEXT      NOT NULL,
    name        TEXT      NOT NULL,
    description TEXT      NOT NULL,
    ind_ativo   TEXT      NOT NULL DEFAULT 'S',
    created_at  TIMESTAMP NOT NULL,
    CONSTRAINT fk_role_context
        FOREIGN KEY (context_id)
            REFERENCES context (id),

    CONSTRAINT ck_role_ind_ativo
        CHECK (ind_ativo IN ('S', 'N'))
);

CREATE UNIQUE INDEX uk_role_context_name
    ON role (context_id, name);

-- ==========================================================
-- ACTION
-- ==========================================================
CREATE TABLE action
(
    id          TEXT PRIMARY KEY,
    name        TEXT      NOT NULL UNIQUE,
    description TEXT      NOT NULL,
    created_at  TIMESTAMP NOT NULL
);

-- ==========================================================
-- USER ROLE
-- ==========================================================
CREATE TABLE user_role
(
    user_id    TEXT      NOT NULL,
    role_id    TEXT      NOT NULL,
    created_at TIMESTAMP NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_role_user
        FOREIGN KEY (user_id)
            REFERENCES user_account (id),

    CONSTRAINT fk_user_role_role
        FOREIGN KEY (role_id)
            REFERENCES role (id)
);

CREATE INDEX idx_user_role_user
    ON user_role (user_id);

CREATE INDEX idx_user_role_role
    ON user_role (role_id);

-- ==========================================================
-- ROLE ACTION
-- ==========================================================
CREATE TABLE role_action
(
    role_id    TEXT      NOT NULL,
    action_id  TEXT      NOT NULL,
    created_at TIMESTAMP NOT NULL,
    PRIMARY KEY (role_id, action_id),
    CONSTRAINT fk_role_action_role
        FOREIGN KEY (role_id)
            REFERENCES role (id),

    CONSTRAINT fk_role_action_action
        FOREIGN KEY (action_id)
            REFERENCES action (id)
);

CREATE INDEX idx_role_action_role
    ON role_action (role_id);

CREATE INDEX idx_role_action_action
    ON role_action (action_id);

-- ==========================================================
-- SEED: CONTEXT
-- ==========================================================
INSERT INTO context (id,
                     name,
                     created_at)
VALUES ('CTX-KANBAN',
        'KANBAN',
        CURRENT_TIMESTAMP);

-- ==========================================================
-- SEED: ACTIONS
-- ==========================================================
INSERT INTO action (id,
                    name,
                    description,
                    created_at)
VALUES ('ACT-BOARD-VIEW',
        'BOARD_VIEW',
        'Visualizar quadro',
        CURRENT_TIMESTAMP),
       ('ACT-BOARD-CREATE',
        'BOARD_CREATE',
        'Criar quadro',
        CURRENT_TIMESTAMP),
       ('ACT-TASK-CREATE',
        'TASK_CREATE',
        'Criar tarefa',
        CURRENT_TIMESTAMP),
       ('ACT-TASK-UPDATE',
        'TASK_UPDATE',
        'Atualizar tarefa',
        CURRENT_TIMESTAMP),
       ('ACT-TASK-DELETE',
        'TASK_DELETE',
        'Excluir tarefa',
        CURRENT_TIMESTAMP);

-- ==========================================================
-- SEED: ROLE ADMIN
-- ==========================================================
INSERT INTO role (id,
                  context_id,
                  name,
                  description,
                  ind_ativo,
                  created_at)
VALUES ('ROLE-ADMIN',
        'CTX-KANBAN',
        'ADMIN',
        'Administrador do sistema',
        'S',
        CURRENT_TIMESTAMP);

-- ==========================================================
-- ROLE ACTIONS ADMIN
-- ==========================================================
INSERT INTO role_action (role_id,
                         action_id,
                         created_at)
SELECT 'ROLE-ADMIN',
       id,
       CURRENT_TIMESTAMP
FROM action;

INSERT INTO user_account (id, name, email, password, ind_ativo, created_at)
VALUES ('0f8e3563-1690-437b-81c9-8c9fd398e49f', 'Kelvin', 'kelvin@koldex.com', '$2a$10$mLI/WreX8HhKi3gT.rcVdeqY.jZlmeu608edTIBrn5RWy69AVw1YC', 'S',
        '2026-06-01 19:01:17');

INSERT INTO user_role (user_id, role_id, created_at)
VALUES ('0f8e3563-1690-437b-81c9-8c9fd398e49f',
        'ROLE-ADMIN',
        '2026-06-01 19:01:17.000');


