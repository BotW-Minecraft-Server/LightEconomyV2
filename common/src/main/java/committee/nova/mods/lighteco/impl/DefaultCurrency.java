package committee.nova.mods.lighteco.impl;

import committee.nova.mods.lighteco.api.interfaces.ICurrency;
import net.minecraft.network.chat.Component;

/**
 * DefaultCurrency
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/5/8 下午10:32
 */
public class DefaultCurrency implements ICurrency {
    public static final DefaultCurrency INSTANCE = new DefaultCurrency();
    @Override
    public Component getDescription() {
        return Component.translatable("info.lighteco.coin_currency");
    }

    @Override
    public String getCurrencyName() {
        return "coin";
    }
}
