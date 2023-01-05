/*
 * Copyright (c) 2023, The ProxyFox Group
 *
 * This Source Code is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

@file:JvmName("MixinSupport")

package dev.proxyfox.debug

// Created 2023-05-01T01:57:12

/**
 * @author KJP12
 * @since 0.1.0
 **/

/**
 * Fetches the exception in the result for MixinKotlinResult.
 *
 * @param result The result to get the exception from.
 * @return The exception, if any.
 */
fun getResultFailure(result: Result<*>) = result.exceptionOrNull()

/**
 * Trims the head of the stack of Kotlin internals for
 * */
fun Throwable.trimStackHead(): Throwable {
	val stack = stackTrace

	if (stack.size > 2) {
		stackTrace = stack.copyOfRange(0, stack.size - 2)
	}

	return this
}
