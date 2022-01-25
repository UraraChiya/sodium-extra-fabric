package me.flashyreese.mods.sodiumextra.common.util;

import me.jellysquid.mods.sodium.client.gui.options.control.ControlValueFormatter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Monitor;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

public interface ControlValueFormatterExtended extends ControlValueFormatter {
    static ControlValueFormatter resolution() {
        Monitor monitor = MinecraftClient.getInstance().getWindow().getMonitor();
        return (v) -> {
            if (monitor == null) {
                return new TranslatableText("options.fullscreen.unavailable").getString();
            } else {
                return v == 0 ? new TranslatableText("options.fullscreen.current").getString() : new LiteralText(monitor.getVideoMode(v - 1).toString()).getString();
            }
        };
    }
}
