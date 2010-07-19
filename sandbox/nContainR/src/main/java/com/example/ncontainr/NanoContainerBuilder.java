package com.example.ncontainr;

import com.example.ncontainr.api.Container;
import com.example.ncontainr.aop.AOP;

/**
 * Builder utility class to create containers. 
 */
public class NanoContainerBuilder {
    /**
     * Build a new Container.
     * @param state if AOP is enabled of not.
     * @return a brand new Container.
     */
    public static Container build(AOP state) {
        if (state.equals(AOP.ENABLED)) {
            return new NanoContainer(true);
        } else {
            return new NanoContainer(false);
        }
    }
}
