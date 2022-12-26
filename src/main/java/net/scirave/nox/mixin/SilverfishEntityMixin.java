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

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.PounceAtTargetGoal;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.SilverfishEntity;
import net.minecraft.world.World;
import net.scirave.nox.config.NoxConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SilverfishEntity.class)
public abstract class SilverfishEntityMixin extends HostileEntityMixin {

    @Override
    public void nox$modifyAttributes(EntityType<?> entityType, World world, CallbackInfo ci) {
        this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).addTemporaryModifier(new EntityAttributeModifier("Nox: Silverfish bonus", 1, EntityAttributeModifier.Operation.MULTIPLY_BASE));
    }

    @Inject(method = "initGoals", at = @At("HEAD"))
    public void nox$silverfishPounce(CallbackInfo ci) {
        this.goalSelector.add(2, new PounceAtTargetGoal((SilverfishEntity) (Object) this, 0.2F));
    }

    @Override
    public void nox$onSuccessfulAttack(LivingEntity target) {
        if (NoxConfig.silverfishMiningFatigueBiteDuration > 0) {
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, NoxConfig.silverfishMiningFatigueBiteDuration, 2), (SilverfishEntity) (Object) this);
        }
    }

    @Override
    public void nox$shouldTakeDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        super.nox$shouldTakeDamage(source, amount, cir);
        if ((source.getName().equals("fall") && !NoxConfig.silverfishTakeFallDamage) || (source.getName().equals("drown") && !NoxConfig.silverfishDrown) || (source.getName().equals("inWall") && !NoxConfig.silverfishSuffocate)) {
            cir.setReturnValue(false);
        }
    }

}
