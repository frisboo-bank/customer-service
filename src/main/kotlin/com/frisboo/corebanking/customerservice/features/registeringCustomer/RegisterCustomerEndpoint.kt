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
package com.frisboo.corebanking.customerservice.features.registeringCustomer

import com.frisboo.corebanking.core.coroutines.raise.scopedEitherWithError
import com.frisboo.corebanking.core.coroutines.withCoroutineContext
import com.frisboo.corebanking.core.coroutines.withPublisherCoroutineScope
import com.frisboo.corebanking.customerservice.apis.CustomersRegisterApiDelegate
import com.frisboo.corebanking.customerservice.features.registeringCustomer.contracts.RegisterCustomerCommandService
import com.frisboo.corebanking.customerservice.models.RegisterCustomerRequestDto
import com.frisboo.corebanking.http.domain.models.HTTPErrorResponse
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class RegisterCustomerEndpoint(
    private val registerCustomerCommandService: RegisterCustomerCommandService,
) : CustomersRegisterApiDelegate {
    private val ctx = withCoroutineContext()
    private val publisherScope = withPublisherCoroutineScope()

    private companion object {
        private val logger = KotlinLogging.logger { }
    }

    override suspend fun registerCustomer(
        acceptLanguage: String,
        xRegion: String,
        registerCustomerRequestDto: RegisterCustomerRequestDto,
        xCorrelationId: String?,
        xRequestId: UUID?,
        xIdempotencyKey: String?,
    ): ResponseEntity<out Any> =
        scopedEitherWithError(ctx) {
//            registerCustomerCommandService.handle(registerCustomerRequestDto.toCommand()).bind()
        }.fold(
            ifLeft = {
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    HTTPErrorResponse(
                        title = "",
                        status = HttpStatus.BAD_REQUEST.value(),
                        detail = it.toString(),
                        instance = null,
                        correlationId = "",
                        code = "",
                    ),
                )
            },
            ifRight = { ResponseEntity.status(HttpStatus.CREATED).body(it) },
        )
}
