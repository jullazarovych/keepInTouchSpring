ALTER TABLE search_history
    DROP CONSTRAINT fk8ll2cxj6i83mnrcyxrxl4b7dm;
ALTER TABLE search_history
    ADD CONSTRAINT fk_searchhistory_user
    FOREIGN KEY (user_id)
    REFERENCES users(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE;


