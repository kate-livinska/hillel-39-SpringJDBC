--
--
--
-- schema
--
--
--
CREATE TABLE t_customer (
                            id BIGINT PRIMARY KEY AUTO_INCREMENT,
                            full_name VARCHAR(150),
                            email VARCHAR(150),
                            social_sec_num INT
);

--
--
--
-- data
--
--
--
INSERT INTO t_customer (full_name, email, social_sec_num)
VALUES
    ('John Doe', 'johndoe@example.com', 123456789),
    ('Jane Smith', 'janesmith@example.com', 987654321),
    ('Robert Johnson', 'robert.johnson@example.com', 112233445),
    ('Emily Davis', 'emily.davis@example.com', 556677889),
    ('Michael Brown', 'michael.brown@example.com', 998877665);