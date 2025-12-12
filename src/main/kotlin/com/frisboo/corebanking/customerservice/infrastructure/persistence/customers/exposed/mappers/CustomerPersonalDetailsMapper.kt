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

import com.frisboo.corebanking.customerservice.domain.customer.models.CustomerPersonalDetails
import com.frisboo.corebanking.customerservice.domain.customer.valueobjects.CountryCode
import com.frisboo.corebanking.customerservice.domain.customer.valueobjects.CustomerId
import com.frisboo.corebanking.customerservice.domain.customer.valueobjects.PersonalDetailId
import com.frisboo.corebanking.customerservice.infrastructure.persistence.customers.exposed.tables.CustomerPersonalDetailsTable
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.statements.InsertStatement
import kotlin.time.toKotlinInstant

internal fun CustomerPersonalDetails.toInsertStatement(
    table: CustomerPersonalDetailsTable,
    stmt: InsertStatement<Number>,
    customerId: CustomerId?,
) {
    val customerUUID = customerId?.id
    require(customerUUID != null) { "Customer ID must not be null when inserting personal details." }

    stmt[table.customerId] = customerUUID
    stmt[table.title] = title
    stmt[table.firstName] = firstName
    stmt[table.middleName] = middleName
    stmt[table.lastName] = lastName
    stmt[table.firstNameInEnglish] = firstNameInEnglish
    stmt[table.middleNameInEnglish] = middleNameInEnglish
    stmt[table.lastNameInEnglish] = lastNameInEnglish
    stmt[table.dateOfBirth] = dateOfBirth
    stmt[table.countryOfBirthCode] = countryOfBirthCode.code
    stmt[table.genderId] = genderId
    stmt[table.isPoliticallyExposed] = isPoliticallyExposed ?: false
    stmt[table.isUSAPerson] = isUSAPerson ?: false
    stmt[table.maritalStatusId] = maritalStatusId
    stmt[table.numberOfDependents] = numberOfDependents ?: 0
}

internal fun ResultRow.toPersonalDetails(table: CustomerPersonalDetailsTable): CustomerPersonalDetails =
    CustomerPersonalDetails(
        personalDetailsId = PersonalDetailId(this[table.personalDetailsId]),
        customerId = CustomerId(this[table.customerId]),
        title = this[table.title],
        firstName = this[table.firstName],
        middleName = this[table.middleName],
        lastName = this[table.lastName],
        firstNameInEnglish = this[table.firstNameInEnglish],
        middleNameInEnglish = this[table.middleNameInEnglish],
        lastNameInEnglish = this[table.lastNameInEnglish],
        dateOfBirth = this[table.dateOfBirth],
        countryOfBirthCode = CountryCode.of(this[table.countryOfBirthCode]),
        genderId = this[table.genderId],
        isPoliticallyExposed = this[table.isPoliticallyExposed],
        isUSAPerson = this[table.isUSAPerson],
        maritalStatusId = this[table.maritalStatusId],
        numberOfDependents = this[table.numberOfDependents],
        version = this[table.version],
        createdAt = this[table.createdAt].toInstant().toKotlinInstant(),
        updatedAt = this[table.updatedAt].toInstant().toKotlinInstant(),
    )
