package net.funoase.sahara.bukkit.util;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Permissions {

    public static <T extends Enum<T>> T getEnumValue(Player player, String permission, T fallback, Class<T> enumm) {
        for(PermissionAttachmentInfo permissionInfo : player.getEffectivePermissions()) {
            String permissionString = permissionInfo.getPermission();
            if(permissionString.startsWith(permission + ".")) {
                permissionString = permissionString.replace(permission + ".", "");
                try {
                    return Enum.valueOf(enumm, permissionString.toUpperCase());
                } catch (IllegalArgumentException ignored) {}
            }
        }

        return fallback;
    }

    public static List<String> getValues(Player player, String permission) {
        List<String> values = new ArrayList<>();
        for(PermissionAttachmentInfo permissionInfo : player.getEffectivePermissions()) {
            String permissionString = permissionInfo.getPermission();
            if(permissionString.startsWith(permission + ".")) {
                permissionString = permissionString.replace(permission + ".", "");
                if(!permissionString.isBlank()) values.add(permissionString);
            }
        }

        return values;
    }

    public static boolean hasExactPermission(Player player, String permission) {
        for(PermissionAttachmentInfo permissionInfo : player.getEffectivePermissions()) {
            if(permissionInfo.getPermission().equalsIgnoreCase(permission)) return true;
        }
        return false;
    }

    public static int getInt(Player player, String permission, int fallback, IntegerStrategy strategy) {
        for(PermissionAttachmentInfo permissionInfo : player.getEffectivePermissions()) {
            String permissionString = permissionInfo.getPermission();
            if(permissionString.startsWith(permission + ".")) {
                permissionString = permissionString.replace(permission + ".", "");
                try {
                    int value = Integer.parseInt(permissionString);
                    if(strategy == IntegerStrategy.HIGHER) {
                        if(value > fallback) fallback = value;
                    } else {
                        if(value < fallback) fallback = value;
                    }
                } catch (NumberFormatException ignored) {}
            }
        }

        return fallback;
    }

    public enum IntegerStrategy {
        HIGHER, LOWER
    }
}
