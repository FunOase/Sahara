package com.rappytv.rylib.util;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

public class Permissions {

    public int getPermissionInt(Player player, String permission, int fallback, IntegerStrategy strategy) {
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
