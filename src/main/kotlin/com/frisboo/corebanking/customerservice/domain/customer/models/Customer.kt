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

import com.frisboo.corebanking.customerservice.domain.customer.models.CustomerIdentityDocument
import com.frisboo.corebanking.customerservice.domain.customer.valueobjects.CustomerId
import kotlin.time.Instant

data class Customer(
    val customerId: CustomerId? = null,
    val addresses: List<CustomerAddress>? = null,
    val emails: List<CustomerEmail>? = null,
    val identityDocuments: List<CustomerIdentityDocument>? = null,
    val nationalities: List<CustomerNationality>? = null,
    val personalDetails: CustomerPersonalDetails? = null,
    val phoneNumbers: List<CustomerPhoneNumber>? = null,
    val disabledAt: Instant? = null,
    val disabledReason: String? = null,
    val deletedAt: Instant? = null,
    val deletedReason: String? = null,
    val version: Long? = null,
    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,
) {
    companion object
}
