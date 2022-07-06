package me.lightningreflex.shelly.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class ShellCommand implements SimpleCommand {

	@Override
	public void execute(final Invocation invocation) {
		CommandSource source = invocation.source();
		// Get the arguments after the command alias
		String[] args = invocation.arguments();

		source.sendMessage(Component.join(
				JoinConfiguration.separator(Component.text(" ")),
				Component.text("Running command:").color(NamedTextColor.AQUA),
				Component.text(String.join(" ", args))
		));

		// Run the command
		try {
			ProcessBuilder builder = new ProcessBuilder(
					"/bin/bash", "-c", String.join(" ", args)
			);
			Process p = builder.start();

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			new Thread(() -> {
				try {
					String s;
					while ((s = stdInput.readLine()) != null) {
						source.sendMessage(
							Component.text(s).clickEvent(ClickEvent.suggestCommand(s))
						);
					}
				} catch (IOException e) {
				}
			}).start();
			new Thread(() -> {
				try {
					String s;
					while ((s = stdError.readLine()) != null) {
						source.sendMessage(
							Component.text(s).clickEvent(ClickEvent.suggestCommand(s))
						);
					}
				} catch (IOException e) {
				}
			}).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean hasPermission(final Invocation invocation) {
		return invocation.source().hasPermission("shelly.shell");
	}
}
