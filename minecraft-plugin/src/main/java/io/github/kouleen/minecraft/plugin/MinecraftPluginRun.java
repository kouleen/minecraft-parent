package io.github.kouleen.minecraft.plugin;

import io.github.kouleen.minecraft.core.factory.MinecraftApplication;
import io.github.kouleen.minecraft.core.lang.annotation.MinecraftPluginCommand;
import io.github.kouleen.minecraft.core.lang.annotation.MinecraftPluginListener;
import io.github.kouleen.minecraft.core.utils.ObjectUtils;
import io.github.kouleen.minecraft.plugin.utils.RegisterUtils;
import org.bukkit.command.TabExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangqing
 * @since 2025/2/10 19:29
 */
public final class MinecraftPluginRun {

    public static MinecraftPluginRun start(Plugin plugin, ClassLoader... classLoader) {
        MinecraftPluginRun minecraftPluginRun = new MinecraftPluginRun();
        MinecraftApplication.run(plugin, classLoader);
        return minecraftPluginRun;
    }

    public void register(Plugin plugin, String commandMainName){
        List<String> packageList = MinecraftApplication.getPackageList(plugin.getClass());
        List<Class<? extends TabExecutor>> commandClazz = new ArrayList<>();
        List<Class<? extends Listener>> listenerClazz = new ArrayList<>();
        for (String packageName : packageList) {
            List<Class<?>> classList = MinecraftApplication.getClassList(packageName);
            for (Class<?> clazz : classList) {
                MinecraftPluginCommand componentCommand = clazz.getAnnotation(MinecraftPluginCommand.class);
                if(!ObjectUtils.isEmpty(componentCommand)){
                    Object bean = MinecraftPluginRun.getBean(clazz);
                    if (!(bean instanceof TabExecutor)) {
                        throw new RuntimeException("MinecraftPluginCommand annotation class is not the implementation class of TabExecutor.");
                    }
                    TabExecutor tabExecutor = (TabExecutor) bean;
                    commandClazz.add(tabExecutor.getClass());
                }
                MinecraftPluginListener componentListener = clazz.getAnnotation(MinecraftPluginListener.class);
                if(!ObjectUtils.isEmpty(componentListener)){
                    Object bean = MinecraftPluginRun.getBean(clazz);
                    if (!(bean instanceof Listener)) {
                        throw new RuntimeException("MinecraftPluginListener annotation class is not the implementation class of Listener.");
                    }
                    Listener listener = (Listener) bean;
                    listenerClazz.add(listener.getClass());
                }
            }
        }
        Class<? extends TabExecutor>[] tabExecutor = new Class[commandClazz.size()];
        Class<? extends TabExecutor>[] commandClazzArray = commandClazz.toArray(tabExecutor);
        Class<? extends Listener>[] listener = new Class[listenerClazz.size()];
        Class<? extends Listener>[] listenerClazzArray = listenerClazz.toArray(listener);
        RegisterUtils.registerTabExecutors(commandMainName, commandClazzArray);
        RegisterUtils.registerListeners(plugin, listenerClazzArray);
    }


    public static <T> T getBean(Class<T> clazz) {
        return MinecraftApplication.getBean(clazz);
    }
}
