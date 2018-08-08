\c biblestudy
DROP SCHEMA material CASCADE;
DROP FUNCTION popOldTstBooks CASCADE;
DROP FUNCTION popNewTstBooks CASCADE;
CREATE SCHEMA material;
DROP TYPE TESTAMENT CASCADE;
CREATE TYPE TESTAMENT AS ENUM('old','new');

CREATE TABLE material.Book(
	booknum SERIAL PRIMARY KEY,
	booktitle VARCHAR(30) NOT NULL,
	testament TESTAMENT NOT NULL
	);

CREATE TABLE material.Verse(
	verse_ID SERIAL PRIMARY KEY,
	chapter INT NOT NULL,
	vnum INT NOT NULL,
	booknum INT REFERENCES material.Book(booknum) NOT NULL,
	verse VARCHAR NOT NULL,
	comment VARCHAR NULL,
	point_to INTEGER[]
	);


CREATE FUNCTION popOldTstBooks(book VARCHAR)
RETURNS void AS
$BODY$
BEGIN
	INSERT INTO material.Book(booktitle,testament)
	VALUES(book,'old');
END
$BODY$
LANGUAGE 'plpgsql' VOLATILE;

CREATE FUNCTION popNewTstBooks(book VARCHAR)
RETURNS void AS
$BODY$
BEGIN
        INSERT INTO material.Book(booktitle,testament)
        VALUES(book,'new');
END
$BODY$
LANGUAGE 'plpgsql' VOLATILE;
