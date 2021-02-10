DROP TABLE IF EXISTS exchange_rate;

CREATE TABLE exchange_rate (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  source_currency VARCHAR(3) NOT NULL,
  target_currency VARCHAR(3) NOT NULL,
  rate NUMERIC(14, 8) DEFAULT NULL
);

INSERT INTO exchange_rate (source_currency, target_currency, rate) VALUES
  ('PEN', 'USD', 0.30959752),
  ('USD', 'PEN', 3.23),
  ('PEN', 'EUR', 0.25),
  ('EUR', 'PEN', 4),
  ('USD', 'EUR', 0.84745762),
  ('EUR', 'USD', 1.18);
