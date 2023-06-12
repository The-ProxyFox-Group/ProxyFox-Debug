/*
 * Copyright (c) 2023, The ProxyFox Group
 *
 * This Source Code is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package dev.proxyfox.debug.test

import dev.proxyfox.debug.api.StringSanitizer
import dev.proxyfox.debug.impl.ProxyPrintStream
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SanitizerTest {

	@Test
	fun testSanitizer() {
		StringSanitizer.addSanitization("owo", "uwu")

		assertEquals(StringSanitizer.sanitize("owo"), "uwu")
	}

	@Test
	fun testSysOut() {
		StringSanitizer.addSanitization("owo", "uwu")
		ProxyPrintStream.setOut()

		println("owo")
	}
}
