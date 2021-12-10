package org.chaves.steven;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.*;

/**
 * Testing against Google geocoding api.
 */
public class GeoCodingApiTest 
{
    private String geocodeUrl = "https://maps.googleapis.com/maps/api/geocode";
    private String geocodeUrlXML = geocodeUrl + "/xml";
    private String geocodeUrlJSON = geocodeUrl + "/json";

    // private String defaultHappyAddress = "?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA";

    // private static String API_KEY = "AIzaSyCS8o0l2NtLQdGYSIJktkg-JbunioinqME";

    @Test(description="without supplying any parameters you test its active and return is 404")
    public void testAPIActive()
    {
        get(geocodeUrl).then().statusCode(404);
    }

    @Test(description="API call asking for JSON output without KEY and Address")
    public void testAPIActiveJSON()
    {
        get(geocodeUrlJSON).then().statusCode(404);
    }

    @Test(description="API call asking for XML output without KEY and Address")
    public void testAPIActiveXML()
    {
        get(geocodeUrlXML).then().statusCode(404);
    }
}
