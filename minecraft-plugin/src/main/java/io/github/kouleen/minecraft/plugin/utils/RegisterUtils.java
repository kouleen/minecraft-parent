package io.github.kouleen.minecraft.plugin.utils;

import io.github.kouleen.minecraft.core.utils.ObjectUtils;
import io.github.kouleen.minecraft.plugin.MinecraftPluginRun;

import java.lang.reflect.Method;

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
    public static void registerTabExecutors(String commandMainName, Class<?>... clazzArgs) {
        try {
            Class<?> bukkitClazz = Class.forName("org.bukkit.Bukkit");
            Method method = bukkitClazz.getMethod("getPluginCommand", String.class);
            Object pluginCommandBean = method.invoke(null, commandMainName);
            if (ObjectUtils.isEmpty(pluginCommandBean)) {
                Method getLoggerMethod = bukkitClazz.getMethod("getLogger");
                Object loggerBean = getLoggerMethod.invoke(null);
                Method severeMethod = loggerBean.getClass().getMethod("severe", String.class);
                severeMethod.invoke(loggerBean, "RegisterUtils pluginCommand is null");
                return;
            }
            Class<?> tabCompleterClazz = Class.forName("org.bukkit.command.TabCompleter");
            Class<?> commandExecutorClazz = Class.forName("org.bukkit.command.CommandExecutor");
            for (Class<?> clazz : clazzArgs) {
                Object tabExecutorBean = MinecraftPluginRun.getBean(clazz);
                Class<?> pluginCommandClazz = pluginCommandBean.getClass();
                Method setTabCompleterMethod = pluginCommandClazz.getMethod("setTabCompleter", tabCompleterClazz);
                setTabCompleterMethod.invoke(pluginCommandBean, tabExecutorBean);
                Method setExecutorMethod = pluginCommandClazz.getMethod("setExecutor", commandExecutorClazz);
                setExecutorMethod.invoke(pluginCommandBean, tabExecutorBean);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    /**
     * 注册监听器
     *
     * @param plugin    插件实例
     * @param clazzArgs 监听器实现类
     */
    @SafeVarargs
    public static <T> void registerListeners(T plugin, Class<?>... clazzArgs) {
        try {
            Class<?> bukkitClazz = Class.forName("org.bukkit.Bukkit");
            Class<?> pluginClass = Class.forName("org.bukkit.plugin.Plugin");
            Class<?> listenerClass = Class.forName("org.bukkit.event.Listener");
            Method getPluginManagerMethod = bukkitClazz.getMethod("getPluginManager");
            Object pluginManagerBean = getPluginManagerMethod.invoke(null);
            for (Class<?> clazz : clazzArgs) {
                Object listenerBean = MinecraftPluginRun.getBean(clazz);
                Method registerEventsMethod = pluginManagerBean.getClass().getMethod("registerEvents", listenerClass, pluginClass);
                registerEventsMethod.invoke(pluginManagerBean, listenerBean, plugin);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
