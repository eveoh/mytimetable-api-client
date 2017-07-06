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

import java.util.ArrayList;
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
    private static final String API_ENABLE_GZIP = "apiEnableGzip";

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
     * Enable Gzip compression
     *
     * Defaults to true
     */
    private boolean apiEnableGzip = true;


    public Configuration() {}

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

        apiEnableGzip = Boolean.parseBoolean(properties.getProperty(API_ENABLE_GZIP));
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

    public boolean isApiEnableGzip() {
        return apiEnableGzip;
    }

    public void setApiEnableGzip(boolean apiEnableGzip) {
        this.apiEnableGzip = apiEnableGzip;
    }

    /**
     * Creates a {@link Properties} object containing the configuration values.
     *
     * @return  Properties object
     */
    public Properties toProperties() {
        Properties ret = new Properties();

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
        ret.setProperty(API_ENABLE_GZIP, String.valueOf(apiEnableGzip));

        if (apiKey != null) {
            ret.setProperty(API_KEY, apiKey);
        }

        return ret;
    }
}
