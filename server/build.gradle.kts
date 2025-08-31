
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
	java
	id("org.springframework.boot") version "4.0.0-SNAPSHOT"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "br.app.pregsrateio"
version = "1.0.0"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/snapshot") }
}

object Versions {
	const val springdoc = "2.8.9"
	const val commonsCodec = "1.19.0"
    const val mapstruct = "1.6.3"
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-aop")
	implementation("org.springframework.boot:spring-boot-starter-cache")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
	implementation("org.springframework.security:spring-security-oauth2-jose")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("io.micrometer:micrometer-tracing-bridge-brave")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${Versions.springdoc}")
	implementation("com.github.ben-manes.caffeine:caffeine")
	implementation("commons-codec:commons-codec:${Versions.commonsCodec}")

    // Lombok
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

    // MapStruct
    implementation("org.mapstruct:mapstruct:${Versions.mapstruct}")
    annotationProcessor("org.mapstruct:mapstruct-processor:${Versions.mapstruct}")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	developmentOnly("org.springframework.boot:spring-boot-docker-compose")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-Amapstruct.defaultComponentModel=spring")
}

tasks.withType<BootBuildImage> {
    imageName.set("registry.pregsrateio.app.br/pregs-rateio-server:${project.version}")
}

tasks.register<BootBuildImage>("bootBuildImageProd") {
    group = "build"
    description = "Buildar imagem Docker pra rodar no ambiente"

    archiveFile.set(tasks.named<BootBuildImage>("bootBuildImage")
        .flatMap { it.archiveFile })
}

tasks.named<BootBuildImage>("bootBuildImageProd") {
    imagePlatform = "linux/amd64"
}
