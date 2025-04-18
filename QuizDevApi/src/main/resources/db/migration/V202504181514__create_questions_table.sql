CREATE TABLE questions (
   id SERIAL PRIMARY KEY,
   title VARCHAR(255) NOT NULL,
   description TEXT NOT NULL,
   level VARCHAR(20) NOT NULL, -- JUNIOR, PLENO, SENIOR
   technology_id INTEGER NOT NULL REFERENCES technologies(id),
   is_xgh BOOLEAN DEFAULT FALSE
);