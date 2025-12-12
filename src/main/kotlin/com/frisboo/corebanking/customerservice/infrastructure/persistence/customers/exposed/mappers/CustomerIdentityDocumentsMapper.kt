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
package com.frisboo.corebanking.customerservice.infrastructure.persistence.customers.exposed.mappers

import com.frisboo.corebanking.customerservice.domain.customer.models.CustomerIdentityDocument
import com.frisboo.corebanking.customerservice.domain.customer.valueobjects.CustomerId
import com.frisboo.corebanking.customerservice.infrastructure.persistence.customers.exposed.tables.CustomerIdentityDocumentsTable
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.statements.BatchInsertStatement

internal fun CustomerIdentityDocument.toInsertStatement(
    table: CustomerIdentityDocumentsTable,
    stmt: BatchInsertStatement,
    customerId: CustomerId?,
) {
    val customerUUID = customerId?.id
    require(customerUUID != null) { "Customer ID must not be null when inserting an address." }

    stmt[table.customerId] = customerUUID
}

internal fun List<ResultRow>.toCustomerIdentityDocuments(
    table: CustomerIdentityDocumentsTable,
): List<CustomerIdentityDocument> = this.map { it.toCustomerIdentityDocument(table) }

internal fun ResultRow.toCustomerIdentityDocument(table: CustomerIdentityDocumentsTable): CustomerIdentityDocument =
    CustomerIdentityDocument()
