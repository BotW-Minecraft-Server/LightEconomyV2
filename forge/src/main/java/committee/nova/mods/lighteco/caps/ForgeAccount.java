package committee.nova.mods.lighteco.caps;

import committee.nova.mods.lighteco.impl.DefaultAccount;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.util.INBTSerializable;

import java.math.BigDecimal;

/**
 * ForgeAccount
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/5/8 下午10:52
 */
@AutoRegisterCapability
public class ForgeAccount extends DefaultAccount implements INBTSerializable<CompoundTag> {
    private static final String TAG_ACCOUNT = "accounts";
    @Override
    public CompoundTag serializeNBT() {
        final CompoundTag tag = new CompoundTag();
        final ListTag accounts = new ListTag();

        this.account.forEach((currency, bigDecimal) -> {
            final CompoundTag account = new CompoundTag();
            account.putString("currency", currency);
            account.putString("balance", bigDecimal.toPlainString());
            accounts.add(account);
        });

        tag.put(TAG_ACCOUNT, accounts);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        final ListTag accounts = tag.getList(TAG_ACCOUNT, Tag.TAG_COMPOUND);
        for(int i = 0; i < accounts.size(); ++i) {
            CompoundTag account = accounts.getCompound(i);
            this.account.put(account.getString("currency"), new BigDecimal(account.getString("balance")));
        }
    }
}
