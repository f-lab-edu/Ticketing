plugins {
	java
	id("org.springframework.boot") version "2.6.7"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
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

extra["springCloudVersion"] = "2021.0.3"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-log4j2")
	implementation("org.projectlombok:lombok:1.18.24")
	implementation("io.springfox:springfox-boot-starter:3.0.0")
	implementation("io.springfox:springfox-swagger-ui:3.0.0")
	implementation("com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.4")
	implementation("com.lmax:disruptor:3.4.4")
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	implementation("com.googlecode.json-simple:json-simple:1.1.1")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("com.google.code.findbugs:jsr305:3.0.2")
	implementation ("org.springframework.cloud:spring-cloud-starter-config")
	implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

	modules {
		module("org.springframework.boot:spring-boot-starter-logging") {
			replacedBy("org.springframework.boot:spring-boot-starter-log4j2", "Use Log4j2 instead of Logback")
		}
	}

	compileOnly("org.projectlombok:lombok")
	runtimeOnly("mysql:mysql-connector-java")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
	annotationProcessor("org.projectlombok:lombok")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

	testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

sourceSets {
	create("intTest") {
		compileClasspath += sourceSets.main.get().output
		runtimeClasspath += sourceSets.main.get().output
	}
}

val intTestImplementation by configurations.getting {
	extendsFrom(configurations.implementation.get())
}

configurations["intTestRuntimeOnly"].extendsFrom(configurations.runtimeOnly.get())
configurations["intTestImplementation"].extendsFrom(configurations.testImplementation.get())

val integrationTest = task<Test>("integrationTest") {
	description = "Runs integration tests."
	group = "verification"

	testClassesDirs = sourceSets["intTest"].output.classesDirs
	classpath = sourceSets["intTest"].runtimeClasspath
	shouldRunAfter("test")
}

tasks.check { dependsOn(integrationTest) }
