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

import com.frisboo.corebanking.customerservice.domain.customer.models.CustomerAddress
import com.frisboo.corebanking.customerservice.domain.customer.valueobjects.AddressId
import com.frisboo.corebanking.customerservice.domain.customer.valueobjects.CountryCode
import com.frisboo.corebanking.customerservice.domain.customer.valueobjects.CustomerId
import com.frisboo.corebanking.customerservice.domain.customer.valueobjects.PostalCode
import com.frisboo.corebanking.customerservice.infrastructure.persistence.customers.exposed.tables.CustomerAddressesTable
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.statements.BatchInsertStatement
import kotlin.time.toKotlinInstant

fun CustomerAddress.toInsertStatement(
    table: CustomerAddressesTable,
    stmt: BatchInsertStatement,
    customerId: CustomerId?,
) {
    val customerUUID = customerId?.id
    require(customerUUID != null) { "Customer ID must not be null when inserting an address." }

    stmt[table.customerId] = customerUUID
    stmt[table.addressType] = addressType
    stmt[table.addressLine1] = addressLine1
    stmt[table.addressLine2] = addressLine2
    stmt[table.addressLine3] = addressLine3
    stmt[table.addressLine4] = addressLine4
    stmt[table.addressLine5] = addressLine5
    stmt[table.city] = city
    stmt[table.district] = district
    stmt[table.state] = state
    stmt[table.region] = region
    stmt[table.postalCode] = postalCode.postalCode ?: "00000"
    stmt[table.countryCode] = countryCode.code
    stmt[table.isPrimary] = isPrimary ?: false
}

internal fun List<ResultRow>.toCustomerAddresses(table: CustomerAddressesTable): List<CustomerAddress> =
    this.map { it.toCustomerAddress(table) }

internal fun ResultRow.toCustomerAddress(table: CustomerAddressesTable): CustomerAddress =
    CustomerAddress(
        addressId = AddressId(this[table.addressId]),
        customerId = CustomerId(this[table.customerId]),
        addressType = this[table.addressType],
        addressLine1 = this[table.addressLine1],
        addressLine2 = this[table.addressLine2],
        addressLine3 = this[table.addressLine3],
        addressLine4 = this[table.addressLine4],
        addressLine5 = this[table.addressLine5],
        city = this[table.city],
        district = this[table.district],
        state = this[table.state],
        region = this[table.region],
        postalCode = PostalCode(this[table.postalCode]),
        countryCode = CountryCode.of(this[table.countryCode]),
        isPrimary = this[table.isPrimary],
        verifiedAt = this[table.verifiedAt]?.toInstant()?.toKotlinInstant(),
        deletedAt = this[table.deletedAt]?.toInstant()?.toKotlinInstant(),
        deletedReason = this[table.deletedReason],
        version = this[table.version],
        createdAt = this[table.createdAt].toInstant().toKotlinInstant(),
        updatedAt = this[table.updatedAt].toInstant().toKotlinInstant(),
    )
