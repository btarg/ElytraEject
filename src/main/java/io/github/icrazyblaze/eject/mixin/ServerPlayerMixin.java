package io.github.icrazyblaze.eject.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void injected(CallbackInfo ci) {

        ServerPlayer player = (ServerPlayer) (Object) this;
        ItemStack chestStack = player.getItemBySlot(EquipmentSlot.CHEST);
        int original = chestStack.getDamageValue();

        if (chestStack.is(Items.ELYTRA) && player.isFallFlying() && player.getFallFlyingTicks() > 20 && player.isShiftKeyDown()) {
            chestStack.setDamageValue(-1);
            chestStack.setDamageValue(original);
        } else {
            ci.cancel();
        }
    }
}