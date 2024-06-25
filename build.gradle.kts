plugins {
    id("java")
}

group = "mabb.tistory"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.snmp4j:snmp4j:2.5.0")
}

tasks.test {
    useJUnitPlatform()
}