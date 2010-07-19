package com.example.ncontainr;

import com.example.ncontainr.annotation.After;
import com.example.ncontainr.annotation.Before;
import com.example.ncontainr.aop.InvocationContext;
import javax.inject.Inject;

public class Interceptor {

    @Inject
    private InterceptorLogger logger;


    @Before
    public void before(InvocationContext ctx) {
        String message = "before " + ctx.getClass().getName()
            + "/" + ctx.getMethod().getName() + "()";
        logger.log(message);
        AOPMessages.getInstance().addBefore(message);
    }

    @After
    public void after(InvocationContext ctx) {
        String message = "after " + ctx.getClass().getName()
            + "/" + ctx.getMethod().getName() + "()";
        logger.log(message);
        AOPMessages.getInstance().addAfter(message);
    }
}
