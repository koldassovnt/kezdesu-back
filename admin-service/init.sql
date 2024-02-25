CREATE USER admin_user WITH ENCRYPTED PASSWORD '2lsWWA12stxXBoh4';
CREATE DATABASE admin_service WITH OWNER admin_user;
GRANT ALL ON DATABASE admin_service TO admin_user;