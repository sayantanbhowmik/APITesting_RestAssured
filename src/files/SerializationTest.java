package files;
import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import io.restassured.RestAssured;
import pojo.AddPlace;
import pojo.Location;

public class SerializationTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AddPlace ap = new AddPlace();
		ap.setAccuracy(60);
		ap.setAddress("33/6/2 Kolkata");
		ap.setLanguage("English");
		ap.setName("Sukanya");
		ap.setPhone_number("9587452122");
		ap.setWebsite("http://google.com");
		List<String> al = new ArrayList<String>();
		al.add("Nikko park");al.add("Metro");
		ap.setTypes(al);
		Location loc = new Location();
		loc.setLat(-38.383494);
		loc.setLng(33.427362);
		ap.setLocation(loc);
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response = given().queryParam("key", "qaclick123").body(ap)
		
		.when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).extract().response().asString();
		System.out.println(response);

	}

}
