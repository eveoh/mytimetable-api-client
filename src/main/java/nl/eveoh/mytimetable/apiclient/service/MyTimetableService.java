/*
 * Copyright 2013 - 2016 Eveoh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl.eveoh.mytimetable.apiclient.service;

import nl.eveoh.mytimetable.apiclient.model.Event;

import java.io.Closeable;
import java.util.List;
import java.util.Locale;

/**
 * Interface for a service which communicates with the MyTimetable API.
 */
public interface MyTimetableService extends Closeable {

    /**
     * Returns the upcoming events for the given user.
     *
     * @param username        username of the user to get the events of.
     *
     * @return List of events for the user.
     */
    List<Event> getUpcomingEvents(String username);

    /**
     * Returns the upcoming events for the given user in the given locale.
     *
     * When the specified locale is not available, the default locale will be used.
     *
     * @param username        username of the user to get the events of.
     * @param locale          locale to get the response in.
     *
     * @return List of events for the user.
     */
    List<Event> getUpcomingEvents(String username, Locale locale);
}