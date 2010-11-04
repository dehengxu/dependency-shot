/*
 *  Copyright 2010 mathieu.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

package cx.ath.mancel01.dependencyshot.dynamic.v2;

import cx.ath.mancel01.dependencyshot.DependencyShot;
import cx.ath.mancel01.dependencyshot.api.DSInjector;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author mathieu
 */
public class DynamicTest {

    public DynamicTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void hello() {

        DSInjector injector = DependencyShot.getInjector(new DynamicBinder() {
            @Override
            public void configureBindings() {
                bindDynamically(PaymentService.class);
                //bind(PaymentService.class).to(CashServiceImpl.class);
            }
        });
        ServiceRegistry.getInstance().registerService(PaymentService.class, PayPalServiceImpl.class);
        ServiceRegistry.getInstance().registerService(PaymentService.class, CreditCardServiceImpl.class);
        PaymentService service = injector.
                getInstance(PaymentService.class);
        service.pay(123);
        ServiceRegistry.getInstance().unregisterService(PayPalServiceImpl.class);
        service.pay(123);
        ServiceRegistry.getInstance().registerService(PaymentService.class, PayPalServiceImpl.class);
        service.pay(123);
        ServiceRegistry.getInstance().swap(PayPalServiceImpl.class);
        service.pay(123);
        ServiceRegistry.getInstance().swap(CashServiceImpl.class);
        service.pay(123);

    }

}