CREATE TABLE USER (
  id int(10) NOT NULL AUTO_INCREMENT,
  username varchar(20),
  password varchar(20),
  role varchar(10),
  firstname varchar(20),
  lastname varchar(20),
  email varchar(50),
  PRIMARY KEY (id)
);