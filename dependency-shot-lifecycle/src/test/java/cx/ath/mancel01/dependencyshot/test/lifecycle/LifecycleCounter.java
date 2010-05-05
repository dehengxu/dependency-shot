/*
 *  Copyright 2010 Mathieu ANCELIN.
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

package cx.ath.mancel01.dependencyshot.test.lifecycle;

/**
 *
 * @author Mathieu ANCELIN
 */
public class LifecycleCounter {

    private static LifecycleCounter instance = null;

    private int loadCounter = 0;

    private int unloadCounter = 0;

    private LifecycleCounter() {

    }

    public static LifecycleCounter getInstance() {
        if (instance == null)
            instance = new LifecycleCounter();
        return instance;
    }

    public void incrementLoad() {
        loadCounter++;
    }

    public void incrementUnload() {
        unloadCounter++;
    }

    public int getLoadCounter() {
        return loadCounter;
    }

    public int getUnloadCounter() {
        return unloadCounter;
    }
}