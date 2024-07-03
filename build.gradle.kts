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
    compileOnly("org.projectlombok:lombok:1.18.12")
    annotationProcessor("org.projectlombok:lombok:1.18.12")
    implementation("org.apache.commons:commons-csv:1.10.0")
    implementation("hikari-cp:hikari-cp:1.8.1")
    implementation("mysql:mysql-connector-java:5.1.42")






}

tasks.test {
    useJUnitPlatform()
}