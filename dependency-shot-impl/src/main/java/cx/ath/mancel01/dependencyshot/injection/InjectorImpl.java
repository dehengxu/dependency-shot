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
package cx.ath.mancel01.dependencyshot.injection;

import cx.ath.mancel01.dependencyshot.api.DSBinder;
import cx.ath.mancel01.dependencyshot.api.DSInjector;
import cx.ath.mancel01.dependencyshot.exceptions.DSException;
import cx.ath.mancel01.dependencyshot.graph.Binder;
import cx.ath.mancel01.dependencyshot.graph.Binding;
import cx.ath.mancel01.dependencyshot.injection.handlers.ClassHandler;
import cx.ath.mancel01.dependencyshot.injection.handlers.ConstructorHandler;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Provider;
import javax.inject.Qualifier;

/**
 * This class represent an injector configured by coded
 * binders.
 * 
 * @author Mathieu ANCELIN
 */
public class InjectorImpl implements DSInjector {
    /**
     * Binders linked to the project.
     */
    private Vector<Binder> binders;
    /**
     * Managed Object instances.
     */
    private Vector<Object> managedInstances;
    /**
     * Singleton scoped object singletonContext.
     */
    private Map<Class<?>, Object> singletonContext;
    /**
     * The constructor.
     */
    public InjectorImpl() {
        binders = new Vector();
        managedInstances = new Vector<Object>();
        singletonContext = new HashMap<Class<?>, Object>();
    }
    /**
     * Configure all present binders of the injector.
     */
    public void configureBinders() {
        if (binders.size() > 0) {
            for (DSBinder binder : binders) {
                binder.configureBindings();
                if (binder.isEmpty()) {
                    Logger.getLogger(InjectorImpl.class.getName()).
                            log(Level.SEVERE, "Ooops, no bindings presents, "
                            + "can't inject your apps ...");
                    throw new DSException("No bindings loaded");
                }
            }
        }
    }

    public void addManagedInstance(Object o) {
        this.managedInstances.add(o);
    }

    public void resetManagedInstances() {
        this.managedInstances.removeAllElements();
    }

    public void setManagedInstances(Vector<Object> managedInstances) {
        this.managedInstances = managedInstances;
    }

    public Vector<Object> getManagedInstances() {
        return managedInstances;
    }

    /**
     * Add a binder in the injector.
     *
     * @param binder the binder to add.
     */
    public void addBinder(final DSBinder binder) {
        binder.setInjector(this);
        binders.add((Binder) binder);
    }
    /**
     * Get the defined bindings in all binders of the injector.
     * 
     * @return current bindings
     */
    public Map<Binding<?>, Binding<?>> bindings() { // TODO : replace for real multi-binder and better perf
        Map<Binding<?>, Binding<?>> bindings = new HashMap<Binding<?>, Binding<?>>();
        for (Binder binder : binders) {
            for (Binding<?> binding : binder.getBindings().keySet()) {
                bindings.put(binding, binder.getBindings().get(binding));
            }
        }
        return bindings;
    }

    /**
     * Get an injected instance of c
     *
     * @param <T> type
     * @param c class of the instance
     * @return instance of c
     */
    @Override
    public <T> T getInstance(Class<T> c) {
		return getInstance(c, null);
	}

    /**
     * Get an injected qualified instance of c
     *
     * @param <T> type
     * @param c class of the instance
     * @param qualifier instance qualifier
     * @return instance of c
     */
	private <T> T getInstance(Class<T> c, Annotation qualifier) {
		return getBinding(c, qualifier).getInstance(this);
	}

    /**
     * Get an existing binding in current bindings.
     *
     * @param <T> type
     * @param c class of the binding
     * @param annotation annotation of the binding
     * @return exsiting binding
     */
	private <T> Binding<T> getBinding(Class<T> c, Annotation annotation) {
		Binding<T> b = (Binding<T>) bindings().get(Binding.lookup(c, annotation));
		if (b != null) {
			return b;
		}
		throw new IllegalStateException("No binding for " + c + " and " + annotation);
	}

    /**
     * Get a singleton instance of a class @Singleton
     *
     * @param <T> type
     * @param c class of the new instance
     * @return singleton instance of c
     */
	public <T> T getSingleton(Class<T> c) {
        // check if the singleton is present in the singleton context
		T result = c.cast(singletonContext.get(c));
        // if not, create one
		if (result == null) {
			result = createInstance(c);
			singletonContext.put(c, result);
		}
		return result;
	}

    /**
     * Create a new instance of a class
     *
     * @param <T> type
     * @param c class of the new instance
     * @return new instance of c
     */
	public <T> T createInstance(Class<T> c) {
		try {
            // create a new instance of a class
			T result = ConstructorHandler.getConstructedInstance(c, this);
            // and inject it !!
			ClassHandler.classInjection(result, c, new ArrayList<Method>(), false, this);
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

    /**
     * Get an object instance of a qualified injection from a normal binding or
     * a defined provider.
     * 
     * @param type class of the object.
     * @param genericType type of the object.
     * @param annotations annotations present on the field or the method
     * @return
     */
	public Object getProviderOrInstance(Class<?> type, Type genericType, Annotation[] annotations) {
		Object value;
		Annotation qualifier = null;
        // search in custom annotations wich one is a qualifier
		for (int i = 0; i < annotations.length; i++) {
			Annotation annotation = annotations[i];
			if (annotation.annotationType().isAnnotationPresent(Qualifier.class)) {
                // when found, do the rest
				qualifier = annotation;
				break;
			}
		}
        // if provided, then return an injected provider
		if (Provider.class.isAssignableFrom(type) && genericType instanceof ParameterizedType) {
			ParameterizedType providerType = (ParameterizedType) genericType;
			final Class<?> providedType = (Class<?>) providerType.getActualTypeArguments()[0];
			final Annotation finalQualifier = qualifier;
			value = new Provider() {
                @Override
				public Object get() {
					return getInstance(providedType, finalQualifier);
				}
			};
		} else { // or get a simple instance
			value = getInstance(type, qualifier);
		}
		return value;
	}

    /**
     * Reset all the binders of an injector.
     */
    public void resetBinders() {
        this.binders = new Vector();
        this.singletonContext = new HashMap<Class<?>, Object>();
    }
}