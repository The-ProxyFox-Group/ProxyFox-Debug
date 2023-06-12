/*
 * Copyright (c) 2023, The ProxyFox Group
 *
 * This Source Code is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package dev.proxyfox.debug.impl

import dev.proxyfox.debug.api.StringSanitizer
import java.io.PrintStream

class ProxyPrintStream(val printer: PrintStream) : PrintStream(printer) {
	override fun write(buf: ByteArray, off: Int, len: Int) {
		super.write(StringSanitizer.sanitize(String(buf)).encodeToByteArray(), off, len)
	}

	companion object {
		fun setOut() {
			System.setOut(ProxyPrintStream(System.out))
			System.setErr(ProxyPrintStream(System.err))
		}
	}
}
