package committee.nova.mods.lighteco.api.interfaces;

import net.minecraft.network.chat.Component;

/**
 * ICurrency
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/5/8 下午7:12
 */
public interface ICurrency {
    Component getDescription();
    String getCurrencyName();
}
