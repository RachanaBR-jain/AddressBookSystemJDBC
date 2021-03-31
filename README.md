# AddressBookSystemJDBC
plugins {
    id 'java'
}

group 'org.example'
version '1.0-SNAPSHOT'

repositories {
    mavenCentral()
}

dependencies {
    //implementation 'junit:junit:4.12'
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.6.0'
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine'
    implementation group: 'mysql', name: 'mysql-connector-java', version: '8.0.22'

}

test {
    useJUnitPlatform()
}
