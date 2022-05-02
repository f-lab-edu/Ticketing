plugins {
	java
	id ("org.springframework.boot") version "2.6.7"
	id ("io.spring.dependency-management") version "1.0.11.RELEASE"
}

group = "com.ticketing"
version = "0.0.1-SNAPSHOT"

val javaVersion = JavaVersion.VERSION_11
java {
	sourceCompatibility = javaVersion
	targetCompatibility = javaVersion
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation ("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation ("org.springframework.boot:spring-boot-starter-security")
	implementation ("org.springframework.boot:spring-boot-starter-validation")
	implementation ("org.springframework.boot:spring-boot-starter-web")

	implementation ("com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.4")

	compileOnly ("org.projectlombok:lombok")
	runtimeOnly ("mysql:mysql-connector-java")
	annotationProcessor ("org.projectlombok:lombok")

	testImplementation ("org.springframework.boot:spring-boot-starter-test")
	testImplementation ("org.springframework.security:spring-security-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
