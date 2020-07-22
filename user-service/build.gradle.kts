import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.springframework.boot") version "2.3.1.RELEASE"
  id("io.spring.dependency-management") version "1.0.9.RELEASE"
  kotlin("jvm") version "1.3.72"
  kotlin("plugin.spring") version "1.3.72"
  kotlin("plugin.jpa") version "1.3.72"
}

group = "com.lorenzoog.gitkib"
version = "0.0.1-SNAPSHOT"

java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
  mavenCentral()
  jcenter()
  maven("https://dl.bintray.com/konrad-kaminski/maven")
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-data-elasticsearch")
  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
  implementation("org.springframework.boot:spring-boot-starter-webflux")
  implementation("org.springframework.boot:spring-boot-starter-data-rest")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7")

  implementation("org.springframework.kotlin:spring-kotlin-coroutine:0.3.7")
  implementation("org.springframework.kotlin:spring-webmvc-kotlin-coroutine:0.3.7")
  implementation("org.springframework.kotlin:spring-webflux-kotlin-coroutine:0.3.7")

  implementation("org.jetbrains.exposed:exposed-spring-boot-starter:0.26.1")

  implementation("org.jetbrains.exposed", "exposed-core", "0.24.1")
  implementation("org.jetbrains.exposed", "exposed-dao", "0.24.1")
  implementation("org.jetbrains.exposed", "exposed-jdbc", "0.24.1")

  implementation("com.orbitz.consul:consul-client:1.4.0")

  implementation("org.hibernate:hibernate-validator:6.1.5.Final")
  implementation("org.hibernate:hibernate-validator-annotation-processor:6.1.5.Final")

  implementation("javax.validation:validation-api:2.0.1.Final")

  implementation("javassist:javassist:3.12.1.GA")

  implementation("com.auth0:java-jwt:3.4.0")

  implementation("com.zaxxer:HikariCP:3.4.5")

  developmentOnly("org.springframework.boot:spring-boot-devtools")

  runtimeOnly("org.postgresql:postgresql")

  testRuntimeOnly("com.h2database:h2")

  testImplementation("org.mockito:mockito-core:2.+")
  testImplementation("junit:junit")

  testImplementation("org.springframework.security:spring-security-test")

  testImplementation("org.springframework.boot:spring-boot-starter-test") {
    exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "1.8"
  }
}
