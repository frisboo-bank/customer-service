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
package com.frisboo.corebanking.customerservice.features.registeringCustomer.services

import arrow.core.Either
import com.frisboo.corebanking.core.coroutines.withCoroutineContext
import com.frisboo.corebanking.core.coroutines.withPublisherCoroutineScope
import com.frisboo.corebanking.customerservice.domain.customer.commands.RegisterCustomerCommand
import com.frisboo.corebanking.customerservice.domain.customer.errors.CustomerError
import com.frisboo.corebanking.customerservice.features.registeringCustomer.contracts.RegisterCustomerCommandService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service

@Service
open class RegisterCustomerCommandServiceImpl(
//    private val sagaRepository: SagasRepository,
) : RegisterCustomerCommandService {
    private companion object {
        private val logger = KotlinLogging.logger {}
    }

    private val ctx = withCoroutineContext()
    private val publisherScope = withPublisherCoroutineScope()

    override suspend fun handle(command: RegisterCustomerCommand): Either<CustomerError, Unit> {
        TODO("Not yet implemented")
    }
}
