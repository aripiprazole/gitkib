import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.3.72"

  application
}

application {
  mainClass.set("io.ktor.server.netty.DevelopmentEngine")
}

group = "com.lorenzoog.gitkib"
version = "0.0.1-SNAPSHOT"

java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
  mavenCentral()
  jcenter()
}

dependencies {
  implementation("io.arrow-kt:arrow-core:0.10.4")
  implementation("io.arrow-kt:arrow-syntax:0.10.4")

  arrayOf("server-netty", "auth", "locations").forEach {
    implementation("io.ktor:ktor-$it:1.3.2")
  }

  // kotlin
  arrayOf("reflect", "stdlib-jdk8").forEach {
    implementation(kotlin(it))
  }

  //koin
  arrayOf("core", "ktor").forEach {
    implementation("org.koin:koin-$it:3.0.0-alpha-2")
  }

  implementation("org.slf4j:slf4j-api:1.7.30")
  implementation("ch.qos.logback:logback-classic:1.2.3")

  // kotlin coroutines
  arrayOf("core", "reactor").forEach {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-$it:1.3.7")
  }

  implementation("com.zaxxer:HikariCP:3.4.5")

  // exposed
  arrayOf("jdbc", "dao", "core").forEach {
    implementation("org.jetbrains.exposed:exposed-$it:0.24.1")
  }

  implementation("com.orbitz.consul:consul-client:1.4.0")

  implementation("com.auth0:java-jwt:3.4.0")

  // database driver
  runtimeOnly("org.postgresql:postgresql:42.2.14")
  testRuntimeOnly("com.h2database:h2:1.4.200")

  // validation
  implementation("am.ik.yavi:yavi:0.4.0")

  // test
  testImplementation("junit:junit")
  testImplementation("org.koin:koin-test:3.0.0-alpha-2")
  testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.1.0")
  testImplementation("org.mockito:mockito-all:1.10.19")
  testImplementation("io.ktor:ktor-server-host:1.3.2")
}

tasks.withType<Test> {
  useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")
    jvmTarget = "1.8"
  }
}
