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
package com.frisboo.corebanking.customerservice.infrastructure.exception

import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler

// @ControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {
//    @ExceptionHandler(MissingRequestHeaderException::class)
//    public fun handleMissingRequestHeaderException(
//        ex: MissingRequestHeaderException,
//        request: ServerHttpRequest,
//    ): ResponseEntity<HTTPErrorResponse> {
//        val errorHTTPResponse =
//            HTTPErrorResponse(
//                title = "Missing required header",
//                status = HttpStatus.BAD_REQUEST.value(),
//                type = URI.create("https://api.frisboo.com/docs/errors/missing-header"),
//                detail = "The required header '${ex.headerName}' is missing from the request.",
//                instance = request.uri,
//                code = "MISSING_REQUEST_HEADER",
//                correlationId = request.headers.getFirst("X-Correlation-ID").toString(),
//            )
//
//        return ResponseEntity
//            .status(HttpStatus.BAD_REQUEST)
//            .contentType(MediaType.APPLICATION_PROBLEM_JSON)
//            .body(errorHTTPResponse)
// //            .also {
// //                logger.info("ResponseEntity: {}", ex.message)
// //            }
//    }
//
//    private companion object {
//        private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
//    }
}
// ApiResponse(
// responseCode = "400",
// description = "Bad request (validation or malformed input)",
// content = [Content(schema = Schema(implementation = ProblemDetails::class))]
// ),
// ApiResponse(
// responseCode = "409",
// description = "Validation or conflict error for domain constraints",
// content = [Content(schema = Schema(implementation = ProblemDetails::class))]
// ),
// ApiResponse(
// responseCode = "422",
// description = "Semantically valid request but cannot be processed (e.g., business rule violation)",
// content = [Content(schema = Schema(implementation = ProblemDetails::class))]
// ),
// ApiResponse(
// responseCode = "429",
// description = "Rate limit exceeded",
// content = [Content(schema = Schema(implementation = ProblemDetails::class))]
// ),
// ApiResponse(
// responseCode = "500",
// description = "Unexpected server error",
// content = [Content(schema = Schema(implementation = ProblemDetails::class))]
// )
