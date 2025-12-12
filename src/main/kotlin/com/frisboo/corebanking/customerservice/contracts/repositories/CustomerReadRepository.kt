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
package com.frisboo.corebanking.customerservice.contracts.repositories

import arrow.core.Either
import com.frisboo.corebanking.customerservice.domain.customer.models.Customer
import com.frisboo.corebanking.http.domain.models.HTTPErrorResponse

interface CustomerReadRepository {
    /**
     * Retrieves a customer by their unique identifier.
     *
     * @param customerId The unique identifier of the customer.
     * @return Either an [HTTPErrorResponse] if retrieval fails, or the [Customer] if successful.
     */
    suspend fun getCustomerById(customerId: String): Either<HTTPErrorResponse, Customer>
}
