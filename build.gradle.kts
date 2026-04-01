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
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    kotlin("jvm") version "2.2.21"
    alias(libs.plugins.coreBankingConvention)
    alias(baseLibs.plugins.spring.boot)
    alias(baseLibs.plugins.spring.dependency.management)
    alias(baseLibs.plugins.flyway)
}

coreBankingConvention {
    enableCaching = true
    enableGRPC = true
    enableOpenapi = true
    enablePersistence = true
    enableMessaging = true

    caching {
        enableRedis = true
    }

    persistence {
        enableFlyway = true
        enableExposed = true
        enablePostgres = true
        enableMongo = true
    }

    messaging {
        enableKafka = true
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
        optIn.add("kotlin.time.ExperimentalTime")
    }
}

dependencies {
    implementation(platform(libs.corebanking.frisboo.bom))
    implementation(libs.corebanking.frisboo.core)
    implementation(libs.corebanking.frisboo.grpc)
    implementation(libs.corebanking.frisboo.http)
    implementation(libs.corebanking.frisboo.persistence)
    implementation(libs.corebanking.frisboo.security)
    implementation(libs.corebanking.grpccontracts.customerservice)

    testImplementation(libs.corebanking.frisboo.tests)
}

tasks.test {
    failOnNoDiscoveredTests = false
}

tasks.named<BootBuildImage>("bootBuildImage") {
    environment.putAll(
        mapOf(
            "BP_HEALTH_CHECKER_ENABLED" to "true",
            "BP_HEALTH_CHECKER_TYPE" to "http",
            "BP_HEALTH_CHECKER_HTTP_URI" to "/actuator/health/readiness",
            "BP_SPRING_AOT_ENABLED" to "true",
            "BP_JVM_JLINK_ENABLED" to "true",
            "BPE_APPEND_JAVA_TOOL_OPTIONS" to "-Djava.security.egd=file:/dev/./urandom",
        ),
    )

    verboseLogging.set(true)
}
