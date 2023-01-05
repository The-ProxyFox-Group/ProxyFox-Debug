/*
 * Copyright (c) 2023, The ProxyFox Group
 *
 * This Source Code is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package dev.proxyfox.debug

import dev.proxyfox.debug.extensions.BeforeThrow
import org.quiltmc.loader.api.ModContainer
import org.quiltmc.loader.api.entrypoint.PreLaunchEntrypoint
import org.spongepowered.asm.mixin.injection.InjectionPoint

// Created 2023-05-01T01:49:42

/**
 * Registers various things for Mixin.
 *
 * @author Ampflower
 * @since 0.1.0
 **/
class Main : PreLaunchEntrypoint {
	override fun onPreLaunch(mod: ModContainer) {
		InjectionPoint.register(BeforeThrow::class.java, "PROXYFOX")
	}
}
