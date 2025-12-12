CREATE TABLE customers.customer_phone_numbers
(
    id             UUID    DEFAULT gen_random_uuid() NOT NULL,
    customer_id    UUID                              NOT NULL,

    country_code   CHAR(2)                           NOT NULL,
    phone_number   TEXT                              NOT NULL,

    is_primary     BOOLEAN DEFAULT FALSE,
    verified_at    TIMESTAMP WITH TIME ZONE,
    deleted_at     TIMESTAMP WITH TIME ZONE,
    deleted_reason TEXT,

    version        BIGINT                            NOT NULL,
    created_at     TIMESTAMP WITH TIME ZONE          NOT NULL,
    updated_at     TIMESTAMP WITH TIME ZONE          NOT NULL,

    CONSTRAINT pk_customer_phone_numbers PRIMARY KEY (id),
    CONSTRAINT fk_customer_phone_numbers_customer
        FOREIGN KEY (customer_id) REFERENCES customers.customers (id),
    CONSTRAINT unique_phone_number_customer UNIQUE (customer_id, country_code, phone_number)
);

CREATE UNIQUE INDEX idx_one_primary_customer_phone_number
    ON customers.customer_phone_numbers (customer_id)
    WHERE (is_primary = true) AND (deleted_at IS NULL);

-- Trigger for updated_at
CREATE TRIGGER update_customer_phone_numbers_timestamps
    BEFORE INSERT OR UPDATE
    ON customers.customer_phone_numbers
    FOR EACH ROW
EXECUTE FUNCTION fcb_handle_timestamps();

-- Trigger for versioning
CREATE TRIGGER update_customer_phone_numbers_version
    BEFORE INSERT OR UPDATE
    ON customers.customer_phone_numbers
    FOR EACH ROW
EXECUTE FUNCTION fcb_update_version_column();
