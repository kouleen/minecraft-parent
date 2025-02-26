package io.github.kouleen.minecraft.core.lang.annotation;

import java.lang.annotation.*;

/**
 * @author zhangqing
 * @since 2025/2/26 13:41
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@MinecraftPluginComponent
public @interface MinecraftPluginCommand {

}
