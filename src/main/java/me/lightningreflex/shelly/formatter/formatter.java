package me.lightningreflex.shelly.formatter;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public interface formatter {
	Component format(
			final @NotNull String string,
			final Audience audience
	);
}