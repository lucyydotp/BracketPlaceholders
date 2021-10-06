package net.lucypoulton.bracket;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.lucyy.squirtgun.format.TextFormatter;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class BracketExpansion extends PlaceholderExpansion {

	public static final boolean adventurePresent;

	static {
		boolean adventurePresent1;
		try {
			Class.forName("net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer");
			adventurePresent1 = true;
		} catch (ClassNotFoundException e) {
			adventurePresent1 = false;
			// the expansion is loaded by placeholderapi so if this is null then something is very wrong
			Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("PlaceholderAPI"))
				.getLogger()
				.warning("The bracket expansion requires at least PlaceholderAPI version 2.10.10. Please update.");
		}
		adventurePresent = adventurePresent1;
	}

	@Override
	public boolean canRegister() {
		return adventurePresent;
	}

	@Override
	public @NotNull String getIdentifier() {
		return "bracket";
	}

	@Override
	public @NotNull String getAuthor() {
		return "Lucy Poulton";
	}

	@Override
	public @NotNull String getVersion() {
		return "1.0-SNAPSHOT";
	}

	@Override
	public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
		final String[] paramsSplit = params.split("_");

		@NotNull final String placeholder;
		@Nullable String prefix;
		@Nullable String suffix;

		switch (paramsSplit.length) {
			case 1:
				placeholder = paramsSplit[0];
				prefix = "";
				suffix = "";
				break;
			case 2:
				prefix = paramsSplit[0];
				placeholder = paramsSplit[1];
				suffix = "";
				break;
			default:
				prefix = paramsSplit[0];
				placeholder = Arrays.stream(paramsSplit)
						.skip(1)
						.limit(paramsSplit.length - 2)
						.collect(Collectors.joining("_"));
				suffix = paramsSplit[paramsSplit.length - 1];
				break;
		}
		String placeholderValue = PlaceholderAPI.setPlaceholders(player, "%" + placeholder + "%");
		if (placeholderValue.equals("")) {
			return "";
		}

		if (prefix.equals("empty")) {
			prefix = "";
		}

		if (suffix.equals("empty")) {
			suffix = "";
		}

		return LegacyComponentSerializer.legacySection().serialize(
				TextFormatter.format(prefix + placeholderValue + suffix)
		);
	}
}
