package io.github.kouleen.minecraft.plugin;

import io.github.kouleen.minecraft.core.factory.MinecraftApplication;

import java.lang.reflect.Method;

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
    public static <T> void start(T plugin, ClassLoader... classLoader){
        MinecraftApplication.run(plugin,classLoader);
        Class<?> pluginClazz = plugin.getClass();
        try {
            Class<?> pluginClass = Class.forName("org.bukkit.plugin.Plugin");
            Method serverMethod = pluginClazz.getMethod("getServer");
            Object server = serverMethod.invoke(plugin);
            Class<?> serverClazz = server.getClass();
            Method pluginManagerMethod = serverClazz.getMethod("getPluginManager");
            Object pluginManagerBean = pluginManagerMethod.invoke(server);
            Class<?> pluginManagerClazz = pluginManagerBean.getClass();
            Method enablePluginMethod = pluginManagerClazz.getMethod("enablePlugin", pluginClass);
            enablePluginMethod.invoke(pluginManagerBean,plugin);
        }catch (Exception exception){
            exception.printStackTrace();
        }
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
