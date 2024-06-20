package committee.nova.mods.lighteco.api;

import committee.nova.mods.lighteco.api.interfaces.IEco;
import committee.nova.mods.lighteco.api.interfaces.ICurrency;
import committee.nova.mods.lighteco.api.util.EcoUtils.EcoActionResult;
import committee.nova.mods.lighteco.caps.ModCaps;
import committee.nova.mods.lighteco.event.BalanceVaryEvent;
import committee.nova.mods.lighteco.impl.DefaultCurrency;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

/**
 * ForgeEco
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/5/9 上午12:01
 */
public final class ForgeEco implements IEco {
    public static ForgeEco INSTANCE = new ForgeEco();

    public EcoActionResult vary(Player player, BigDecimal value, BiPredicate<Player, BigDecimal> argChecker, BiFunction<Player, BigDecimal, BigDecimal> processor, BiPredicate<Player, BigDecimal> resultChecker) {
       return vary(player, value, DefaultCurrency.INSTANCE, argChecker, processor, resultChecker);
    }

    public Optional<BigDecimal> getBalance(Player player) {
        return getBalance(player, DefaultCurrency.INSTANCE);
    }

    public EcoActionResult debt(Player player, BigDecimal value) {
        return debt(player, value, DefaultCurrency.INSTANCE);
    }

    public EcoActionResult credit(Player player, BigDecimal value) {
        return credit(player, value, DefaultCurrency.INSTANCE);
    }

    @Override
    public EcoActionResult vary(Player player, BigDecimal value, ICurrency currency, BiPredicate<Player, BigDecimal> argChecker, BiFunction<Player, BigDecimal, BigDecimal> processor, BiPredicate<Player, BigDecimal> resultChecker) {
        final BalanceVaryEvent.Pre pre = new BalanceVaryEvent.Pre(player, value, currency, argChecker, processor, resultChecker);
        if (MinecraftForge.EVENT_BUS.post(pre)) return EcoActionResult.EVENT_CANCELED;
        final BigDecimal baseValue = pre.getBaseValue();
        if (!argChecker.test(player, baseValue)) return EcoActionResult.ARG_ILLEGAL;
        final AtomicReference<EcoActionResult> result = new AtomicReference<>(EcoActionResult.CAPABILITY_FAILURE);
        player.getCapability(ModCaps.ACCOUNT).ifPresent(a -> {
            final BigDecimal pastValue = a.getBalance(currency);
            final BigDecimal newValue = pastValue.add(processor.apply(player, baseValue));
            if (!resultChecker.test(player, newValue)) {
                result.set(EcoActionResult.RESULT_ILLEGAL);
                return;
            }
            a.setBalance(currency, newValue);
            result.set(EcoActionResult.SUCCESS);
            MinecraftForge.EVENT_BUS.post(new BalanceVaryEvent.Post(player, baseValue, currency, pastValue, newValue));
        });
        return result.get();
    }

    @Override
    public Optional<BigDecimal> getBalance(Player player, ICurrency currency) {
        final AtomicReference<BigDecimal> result = new AtomicReference<>(null);
        player.getCapability(ModCaps.ACCOUNT).ifPresent(a -> result.set(a.getBalance(currency)));
        return result.get() == null ? Optional.empty() : Optional.of(result.get());
    }
}
