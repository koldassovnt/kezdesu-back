CREATE USER file_user WITH ENCRYPTED PASSWORD '17l3w5IKxU5w2gxjZBq4XeRe';
CREATE DATABASE file_service WITH OWNER file_user;
GRANT ALL ON DATABASE file_service TO file_user;