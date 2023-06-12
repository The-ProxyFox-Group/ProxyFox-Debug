/*
 * Copyright (c) 2023, The ProxyFox Group
 *
 * This Source Code is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	alias(libs.plugins.kotlin.jvm)
	alias(libs.plugins.licenser)
	`maven-publish`
}

license {
	rule(file("HEADER"))
}

java {
	sourceCompatibility = JavaVersion.VERSION_17
	targetCompatibility = JavaVersion.VERSION_17
	withSourcesJar()
	withJavadocJar()
}

repositories {
	mavenCentral()
	maven("https://maven.quiltmc.org/repository/release/") {
		name = "QuiltMC"
	}
	maven("https://maven.fabricmc.net/") {
		name = "FabricMC"
	}
}

dependencies {
	implementation("org.quiltmc:quilt-loader:0.19.0")
	implementation("net.fabricmc:sponge-mixin:0.12.5+mixin.0.8.5")
	implementation("org.ow2.asm:asm:9.5")
	implementation("org.ow2.asm:asm-analysis:9.5")
	implementation("org.ow2.asm:asm-commons:9.5")
	implementation("org.ow2.asm:asm-tree:9.5")
	implementation("org.ow2.asm:asm-util:9.5")
	testImplementation(kotlin("test"))
}

tasks {
	test {
		useJUnitPlatform()
	}
	processResources {
		val map = mapOf(
			"java" to java.targetCompatibility.majorVersion,
			"version" to project.version
		)

		inputs.properties(map)

		filesMatching("quilt.mod.json") { expand(map) }
	}
	withType<KotlinCompile> {
		kotlinOptions.jvmTarget = java.targetCompatibility.toString()
	}
}

publishing {
	publications {
		create<MavenPublication>("maven") {
			from(components["java"])
		}
	}
}
