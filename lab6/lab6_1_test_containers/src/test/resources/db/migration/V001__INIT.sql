CREATE TABLE IF NOT EXISTS student (
  student_nummber INT NOT NULL,
  student_name VARCHAR(255) NOT NULL,
  student_email VARCHAR(255) NOT NULL,
  student_birthdate DATE NOT NULL,
  PRIMARY KEY (student_nummber)
)