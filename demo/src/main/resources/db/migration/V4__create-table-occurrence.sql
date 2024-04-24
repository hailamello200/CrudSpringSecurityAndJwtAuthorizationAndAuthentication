CREATE TABLE occurrence (
    id_occurrence SERIAL PRIMARY KEY,
    id_client INT,
    id_address INT,
    date_occurrence TIMESTAMP,
    satus VARCHAR(100),
    FOREIGN KEY (id_client) REFERENCES client(id_client),
    FOREIGN KEY (id_address) REFERENCES address(id_address)
);