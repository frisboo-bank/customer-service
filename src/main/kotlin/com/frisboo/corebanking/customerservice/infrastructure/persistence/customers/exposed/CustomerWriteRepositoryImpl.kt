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
package com.frisboo.corebanking.customerservice.infrastructure.persistence.customers.exposed

import arrow.core.Either
import com.frisboo.corebanking.core.coroutines.withCoroutineContext
import com.frisboo.corebanking.core.domain.errors.AppError
import com.frisboo.corebanking.customerservice.constants.CUSTOMERS_TABLE_NAME
import com.frisboo.corebanking.customerservice.constants.CUSTOMER_ADDRESSES_TABLE_NAME
import com.frisboo.corebanking.customerservice.constants.CUSTOMER_EMAILS_TABLE_NAME
import com.frisboo.corebanking.customerservice.constants.CUSTOMER_IDENTITY_DOCUMENTS_TABLE_NAME
import com.frisboo.corebanking.customerservice.constants.CUSTOMER_NATIONALITIES_TABLE_NAME
import com.frisboo.corebanking.customerservice.constants.CUSTOMER_PERSONAL_DETAILS_TABLE_NAME
import com.frisboo.corebanking.customerservice.constants.CUSTOMER_PHONE_NUMBERS_TABLE_NAME
import com.frisboo.corebanking.customerservice.contracts.repositories.CustomerWriteRepository
import com.frisboo.corebanking.customerservice.domain.customer.errors.CustomerError
import com.frisboo.corebanking.customerservice.domain.customer.models.Customer
import com.frisboo.corebanking.customerservice.infrastructure.persistence.customers.exposed.tables.CustomerAddressesTable
import com.frisboo.corebanking.customerservice.infrastructure.persistence.customers.exposed.tables.CustomerEmailsTable
import com.frisboo.corebanking.customerservice.infrastructure.persistence.customers.exposed.tables.CustomerIdentityDocumentsTable
import com.frisboo.corebanking.customerservice.infrastructure.persistence.customers.exposed.tables.CustomerNationalitiesTable
import com.frisboo.corebanking.customerservice.infrastructure.persistence.customers.exposed.tables.CustomerPersonalDetailsTable
import com.frisboo.corebanking.customerservice.infrastructure.persistence.customers.exposed.tables.CustomerPhoneNumbersTable
import com.frisboo.corebanking.customerservice.infrastructure.persistence.customers.exposed.tables.CustomersTable
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
open class CustomerWriteRepositoryImpl : CustomerWriteRepository {
    private companion object {
        private val logger = KotlinLogging.logger { }

        private val customersTable = CustomersTable(CUSTOMERS_TABLE_NAME)
        private val customerAddressesTable = CustomerAddressesTable(CUSTOMER_ADDRESSES_TABLE_NAME, customersTable)
        private val customerEmailsTable = CustomerEmailsTable(CUSTOMER_EMAILS_TABLE_NAME, customersTable)
        private val customerIdentityDocumentsTable =
            CustomerIdentityDocumentsTable(CUSTOMER_IDENTITY_DOCUMENTS_TABLE_NAME, customersTable)
        private val customerNationalitiesTable =
            CustomerNationalitiesTable(CUSTOMER_NATIONALITIES_TABLE_NAME, customersTable)
        private val customerPersonalDetailsTable =
            CustomerPersonalDetailsTable(CUSTOMER_PERSONAL_DETAILS_TABLE_NAME, customersTable)
        private val customerPhoneNumbersTable =
            CustomerPhoneNumbersTable(CUSTOMER_PHONE_NUMBERS_TABLE_NAME, customersTable)
    }

    /** Coroutine context for database operations. */
    private val ctx = withCoroutineContext()

    /**
     * Creates a new customer in the database.
     *
     * @param customerToInsert The customer to create.
     * @return Either an AppError or the created Customer.
     */
    override suspend fun insertCustomer(customerToInsert: Customer): Either<CustomerError, Customer> {
        TODO("Not yet implemented")
    }
//    @Transactional
//    override suspend fun insertCustomer(customerToInsert: Customer): Either<CustomerError, Customer> =
//        scopedEither(ctx) {
//            suspendTransaction {
//                val savedCustomer = customersTable.insertReturning {}.single().also {
//                    logger.info { "Inserted customer: $it" }
//                }
//                val customer = savedCustomer.toCustomer(customersTable)
//
//                data class CustomerDependencies(
//                    val addresses: List<CustomerAddress>? = null,
//                    val emails: List<CustomerEmail>? = null,
//                    val nationalities: List<CustomerNationality>? = null,
//                    val personalDetails: CustomerPersonalDetails? = null,
//                    val phoneNumbers: List<CustomerPhoneNumber>? = null,
//                )
//
//                val (addresses, emails, nationalities, personalDetails, phoneNumbers) = parZip(
//                    {
//                        customerToInsert.addresses?.takeIf { it.isNotEmpty() }?.let { addresses ->
//                            val savedAddresses = customerAddressesTable.batchInsert(addresses) { address ->
//                                address.toInsertStatement(customerAddressesTable, this, customer.customerId)
//                            }
//                            savedAddresses.toCustomerAddresses(customerAddressesTable)
// //                                .also { logger.info { "Inserter ${it.size} addresses for customer: ${customer.customerId}" }
//                        }
//                    },
//                    {
//                        customerToInsert.emails?.takeIf { it.isNotEmpty() }?.let { emails ->
//                            val savedEmails = customerEmailsTable.batchInsert(emails) { email ->
//                                email.toInsertStatement(customerEmailsTable, this, customer.customerId)
//                            }
//                            savedEmails.toCustomerEmails(customerEmailsTable)
//                        }
//                    },
//                    {
//                        customerToInsert.nationalities?.takeIf { it.isNotEmpty() }?.let { nationalities ->
//                            val savedNationalities =
//                                customerNationalitiesTable.batchInsert(nationalities) { nationality ->
//                                    nationality.toInsertStatement(customerNationalitiesTable, this, customer.customerId)
//                                }
//                            savedNationalities.toCustomerNationalities(customerNationalitiesTable)
//                        }
//                    },
//                    {
//                        customerToInsert.personalDetails?.let { personalDetails ->
//                            val savedPersonalDetails = customerPersonalDetailsTable.insertReturning { stmt ->
//                                customerToInsert.personalDetails?.toInsertStatement(
//                                    customerPersonalDetailsTable,
//                                    stmt,
//                                    customer.customerId,
//                                )
//                            }.single()
//                            savedPersonalDetails.toPersonalDetails(customerPersonalDetailsTable)
//                        }
//                    },
//                    {
//                        customerToInsert.phoneNumbers?.takeIf { it.isNotEmpty() }?.let { phoneNumbers ->
//                            val savedPhoneNumbers = customerPhoneNumbersTable.batchInsert(phoneNumbers) { phoneNumber ->
//                                phoneNumber.toInsertStatement(customerPhoneNumbersTable, this, customer.customerId)
//                            }
//                            savedPhoneNumbers.toCustomerPhoneNumbers(customerPhoneNumbersTable)
//                        }
//                    },
//                ) { addresses, emails, nationalities, personalDetails, phoneNumbers ->
//                    CustomerDependencies(
//                        addresses,
//                        emails,
//                        nationalities,
//                        personalDetails,
//                        phoneNumbers,
//                    )
//                }
//
//                customer.copy(
//                    addresses = addresses,
//                    emails = emails,
//                    nationalities = nationalities,
//                    personalDetails = personalDetails,
//                    phoneNumbers = phoneNumbers,
//                )
//            }
//        }
//            .onLeft { logger.error { "createCustomer failed with error: $it" } }
//            .onRight { logger.debug { "createCustomer success: $it" } }

    @Transactional
    override suspend fun updateCustomer(customer: Customer): Either<AppError, Customer> {
        TODO("Not yet implemented")
    }

    @Transactional
    override suspend fun deletedCustomer(customer: Customer): Either<AppError, Customer> {
        TODO("Not yet implemented")
    }
}
