\c biblestudy
DROP SCHEMA material CASCADE;
DROP FUNCTION popOldTstBooks;
DROP FUNCTION popNewTstBooks;
CREATE SCHEMA material;
DROP TYPE TESTAMENT;
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
	comment VARCHAR NULL
	);

CREATE TABLE material.Pointer(
	pointer_ID SERIAL PRIMARY KEY,
	verse_ID INT REFERENCES material.Verse(verse_ID),
	booknum INT REFERENCES material.Book(booknum)	
	);

CREATE TABLE material.Verse_Point(
	pointer_ID INT REFERENCES material.Pointer(pointer_ID), 
	verse_ID INT REFERENCES material.Verse(verse_ID)
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

SELECT popOldTstBooks('Genesis');
SELECT popNewTstBooks('Mathew');
