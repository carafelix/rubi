package com.kevinsundqvistnorlen.rubi.option;

import com.kevinsundqvistnorlen.rubi.Utils;
import com.mojang.serialization.Codec;
import net.minecraft.client.option.*;
import net.minecraft.text.Text;

import java.util.Arrays;

public enum RubyRenderMode {
    HIDDEN("hidden"),
    ABOVE("above"),
    BELOW("below"),
    REPLACE("replace");

    private final String translationKey;

    RubyRenderMode(String name) {
        this.translationKey = Option.TRANSLATION_KEY + "." + name;
    }

    public Text getText() {
        return Text.translatable(this.translationKey);
    }

    public static void accept(GameOptions.Visitor visitor) {
        visitor.accept("rubi.renderMode", Option.INSTANCE);
    }

    public static SimpleOption<RubyRenderMode> getOption() {
        return Option.INSTANCE;
    }

    public static RubyRenderMode byId(int id) {
        return RubyRenderMode.values()[id];
    }

    private static final class Option {
        static final String TRANSLATION_KEY = "options.rubi.renderMode";

        static final SimpleOption<RubyRenderMode> INSTANCE = new SimpleOption<>(
            TRANSLATION_KEY,
            SimpleOption.emptyTooltip(),
            (optionText, value) -> value.getText(), // ✅ now valid
            new SimpleOption.PotentialValuesBasedCallbacks<>(
                Arrays.asList(RubyRenderMode.values()),
                Codec.INT.xmap(RubyRenderMode::byId, RubyRenderMode::ordinal)
            ),
            RubyRenderMode.ABOVE,
            (value) -> {
                Utils.LOGGER.debug(
                    "Ruby display mode changed to {} ({})",
                    value.toString(),
                    value.ordinal()
                );
            }
        );
    }
}
