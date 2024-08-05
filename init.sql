DO
$do$
BEGIN
   IF EXISTS (
      SELECT FROM pg_catalog.pg_roles
      WHERE  rolname = 'bantads') THEN

      RAISE NOTICE 'Role "bantads" already exists. Skipping.';
   ELSE
      CREATE ROLE bantads LOGIN PASSWORD 'bantads';
   END IF;
END
$do$;

CREATE DATABASE "ms-client";
CREATE DATABASE "ms-manager";
CREATE DATABASE "ms-account-command";
CREATE DATABASE "ms-account-query";
GRANT ALL PRIVILEGES ON DATABASE "ms-client" TO bantads;
GRANT ALL PRIVILEGES ON DATABASE "ms-manager" TO bantads;
GRANT ALL PRIVILEGES ON DATABASE "ms-account-command" TO bantads;
GRANT ALL PRIVILEGES ON DATABASE "ms-account-query" TO bantads;

