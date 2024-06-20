package committee.nova.mods.lighteco.caps;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

/**
 * ForgeAccountProvider
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/5/8 下午11:56
 */
@MethodsReturnNonnullByDefault
public class ForgeAccountProvider implements ICapabilitySerializable<CompoundTag> {
    private ForgeAccount account;

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == ModCaps.ACCOUNT ? LazyOptional.of(this::getOrCreateCapability).cast() : LazyOptional.empty();
    }

    public ForgeAccount getOrCreateCapability() {
        if (account == null) this.account = new ForgeAccount();
        return this.account;
    }

    @Override
    public CompoundTag serializeNBT() {
        return getOrCreateCapability().serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        getOrCreateCapability().deserializeNBT(tag);
    }
}
