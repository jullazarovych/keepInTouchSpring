
ALTER TABLE event_categories
    DROP CONSTRAINT fkr1pvd2finvdolgyyntti13d3x;

ALTER TABLE event_categories
    ADD CONSTRAINT fk_event_categories_category
        FOREIGN KEY (category_id)
            REFERENCES categories(id)
            ON DELETE SET NULL
            ON UPDATE CASCADE;

ALTER TABLE event_categories
    DROP CONSTRAINT fk25gan1thxtb38jlco3w9pjsdo;

ALTER TABLE event_categories
    ADD CONSTRAINT fk_event_categories_event
        FOREIGN KEY (event_id)
            REFERENCES events(id)
            ON DELETE NO ACTION
            ON UPDATE CASCADE;
