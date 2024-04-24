CREATE table client (
	id_client SERIAL PRIMARY key,
	name_client VARCHAR(100),
	date_of_birth DATE,
	social_security_number VARCHAR(14),
	date_of_creation TIMESTAMP
)