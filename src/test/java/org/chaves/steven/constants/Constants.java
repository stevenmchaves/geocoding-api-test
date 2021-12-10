package org.chaves.steven.constants;

public final class Constants {
    private Constants() {
        // restrict instantiation
    }

    public static final String API_KEY = "AIzaSyCS8o0l2NtLQdGYSIJktkg-JbunioinqME";
    public static final String defaultHappyAddress = "?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA";
    public static final String MISSING_KEY = "You must use an API key to authenticate each request to Google Maps Platform APIs. For additional information, please refer to http://g.co/dev/maps-no-account";
    public static final String MISSING_PARAMS = "Invalid request. Missing the 'address', 'components', 'latlng' or 'place_id' parameter.";
    public static final String ERROR_RETURN_MSG = "An error should have been returned in the message";
}
