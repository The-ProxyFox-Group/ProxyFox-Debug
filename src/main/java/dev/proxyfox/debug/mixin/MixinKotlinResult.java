/*
 * Copyright (c) 2023, The ProxyFox Group
 *
 * This Source Code is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package dev.proxyfox.debug.mixin;// Created 2023-05-01T00:58:40

import dev.proxyfox.debug.MixinSupport;
import dev.proxyfox.debug.Trace;
import kotlin.ResultKt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Ampflower
 * @since 0.1.0
 **/
@Mixin(ResultKt.class)
public class MixinKotlinResult {
	/**
	 * Injects a Trace exception in the suppressed stack of
	 */
	@Inject(method = "throwOnFailure", at = @At("PROXYFOX:THROW"))
	private static void proxyfox$onThrow(final Object self, final CallbackInfo ci) {
		final var exception = MixinSupport.getResultFailure(self);
		assert exception != null : "There should be an exception here, yet there isn't?!";

		exception.addSuppressed(MixinSupport.trimStackHead(new Trace()));
	}
}
