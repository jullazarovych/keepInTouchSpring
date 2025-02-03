ALTER TABLE events
DROP CONSTRAINT fkfmvcvglxed08fybooa9l613iw;

-- Додаємо новий зовнішній ключ з потрібними опціями
ALTER TABLE events
    ADD CONSTRAINT fk_event_university
        FOREIGN KEY (university_id)
            REFERENCES universities(id)
            ON DELETE CASCADE
            ON UPDATE CASCADE;