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
package com.frisboo.corebanking.customerservice.infrastructure.persistence.customers.exposed.tables

import com.frisboo.corebanking.customerservice.constants.DELETED_REASON_MAX_LENGTH
import com.frisboo.corebanking.persistence.exposed.abstracts.BaseTable
import com.frisboo.corebanking.persistence.exposed.extensions.WithOptimisticLocking
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.datetime.timestampWithTimeZone
import java.time.OffsetDateTime
import java.util.UUID

class CustomerAddressesTable(
    name: String,
    customersTable: CustomersTable,
) : BaseTable(name),
    WithOptimisticLocking {
    companion object {
        private const val ADDRESS_LINE1_MAX_LENGTH = 50
        private const val ADDRESS_LINE2_MAX_LENGTH = 50
        private const val ADDRESS_LINE3_MAX_LENGTH = 50
        private const val ADDRESS_LINE4_MAX_LENGTH = 50
        private const val ADDRESS_LINE5_MAX_LENGTH = 50
        private const val CITY_MAX_LENGTH = 50
        private const val DISTRICT_MAX_LENGTH = 50
    }

    val addressId: Column<UUID> = uuid("id").autoGenerate()
    val customerId: Column<UUID> =
        uuid("customer_id").references(
            customersTable.customerId,
        )

    val addressType: Column<Char> = char("address_type")
    val addressLine1: Column<String> = varchar("address_line1", length = ADDRESS_LINE1_MAX_LENGTH)
    val addressLine2: Column<String?> = varchar("address_line2", length = ADDRESS_LINE2_MAX_LENGTH).nullable()
    val addressLine3: Column<String?> = varchar("address_line3", length = ADDRESS_LINE3_MAX_LENGTH).nullable()
    val addressLine4: Column<String?> = varchar("address_line4", length = ADDRESS_LINE4_MAX_LENGTH).nullable()
    val addressLine5: Column<String?> = varchar("address_line5", length = ADDRESS_LINE5_MAX_LENGTH).nullable()
    val city: Column<String> = varchar("city", length = CITY_MAX_LENGTH)
    val district: Column<String?> = varchar("district", length = DISTRICT_MAX_LENGTH).nullable()
    val state: Column<String?> = varchar("state", length = 255).nullable()
    val region: Column<String?> = varchar("region", length = 255).nullable()
    val postalCode: Column<String> = varchar("postal_code", length = 64)
    val countryCode: Column<String> = varchar("country_code", length = 2)

    val isPrimary: Column<Boolean> = bool("is_primary")
    val verifiedAt: Column<OffsetDateTime?> = timestampWithTimeZone("verified_at").nullable()
    val deletedAt: Column<OffsetDateTime?> = timestampWithTimeZone("deleted_at").nullable()
    val deletedReason: Column<String?> = varchar("deleted_reason", length = DELETED_REASON_MAX_LENGTH).nullable()

    override val optimisticLockingVersion: Column<Long> = this.version

    override val primaryKey: PrimaryKey = PrimaryKey(arrayOf(addressId, customerId), name = "pk_customer_addresses")
}
