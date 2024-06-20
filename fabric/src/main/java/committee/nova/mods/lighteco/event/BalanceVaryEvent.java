package committee.nova.mods.lighteco.event;

import committee.nova.mods.lighteco.api.interfaces.ICurrency;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.math.BigDecimal;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

/**
 * BalanceVaryEvent
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/5/9 上午12:57
 */
public class BalanceVaryEvent {
    public static final Event<Pre> PRE = EventFactory.createArrayBacked(Pre.class, callbacks ->
            (player, baseValue, currency,
             argChecker, processor, resultChecker
            ) -> {
        for (Pre callback : callbacks) {
            if (!callback.pre(player, baseValue, currency, argChecker, processor, resultChecker)) {
                return false;
            }
        }
        return true;
    });

    public static final Event<Post> POST = EventFactory.createArrayBacked(Post.class, callbacks ->
            (player, baseValue, currency,
             oldValue, newValue
            ) -> {
                for (Post callback : callbacks) {
                    callback.post(player, baseValue, currency,
                            oldValue, newValue);
                }
            });

    @FunctionalInterface
    public interface Pre {
        boolean pre(Player player, BigDecimal baseValue, ICurrency currency,
                    BiPredicate<Player, BigDecimal> argChecker,
                    BiFunction<Player, BigDecimal, BigDecimal> processor,
                    BiPredicate<Player, BigDecimal> resultChecker
        );
    }

    @FunctionalInterface
    public interface Post {
        void post(Player player, BigDecimal baseValue, ICurrency currency,
                 BigDecimal oldValue, BigDecimal newValue);
    }
}
