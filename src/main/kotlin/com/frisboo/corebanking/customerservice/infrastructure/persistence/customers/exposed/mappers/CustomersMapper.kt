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

import com.frisboo.corebanking.customerservice.domain.customer.models.Customer
import com.frisboo.corebanking.customerservice.domain.customer.valueobjects.CustomerId
import com.frisboo.corebanking.customerservice.infrastructure.persistence.customers.exposed.tables.CustomersTable
import org.jetbrains.exposed.v1.core.ResultRow
import kotlin.time.toKotlinInstant

internal fun ResultRow.toCustomer(table: CustomersTable): Customer =
    Customer(
        customerId = CustomerId(this[table.customerId]),
        disabledAt = this[table.disabledAt]?.toInstant()?.toKotlinInstant(),
        disabledReason = this[table.disabledReason],
        deletedAt = this[table.deletedAt]?.toInstant()?.toKotlinInstant(),
        deletedReason = this[table.deletedReason],
        version = this[table.version],
        createdAt = this[table.createdAt].toInstant().toKotlinInstant(),
        updatedAt = this[table.updatedAt].toInstant().toKotlinInstant(),
    )
