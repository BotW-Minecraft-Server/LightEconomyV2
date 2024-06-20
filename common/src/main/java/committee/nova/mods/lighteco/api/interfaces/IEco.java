package committee.nova.mods.lighteco.api.interfaces;

import committee.nova.mods.lighteco.api.util.EcoUtils;
import net.minecraft.world.entity.player.Player;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

import static committee.nova.mods.lighteco.api.util.EcoUtils.NON_NEGATIVE;

/**
 * EcoApi
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/5/8 下午10:31
 */
public interface IEco {
    Optional<BigDecimal> getBalance(Player player, ICurrency currency);

    EcoUtils.EcoActionResult vary(
            Player player, BigDecimal value, ICurrency currency,
            BiPredicate<Player, BigDecimal> argChecker,
            BiFunction<Player, BigDecimal, BigDecimal> processor,
            BiPredicate<Player, BigDecimal> resultChecker
    );

    default EcoUtils.EcoActionResult debt(Player player, BigDecimal value, ICurrency currency){
        return vary(player, value, currency, NON_NEGATIVE, (p, v) -> v, NON_NEGATIVE);
    };

    default EcoUtils.EcoActionResult credit(Player player, BigDecimal value, ICurrency currency){
        return vary(player, value, currency, NON_NEGATIVE, (p, v) -> v.negate(), NON_NEGATIVE);
    }
}
