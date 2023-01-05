/*
 * Copyright (c) 2023, The ProxyFox Group
 *
 * This Source Code is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package dev.proxyfox.debug.extensions

import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.AbstractInsnNode
import org.objectweb.asm.tree.InsnList
import org.objectweb.asm.tree.InsnNode
import org.spongepowered.asm.mixin.injection.IInjectionPointContext
import org.spongepowered.asm.mixin.injection.InjectionPoint
import org.spongepowered.asm.mixin.injection.points.BeforeReturn
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData

// Created 2023-05-01T01:51:45

/**
 * Copy of [BeforeReturn] for the [ATHROW][Opcodes.ATHROW] instruction.
 *
 * @author KJP12
 * @since 0.1.0
 **/
@InjectionPoint.AtCode(namespace = "PROXYFOX", value = "THROW")
class BeforeThrow(data: InjectionPointData) : InjectionPoint() {
	private val ordinal: Int = data.ordinal

	override fun checkPriority(targetPriority: Int, mixinPriority: Int): Boolean {
		return true
	}

	override fun getTargetRestriction(context: IInjectionPointContext?): RestrictTargetLevel {
		return RestrictTargetLevel.ALLOW_ALL
	}

	override fun find(desc: String, insns: InsnList, nodes: MutableCollection<AbstractInsnNode>): Boolean {
		var ordinal = 0
		var found = false

		for (insn in insns) {
			if (insn is InsnNode && insn.opcode == Opcodes.ATHROW) {
				if (this.ordinal == -1 || this.ordinal == ordinal) {
					nodes.add(insn)
					found = true
				}

				ordinal++
			}
		}

		return found
	}
}
