package com.example.ncontainr;

import com.example.ncontainr.annotation.Intercept;
import com.example.ncontainr.annotation.PostConstruct;
import com.example.ncontainr.annotation.PreDestroy;

public class LoggerImpl implements Logger {

    @PostConstruct
    public void start() {
        System.out.println("start");
    }

    @PreDestroy
    public void stop() {
        System.out.println("stop");
    }

    @Override
    @Intercept(Interceptor.class)
    public void log(String message) {
        System.out.println("[ LOGGER IMPL ] " + message);
    }
}
