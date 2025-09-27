package io.github.kouleen.minecraft.plugin;

import io.github.kouleen.minecraft.core.factory.MinecraftApplication;
import io.github.kouleen.minecraft.core.lang.annotation.MinecraftPluginCommand;
import io.github.kouleen.minecraft.core.lang.annotation.MinecraftPluginListener;
import io.github.kouleen.minecraft.core.utils.CollectionUtils;
import io.github.kouleen.minecraft.core.utils.ObjectUtils;
import io.github.kouleen.minecraft.plugin.utils.RegisterUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangqing
 * @since 2025/3/4 10:56
 */
public final class MinecraftPluginRegister {

    static <T> void register(T plugin) {
        List<String> packageList = MinecraftApplication.getPackageList(plugin.getClass());
        Map<String, List<Class<?>>> commandClazzMap = new HashMap<>();
        List<Class<?>> listenerClazz = new ArrayList<>();
        for (String packageName : packageList) {
            List<Class<?>> classList = MinecraftApplication.getClassList(packageName);
            for (Class<?> clazz : classList) {
                try {
                    MinecraftPluginCommand componentCommand = clazz.getAnnotation(MinecraftPluginCommand.class);
                    if (!ObjectUtils.isEmpty(componentCommand)) {
                        Class<?> tabExecutorClazz = Class.forName("org.bukkit.command.TabExecutor");
                        if (!tabExecutorClazz.isAssignableFrom(clazz)) {
                            throw new RuntimeException("MinecraftPluginCommand annotation class is not the implementation class of TabExecutor.");
                        }
                        String[] commands = componentCommand.value();
                        for (String command : commands) {
                            List<Class<?>> commandClazz = commandClazzMap.get(command);
                            if (CollectionUtils.isEmpty(commandClazz)) {
                                commandClazz = new ArrayList<>();
                            }
                            commandClazz.add(clazz);
                            commandClazzMap.put(command, commandClazz);
                        }
                    }
                    MinecraftPluginListener componentListener = clazz.getAnnotation(MinecraftPluginListener.class);
                    if (!ObjectUtils.isEmpty(componentListener)) {
                        Class<?> listenerClass = Class.forName("org.bukkit.event.Listener");
                        if (!listenerClass.isAssignableFrom(clazz)) {
                            throw new RuntimeException("MinecraftPluginListener annotation class is not the implementation class of Listener.");
                        }
                        listenerClazz.add(clazz);
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
        commandClazzMap.forEach((command, cmdClazzList) -> {
            Class<?>[] tabExecutor = new Class[cmdClazzList.size()];
            Class<?>[] commandClazzArray = cmdClazzList.toArray(tabExecutor);
            RegisterUtils.registerTabExecutors(command, commandClazzArray);
        });
        Class<?>[] listener = new Class[listenerClazz.size()];
        Class<?>[] listenerClazzArray = listenerClazz.toArray(listener);
        RegisterUtils.registerListeners(plugin, listenerClazzArray);
    }
}
