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
import com.frisboo.corebanking.core.domain.errors.AppError
import com.frisboo.corebanking.customerservice.domain.customer.errors.CustomerError
import com.frisboo.corebanking.customerservice.domain.customer.models.Customer

/**
 * Repository interface for writing customer data.
 */
interface CustomerWriteRepository {
    /**
     * Creates a new customer.
     *
     * @param customerToInsert The customer entity to be created.
     * @return Either a [AppError] if creation fails, or the created [Customer] if successful.
     */
    suspend fun insertCustomer(customerToInsert: Customer): Either<CustomerError, Customer>

    /**
     * Updates an existing customer.
     *
     * @param customerToUpdate The customer entity with updated information.
     * @return Either a [AppError] if the update fails, or the updated [Customer] if successful.
     */
    suspend fun updateCustomer(customerToUpdate: Customer): Either<AppError, Customer>

    /**
     * Marks a customer as deleted.
     *
     * @param customerToDelete The customer entity to be marked as deleted.
     * @return Either a [AppError] if the operation fails, or the updated [Customer] if successful.
     */
    suspend fun deletedCustomer(customerToDelete: Customer): Either<AppError, Customer>
}
