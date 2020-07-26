import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("io.spring.dependency-management") version "1.0.9.RELEASE"
  kotlin("jvm") version "1.3.72"
  kotlin("plugin.spring") version "1.3.72"
}

group = "com.lorenzoog.gitkib"
version = "0.0.1-SNAPSHOT"

java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
  mavenCentral()
  jcenter()
  maven("https://dl.bintray.com/konrad-kaminski/maven")
}

@Suppress("NOTHING_TO_INLINE")
inline fun exposed(dependency: String, version: String = "0.24.1") = "org.jetbrains.exposed:exposed-$dependency:$version"

dependencyManagement {
  imports {
    mavenBom("org.springframework.boot:spring-boot-dependencies:2.3.1.RELEASE")
  }
}

dependencies {
  // kotlin
  arrayOf("reflect", "stdlib-jdk8").forEach {
    implementation(kotlin(it))
  }

  // spring
  arrayOf("webflux", "context", "web", "core").forEach {
    implementation("org.springframework:spring-$it")
  }

  implementation("io.projectreactor.netty:reactor-netty:0.9.10.RELEASE")

  implementation("org.slf4j:slf4j-api:1.7.30")
  implementation("ch.qos.logback:logback-classic:1.2.3")

  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

  // kotlin coroutines
  arrayOf("core", "reactor").forEach {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-$it:1.3.7")
  }

  // spring kotlin coroutines
  arrayOf("kotlin-coroutine", "webflux-kotlin-coroutine").forEach {
    implementation("org.springframework.kotlin:spring-$it:0.3.7")
  }

  implementation("com.zaxxer:HikariCP:3.4.5")

  // exposed
  arrayOf("jdbc", "dao", "core").forEach {
    implementation(exposed(it))
  }

  implementation("com.orbitz.consul:consul-client:1.4.0")

  implementation("com.auth0:java-jwt:3.4.0")

  // database driver
  runtimeOnly("org.postgresql:postgresql")
  testRuntimeOnly("com.h2database:h2")

  // validation
  implementation("am.ik.yavi:yavi:0.4.0")

  // test
  testImplementation("junit:junit")
  testImplementation("io.projectreactor:reactor-test")
  testImplementation("io.mockk:mockk:1.10.0")
  testImplementation("org.springframework.boot:spring-boot-starter-test") {
    exclude(group = "org.mockito")
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
