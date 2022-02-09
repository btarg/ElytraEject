package io.github.icrazyblaze.eject.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {


    @Inject(method = "updateFallFlying", at = @At("HEAD"), cancellable = true)
    private void injectTick(CallbackInfo ci) {

        LivingEntity player = (LivingEntity) (Object) this;

        if (player instanceof ServerPlayer && player.isFallFlying() && player.getFallFlyingTicks() > 20 && player.isShiftKeyDown()) {
            ((EntityAccessor) player).setSharedFlagInvoker(7, false);
            ci.cancel();
        }

    }
}