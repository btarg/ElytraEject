package io.github.icrazyblaze.eject.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ElytraItem.class)
public abstract class ElytraMixin extends Item {

    public ElytraMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "isFlyEnabled", at = @At("RETURN"), cancellable = true)
    private static void injected(CallbackInfoReturnable<Boolean> cir) {

        LocalPlayer player = Minecraft.getInstance().player;

        if (!player.level.isClientSide()) {
            return;
        }

        if (player.getItemBySlot(EquipmentSlot.CHEST).is(Items.ELYTRA) && player.isFallFlying() && player.getFallFlyingTicks() > 20 && player.input.shiftKeyDown) {
            cir.setReturnValue(false);
        } else {
            cir.cancel();
        }

    }
}