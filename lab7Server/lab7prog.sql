CREATE TABLE users(
                      id BIGSERIAL PRIMARY KEY,
                      username VARCHAR(255) NOT NULL UNIQUE,
                      password VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE studygroups(
                            id BIGSERIAL PRIMARY KEY,
                            owner_id BIGINT NOT NULL REFERENCES users(id),
                            name VARCHAR(255) NOT NULL,
                            coordinates_x BIGINT NOT NULL,
                            coordinates_y FLOAT NOT NULL,
                            creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            students_count INT NOT NULL,
                            expelled_students INT NOT NULL,
                            should_be_expelled BIGINT,
                            semester VARCHAR(50) NOT NULL,
                            admin_name VARCHAR(255) NOT NULL,
                            birthday DATE,
                            eye_color VARCHAR(50),
                            location_x BIGINT,
                            location_y INT,
                            location_name VARCHAR(255)
);