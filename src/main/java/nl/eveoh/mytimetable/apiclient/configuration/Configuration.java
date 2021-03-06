/*
 * Copyright 2013 - 2014 Eveoh
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

package nl.eveoh.mytimetable.apiclient.configuration;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Generic Configuration object.
 *
 * @author Marco Krikke
 * @author Erik van Paassen
 */
public class Configuration {

    private static final String API_ENDPOINT_URIS = "apiEndpointUris";
    private static final String API_KEY = "apiKey";
    private static final String API_SSL_CN_CHECK = "apiSslCnCheck";
    private static final String API_CONNECT_TIMEOUT = "apiConnectTimeout";
    private static final String API_SOCKET_TIMEOUT = "apiSocketTimeout";
    private static final String API_MAX_CONNECTIONS = "apiMaxConnections";
    private static final String APPLICATION_URI = "applicationUri";
    private static final String APPLICATION_TARGET = "applicationTarget";
    private static final String USERNAME_DOMAIN_PREFIX = "usernameDomainPrefix";
    private static final String USERNAME_POSTFIX = "usernamePostfix";
    private static final String MAX_NUMBER_OF_EVENTS = "maxNumberOfEvents";
    private static final String DEFAULT_NUMBER_OF_EVENTS = "defaultNumberOfEvents";
    private static final String TIMETABLE_TYPES = "timetableTypes";
    private static final String SHOW_ACTIVITY_TYPES = "showActivityType";
    private static final String UNKNOWN_LOCATION_DESCRIPTION = "unknownLocationDescription";

    private static final String[] DEFAULT_TIMETABLE_TYPES =
            new String[] { "module", "pos", "posgroup", "studentsetgroup", "posss", "student", "staff", "activitygroup",
                    "modulepos", "studentset" };

    /**
     * Key used for communicating with the MyTimetable API.
     * <p/>
     * Key should have elevated access.
     */
    private String apiKey = "";

    /**
     * Endpoint URL of the MyTimetable API.
     * <p/>
     * Should be something like <tt>https://timetable.institution.ac.uk/api/v0/</tt>.
     */
    private List<String> apiEndpointUris = new ArrayList<String>();

    /**
     * Whether the SSL certificate CN should be verified when connecting to the MyTimetable API.
     * <p/>
     * Default to {@code true}.
     */
    private boolean apiSslCnCheck = true;

    /**
     * Timeout for connecting to the MyTimetable API, in milliseconds.
     * <p/>
     * Defaults to 1000 (1 second).
     */
    private int apiConnectTimeout = 1000;

    /**
     * Timeout for the socket waiting for data from the MyTimetable API.
     * <p/>
     * Defaults to 10000 (10 seconds).
     */
    private int apiSocketTimeout = 10000;

    /**
     * Maximum number of concurrent connections in the MyTimetable API connection pool.
     */
    private int apiMaxConnections = 20;

    /**
     * URL to the full MyTimetable application.
     * <p/>
     * Should be something like <tt>https://timetable.institution.ac.uk/</tt>.
     */
    private String applicationUri = null;

    /**
     * Target of the full application link.
     * <p/>
     * Should be <tt>_self</tt>, <tt>_blank</tt>, <tt>_parent</tt>, or <tt>_top</tt>. Defaults to <tt>_blank</tt>.
     */
    private String applicationTarget = "_blank";

    /**
     * Maximum number of events to display.
     * <p/>
     * Defaults to 5.
     */
    private int maxNumberOfEvents = 5;

    /**
     * Number of events that is displayed by default
     * <p/>
     * Defaults to 5.
     */
    private int defaultNumberOfEvents = 5;

    /**
     * Domain to prefix usernames with (excluding slash delimiter).
     * <p/>
     * Defaults to {@code null}.
     */
    private String usernameDomainPrefix;

    /**
     * String to postfix usernames with.
     * <p/>
     * Defaults to {@code null}.
     */
    private String usernamePostfix;

    /**
     * List of timetable type identifiers of which activities should be included when retrieving the user's personal
     * timetable. Can be empty to include all activities. Defaults to 'module', 'pos', 'posgroup', 'studentsetgroup',
     * 'posss', 'student', 'staff', 'activitygroup', 'modulepos', 'studentset' (i.e., all non-location timetables)
     */
    private final List<String> timetableTypes;

    /**
     * Show Activity Type in the overivew.
     * <p/>
     * Defaults to false;
     */
    private boolean showActivityType = true;

    /**
     * Replace the default text displayed when the location is unknown;
     * <p/>
     * Not set by default.
     */
    private String unknownLocationDescription;

    public Configuration() {
        timetableTypes = new ArrayList<String>(Arrays.asList(DEFAULT_TIMETABLE_TYPES));
    }

    public Configuration(Properties properties) {
        apiKey = properties.getProperty(API_KEY);

        String uris = properties.getProperty(API_ENDPOINT_URIS);
        if (uris != null) {
            for (String uri : uris.split("\n")) {
                uri = uri.trim();

                if (!uri.isEmpty()) {
                    apiEndpointUris.add(uri);
                }
            }
        }

        apiSslCnCheck = Boolean.parseBoolean(properties.getProperty(API_SSL_CN_CHECK));

        try {
            apiConnectTimeout = Integer.parseInt(properties.getProperty(API_CONNECT_TIMEOUT));
        } catch (NumberFormatException e) { /* Do nothing, keep default value. */ }

        try {
            apiSocketTimeout = Integer.parseInt(properties.getProperty(API_SOCKET_TIMEOUT));
        } catch (NumberFormatException e) { /* Do nothing, keep default value. */ }

        try {
            apiMaxConnections = Integer.parseInt(properties.getProperty(API_MAX_CONNECTIONS));
        } catch (NumberFormatException e) { /* Do nothing, keep default value. */ }

        applicationUri = properties.getProperty(APPLICATION_URI);
        applicationTarget = properties.getProperty(APPLICATION_TARGET);
        usernameDomainPrefix = properties.getProperty(USERNAME_DOMAIN_PREFIX);
        usernamePostfix = properties.getProperty(USERNAME_POSTFIX);

        String timetableTypesStr = properties.getProperty(TIMETABLE_TYPES);
        if (!Strings.isNullOrEmpty(timetableTypesStr)) {
            timetableTypes = new ArrayList<String>(Splitter.on(';').trimResults().omitEmptyStrings().splitToList(timetableTypesStr));
        } else {
            timetableTypes = new ArrayList<String>(Arrays.asList(DEFAULT_TIMETABLE_TYPES));
        }

        try {
            maxNumberOfEvents = Integer.parseInt(properties.getProperty(MAX_NUMBER_OF_EVENTS));
        } catch (NumberFormatException e) { /* Do nothing, keep default value. */ }

        try {
            defaultNumberOfEvents = Integer.parseInt(properties.getProperty(DEFAULT_NUMBER_OF_EVENTS));
        } catch (NumberFormatException e) { /* Do nothing, keep default value. */ }

        showActivityType = Boolean.parseBoolean(properties.getProperty(SHOW_ACTIVITY_TYPES));

        unknownLocationDescription = properties.getProperty(UNKNOWN_LOCATION_DESCRIPTION);
    }


    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public List<String> getApiEndpointUris() {
        return apiEndpointUris;
    }

    public void setApiEndpointUris(List<String> apiEndpointUris) {
        this.apiEndpointUris = apiEndpointUris;
    }

    public boolean isApiSslCnCheck() {
        return apiSslCnCheck;
    }

    public void setApiSslCnCheck(boolean apiSslCnCheck) {
        this.apiSslCnCheck = apiSslCnCheck;
    }

    public int getApiConnectTimeout() {
        return apiConnectTimeout;
    }

    public void setApiConnectTimeout(int apiConnectTimeout) {
        this.apiConnectTimeout = apiConnectTimeout;
    }

    public int getApiSocketTimeout() {
        return apiSocketTimeout;
    }

    public void setApiSocketTimeout(int apiSocketTimeout) {
        this.apiSocketTimeout = apiSocketTimeout;
    }

    public int getApiMaxConnections() {
        return apiMaxConnections;
    }

    public void setApiMaxConnections(int apiMaxConnections) {
        this.apiMaxConnections = apiMaxConnections;
    }

    public String getApplicationUri() {
        return applicationUri;
    }

    public void setApplicationUri(String applicationUri) {
        this.applicationUri = applicationUri;
    }

    public String getApplicationTarget() {
        return applicationTarget;
    }

    public void setApplicationTarget(String applicationTarget) {
        this.applicationTarget = applicationTarget;
    }

    public int getMaxNumberOfEvents() {
        return maxNumberOfEvents;
    }

    public void setMaxNumberOfEvents(int maxNumberOfEvents) {
        this.maxNumberOfEvents = maxNumberOfEvents;
    }

    public int getDefaultNumberOfEvents() {
        return defaultNumberOfEvents;
    }

    public void setDefaultNumberOfEvents(int defaultNumberOfEvents) {
        this.defaultNumberOfEvents = defaultNumberOfEvents;
    }

    public String getUsernameDomainPrefix() {
        return usernameDomainPrefix;
    }

    public void setUsernameDomainPrefix(String usernameDomainPrefix) {
        this.usernameDomainPrefix = usernameDomainPrefix;
    }

    public String getUsernamePostfix() {
        return usernamePostfix;
    }

    public void setUsernamePostfix(String usernamePostfix) {
        this.usernamePostfix = usernamePostfix;
    }

    public List<String> getTimetableTypes() {
        return timetableTypes;
    }

    public boolean isShowActivityType() {
        return showActivityType;
    }

    public void setShowActivityType(boolean showActivityType) {
        this.showActivityType = showActivityType;
    }

    public String getUnknownLocationDescription() {
        return unknownLocationDescription;
    }

    public void setUnknownLocationDescription(String unknownLocationDescription) {
        this.unknownLocationDescription = unknownLocationDescription;
    }

    /**
     * Creates a {@link Properties} object containing the configuration values.
     *
     * @return  Properties object
     */
    public Properties toProperties() {
        Properties ret = new Properties();

        if (applicationUri != null) {
            ret.setProperty(APPLICATION_URI, applicationUri);
        }

        if (applicationTarget != null) {
            ret.setProperty(APPLICATION_TARGET, applicationTarget);
        }

        StringBuilder uris = new StringBuilder();
        for (String apiEndpointUri : apiEndpointUris) {
            if (uris.length() > 0) {
                uris.append('\n');
            }

            uris.append(apiEndpointUri);
        }
        ret.setProperty(API_ENDPOINT_URIS, uris.toString());

        ret.setProperty(API_SSL_CN_CHECK, String.valueOf(apiSslCnCheck));
        ret.setProperty(API_CONNECT_TIMEOUT, String.valueOf(apiConnectTimeout));
        ret.setProperty(API_SOCKET_TIMEOUT, String.valueOf(apiSocketTimeout));
        ret.setProperty(API_MAX_CONNECTIONS, String.valueOf(apiMaxConnections));

        ret.setProperty(SHOW_ACTIVITY_TYPES, String.valueOf(showActivityType));
        ret.setProperty(DEFAULT_NUMBER_OF_EVENTS, String.valueOf(defaultNumberOfEvents));

        if (apiKey != null) {
            ret.setProperty(API_KEY, apiKey);
        }

        ret.setProperty(MAX_NUMBER_OF_EVENTS, String.valueOf(maxNumberOfEvents));

        if (usernameDomainPrefix != null) {
            ret.setProperty(USERNAME_DOMAIN_PREFIX, usernameDomainPrefix);
        }

        if (usernamePostfix != null) {
            ret.setProperty(USERNAME_POSTFIX, usernamePostfix);
        }

        if (!timetableTypes.isEmpty()) {
            ret.setProperty(TIMETABLE_TYPES, Joiner.on(';').skipNulls().join(timetableTypes));
        }

        if (unknownLocationDescription != null) {
            ret.setProperty(UNKNOWN_LOCATION_DESCRIPTION, unknownLocationDescription);
        }

        return ret;
    }
}
