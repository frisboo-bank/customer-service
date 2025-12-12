/*
 * Copyright 2025 Frisboo Bank
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.frisboo.corebanking.customerservice.constants

// Constants representing the names of database tables in the "customers" schema.

/**
 * Name of the table storing customer records.
 */
const val CUSTOMERS_TABLE_NAME = "customers.customers"

/**
 * Name of the table storing customer address records.
 */
const val CUSTOMER_ADDRESSES_TABLE_NAME = "customers.customer_addresses"

/**
 * Name of the table storing customer email records.
 */
const val CUSTOMER_EMAILS_TABLE_NAME = "customers.customer_emails"

/**
 * Name of the table storing customer identity document records.
 */
const val CUSTOMER_IDENTITY_DOCUMENTS_TABLE_NAME = "customers.customer_identity_documents"

/**
 * Name of the table storing customer nationality records.
 */
const val CUSTOMER_NATIONALITIES_TABLE_NAME = "customers.customer_nationalities"

/**
 * Name of the table storing customer personal details records.
 */
const val CUSTOMER_PERSONAL_DETAILS_TABLE_NAME = "customers.customer_personal_details"

/**
 * Name of the table storing customer phone number records.
 */
const val CUSTOMER_PHONE_NUMBERS_TABLE_NAME = "customers.customer_phone_numbers"

const val DELETED_REASON_MAX_LENGTH = 255
