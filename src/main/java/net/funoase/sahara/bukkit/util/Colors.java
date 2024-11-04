package net.funoase.sahara.bukkit.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.CharacterAndFormat;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Colors {

    public static Component translateCodes(String message) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(message);
    }

    public static Component translatePlayerCodes(CommandSender sender, String message, String basePermission) {
        LegacyComponentSerializer.Builder builder = LegacyComponentSerializer
                .builder()
                .character(LegacyComponentSerializer.AMPERSAND_CHAR)
                .hexCharacter(LegacyComponentSerializer.HEX_CHAR);
        List<CharacterAndFormat> formats = new ArrayList<>();
        if(sender.hasPermission(basePermission + ".hex")) {
            builder.hexColors();
        }
        if(sender.hasPermission(basePermission + ".colors")) {
            formats.add(CharacterAndFormat.BLACK);
            formats.add(CharacterAndFormat.DARK_BLUE);
            formats.add(CharacterAndFormat.DARK_GREEN);
            formats.add(CharacterAndFormat.DARK_AQUA);
            formats.add(CharacterAndFormat.DARK_RED);
            formats.add(CharacterAndFormat.DARK_PURPLE);
            formats.add(CharacterAndFormat.GOLD);
            formats.add(CharacterAndFormat.GRAY);
            formats.add(CharacterAndFormat.DARK_GRAY);
            formats.add(CharacterAndFormat.BLUE);
            formats.add(CharacterAndFormat.GREEN);
            formats.add(CharacterAndFormat.AQUA);
            formats.add(CharacterAndFormat.RED);
            formats.add(CharacterAndFormat.LIGHT_PURPLE);
            formats.add(CharacterAndFormat.YELLOW);
            formats.add(CharacterAndFormat.WHITE);
            formats.add(CharacterAndFormat.RESET);
        }
        if(sender.hasPermission(basePermission + ".bold")) {
            formats.add(CharacterAndFormat.BOLD);
        }
        if(sender.hasPermission(basePermission + ".italic")) {
            formats.add(CharacterAndFormat.ITALIC);
        }
        if(sender.hasPermission(basePermission + ".underline")) {
            formats.add(CharacterAndFormat.UNDERLINED);
        }
        if(sender.hasPermission(basePermission + ".strikethrough")) {
            formats.add(CharacterAndFormat.STRIKETHROUGH);
        }
        if(sender.hasPermission(basePermission + ".magic")) {
            formats.add(CharacterAndFormat.OBFUSCATED);
        }
        if(!formats.isEmpty()) builder.formats(formats);
        return builder.build().deserialize(message);
    }
}
