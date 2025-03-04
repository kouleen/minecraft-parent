package io.github.kouleen.minecraft.plugin;

import io.github.kouleen.minecraft.core.factory.MinecraftApplication;
import org.bukkit.plugin.Plugin;

/**
 * @author zhangqing
 * @since 2025/2/10 19:29
 */
public final class MinecraftPluginRun {

    /**
     * 建议在org.bukkit.plugin.Plugin#onLoad()内调用
     * @param plugin 插件示例
     * @param classLoader 类加载器
     */
    public static void start(Plugin plugin, ClassLoader... classLoader) {
        MinecraftApplication.run(plugin, classLoader);
        plugin.getServer().getPluginManager().enablePlugin(plugin);
        MinecraftPluginRegister.register(plugin);
    }

    /**
     * 通过类型注入Bean
     * @param clazz 类型
     * @return 实例
     * @param <T> 实例类型
     */
    public static <T> T getBean(Class<T> clazz) {
        return MinecraftApplication.getBean(clazz);
    }
}
