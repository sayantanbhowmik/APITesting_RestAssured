import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import files.PayLoad;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath; 

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String res=given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json").body(PayLoad.addPlace())
		.when().post("maps/api/place/add/json").then().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
		//System.out.println(res);
		
		JsonPath js = new JsonPath(res);
		System.out.println(js.getString("place_id"));

	}

}
