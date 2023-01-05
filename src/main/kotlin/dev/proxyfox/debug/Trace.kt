/*
 * Copyright (c) 2023, The ProxyFox Group
 *
 * This Source Code is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package dev.proxyfox.debug

// Created 2023-05-01T01:57:43

/**
 * Trace exceptions for recovering the stack trace of coroutines when
 * sent through KTor's SuspendFunctionGun and similar.
 *
 * @author Ampflower
 * @since 0.1.0
 **/
class Trace : Throwable("Trace call @ ${Thread.currentThread()}"), Comparable<Trace> {
	/**
	 * Exact time the trace was created. Used for sorting.
	 * */
	private val time = System.nanoTime()

	override fun compareTo(other: Trace): Int {
		return time.compareTo(other.time)
	}
}
