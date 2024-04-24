CREATE TABLE address (
    id_address SERIAL PRIMARY KEY,
    public_place VARCHAR(100),
    neighborhood VARCHAR(100),
    zip_code VARCHAR(100),
    city VARCHAR(100),
    state VARCHAR(100)
);