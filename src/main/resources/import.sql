INSERT INTO roles (role) VALUES ('ADMIN');
INSERT INTO roles (role) VALUES ('RENTER');
INSERT INTO roles (role) VALUES ('OWNER');

INSERT INTO registered_users (first_name, last_name, date_of_birth, phone_number, username, email, password) VALUES ('TOM', 'Han', '1999-09-09', '+34111111111', 'admin', 'admin@gmail.com', '$2a$10$OQOEjXFqmFTpX2WtgWqi..myO2nS1BMC0jaFm52ydH3gR.XbWLQVe');

INSERT INTO user_role (user_id, role_id) VALUES (1, 1);