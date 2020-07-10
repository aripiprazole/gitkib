plugins {
  kotlin("jvm")
}

group = "com.lorenzoog.gitkib"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
  jcenter()
}

dependencies {
  implementation(kotlin("stdlib-jdk8"))
  implementation("io.ktor", "ktor-server-netty", "1.3.2")
  implementation("io.ktor", "ktor-serialization", "1.3.2")

  // Logging dependency
  implementation("ch.qos.logback:logback-classic:1.2.3")

  // Exposed dependencies
  implementation("org.jetbrains.exposed", "exposed-core", "0.24.1")
  implementation("org.jetbrains.exposed", "exposed-dao", "0.24.1")
  implementation("org.jetbrains.exposed", "exposed-jdbc", "0.24.1")

  // hikari dependency
  implementation("com.zaxxer", "HikariCP", "3.4.5")

  // database
  implementation("org.postgresql", "postgresql", "42.1.4")

  // dot-env dependency
  implementation("io.github.cdimascio", "java-dotenv", "5.2.1")

  // local dependencies
  implementation(project(":commons"))

  // validator dependency
  implementation("org.hibernate.validator", "hibernate-validator", "6.1.1.Final")

  testImplementation("com.github.javafaker", "javafaker", "1.0.2")
  testImplementation("com.h2database", "h2", "1.4.200")
  testImplementation("org.mockito", "mockito-core", "2.1.0")
  testImplementation("io.ktor", "ktor-server-test-host", "1.3.2")
  testImplementation("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
  sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks {
  compileKotlin {
    kotlinOptions.jvmTarget = "1.8"
  }
  compileTestKotlin {
    kotlinOptions.jvmTarget = "1.8"
  }
}
