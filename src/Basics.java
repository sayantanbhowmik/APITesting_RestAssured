import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;


//Add place->Update place with new address ->Get place to validate if new address is present in response

public class Basics {

	public static void main(String[] args) {
		//Validate if Add place API is working as expected
		//Rest Assured worked in three principles
		//Given - All input details
		//When- Submit the API - resources,http method
		//Then-validate the response
		//content of the file to String -> content of file can convert into Byte -> Byte data to String
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response = null;
		try {
			response = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
			.body(new String(Files.readAllBytes(Paths.get("C:\\Users\\User\\Downloads\\AddPlace.json")))).when().post("maps/api/place/add/json")
			.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
			.header("server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Response of Add place: "+response);
			
		JsonPath js =ReUsableMethods.rawToJson(response);//for parsing JSON
		String place_id = js.getString("place_id");
		System.out.println("Place ID: "+place_id);
		
//		String ref= js.getString("reference");
//		System.out.println("Reference: "+ref);
		
		
	//update place
		String newAddress = "AC-71,Newtown";
		String respose_AddUpdate =given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+place_id+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}\r\n"
				+ "").when().put("maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"))
		.header("server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
		System.out.println("After adding address: "+respose_AddUpdate);
		
	//Get place
		String res_getPlace = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", place_id)
		.when().get("maps/api/place/get/json")
		.then().assertThat().log().all().statusCode(200)
		.extract().response().asString();
		System.out.println("Response of get Place api: "+res_getPlace);
		
		JsonPath js1 = ReUsableMethods.rawToJson(res_getPlace);//for parsing JSON
		
		String address = js1.getString("address");
		System.out.println("Updated address: "+address);
		Assert.assertEquals(address, newAddress);
		
		
		
		
	}

}
