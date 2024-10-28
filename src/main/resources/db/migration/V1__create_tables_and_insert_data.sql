-- Create the operator table
CREATE TABLE operator (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY(id)
);

-- Create the customer table
CREATE TABLE customer (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY(id)
);

-- Create the appointment table
CREATE TABLE appointment (
    id BIGINT NOT NULL AUTO_INCREMENT,
    operator_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (operator_id) REFERENCES operator(id),
    FOREIGN KEY (customer_id) REFERENCES customer(id)
);

-- Insert data into the operator table
INSERT INTO operator ( name) VALUES ( 'Operator 1');
INSERT INTO operator ( name) VALUES ( 'Operator 2');
INSERT INTO operator ( name) VALUES ( 'Operator 3');

-- Insert data into the customer table
INSERT INTO customer ( name) VALUES ( 'Customer 1');

-- Insert sample appointments for the previous, current, and next day
-- Appointments for the previous day
INSERT INTO appointment ( operator_id, customer_id, start_time, end_time)
VALUES ( 1, 1, CURRENT_TIMESTAMP - INTERVAL '1' DAY, CURRENT_TIMESTAMP - INTERVAL '1' DAY + INTERVAL '1' HOUR);
INSERT INTO appointment ( operator_id, customer_id, start_time, end_time)
VALUES ( 2, 1, CURRENT_TIMESTAMP - INTERVAL '1' DAY + INTERVAL '1' HOUR, CURRENT_TIMESTAMP - INTERVAL '1' DAY + INTERVAL '2' HOUR);

-- Appointments for the current day
INSERT INTO appointment ( operator_id, customer_id, start_time, end_time)
VALUES   (1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '1' HOUR),
         (1, 1, CURRENT_TIMESTAMP + INTERVAL '1' HOUR, CURRENT_TIMESTAMP + INTERVAL '2' HOUR),
         (1, 1, CURRENT_TIMESTAMP + INTERVAL '3' HOUR, CURRENT_TIMESTAMP + INTERVAL '4' HOUR),
         (2, 1, CURRENT_TIMESTAMP + INTERVAL '1' HOUR, CURRENT_TIMESTAMP + INTERVAL '2' HOUR),
         (2, 1, CURRENT_TIMESTAMP + INTERVAL '3' HOUR, CURRENT_TIMESTAMP + INTERVAL '4' HOUR),
         (3, 1, CURRENT_TIMESTAMP + INTERVAL '3' HOUR, CURRENT_TIMESTAMP + INTERVAL '4' HOUR);

-- Appointments for the next day
INSERT INTO appointment ( operator_id, customer_id, start_time, end_time)
VALUES ( 1, 1, CURRENT_TIMESTAMP + INTERVAL '1' DAY, CURRENT_TIMESTAMP + INTERVAL '1' DAY + INTERVAL '1' HOUR);
INSERT INTO appointment ( operator_id, customer_id, start_time, end_time)
VALUES (2, 1, CURRENT_TIMESTAMP + INTERVAL '1' DAY + INTERVAL '1' HOUR, CURRENT_TIMESTAMP + INTERVAL '1' DAY + INTERVAL '2' HOUR);
