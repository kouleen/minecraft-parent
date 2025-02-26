package io.github.kouleen.minecraft.plugin.utils;

import io.github.kouleen.minecraft.core.utils.ObjectUtils;
import io.github.kouleen.minecraft.plugin.MinecraftPluginRun;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

/**
 * @author zhangqing
 * @since 2025/2/26 13:22
 */
public final class RegisterUtils {

    /**
     * 注册cmd执行器、tab执行器
     *
     * @param commandMainName 主命令
     * @param clazzArgs       cmd执行器、tab执行器实现类
     */
    @SafeVarargs
    public static void registerTabExecutors(String commandMainName, Class<? extends TabExecutor>... clazzArgs) {
        PluginCommand pluginCommand = Bukkit.getPluginCommand(commandMainName);
        if (ObjectUtils.isEmpty(pluginCommand)) {
            Bukkit.getLogger().severe("RegisterUtils pluginCommand is null");
            return;
        }
        for (Class<? extends TabExecutor> clazz : clazzArgs) {
            TabExecutor tabExecutor = MinecraftPluginRun.getBean(clazz);
            pluginCommand.setTabCompleter(tabExecutor);
            pluginCommand.setExecutor(tabExecutor);
        }
    }

    /**
     * 注册监听器
     *
     * @param plugin    插件实例
     * @param clazzArgs 监听器实现类
     */
    @SafeVarargs
    public static void registerListeners(Plugin plugin, Class<? extends Listener>... clazzArgs) {
        PluginManager pluginManager = Bukkit.getPluginManager();
        for (Class<? extends Listener> clazz : clazzArgs) {
            Listener listener = MinecraftPluginRun.getBean(clazz);
            pluginManager.registerEvents(listener, plugin);
        }
    }
}
