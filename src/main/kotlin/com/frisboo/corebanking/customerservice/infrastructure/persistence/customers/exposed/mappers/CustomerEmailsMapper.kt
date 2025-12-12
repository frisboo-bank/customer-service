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

import com.frisboo.corebanking.customerservice.domain.customer.models.CustomerEmail
import com.frisboo.corebanking.customerservice.domain.customer.valueobjects.CustomerId
import com.frisboo.corebanking.customerservice.domain.customer.valueobjects.EmailId
import com.frisboo.corebanking.customerservice.infrastructure.persistence.customers.exposed.tables.CustomerEmailsTable
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.statements.BatchInsertStatement
import kotlin.time.toKotlinInstant

internal fun CustomerEmail.toInsertStatement(
    table: CustomerEmailsTable,
    stmt: BatchInsertStatement,
    customerId: CustomerId?,
) {
    val customerUUID = customerId?.id
    require(customerUUID != null) { "Customer ID must not be null when inserting an email." }

    stmt[table.customerId] = customerUUID
    stmt[table.email] = email
    stmt[table.isPrimary] = isPrimary ?: false
}

internal fun List<ResultRow>.toCustomerEmails(table: CustomerEmailsTable): List<CustomerEmail> =
    this.map { it.toCustomerEmail(table) }

internal fun ResultRow.toCustomerEmail(table: CustomerEmailsTable): CustomerEmail =
    CustomerEmail(
        emailId = EmailId(this[table.emailId]),
        customerId = CustomerId(this[table.customerId]),
        email = this[table.email],
        isPrimary = this[table.isPrimary],
        verifiedAt = this[table.verifiedAt]?.toInstant()?.toKotlinInstant(),
        deletedAt = this[table.deletedAt]?.toInstant()?.toKotlinInstant(),
        deletedReason = this[table.deletedReason],
        version = this[table.version],
        createdAt = this[table.createdAt].toInstant().toKotlinInstant(),
        updatedAt = this[table.updatedAt].toInstant().toKotlinInstant(),
    )
