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

package javax.annotation;

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * The PreDestroy annotation is used on methods as a callback notification to
 * signal that the instance is in the process of being removed by the
 * container. The method annotated with PreDestroy is typically used to
 * release resources that it has been holding. This annotation MUST be
 * supported by all container managed objects that support PostConstruct
 * except the application client container in Java EE 5. The method on which
 * the PreDestroy annotation is applied MUST fulfill all of the following
 * criteria -
 * - The method MUST NOT have any parameters except in the case of EJB
 * interceptors in which case it takes an InvocationContext object as defined
 * by the EJB specification.
 * - The return type of the method MUST be void.
 * - The method MUST NOT throw a checked exception.
 * - The method on which PreDestroy is applied MAY be public, protected,
 * package private or private.
 * - The method MUST NOT be static.
 * - The method MAY be final.
 * - If the method throws an unchecked exception it is ignored except in the
 * case of EJBs where the EJB can handle exceptions.
 *
 * @see javax.annotation.PostConstruct
 * @see javax.annotation.Resource
 * @since Common Annotations 1.0
 */
@Documented
@Retention (RUNTIME)
@Target(METHOD)
public @interface PreDestroy {
}
