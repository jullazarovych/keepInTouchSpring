
ALTER TABLE user_universities
    DROP CONSTRAINT fktf7ppiusbk7lher3wac82i48c;
ALTER TABLE user_universities
    ADD CONSTRAINT fk_user_universities_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE;

ALTER TABLE user_universities
    DROP CONSTRAINT fkry309r53yc651kvyyk26vt6jl;
ALTER TABLE user_universities
    ADD CONSTRAINT fk_user_universities_university
    FOREIGN KEY (university_id)
    REFERENCES universities(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE;
