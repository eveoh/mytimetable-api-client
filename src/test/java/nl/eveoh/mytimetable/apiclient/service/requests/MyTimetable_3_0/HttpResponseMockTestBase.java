/*
 * Copyright 2013 - 2017 Eveoh
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

package nl.eveoh.mytimetable.apiclient.service.requests.MyTimetable_3_0;

import nl.eveoh.mytimetable.apiclient.configuration.Configuration;
import nl.eveoh.mytimetable.apiclient.service.requests.AbstractHttpResponseMockTestBase;

public class HttpResponseMockTestBase extends AbstractHttpResponseMockTestBase {

    @Override
    public Configuration getConfiguration() {
        Configuration configuration = new Configuration(API_KEY, API_ENDPOINT);
        configuration.setMyTimetableVersion(Configuration.MyTimetable_Version.V3_0);

        return configuration;
    }
}
