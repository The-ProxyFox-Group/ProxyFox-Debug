/*
 * Copyright (c) 2023, The ProxyFox Group
 *
 * This Source Code is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package dev.proxyfox.debug.api

object StringSanitizer {
	private val sanitizations = ArrayList<Sanitization>()

	fun addSanitization(value: String, replace: String) {
		sanitizations.add(Literal(value, replace))
	}

	fun addSanitization(regex: Regex, replace: String) {
		sanitizations.add(Regexp(regex, replace))
	}

	fun sanitize(string: String): String {
		var out = string
		sanitizations.forEach {
			out = it.sanitize(out)
		}
		return out
	}

	sealed interface Sanitization {
		fun sanitize(string: String): String
	}

	class Literal(private val value: String, private val replace: String) : Sanitization {
		override fun sanitize(string: String): String {
			return string.replace(value, replace)
		}
	}

	class Regexp(private val regex: Regex, private val replace: String) : Sanitization {
		override fun sanitize(string: String): String {
			return string.replace(regex, replace)
		}
	}
}
