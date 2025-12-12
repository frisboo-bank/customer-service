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

import com.frisboo.corebanking.customerservice.domain.customer.models.CustomerIdentityDocument
import com.frisboo.corebanking.customerservice.domain.customer.valueobjects.CountryCode
import com.frisboo.corebanking.customerservice.domain.customer.valueobjects.DocumentUploadId
import com.frisboo.corebanking.customerservice.models.RegisterCustomerRequestIdentityDocumentsInnerDto
import kotlinx.datetime.toKotlinLocalDate

fun RegisterCustomerRequestIdentityDocumentsInnerDto.toIdentityDocument(): CustomerIdentityDocument =
    CustomerIdentityDocument(
        type = type,
        number = number,
        issuingCountry = CountryCode.of(issuingCountry),
        issueDate = issueDate?.toKotlinLocalDate(),
        expirationDate = expirationDate?.toKotlinLocalDate(),
        verificationStatus = verificationStatus,
        frontDocumentUploadId = DocumentUploadId(frontDocumentUploadId),
        backDocumentUploadId = DocumentUploadId(backDocumentUploadId),
        metadata = metadata,
    )
