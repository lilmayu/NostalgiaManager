package dev.mayuna.nostalgiamanager;

import dev.mayuna.nostalgiamanager.api.Module;
import dev.mayuna.nostalgiamanager.modules.enhancedchat.EnhancedChatModule;
import dev.mayuna.nostalgiamanager.modules.moblimiter.MobLimiterModule;
import dev.mayuna.nostalgiamanager.modules.worldborder.WorldBorderModule;
import dev.mayuna.nostalgiamanager.utils.Logger;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

public class Modules {

    private static final @Getter List<Module> modules = new LinkedList<>();

    public static void prepareModules() {
        // All modules regardless if they are enabled or not
        modules.add(new WorldBorderModule());
        modules.add(new EnhancedChatModule());
        modules.add(new MobLimiterModule());
    }

    public static void loadModules() {
        modules.forEach(module -> {
            Logger.addClassPrefix(module.getClass(), module.getClass().getSimpleName());

            Logger.debug("Loading module " + module.getClass().getSimpleName());

            module.onLoad();
        });
    }

    public static void unloadModules() {
        modules.forEach(module -> {
            Logger.debug("Unloading module " + module.getClass().getSimpleName());

            module.onUnload();
        });
    }
}
