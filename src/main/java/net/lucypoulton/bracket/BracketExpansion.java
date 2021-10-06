package net.lucypoulton.bracket;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.lucyy.squirtgun.format.TextFormatter;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Arrays;
import java.util.stream.Collectors;

public class BracketExpansion extends PlaceholderExpansion {
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
		@Nullable final String prefix;
		@Nullable final String suffix;

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
		String placeholderValue = PlaceholderAPI.setPlaceholders(player, placeholder);
		if (placeholderValue.equals("")) return "";

		return LegacyComponentSerializer.legacySection().serialize(
				TextFormatter.format(prefix + placeholderValue + suffix)
		);
	}
}
