/*
 * Copyright (c) 2023, The ProxyFox Group
 *
 * This Source Code is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

@file:JvmName("TraceUtil")

package dev.proxyfox.debug

import java.util.*

// Created 2023-05-01T02:50:17

/**
 * @author Ampflower
 * @since 0.1.0
 **/

fun Throwable.recoverStackTrace(): Array<StackTraceElement> {
	val traces = this.suppressed.filterIsInstance<Trace>()
	val stackTrace = this.stackTrace

	// If there are no suppressed traces, return the original stack trace.
	if (traces.isEmpty()) {
		return stackTrace
	}

	var lastMutualCount = 0
	var first = true
	val recovered = ArrayList<StackTraceElement>()

	for (trace in traces) {
		val traceStackTrace = trace.stackTrace
		val to = countToMutual(traceStackTrace, stackTrace)
		lastMutualCount = traceStackTrace.size - to

		if (first) {
			recovered.addAllInRange(stackTrace, 0, stackTrace.size - lastMutualCount)
			first = false
		}


		recovered.addAllInRange(traceStackTrace, 0, to)
	}

	recovered.addAllInRange(stackTrace, stackTrace.size - lastMutualCount, stackTrace.size)

	return recovered.toTypedArray()
}

fun <T> MutableCollection<T>.addAllInRange(array: Array<T>, from: Int, to: Int) {
	if (from >= to) throw IllegalArgumentException("to >= from ($to >= $from) for array size ${array.size}")
	if (from < 0 || from > array.size) throw ArrayIndexOutOfBoundsException("from ($from) out of range of 0 until ${array.size}; to: $to")
	if (to > array.size) throw ArrayIndexOutOfBoundsException("to ($to) out of range of 0 until ${array.size}; from: $from")

	for (i in from until to) {
		add(array[i])
	}
}

fun <T> countToMutual(trace: Array<T>, reference: Array<T>): Int {
	var ia = trace.size
	var ib = reference.size

	while (--ia >= 0 && --ib >= 0 && trace[ia] == reference[ib]) {
	}

	ia++
	ib++

	assert((trace.size - ia) == (reference.size - ib)) {
		"Mutual count failed; ${trace.size - ia} != ${reference.size - ib}"
	}

	assert(Arrays.equals(trace, ia, trace.size, reference, ib, reference.size)) {
		buildString {
			append("Array mismatch: ia = $ia, ib = $ib, hint = .\n\ntrace:\n")
			trace.forEach(this::append)
			append("\n\nreference:\n")
			reference.forEach(this::append)
			append("\n\norigin:")
		}
	}

	return ia
}
