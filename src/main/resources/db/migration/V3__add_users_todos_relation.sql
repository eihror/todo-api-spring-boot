ALTER TABLE todos
    ADD COLUMN user_id UUID NOT NULL;

CREATE INDEX idx_todos_user_id ON todos(user_id);

ALTER TABLE todos
    ADD CONSTRAINT fk_todos_user
    FOREIGN KEY (user_id)
    REFERENCES users(id)
    ON DELETE CASCADE;