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
plugins {
    alias(libs.plugins.corebanking.convention)
    alias(libs.plugins.corebanking.convention.core)
    alias(libs.plugins.corebanking.convention.corelibrary)
    alias(libs.plugins.corebanking.convention.grpc)
    alias(libs.plugins.corebanking.convention.messaging)
    alias(libs.plugins.corebanking.convention.openapi)
    alias(libs.plugins.corebanking.convention.persistence)
    alias(libs.plugins.corebanking.convention.quality)
    alias(libs.plugins.corebanking.convention.springboot)
    alias(libs.plugins.corebanking.convention.telemetry)
    alias(libs.plugins.corebanking.convention.testing)
}

dependencies {
//    implementation("com.frisboo.corebanking.test:proto-test")
}

kotlin {
    compilerOptions {
        optIn.add("kotlin.time.ExperimentalTime")
    }
}

coreBankingPersistence {
    enableExposed = true
    enableFlyway = true
    enableMongo = true
    enablePostgres = true
}

coreBankingMessaging {
    enableKafka = true
}
