package files;
import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;

public class SpecBuilderTest {

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
		
		
			
	/*here we are using Request spec builder concept to create an single object of RequestSpecification type which contains all the pre-configuration details like
		base uri, query param and other input values. */
		RequestSpecification req= new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
 						  .addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();
		
	/*here are are using response spec builder concept to create an single object of ResponseSpecificationtype which contains many expected response methods
	 * such as expectStatusCode(),expectContentType() */
		ResponseSpecification resspec= new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		RequestSpecification res = given().spec(req).body(ap);
		Response response=	res.when().post("maps/api/place/add/json").then().spec(resspec).extract().response();
		
		System.out.println(response.asString());
		

	}

}
