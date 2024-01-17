object Dependencies {

	const val SPRING_BOOT_STARTER_DATA_JPA = "org.springframework.boot:spring-boot-starter-data-jpa:${Versions.SPRING_BOOT_STARTER}"
	const val SPRING_BOOT_STARTER_SECURITY = "org.springframework.boot:spring-boot-starter-security:${Versions.SPRING_BOOT_STARTER}"
	const val SPRING_BOOT_STARTER_VALIDATION = "org.springframework.boot:spring-boot-starter-validation:${Versions.SPRING_BOOT_STARTER}"
	const val SPRING_BOOT_STARTER_WEB = "org.springframework.boot:spring-boot-starter-web:${Versions.SPRING_BOOT_STARTER}"
	const val SPRING_BOOT_STARTER_ACTUATOR = "org.springframework.boot:spring-boot-starter-actuator:${Versions.SPRING_BOOT_STARTER}"
	const val SPRING_BOOT_STARTER_LOG4J2 = "org.springframework.boot:spring-boot-starter-log4j2:${Versions.SPRING_BOOT_STARTER}"
	const val SPRING_BOOT_STARTER_REDIS = "org.springframework.boot:spring-boot-starter-data-redis:${Versions.SPRING_BOOT_STARTER}"

	const val SPRING_CLOUD_STARTER = "org.springframework.cloud:spring-cloud-starter-config:${Versions.SPRING_CLOUD_STARTER}"
	const val SPRING_CLOUD_FEIGN = "org.springframework.cloud:spring-cloud-starter-openfeign:${Versions.SPRING_CLOUD_STARTER}"

	const val LOMBOK = "org.projectlombok:lombok:${Versions.LOMBOK}"

	const val SPRING_FOX_STARTER = "io.springfox:springfox-boot-starter:${Versions.SPRING_FOX}"
	const val SWAGGER = "io.springfox:springfox-swagger-ui:${Versions.SPRING_FOX}"

	const val JASYPT_SPRING_BOOT_STARTER = "com.github.ulisesbocchio:jasypt-spring-boot-starter:${Versions.JASYPT}"

	const val DISRUPTOR = "com.lmax:disruptor:${Versions.DISRUPTOR}"

	const val JJWT = "io.jsonwebtoken:jjwt-api:${Versions.JJWT}"
	const val JSON_SIMPLE = "com.googlecode.json-simple:json-simple:${Versions.JSON_SIMPLE}"

	const val MICROMETER_CORE = "io.micrometer:micrometer-core:${Versions.MICROMETER}"
	const val MICROMETER_REGISTRY_PROMETHEUS = "io.micrometer:micrometer-registry-prometheus:${Versions.MICROMETER}"

	const val JSR350 = "com.google.code.findbugs:jsr305:${Versions.JSR350}"

	object ModuleLib {
		const val SPRING_BOOT_STARTER_LOGGING = "org.springframework.boot:spring-boot-starter-logging"
		const val SPRING_BOOT_STARTER_LOG4J2 = "org.springframework.boot:spring-boot-starter-log4j2"
	}

	object RuntimeOnlyLib {
		const val MYSQL = "mysql:mysql-connector-java:${Versions.MYSQL_CONNECTOR_JAVA}"
		const val JJWT_IMPL = "io.jsonwebtoken:jjwt-impl:${Versions.JJWT}"
		const val JJWT_JACKSON = "io.jsonwebtoken:jjwt-jackson:${Versions.JJWT}"
	}

	object AnnotationProcessorLib {
		const val SPRING_BOOT_PROCESSOR = "org.springframework.boot:spring-boot-configuration-processor:${Versions.SPRING_BOOT_STARTER}"
	}

	object TestLib {
		const val JUPITER = "org.junit.jupiter:junit-jupiter-api:${Versions.JUPITER}"
		const val SPRING_BOOT_STARTER = "org.springframework.boot:spring-boot-starter-test:${Versions.SPRING_BOOT_STARTER}"
		const val SPRING_SECURITY = "org.springframework.security:spring-security-test:${Versions.SECURITY}"
	}

	object DependencyManagementLib {
		const val SPRING_CLOUD = "org.springframework.cloud:spring-cloud-dependencies:${Versions.SPRING_CLOUD}"
	}

}
