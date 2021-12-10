package org.chaves.steven; 

import static io.restassured.RestAssured.*; 
import static org.testng.Assert.*;
import static org.chaves.steven.constants.Constants.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test; 

import io.restassured.path.xml.XmlPath; 
import io.restassured.response.Response; 

/**
 * Testing against Google geocoding api.
 */
public class GeoCodingApiXmlFormatTest {

    @BeforeClass
    public void beforeClass() {
        // Setting BaseURI once
        baseURI = "https://maps.googleapis.com/maps/api/geocode";
    }

    @BeforeMethod
    public void beforeMethod() {
        // Setting BaseURI once
        baseURI = "https://maps.googleapis.com/maps/api/geocode";
    }

    private String invalidAddress = "?address=3945+White+Parkway";
    private String invalidAddressZero = "?address=39455555555555555555555555555555555555555555555555555555";

    @Test(description = "API call asking for XML output without KEY and Address")
    public void testAPIActiveXML() {
        Response response = given().get("/xml");
        response.then().statusCode(400);
        XmlPath xmlPath = new XmlPath(response.asString()).setRootPath("GeocodeResponse");
        String actualError = xmlPath.get("error_message");
        assertEquals(actualError, MISSING_PARAMS);
        assertEquals(xmlPath.getString("status"), "INVALID_REQUEST");
    }

    @Test(description = "API call asking for XML output without KEY")
    public void testAPIActiveXMLWithAddress() {
        Response response = given().get("/xml" + defaultHappyAddress);
        response.then().statusCode(200);
        XmlPath xmlPath = new XmlPath(response.asString()).setRootPath("GeocodeResponse");
        String actualError = xmlPath.get("error_message");
        assertEquals(actualError, MISSING_KEY);
        assertEquals(xmlPath.getString("status"), "REQUEST_DENIED");
    }

    @Test(description = "API call asking for XML output without Address")
    public void testAPIActiveXMLWithKey() {
        Response response = given().get("/xml&key=" + API_KEY);
        response.then().statusCode(404);
        assertTrue(response.getBody().asString().contains("was not found on this server."),
                "An error should have been returned in the message");
    }

    @Test(description = "API call asking for XML output with invalid parameter")
    public void testAPIActiveXMLWithInvalidParameter() {
        Response response = given().get("/xml?key=5");
        response.then().statusCode(400);
        XmlPath xmlPath = new XmlPath(response.asString()).setRootPath("GeocodeResponse");
        String actualError = xmlPath.get("error_message");
        assertEquals(actualError, MISSING_PARAMS);
        assertEquals(xmlPath.getString("status"), "INVALID_REQUEST");
    }

    @Test(description = "API call asking for XML output with only partial parameter")
    public void testAPIActiveXMLWithPartialParameter() {
        Response response = given().get("/xml?address=234" + "&key=" + API_KEY);
        response.then().statusCode(200);
        XmlPath xmlPath = new XmlPath(response.asString()).setRootPath("GeocodeResponse");
        String actualError = xmlPath.getString("error_message");
        assertEquals(actualError, "");
        assertEquals(xmlPath.getString("status"), "OK");
        XmlPath resultPath = xmlPath.setRootPath("GeocodeResponse.result");
        assertNotNull(resultPath.getString("formatted_address"));
    }

    @Test(description = "API call asking for XML output with KEY and invalid chars address")
    public void testXMLInvalidFormatAddress() {
         Response response = given().get("/xml" + invalidAddress + "&key=" + API_KEY);
        response.then().statusCode(200);
        XmlPath xmlPath = new XmlPath(response.asString()).setRootPath("GeocodeResponse");
        String actualError = xmlPath.getString("error_message");
        assertEquals(actualError, "");
        assertEquals(xmlPath.getString("status"), "OK");
        XmlPath resultPath = xmlPath.setRootPath("GeocodeResponse.result");
        String formattedAddress = resultPath.getString("formatted_address");
        assertNotEquals(formattedAddress, "Google Building 40, 1600 Amphitheatre Pkwy, Mountain View, CA 94043, USA");
        assertTrue(formattedAddress.contains("3945"), "Street Address should have contained 3945");
        assertTrue(formattedAddress.contains("White"), "Street Address should have contained White");
    }

    @Test(description = "API call asking for XML output with KEY and  address resulting in zero results")
    public void testXMLInvalidAddressZeroResults() {
        Response response = given().get("/xml" + invalidAddressZero + "&key=" + API_KEY);
        response.then().statusCode(200);
        XmlPath xmlPath = new XmlPath(response.asString()).setRootPath("GeocodeResponse");
        String actualError = xmlPath.getString("error_message");
        assertEquals(actualError, "");
        assertEquals(xmlPath.getString("status"), "ZERO_RESULTS");
        XmlPath resultPath = xmlPath.setRootPath("GeocodeResponse.result");
        String formattedAddress = resultPath.getString("formatted_address");
        assertEquals(formattedAddress.trim(), "");
    }

    @Test(description = "API call asking for XML output with KEY and address")
    public void testHappyPathXML() {
        Response response = given().get("/xml" + defaultHappyAddress + "&key=" + API_KEY);
        response.then().statusCode(200);
        XmlPath xmlPath = new XmlPath(response.asString()).setRootPath("GeocodeResponse");
        String actualError = xmlPath.getString("error_message");
        assertEquals(actualError, "");
        assertEquals(xmlPath.getString("status"), "OK");
        XmlPath resultPath = xmlPath.setRootPath("GeocodeResponse.result");
        assertEquals(resultPath.getString("formatted_address"),
                "Google Building 40, 1600 Amphitheatre Pkwy, Mountain View, CA 94043, USA");
    }
}

