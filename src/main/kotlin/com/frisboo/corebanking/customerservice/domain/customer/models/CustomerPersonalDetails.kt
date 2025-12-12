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
package com.frisboo.corebanking.customerservice.domain.customer.models

import com.frisboo.corebanking.customerservice.domain.customer.valueobjects.CountryCode
import com.frisboo.corebanking.customerservice.domain.customer.valueobjects.CustomerId
import com.frisboo.corebanking.customerservice.domain.customer.valueobjects.PersonalDetailId
import kotlinx.datetime.LocalDate
import kotlin.time.Instant

data class CustomerPersonalDetails(
    val personalDetailsId: PersonalDetailId? = null,
    val customerId: CustomerId? = null,
    val title: String,
    val firstName: String,
    val middleName: String? = null,
    val lastName: String,
    val firstNameInEnglish: String? = null,
    val middleNameInEnglish: String? = null,
    val lastNameInEnglish: String? = null,
    val dateOfBirth: LocalDate,
    val countryOfBirthCode: CountryCode,
    val genderId: String,
    val isPoliticallyExposed: Boolean? = null,
    val isUSAPerson: Boolean? = null,
    val maritalStatusId: String,
    val numberOfDependents: Int,
    val version: Long? = null,
    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,
) {
    companion object
}
