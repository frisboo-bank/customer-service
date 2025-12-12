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
package com.frisboo.corebanking.customerservice.infrastructure.configurations.http

import arrow.core.Option.Companion.fromNullable
import arrow.core.getOrElse
import com.frisboo.corebanking.customerservice.infrastructure.constants.HTTPHeader
import org.slf4j.MDC
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.fromCallable
import reactor.util.context.Context
import java.util.UUID

private object CorrelationIdContextKey

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public open class CorrelationIdFilter : WebFilter {
    override fun filter(
        exchange: ServerWebExchange,
        chain: WebFilterChain,
    ): Mono<Void?> =
        fromCallable {
            extractOrGenerateCorrelationId(exchange.request)
        }.flatMap { correlationId ->
            exchange.attributes[HTTPHeader.CORRELATION_ID] = correlationId
            exchange.response.headers.set(HTTPHeader.CORRELATION_ID, correlationId)

            chain
                .filter(exchange)
                .contextWrite { context: Context ->
                    context.put(CorrelationIdContextKey, correlationId)
                }.doOnSubscribe { MDC.put(HTTPHeader.CORRELATION_ID, correlationId) }
                .doFinally { MDC.remove(HTTPHeader.CORRELATION_ID) }
        }
}

private fun CorrelationIdFilter.extractOrGenerateCorrelationId(request: ServerHttpRequest): String =
    fromNullable(request.headers.getFirst(HTTPHeader.CORRELATION_ID))
        .filter { it.isNotBlank() }
        .getOrElse { UUID.randomUUID().toString() }
