package com.vm62.diary.common.utils;

import com.google.inject.Injector;

public class InjectorProvider {

    private static Injector injector;

    public synchronized static void init(Injector injector) {
        if (InjectorProvider.injector != null) {
            throw new IllegalStateException("InjectorProvider is already initialized.");
        }
        InjectorProvider.injector = injector;
    }

    public static <T> T inject(Class<T> type) {
        if (injector == null) {
            throw new IllegalStateException("Cannot inject instace of " + type.getName() + ". " +
                    "InjectorProvider is not initialized.");
        }
        return injector.getInstance(type);
    }
}