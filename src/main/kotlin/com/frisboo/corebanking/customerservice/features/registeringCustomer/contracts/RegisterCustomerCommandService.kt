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
package com.frisboo.corebanking.customerservice.features.registeringCustomer.contracts

import arrow.core.Either
import com.frisboo.corebanking.customerservice.domain.customer.commands.RegisterCustomerCommand
import com.frisboo.corebanking.customerservice.domain.customer.errors.CustomerError

/**
 * Service interface for handling customer registration commands.
 */
interface RegisterCustomerCommandService {
    /**
     * Handles the registration of a new customer based on the provided command.
     *
     * @param command The command containing details for registering the customer.
     */
    suspend fun handle(command: RegisterCustomerCommand): Either<CustomerError, Unit>
}
