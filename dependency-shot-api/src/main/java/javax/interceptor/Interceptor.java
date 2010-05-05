/*
 *  Copyright 2009-2010 Mathieu ANCELIN
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

package javax.interceptor;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
/**
 * <p>Specifies that a class is an interceptor.</p>
 *
 * <pre>
 * &#064;Interceptor
 * public class ValidationInterceptor { ... }
 * </pre>
 *
 * <p>This annotation is optional if
 * {@link javax.interceptor.Interceptors &#064;Interceptors} or
 * the EJB deployment descriptor are used to associate the
 * interceptor with the target class. It is required when an
 * {@linkplain javax.interceptor.InterceptorBinding interceptor binding}
 * is used.</p>
 *
 * @see javax.interceptor.Interceptors
 */
@Retention(RUNTIME)
@Target(TYPE)
@Documented
public @interface Interceptor{}