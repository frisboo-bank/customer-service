CREATE TABLE IF NOT EXISTS customers.customers
(
    id              UUID DEFAULT gen_random_uuid() NOT NULL,

    disabled_at     TIMESTAMP WITH TIME ZONE,
    disabled_reason TEXT,
    deleted_at      TIMESTAMP WITH TIME ZONE,
    deleted_reason  TEXT,

    version         BIGINT                         NOT NULL,
    created_at      TIMESTAMP WITH TIME ZONE       NOT NULL,
    updated_at      TIMESTAMP WITH TIME ZONE       NOT NULL,

    CONSTRAINT customers_pkey PRIMARY KEY (id)
);

-- Trigger for updated_at
CREATE TRIGGER update_customers_updated_at
    BEFORE INSERT OR UPDATE
    ON customers.customers
    FOR EACH ROW
EXECUTE FUNCTION fcb_handle_timestamps();

-- Trigger for versioning
CREATE TRIGGER update_customers_version
    BEFORE INSERT OR UPDATE
    ON customers.customers
    FOR EACH ROW
EXECUTE FUNCTION fcb_update_version_column();
