CREATE PROCEDURE DROP_TABLE_IF_EXISTS(IN TABLE_NAME VARCHAR(64))
PARAMETER STYLE JAVA MODIFIES SQL DATA LANGUAGE JAVA EXTERNAL NAME
  'net.ripe.rpki.validator.StoredProcedures.dropTableIfExists';

CALL DROP_TABLE_IF_EXISTS('LATEST_HTTP_SNAPSHOT');

CREATE TABLE LATEST_HTTP_SNAPSHOT (
  url           VARCHAR(2000) NOT NULL,
  session_id    VARCHAR(36)   NOT NULL,
  serial_number VARCHAR(128)  NOT NULL,
  PRIMARY KEY (url)
);

DROP PROCEDURE DROP_TABLE_IF_EXISTS;