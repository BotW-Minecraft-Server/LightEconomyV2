package committee.nova.mods.lighteco.api.util;

import net.minecraft.world.entity.player.Player;

import java.math.BigDecimal;
import java.util.function.BiPredicate;

/**
 * EcoUtils
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/5/8 下午10:45
 */
public class EcoUtils {
    public static final BigDecimal ZERO = new BigDecimal(0);
    public static final BiPredicate<Player, BigDecimal> NON_NEGATIVE = (p, v) -> v.compareTo(ZERO) > -1;


    public enum EcoActionResult {
        SUCCESS("success"),
        ARG_ILLEGAL("arg_illegal"),
        RESULT_ILLEGAL("result_illegal"),
        CAPABILITY_FAILURE("capability_failure"),
        EVENT_CANCELED("event_canceled");

        private final String id;

        EcoActionResult(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }
}
