CREATE TABLE IF NOT EXISTS customers.customer_identity_documents
(
    id                  UUID        DEFAULT gen_random_uuid() NOT NULL,
    customer_id         UUID                                  NOT NULL,

    type                VARCHAR(20)                           NOT NULL CHECK (type IN
                                                                              ('PASSPORT',
                                                                               'DRIVERS_LICENSE',
                                                                               'NATIONAL_ID',
                                                                               'RESIDENCE_PERMIT',
                                                                               'OTHER')),
    number              VARCHAR(50)                           NOT NULL,
    issuing_country     CHAR(2)                               NOT NULL,
    issue_date          DATE                                  NOT NULL,
    expiration_date     DATE                                  NOT NULL,
    verification_status VARCHAR(20) DEFAULT 'PENDING' CHECK (verification_status IN ('VERIFIED', 'PENDING', 'EXPIRED', 'REJECTED')),
    front_document_id   UUID,
    back_document_id    UUID,
    metadata            JSONB,

    is_primary          BOOLEAN     DEFAULT FALSE             NOT NULL,

    verified_at         TIMESTAMP WITH TIME ZONE,

    deleted_at          TIMESTAMP WITH TIME ZONE,
    deleted_reason      TEXT,

    version             BIGINT                                NOT NULL,
    created_at          TIMESTAMP WITH TIME ZONE              NOT NULL,
    updated_at          TIMESTAMP WITH TIME ZONE              NOT NULL,

    CONSTRAINT pk_customer_identity_documents PRIMARY KEY (id),
    CONSTRAINT fk_customer_identity_documents_customer FOREIGN KEY (customer_id)
        REFERENCES customers.customers (id),
    CONSTRAINT chk_dates CHECK (expiration_date > issue_date),
    CONSTRAINT chk_issue_date_past CHECK (issue_date <= CURRENT_DATE),
    CONSTRAINT chk_expiration_date CHECK (expiration_date > CURRENT_DATE + INTERVAL '6 months')
);

-- Unique index to prevent duplicate per country
CREATE UNIQUE INDEX idx_unique_identity_document
    ON customers.customer_identity_documents (customer_id, type, issuing_country)
    WHERE (deleted_at IS NULL);

-- Ensure only one primary document per customer
CREATE UNIQUE INDEX idx_one_primary_document_per_customer
    ON customers.customer_identity_documents (customer_id)
    WHERE (is_primary = true AND deleted_at IS NULL);

-- Trigger for updated_at
CREATE TRIGGER update_customer_identity_documents_timestamps
    BEFORE INSERT OR UPDATE
    ON customers.customer_identity_documents
    FOR EACH ROW
EXECUTE FUNCTION fcb_handle_timestamps();

-- Trigger for versioning
CREATE TRIGGER update_customer_identity_documents_version
    BEFORE INSERT OR UPDATE
    ON customers.customer_identity_documents
    FOR EACH ROW
EXECUTE FUNCTION fcb_update_version_column();
