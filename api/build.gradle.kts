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
  testImplementation(platform("org.junit:junit-bom:5.10.0"))
  testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
  useJUnitPlatform()
}
