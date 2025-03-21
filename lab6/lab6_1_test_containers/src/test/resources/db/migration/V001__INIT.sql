CREATE TABLE IF NOT EXISTS student (
  student_number INT NOT NULL,
  student_name VARCHAR(255) NOT NULL,
  student_email VARCHAR(255) NOT NULL,
  student_birthdate DATE NOT NULL,
  student_sex CHAR(1) NOT NULL,
  PRIMARY KEY (student_number)
);

INSERT INTO student (student_number, student_name, student_email, student_birthdate, student_sex) VALUES
(1, 'Alice Johnson', 'alice@student.com', '2000-05-15', 'F'),
(2, 'Bob Smith', 'bob@student.com', '1999-08-22', 'M'),
(3, 'Charlie Davis', 'charlie@student.com', '2001-02-10', 'M'),
(4, 'David White', 'david@student.com', '2002-11-05', 'M'),
(5, 'Emma Brown', 'emma@student.com', '2000-07-30', 'F'),
(6, 'Franklin Harris', 'franklin@student.com', '1998-12-12', 'M'),
(7, 'Grace Lee', 'grace@student.com', '2001-09-17', 'F'),
(8, 'Henry Walker', 'henry@student.com', '1999-04-25', 'M'),
(9, 'Isabella Adams', 'isabella@student.com', '2002-06-19', 'F'),
(10, 'Jack Wilson', 'jack@student.com', '2000-10-01', 'M');
