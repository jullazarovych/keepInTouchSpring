ALTER TABLE universities
    DROP CONSTRAINT fkthvg60a5dvs9au8aul9dskj2f;
ALTER TABLE universities
ADD CONSTRAINT fk_university_city
FOREIGN KEY (city_id)
REFERENCES cities(id)
ON DELETE SET NULL
   ON UPDATE CASCADE;
