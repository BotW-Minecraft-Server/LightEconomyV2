package committee.nova.mods.lighteco.event;

import committee.nova.mods.lighteco.api.interfaces.ICurrency;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Cancelable;

import java.math.BigDecimal;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

/**
 * BalanceVaryEvent
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/5/8 下午10:43
 */
public class BalanceVaryEvent extends PlayerEvent {
    protected ICurrency currency;
    protected BigDecimal baseValue;

    private BalanceVaryEvent(Player player, BigDecimal baseValue, ICurrency currency) {
        super(player);
        this.baseValue = baseValue;
        this.currency = currency;
    }

    public BigDecimal getBaseValue() {
        return baseValue;
    }

    public ICurrency getCurrency() {
        return currency;
    }

    @Cancelable
    public static class Pre extends BalanceVaryEvent{
        private final BiPredicate<Player, BigDecimal> argChecker;
        private final BiFunction<Player, BigDecimal, BigDecimal> processor;
        private final BiPredicate<Player, BigDecimal> resultChecker;

        public Pre(
                Player player, BigDecimal baseValue, ICurrency currency,
                BiPredicate<Player, BigDecimal> argChecker,
                BiFunction<Player, BigDecimal, BigDecimal> processor,
                BiPredicate<Player, BigDecimal> resultChecker) {
            super(player, baseValue, currency);
            this.argChecker = argChecker;
            this.processor = processor;
            this.resultChecker = resultChecker;
        }

        public void setBaseValue(BigDecimal baseValue) {
            this.baseValue = baseValue;
        }

        public BiPredicate<Player, BigDecimal> getArgChecker() {
            return argChecker;
        }

        public BiFunction<Player, BigDecimal, BigDecimal> getProcessor() {
            return processor;
        }

        public BiPredicate<Player, BigDecimal> getResultChecker() {
            return resultChecker;
        }
    }

    public static class Post extends BalanceVaryEvent {
        private final BigDecimal oldValue;
        private final BigDecimal newValue;

        public Post(Player player, BigDecimal baseValue, ICurrency currency,
                    BigDecimal oldValue, BigDecimal newValue) {
            super(player, baseValue, currency);
            this.oldValue = oldValue;
            this.newValue = newValue;
        }

        public BigDecimal getOldValue() {
            return oldValue;
        }

        public BigDecimal getNewValue() {
            return newValue;
        }
    }
}
