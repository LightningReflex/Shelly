package me.lightningreflex.shelly;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import me.lightningreflex.shelly.commands.ShellCommand;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

@Plugin(
		id = "shelly",
		name = "Shelly",
		version = "1.0",
		description = "Run shell commands from in game.",
		url = "https://discord.gg/C6RQMtXXcV",
		authors = {"LightningReflex"}
)
public class Shelly {
	private ProxyServer proxy;
	public me.lightningreflex.shelly.formatter.formatter formatter;

	@Inject
	public Shelly(
			final ProxyServer proxy
	) {
		this.proxy = proxy;
	}

	@Subscribe
	public void onProxyInitialization(ProxyInitializeEvent event) {
		CommandManager commandManager = proxy.getCommandManager();
		CommandMeta meta = commandManager.metaBuilder("shelly")
				// Specify other aliases (optional)
				.aliases("shell", "bash", "sh")
				.build();
		commandManager.register(meta, new ShellCommand());
	}
}
