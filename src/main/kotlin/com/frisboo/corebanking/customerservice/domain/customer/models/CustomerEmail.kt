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

import com.frisboo.corebanking.customerservice.domain.customer.valueobjects.CustomerId
import com.frisboo.corebanking.customerservice.domain.customer.valueobjects.EmailId
import kotlin.time.Instant

data class CustomerEmail(
    val emailId: EmailId? = null,
    val customerId: CustomerId? = null,
    val email: String,
    val isPrimary: Boolean? = null,
    val verifiedAt: Instant? = null,
    val deletedAt: Instant? = null,
    val deletedReason: String? = null,
    val version: Long? = null,
    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,
) {
    companion object
}
