import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
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
  mavenLocal()
  jcenter()
  maven("https://dl.bintray.com/konrad-kaminski/maven")
  maven("https://repo.spring.io/milestone")
  maven("https://repo.spring.io/snapshot")
}

@Suppress("NOTHING_TO_INLINE")
inline fun exposed(dependency: String, version: String = "0.24.1") = "org.jetbrains.exposed:exposed-$dependency:$version"

dependencies {
  // kotlin
  arrayOf("reflect", "stdlib-jdk8").forEach {
    implementation(kotlin(it))
  }

  // spring
  arrayOf("webflux", "data-jdbc", "security", "data-elasticsearch").forEach {
    implementation("org.framework.boot:spring-boot-starter-$it")
  }

  // spring kotlin coroutines
  arrayOf("kotlin-coroutine", "webmvc-kotlin-coroutine", "webflux-kotlin-coroutine").forEach {
    implementation("org.framework.spring.kotlin:spring-$it:0.3.7")
  }

  implementation("io.projectreactor.netty:reactor-netty:1.0.0-M1")

  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

  // kotlin coroutines
  arrayOf("core", "reactor").forEach {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-$it:1.3.7")
  }

  implementation("com.zaxxer:HikariCP:3.4.5")

  // exposed
  arrayOf("jdbc", "dao", "core").forEach {
    implementation(exposed(it))
  }
  implementation(exposed("spring-boot-starter", "0.26.1"))

  implementation("com.orbitz.consul:consul-client:1.4.0")

  implementation("com.auth0:java-jwt:3.4.0")

  // database driver
  runtimeOnly("org.postgresql:postgresql")
  testRuntimeOnly("com.h2database:h2")

  // validation
  implementation("am.ik.yavi:yavi:0.4.0")

  // test
  testImplementation("junit:junit")
  testImplementation("org.framework.spring.security:spring-security-test")
  testImplementation("org.framework.spring.boot:spring-boot-starter-test") {
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
