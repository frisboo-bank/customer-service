CREATE TABLE customers.customer_personal_details
(
    id                     UUID                              DEFAULT gen_random_uuid() NOT NULL,
    customer_id            UUID                     NOT NULL UNIQUE,

    title                  TEXT,
    first_name             TEXT,
    middle_name            TEXT,
    last_name              TEXT,
    first_name_in_english  TEXT,
    middle_name_in_english TEXT,
    last_name_in_english   TEXT,
    date_of_birth          DATE CHECK (fcb_validate_of_age(date_of_birth)),
    country_of_birth_code  CHAR(2),
    gender_id              TEXT,
    is_politically_exposed BOOL                     NOT NULL DEFAULT false,
    is_us_person           BOOL                     NOT NULL DEFAULT false,
    marital_status_id      TEXT,
    number_of_dependents   SMALLINT                 NOT NULL DEFAULT 0 CHECK (number_of_dependents >= 0),

    version                BIGINT                   NOT NULL,
    created_at             TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at             TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT pk_customer_personal_details PRIMARY KEY (id),
    CONSTRAINT fk_customer_personal_details_customer
        FOREIGN KEY (customer_id) REFERENCES customers.customers (id)
);

-- Trigger for updated_at
CREATE TRIGGER update_customer_personal_details_timestamps
    BEFORE INSERT OR UPDATE
    ON customers.customer_personal_details
    FOR EACH ROW
EXECUTE FUNCTION fcb_handle_timestamps();

-- Trigger for versioning
CREATE TRIGGER update_customer_personal_details_version
    BEFORE INSERT OR UPDATE
    ON customers.customer_personal_details
    FOR EACH ROW
EXECUTE FUNCTION fcb_update_version_column();
