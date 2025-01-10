  DO $$
            DECLARE
            i INT;
            BEGIN
            FOR i IN 1..100 LOOP
            INSERT INTO bills.executor (phone, name, organization)
            VALUES (
            CONCAT('+7', LPAD(i::TEXT, 10, '0')),
            CONCAT('Имя ', i),
            CONCAT('Организация ', i%7 + 1)
            );
            END LOOP;
            END $$;