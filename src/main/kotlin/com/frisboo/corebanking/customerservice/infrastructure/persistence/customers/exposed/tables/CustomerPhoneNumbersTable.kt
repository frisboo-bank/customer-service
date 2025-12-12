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

internal class CustomerPhoneNumbersTable(
    name: String,
    customersTable: CustomersTable,
) : BaseTable(name),
    WithOptimisticLocking {
    companion object {
        private const val COUNTRY_CODE_MAX_LENGTH = 2
        private const val PHONE_NUMBER_MAX_LENGTH = 64
    }

    val phoneNumberId: Column<UUID> = uuid("id").autoGenerate()
    val customerId: Column<UUID> = uuid("customer_id").references(customersTable.customerId)

    val countryCode: Column<String> = varchar("country_code", length = COUNTRY_CODE_MAX_LENGTH)
    val phoneNumber: Column<String> = varchar("phone_number", length = PHONE_NUMBER_MAX_LENGTH)

    override val optimisticLockingVersion = this.version

    val isPrimary: Column<Boolean> = bool("is_primary")
    val verifiedAt: Column<OffsetDateTime?> = timestampWithTimeZone("verified_at").nullable()
    val deletedAt: Column<OffsetDateTime?> = timestampWithTimeZone("deleted_at").nullable()
    val deletedReason: Column<String?> = varchar("deleted_reason", DELETED_REASON_MAX_LENGTH).nullable()

    override val primaryKey: PrimaryKey = PrimaryKey(phoneNumberId, name = "pk_customer_phone_numbers")
}
