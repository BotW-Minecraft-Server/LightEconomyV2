package committee.nova.mods.lighteco.caps;

import committee.nova.mods.lighteco.api.interfaces.IAccount;
import committee.nova.mods.lighteco.api.interfaces.ICurrency;
import committee.nova.mods.lighteco.impl.DefaultAccount;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.entity.PlayerComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

/**
 * FabricAccount
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/5/9 上午12:43
 */
public class FabricAccount extends DefaultAccount implements PlayerComponent<FabricAccount>, AutoSyncedComponent {
    private static final String TAG_ACCOUNT = "accounts";
    private final Player provider;


    public FabricAccount(Player player) {
        this.provider = player;
    }

    @Override
    public void setBalance(ICurrency currency, BigDecimal value) {
        account.put(currency.getCurrencyName(), value);
        ModCaps.ACCOUNT.sync(provider);
    }

    @Override
    public void readFromNbt(@NotNull CompoundTag tag) {
        final ListTag accounts = tag.getList(TAG_ACCOUNT, Tag.TAG_COMPOUND);
        for(int i = 0; i < accounts.size(); ++i) {
            CompoundTag account = accounts.getCompound(i);
            this.account.put(account.getString("currency"), new BigDecimal(account.getString("balance")));
        }
    }

    @Override
    public void writeToNbt(@NotNull CompoundTag tag) {
        final ListTag accounts = new ListTag();

        this.account.forEach((currency, bigDecimal) -> {
            final CompoundTag account = new CompoundTag();
            account.putString("currency", currency);
            account.putString("balance", bigDecimal.toPlainString());
            accounts.add(account);
        });

        tag.put(TAG_ACCOUNT, accounts);
    }
}
