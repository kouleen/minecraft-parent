package io.github.kouleen.minecraft.core.lang.annotation;

import java.lang.annotation.*;

/**
 * @author zhangqing
 * @since 2025/1/22 16:06
 */
@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Nullable {
}
