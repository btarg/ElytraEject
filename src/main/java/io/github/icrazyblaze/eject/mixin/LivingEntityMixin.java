package io.github.icrazyblaze.eject.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "updateFallFlying", at = @At("HEAD"), cancellable = true)
    private void injectTick(CallbackInfo ci) {

        LivingEntity player = (LivingEntity) (Object) this;

        if (player instanceof ServerPlayer && player.isFallFlying() && player.getFallFlyingTicks() > 20 && player.isShiftKeyDown()) {
            this.setSharedFlag(7, false);
            ci.cancel();
        }

    }
}