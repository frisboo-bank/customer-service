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
package com.frisboo.corebanking.customerservice.features.registeringCustomer.models.dtos

import com.frisboo.corebanking.customerservice.domain.customer.models.CustomerPersonalDetails
import com.frisboo.corebanking.customerservice.domain.customer.valueobjects.CountryCode
import com.frisboo.corebanking.customerservice.models.RegisterCustomerRequestPersonalDetailsDto
import kotlinx.datetime.toKotlinLocalDate

fun RegisterCustomerRequestPersonalDetailsDto.toPersonalDetails(): CustomerPersonalDetails =
    CustomerPersonalDetails(
        title = title,
        firstName = firstName,
        middleName = middleName,
        lastName = lastName,
        firstNameInEnglish = firstNameInEnglish,
        middleNameInEnglish = middleNameInEnglish,
        lastNameInEnglish = lastNameInEnglish,
        dateOfBirth = dateOfBirth.toKotlinLocalDate(),
        countryOfBirthCode = CountryCode.of(countryOfBirthCode),
        genderId = genderId,
        isPoliticallyExposed = isPoliticallyExposed,
        isUSAPerson = isUSAPerson,
        maritalStatusId = maritalStatusId,
        numberOfDependents = numberOfDependents,
    )
