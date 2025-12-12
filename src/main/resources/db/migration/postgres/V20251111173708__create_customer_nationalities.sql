CREATE TABLE IF NOT EXISTS customers.customer_nationalities
(
    id             UUID    DEFAULT gen_random_uuid() NOT NULL,
    customer_id    UUID                              NOT NULL,

    country_code   CHAR(2)                           NOT NULL,
    metadata       JSONB,

    is_primary     BOOLEAN DEFAULT FALSE,
    verified_at    TIMESTAMP WITH TIME ZONE,
    deleted_at     TIMESTAMP WITH TIME ZONE,
    deleted_reason TEXT,

    version        BIGINT                            NOT NULL,
    created_at     TIMESTAMP WITH TIME ZONE          NOT NULL,
    updated_at     TIMESTAMP WITH TIME ZONE          NOT NULL,

    CONSTRAINT pk_customer_nationalities PRIMARY KEY (id),
    CONSTRAINT fk_customer_nationalities_customer
        FOREIGN KEY (customer_id) REFERENCES customers.customers (id),
    CONSTRAINT unique_nationality_customer UNIQUE (customer_id, country_code)
);

CREATE UNIQUE INDEX idx_one_nationality_per_country
    ON customers.customer_nationalities (customer_id, country_code)
    WHERE (deleted_at IS NULL);

CREATE UNIQUE INDEX idx_one_primary_nationality
    ON customers.customer_nationalities (customer_id)
    WHERE (is_primary = true) AND (deleted_at IS NULL);

-- Trigger for updated_at
CREATE TRIGGER update_customer_nationalities_timestamps
    BEFORE INSERT OR UPDATE
    ON customers.customer_nationalities
    FOR EACH ROW
EXECUTE FUNCTION fcb_handle_timestamps();

-- Trigger for versioning
CREATE TRIGGER update_customer_nationalities_version
    BEFORE INSERT OR UPDATE
    ON customers.customer_nationalities
    FOR EACH ROW
EXECUTE FUNCTION fcb_update_version_column();
