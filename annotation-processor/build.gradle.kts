plugins {
  `java-library`
}

java {
  toolchain {
    version = JavaLanguageVersion.of(21)
  }
}

group = "com.github.jsonrecords"
version = "0.1.0"

repositories {
  mavenCentral()
}

dependencies {
  compileOnly("com.google.auto.service:auto-service-annotations:1.1.1")
  annotationProcessor("com.google.auto.service:auto-service:1.1.1")

  testImplementation(platform("org.junit:junit-bom:5.10.0"))
  testImplementation("org.junit.jupiter:junit-jupiter")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher") // see https://docs.gradle.org/8.7/userguide/upgrading_version_8.html#test_framework_implementation_dependencies
  testImplementation("io.toolisticon.cute:cute:1.7.0")
  testImplementation("io.toolisticon.cute:extension-junit5:1.7.0")
}

tasks.test {
  useJUnitPlatform()
}
