/*
 *  Copyright 2010 mathieuancelin.
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

package cx.ath.mancel01.dependencyshot.event;

import cx.ath.mancel01.dependencyshot.exceptions.DSException;
import java.util.UUID;
import javax.inject.Inject;

/**
 * Event class.
 *
 * @author mathieuancelin
 */
public class Event {

    private String id;

    @Inject
    private EventManager manager;

    public Event() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void fire() {
        if (manager == null) {
            throw new DSException("You should inject the event in order to call fire on it.");
        }
        manager.fireEvent(this);
    }

}