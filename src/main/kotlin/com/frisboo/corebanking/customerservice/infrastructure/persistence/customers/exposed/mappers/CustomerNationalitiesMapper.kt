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

import com.frisboo.corebanking.customerservice.domain.customer.models.CustomerNationality
import com.frisboo.corebanking.customerservice.domain.customer.valueobjects.CountryCode
import com.frisboo.corebanking.customerservice.domain.customer.valueobjects.CustomerId
import com.frisboo.corebanking.customerservice.domain.customer.valueobjects.NationalityId
import com.frisboo.corebanking.customerservice.infrastructure.persistence.customers.exposed.tables.CustomerNationalitiesTable
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.statements.BatchInsertStatement
import kotlin.time.toKotlinInstant

internal fun CustomerNationality.toInsertStatement(
    table: CustomerNationalitiesTable,
    stmt: BatchInsertStatement,
    customerId: CustomerId?,
) {
    val customerUUID = customerId?.id
    require(customerUUID != null) { "Customer ID must not be null when inserting a nationality." }

    stmt[table.customerId] = customerUUID
    stmt[table.countryCode] = countryCode.code
    stmt[table.isPrimary] = isPrimary ?: false
}

internal fun List<ResultRow>.toCustomerNationalities(table: CustomerNationalitiesTable): List<CustomerNationality> =
    this.map { it.toCustomerNationality(table) }

internal fun ResultRow.toCustomerNationality(table: CustomerNationalitiesTable): CustomerNationality =
    CustomerNationality(
        nationalityId = NationalityId(this[table.nationalityId]),
        customerId = CustomerId(this[table.customerId]),
        countryCode = CountryCode.of(this[table.countryCode]),
        isPrimary = this[table.isPrimary],
        deletedAt = this[table.deletedAt]?.toInstant()?.toKotlinInstant(),
        version = this[table.version],
        createdAt = this[table.createdAt].toInstant().toKotlinInstant(),
        updatedAt = this[table.updatedAt].toInstant().toKotlinInstant(),
    )
