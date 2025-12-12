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

import com.frisboo.corebanking.persistence.exposed.abstracts.BaseTable
import com.frisboo.corebanking.persistence.exposed.extensions.WithOptimisticLocking
import kotlinx.datetime.LocalDate
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.datetime.date
import java.util.UUID

internal class CustomerPersonalDetailsTable(
    name: String,
    customersTable: CustomersTable,
) : BaseTable(name),
    WithOptimisticLocking {
    companion object

    val personalDetailsId: Column<UUID> = uuid("id").autoGenerate()
    val customerId: Column<UUID> =
        uuid("customer_id").references(
            customersTable.customerId,
        )

    val title: Column<String> = varchar("title", length = 64)
    val firstName: Column<String> = varchar("first_name", length = 255)
    val middleName: Column<String?> = varchar("middle_name", length = 255).nullable()
    val lastName: Column<String> = varchar("last_name", length = 255)
    val firstNameInEnglish: Column<String?> = varchar("first_name_in_english", length = 255).nullable()
    val middleNameInEnglish: Column<String?> = varchar("middle_name_in_english", length = 255).nullable()
    val lastNameInEnglish: Column<String?> = varchar("last_name_in_english", length = 255).nullable()
    val dateOfBirth: Column<LocalDate> = date("date_of_birth")
    val countryOfBirthCode: Column<String> = varchar("country_of_birth_code", length = 2)
    val genderId: Column<String> = varchar("gender_id", length = 64)
    val isPoliticallyExposed: Column<Boolean> = bool("is_politically_exposed")
    val isUSAPerson: Column<Boolean> = bool("is_us_person")
    val maritalStatusId: Column<String> = varchar("marital_status_id", length = 64)
    val numberOfDependents: Column<Int> = integer("number_of_dependents")

    override val optimisticLockingVersion: Column<Long> = this.version

    override val primaryKey: PrimaryKey = PrimaryKey(personalDetailsId, name = "pk_customer_personal_details")
}
