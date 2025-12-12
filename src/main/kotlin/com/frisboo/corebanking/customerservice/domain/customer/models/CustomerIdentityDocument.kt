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
import com.frisboo.corebanking.customerservice.domain.customer.valueobjects.DocumentUploadId
import com.frisboo.corebanking.customerservice.domain.customer.valueobjects.IdentityDocumentId
import com.frisboo.corebanking.customerservice.models.RegisterCustomerRequestIdentityDocumentsInnerDto
import kotlinx.datetime.LocalDate
import kotlin.time.Instant

data class CustomerIdentityDocument(
    val identityDocument: IdentityDocumentId? = null,
    val customerId: CustomerId? = null,
    val type: String? = null,
    val number: String? = null,
    val issuingCountry: CountryCode? = null,
    val issueDate: LocalDate? = null,
    val expirationDate: LocalDate? = null,
    val verificationStatus: RegisterCustomerRequestIdentityDocumentsInnerDto.VerificationStatus? = null,
    val frontDocumentUploadId: DocumentUploadId? = null,
    val backDocumentUploadId: DocumentUploadId? = null,
    val metadata: Any? = null,
    val isPrimary: Boolean? = null,
    val verifiedAt: Instant? = null,
    val deletedAt: Instant? = null,
    val deletedReason: String? = null,
    val version: Long = 0,
    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,
) {
    companion object
}
