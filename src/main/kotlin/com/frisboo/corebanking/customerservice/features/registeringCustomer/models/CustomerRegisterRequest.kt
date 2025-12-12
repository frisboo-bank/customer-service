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
package com.frisboo.corebanking.customerservice.features.registeringCustomer.models

import com.frisboo.corebanking.customerservice.domain.customer.commands.RegisterCustomerCommand
import com.frisboo.corebanking.customerservice.features.registeringCustomer.models.dtos.toAddress
import com.frisboo.corebanking.customerservice.features.registeringCustomer.models.dtos.toEmail
import com.frisboo.corebanking.customerservice.features.registeringCustomer.models.dtos.toIdentityDocument
import com.frisboo.corebanking.customerservice.features.registeringCustomer.models.dtos.toNationality
import com.frisboo.corebanking.customerservice.features.registeringCustomer.models.dtos.toPersonalDetails
import com.frisboo.corebanking.customerservice.features.registeringCustomer.models.dtos.toPhoneNumber
import com.frisboo.corebanking.customerservice.models.RegisterCustomerRequestDto

fun RegisterCustomerRequestDto.toCommand(): RegisterCustomerCommand =
    RegisterCustomerCommand(
        addresses = addresses.map { it.toAddress() },
        emails = emails.map { it.toEmail() },
        identityDocuments = identityDocuments.map { it.toIdentityDocument() },
        nationalities = nationalities.map { it.toNationality() },
        personalDetails = personalDetails.toPersonalDetails(),
        phoneNumbers = phoneNumbers.map { it.toPhoneNumber() },
    )
//
//
// fun RegisterCustomerCommand.toSaga() = Saga.createSagaDraft<RegisterCustomerCommand, Any>(
//    sagaType = "RegisterCustomerSaga",
//    correlationId = UUID.randomUUID().toString(),
//    tenantId = null,
//    commandType = RegisterCustomerCommand::class.qualifiedName!!,
//    commandPayload = this,
//    commandMetadata = emptyMap<String, Any>(),
// )
