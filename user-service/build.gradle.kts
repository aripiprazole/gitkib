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

@Suppress("NOTHING_TO_INLINE")
inline fun exposed(dependency: String, version: String = "0.24.1") = "org.jetbrains.exposed:exposed-$dependency:$version"

@Suppress("NOTHING_TO_INLINE")
inline fun spring(rest: String) = "org.springframework.$rest"

dependencies {
  // kotlin
  arrayOf("reflect", "stdlib-jdk8").forEach {
    implementation(kotlin(it))
  }

  // spring
  arrayOf("data-rest", "webflux", "data-jdbc", "security", "data-elasticsearch").forEach {
    implementation(spring("boot:spring-boot-starter-$it"))
  }

  // spring kotlin coroutines
  arrayOf("kotlin-coroutine", "webmvc-kotlin-coroutine", "webflux-kotlin-coroutine").forEach {
    implementation(spring("kotlin:spring-$it:0.3.7"))
  }

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


  // validator
  arrayOf("validator", "validator-annotation-processor").forEach {
    implementation("org.hibernate:hibernate-$it:6.1.5.Final")
    implementation("org.hibernate:hibernate-$it:6.1.5.Final")
  }
  implementation("javax.validation:validation-api:2.0.1.Final")
  implementation("javassist:javassist:3.12.1.GA")

  implementation("com.auth0:java-jwt:3.4.0")

  developmentOnly(spring("boot:spring-boot-devtools"))

  // database driver
  runtimeOnly("org.postgresql:postgresql")
  testRuntimeOnly("com.h2database:h2")

  // test
  testImplementation("junit:junit")
  testImplementation(spring("security:spring-security-test"))
  testImplementation(spring("boot:spring-boot-starter-test")) {
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
