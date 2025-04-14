CREATE TABLE transaction (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             account_number VARCHAR(255) NOT NULL,
                             trx_amount DOUBLE NOT NULL,
                             description VARCHAR(255),
                             trx_date VARCHAR(255) NOT NULL,
                             trx_time VARCHAR(255) NOT NULL,
                             customer_id VARCHAR(255) NOT NULL,
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                             modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                             version BIGINT NOT NULL
);

CREATE INDEX idx_account_number ON transaction (account_number);
CREATE INDEX idx_trx_date ON transaction (trx_date);
CREATE INDEX idx_customer_id ON transaction (customer_id);