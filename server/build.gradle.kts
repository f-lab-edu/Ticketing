import Dependencies.AnnotationProcessorLib
import Dependencies.DependencyManagementLib
import Dependencies.ModuleLib
import Dependencies.RuntimeOnlyLib
import Dependencies.TestLib

plugins {
	java
	id("org.springframework.boot") version Versions.SPRING_BOOT_STARTER
	id("io.spring.dependency-management") version Versions.SPRING_DEPENDENCY_MANAGEMENT
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

extra["springCloudVersion"] = Versions.SPRING_CLOUD

dependencies {
	implementation(Dependencies.SPRING_BOOT_STARTER_DATA_JPA)
	implementation(Dependencies.SPRING_BOOT_STARTER_SECURITY)
	implementation(Dependencies.SPRING_BOOT_STARTER_VALIDATION)
	implementation(Dependencies.SPRING_BOOT_STARTER_WEB)
	implementation(Dependencies.SPRING_BOOT_STARTER_ACTUATOR)
	implementation(Dependencies.SPRING_BOOT_STARTER_LOG4J2)
	implementation(Dependencies.LOMBOK)
	implementation(Dependencies.SPRING_FOX_STARTER)
	implementation(Dependencies.SWAGGER)
	implementation(Dependencies.JASYPT_SPRING_BOOT_STARTER)
	implementation(Dependencies.DISRUPTOR)
	implementation(Dependencies.JJWT)
	implementation(Dependencies.JSON_SIMPLE)
	implementation(Dependencies.SPRING_BOOT_STARTER_REDIS)
	implementation(Dependencies.JSR350)
	implementation(Dependencies.SPRING_CLOUD_STARTER)
	implementation(Dependencies.SPRING_CLOUD_FEIGN)
	implementation(Dependencies.MICROMETER_CORE)
	implementation(Dependencies.MICROMETER_REGISTRY_PROMETHEUS)

	modules {
		module(ModuleLib.SPRING_BOOT_STARTER_LOGGING) {
			replacedBy(ModuleLib.SPRING_BOOT_STARTER_LOG4J2, "Use Log4j2 instead of Logback")
		}
	}

	compileOnly(Dependencies.LOMBOK)
	runtimeOnly(RuntimeOnlyLib.MYSQL)
	runtimeOnly(RuntimeOnlyLib.JJWT_IMPL)
	runtimeOnly(RuntimeOnlyLib.JJWT_JACKSON)
	annotationProcessor(Dependencies.LOMBOK)
	annotationProcessor(AnnotationProcessorLib.SPRING_BOOT_PROCESSOR)

	testImplementation(TestLib.JUPITER)
	testImplementation(TestLib.SPRING_BOOT_STARTER)
	testImplementation(TestLib.SPRING_SECURITY)
}

dependencyManagement {
	imports {
		mavenBom(DependencyManagementLib.SPRING_CLOUD)
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
