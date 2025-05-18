plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation ("org.apache.logging.log4j:log4j-api:2.20.0")
    implementation ("org.apache.logging.log4j:log4j-core:2.20.0")
    implementation ("org.apache.logging.log4j:log4j-slf4j-impl:2.20.0")
    testImplementation("org.seleniumhq.selenium:selenium-java:4.25.0")

    // Dependencia de TestNG
    testImplementation("org.testng:testng:7.9.0")
}

tasks.test {
    useTestNG {
        suites ("src/test/resources/testng.xml") 
    }
    reports.html.outputLocation = file("build/reports/testng-report-browserstack")

    systemProperty("mode", System.getProperty("mode", "headless"))

}
