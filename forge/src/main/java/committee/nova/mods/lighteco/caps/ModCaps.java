package committee.nova.mods.lighteco.caps;

import committee.nova.mods.lighteco.Constants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * ModCaps
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/5/8 下午10:50
 */
@Mod.EventBusSubscriber
public class ModCaps {
    public static final Capability<ForgeAccount> ACCOUNT = CapabilityManager.get(new CapabilityToken<>() {});


    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (!(event.getObject() instanceof Player)) return;
        event.addCapability(new ResourceLocation(Constants.MOD_ID, "account"), new ForgeAccountProvider());
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        final Player original = event.getOriginal();
        original.reviveCaps();
        final LazyOptional<ForgeAccount> oldCap = original.getCapability(ACCOUNT);
        final LazyOptional<ForgeAccount> newCap = event.getEntity().getCapability(ACCOUNT);
        newCap.ifPresent((n) -> oldCap.ifPresent((o) -> n.deserializeNBT(o.serializeNBT())));
        original.invalidateCaps();
    }
}
