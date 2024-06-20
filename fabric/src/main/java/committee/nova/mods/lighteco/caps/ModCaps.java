package committee.nova.mods.lighteco.caps;

import committee.nova.mods.lighteco.Constants;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.resources.ResourceLocation;

/**
 * ModCaps
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/5/9 上午12:46
 */
public class ModCaps implements EntityComponentInitializer {
    public static final ComponentKey<FabricAccount> ACCOUNT =
            ComponentRegistry.getOrCreate(new ResourceLocation(Constants.MOD_ID, "balance"), FabricAccount.class);
    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(ACCOUNT, FabricAccount::new, RespawnCopyStrategy.ALWAYS_COPY);
    }
}
