create schema if not exists MCH_USER;
create schema if not exists MCH_ASSET;

CREATE TABLE IF NOT EXISTS MCH_USER.users (
    doc_id VARCHAR(120) NOT NULL,
    active INT  NULL ,
    email VARCHAR(120) NULL,
    group_id INT NULL,
    mid INT NULL,
    password VARCHAR(120) NULL,
    username VARCHAR(120) NULL
);

COMMIT;