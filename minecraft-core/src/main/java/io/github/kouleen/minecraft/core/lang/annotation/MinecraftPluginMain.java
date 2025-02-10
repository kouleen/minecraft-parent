package io.github.kouleen.minecraft.core.lang.annotation;

import java.lang.annotation.*;

/**
 * @author zhangqing
 * @since 2025/2/10 19:12
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MinecraftPluginMain {

    String value() default "";

    String[] packages() default {};
}
