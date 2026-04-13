
CREATE TABLE IF NOT EXISTS drivers (
    id SERIAL PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    team VARCHAR(120) NOT NULL,
    nationality VARCHAR(80) NOT NULL
);

CREATE TABLE IF NOT EXISTS race_results (
    id SERIAL PRIMARY KEY,
    season INT NOT NULL CHECK (season >= 1950),
    grand_prix VARCHAR(120) NOT NULL,
    position INT NOT NULL CHECK (position > 0),
    points INT NOT NULL CHECK (points >= 0),
    driver_id INT NOT NULL REFERENCES drivers(id) ON DELETE RESTRICT
);
