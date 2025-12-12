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

class CustomersTable(
    name: String,
) : BaseTable(name),
    WithOptimisticLocking {
    companion object {
        private const val DISABLED_REASON_MAX_LENGTH = 255
    }

    val customerId: Column<UUID> = uuid("id").autoGenerate()

    val disabledAt: Column<OffsetDateTime?> = timestampWithTimeZone("disabled_at").nullable()
    val disabledReason: Column<String?> = varchar("disabled_reason", length = DISABLED_REASON_MAX_LENGTH).nullable()
    val deletedAt: Column<OffsetDateTime?> = timestampWithTimeZone("deleted_at").nullable()
    val deletedReason: Column<String?> = varchar("deleted_reason", length = DELETED_REASON_MAX_LENGTH).nullable()

    override val optimisticLockingVersion = this.version

    override val primaryKey: PrimaryKey = PrimaryKey(customerId, name = "pk_customers")
}
