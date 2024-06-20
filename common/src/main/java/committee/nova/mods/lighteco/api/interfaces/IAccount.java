package committee.nova.mods.lighteco.api.interfaces;

import java.math.BigDecimal;

/**
 * IAccount
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/5/8 下午7:12
 */
public interface IAccount {
    BigDecimal getBalance(ICurrency currency);

    void setBalance(ICurrency currency, BigDecimal value);
}
