CREATE SEQUENCE urlmetadata_id_seq INCREMENT BY 50;

CREATE TABLE urlmetadata(
  id 									            BIGINT 	      PRIMARY KEY,
	hash							              VARCHAR(255)	UNIQUE NOT NULL,
  long_url                        VARCHAR(500)  NOT NULL,
  short_url                       VARCHAR(50)   NOT NULL,
  creation_timestamp              TIMESTAMP,
  updation_timestamp              TIMESTAMP
);