package io.github.kouleen.minecraft.core.utils;

import io.github.kouleen.minecraft.core.lang.Nullable;

import java.util.Collection;
import java.util.Map;

/**
 * @author zhangqing
 * @since 2025/1/22 16:12
 */
public final class CollectionUtils {

    public static boolean isEmpty(@Nullable Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    public static boolean isEmpty(@Nullable Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }
}
