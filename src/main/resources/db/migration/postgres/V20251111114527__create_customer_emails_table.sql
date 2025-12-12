CREATE TABLE IF NOT EXISTS customers.customer_emails
(
    id             UUID    DEFAULT gen_random_uuid() NOT NULL,
    customer_id    UUID                              NOT NULL,

    email          CITEXT                            NOT NULL,

    is_primary     BOOLEAN DEFAULT FALSE             NOT NULL,

    verified_at    TIMESTAMP WITH TIME ZONE,

    deleted_at     TIMESTAMP WITH TIME ZONE,
    deleted_reason TEXT,

    version        BIGINT                            NOT NULL,
    created_at     TIMESTAMP WITH TIME ZONE          NOT NULL,
    updated_at     TIMESTAMP WITH TIME ZONE          NOT NULL,

    CONSTRAINT pk_customer_emails PRIMARY KEY (id),
    CONSTRAINT fk_customer_emails_customer
        FOREIGN KEY (customer_id) REFERENCES customers.customers (id),
    CONSTRAINT unique_email_customer UNIQUE (customer_id, email)
);

CREATE UNIQUE INDEX idx_one_primary_customer_email
    ON customers.customer_emails (customer_id)
    WHERE (is_primary = true) AND (deleted_at IS NULL);

-- Trigger for updated_at
CREATE TRIGGER update_customer_emails_timestamps
    BEFORE INSERT OR UPDATE
    ON customers.customer_emails
    FOR EACH ROW
EXECUTE FUNCTION fcb_handle_timestamps();

-- Trigger for versioning
CREATE TRIGGER update_customer_emails_version
    BEFORE INSERT OR UPDATE
    ON customers.customer_emails
    FOR EACH ROW
EXECUTE FUNCTION fcb_update_version_column();
