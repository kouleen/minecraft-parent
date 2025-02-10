package io.github.kouleen.minecraft.core.exception;

/**
 * @author zhangqing
 * @since 2025/2/10 19:32
 */
public class SingletonException extends RuntimeException {

    /**
     * Create a new SingletonException with the specified message.
     *
     * @param message the detail message
     */
    public SingletonException(String message) {
        super(message);
    }

    /**
     * Create a new SingletonException with the specified message
     * and root cause.
     *
     * @param message   the detail message
     * @param throwable the root cause
     */
    public SingletonException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
