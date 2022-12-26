/*
 * -------------------------------------------------------------------
 * Nox
 * Copyright (c) 2022 SciRave
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 * -------------------------------------------------------------------
 */

package net.scirave.nox.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.CaveSpiderEntity;
import net.scirave.nox.config.NoxConfig;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CaveSpiderEntity.class)
public abstract class CaveSpiderEntityMixin extends SpiderEntityMixin {

    @Override
    public void nox$onSuccessfulAttack(LivingEntity target) {
        if (NoxConfig.caveSpiderSlownessBiteDuration > 0) {
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, NoxConfig.caveSpiderSlownessBiteDuration, NoxConfig.caveSpiderSlownessBiteLevel - 1), (CaveSpiderEntity) (Object) this);
        }
    }

}
