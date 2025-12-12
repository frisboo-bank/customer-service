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
package com.frisboo.corebanking.customerservice.domain.customer.commands

import com.frisboo.corebanking.core.contracts.Command
import com.frisboo.corebanking.customerservice.domain.customer.models.CustomerAddress
import com.frisboo.corebanking.customerservice.domain.customer.models.CustomerEmail
import com.frisboo.corebanking.customerservice.domain.customer.models.CustomerIdentityDocument
import com.frisboo.corebanking.customerservice.domain.customer.models.CustomerNationality
import com.frisboo.corebanking.customerservice.domain.customer.models.CustomerPersonalDetails
import com.frisboo.corebanking.customerservice.domain.customer.models.CustomerPhoneNumber

data class RegisterCustomerCommand(
    val addresses: List<CustomerAddress>,
    val emails: List<CustomerEmail>,
    val identityDocuments: List<CustomerIdentityDocument>,
    val nationalities: List<CustomerNationality>,
    val personalDetails: CustomerPersonalDetails,
    val phoneNumbers: List<CustomerPhoneNumber>,
) : Command {
    companion object
}
