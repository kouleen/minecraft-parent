package io.github.kouleen.minecraft.plugin;

import io.github.kouleen.minecraft.core.factory.MinecraftApplication;
import io.github.kouleen.minecraft.core.lang.annotation.MinecraftPluginCommand;
import io.github.kouleen.minecraft.core.lang.annotation.MinecraftPluginListener;
import io.github.kouleen.minecraft.core.utils.CollectionUtils;
import io.github.kouleen.minecraft.core.utils.ObjectUtils;
import io.github.kouleen.minecraft.plugin.utils.RegisterUtils;
import org.bukkit.command.TabExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangqing
 * @since 2025/3/4 10:56
 */
public final class MinecraftPluginRegister {

    static void register(Plugin plugin) {
        List<String> packageList = MinecraftApplication.getPackageList(plugin.getClass());
        Map<String, List<Class<? extends TabExecutor>>> commandClazzMap = new HashMap<>();
        List<Class<? extends Listener>> listenerClazz = new ArrayList<>();
        for (String packageName : packageList) {
            List<Class<?>> classList = MinecraftApplication.getClassList(packageName);
            for (Class<?> clazz : classList) {
                MinecraftPluginCommand componentCommand = clazz.getAnnotation(MinecraftPluginCommand.class);
                if (!ObjectUtils.isEmpty(componentCommand)) {
                    Object bean = MinecraftPluginRun.getBean(clazz);
                    if (!(bean instanceof TabExecutor)) {
                        throw new RuntimeException("MinecraftPluginCommand annotation class is not the implementation class of TabExecutor.");
                    }

                    TabExecutor tabExecutor = (TabExecutor) bean;
                    String[] commands = componentCommand.value();
                    for (String command : commands) {
                        List<Class<? extends TabExecutor>> commandClazz = commandClazzMap.get(command);
                        if (CollectionUtils.isEmpty(commandClazz)) {
                            commandClazz = new ArrayList<>();
                        }
                        commandClazz.add(tabExecutor.getClass());
                        commandClazzMap.put(command,commandClazz);
                    }
                }
                MinecraftPluginListener componentListener = clazz.getAnnotation(MinecraftPluginListener.class);
                if (!ObjectUtils.isEmpty(componentListener)) {
                    Object bean = MinecraftPluginRun.getBean(clazz);
                    if (!(bean instanceof Listener)) {
                        throw new RuntimeException("MinecraftPluginListener annotation class is not the implementation class of Listener.");
                    }
                    Listener listener = (Listener) bean;
                    listenerClazz.add(listener.getClass());
                }
            }
        }
        commandClazzMap.forEach((command, cmdClazzList) -> {
            Class<? extends TabExecutor>[] tabExecutor = new Class[cmdClazzList.size()];
            Class<? extends TabExecutor>[] commandClazzArray = cmdClazzList.toArray(tabExecutor);
            RegisterUtils.registerTabExecutors(command, commandClazzArray);
        });
        Class<? extends Listener>[] listener = new Class[listenerClazz.size()];
        Class<? extends Listener>[] listenerClazzArray = listenerClazz.toArray(listener);
        RegisterUtils.registerListeners(plugin, listenerClazzArray);
    }
}
