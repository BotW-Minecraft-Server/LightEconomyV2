package committee.nova.mods.lighteco.impl;

import committee.nova.mods.lighteco.api.interfaces.IAccount;
import committee.nova.mods.lighteco.api.interfaces.ICurrency;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * DefaultAccount
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/5/8 下午10:37
 */
public class DefaultAccount implements IAccount {
    public Map<String, BigDecimal> account = new HashMap<>();
    @Override
    public BigDecimal getBalance(ICurrency currency) {
        if (account.containsKey(currency.getCurrencyName())) { return account.get(currency.getCurrencyName()); }
        else return new BigDecimal(0);
    }

    @Override
    public void setBalance(ICurrency currency, BigDecimal value) {
        account.put(currency.getCurrencyName(), value);
    }
}
