/*
 * Copyright (c) 2023, The ProxyFox Group
 *
 * This Source Code is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package dev.proxyfox.debug.test

import dev.proxyfox.debug.Trace
import dev.proxyfox.debug.recoverStackTrace
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

// Created 2023-05-01T03:41:24

/**
 * @author KJP12
 * @since 0.1.0
 **/
class TraceUtilTest {
	@Test
	fun testRecoverTrace() {
		val exception = frameC()

		frameA(exception)
		frameB(exception)

		val stack = exception.recoverStackTrace()

		for (i in 0 until 6) {
			assertEquals(stack[i].className, this.javaClass.canonicalName)
		}

		assertEquals(stack[0].methodName, "frameC")
		assertEquals(stack[1].methodName, "testRecoverTrace")
		assertEquals(stack[2].methodName, "frameA")
		assertEquals(stack[3].methodName, "testRecoverTrace")
		assertEquals(stack[4].methodName, "frameB")
		assertEquals(stack[5].methodName, "testRecoverTrace")
		assertNotEquals(stack[6].className, this.javaClass.canonicalName)
	}

	private fun frameA(throwable: Throwable) {
		throwable.addSuppressed(Trace())
	}

	private fun frameB(throwable: Throwable) {
		throwable.addSuppressed(Trace())
	}

	private fun frameC() = Exception()
}
