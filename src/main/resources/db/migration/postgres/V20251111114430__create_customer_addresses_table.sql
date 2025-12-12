CREATE TABLE IF NOT EXISTS customers.customer_addresses
(
    id             UUID    DEFAULT gen_random_uuid() NOT NULL,
    customer_id    UUID                              NOT NULL,

    address_type   CHAR                              NOT NULL,
    address_line1  TEXT                              NOT NULL,
    address_line2  TEXT,
    address_line3  TEXT,
    address_line4  TEXT,
    address_line5  TEXT,
    city           TEXT                              NOT NULL,
    district       TEXT,
    state          TEXT,
    region         TEXT,
    postal_code    TEXT                              NOT NULL,
    country_code   CHAR(2)                           NOT NULL,

    is_primary     BOOLEAN DEFAULT FALSE             NOT NULL,

    verified_at    TIMESTAMP WITH TIME ZONE,

    deleted_at     TIMESTAMP WITH TIME ZONE,
    deleted_reason TEXT,

    version        BIGINT                            NOT NULL,
    created_at     TIMESTAMP WITH TIME ZONE          NOT NULL,
    updated_at     TIMESTAMP WITH TIME ZONE          NOT NULL,

    CONSTRAINT customer_addresses_pkey PRIMARY KEY (id),
    CONSTRAINT fk_customer_addresses_customer
        FOREIGN KEY (customer_id) REFERENCES customers.customers (id)
);

CREATE UNIQUE INDEX idx_one_primary_address
    ON customers.customer_addresses (customer_id)
    WHERE (is_primary = true) AND (deleted_at IS NULL);

-- Trigger for updated_at
CREATE TRIGGER update_customer_addresses_timestamps
    BEFORE INSERT OR UPDATE
    ON customers.customer_addresses
    FOR EACH ROW
EXECUTE FUNCTION fcb_handle_timestamps();

-- Trigger for versioning
CREATE TRIGGER update_customer_addresses_version
    BEFORE INSERT OR UPDATE
    ON customers.customer_addresses
    FOR EACH ROW
EXECUTE FUNCTION fcb_update_version_column();
