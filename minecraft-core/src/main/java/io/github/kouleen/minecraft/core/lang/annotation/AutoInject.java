package io.github.kouleen.minecraft.core.lang.annotation;

import java.lang.annotation.*;

/**
 * @author zhangqing
 * @since 2025/2/10 19:11
 */
@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoInject {
}
