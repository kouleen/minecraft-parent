package io.github.kouleen.minecraft.plugin;

import io.github.kouleen.minecraft.core.factory.MinecraftApplication;

/**
 * @author zhangqing
 * @since 2025/2/10 19:29
 */
public final class MinecraftPluginRun {

    public static void start(Object plugin, ClassLoader... classLoader) {
        MinecraftApplication.run(plugin, classLoader);
    }

    public static <T> T getBean(Class<T> clazz) {
        return MinecraftApplication.getBean(clazz);
    }
}
