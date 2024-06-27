package files;

import static io.restassured.RestAssured.given;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJson {
	@Test(dataProvider="BooksData")
	public void addBook(String isbn,String aisle)
	{
		RestAssured.baseURI="http://216.10.245.166";
		String response=given().log().all().header("Content-Type","application/json")
		.body(PayLoad.addBook(isbn,aisle)).when().post("/Library/Addbook.php")
		.then().assertThat().log().all().statusCode(200)
		.extract().response().asString();
		JsonPath js = ReUsableMethods.rawToJson(response);
		String msg=js.get("Msg");
		System.out.println("Message of the Add Book API: "+msg);
		String iD=js.get("ID");
		System.out.println("Id of the book:  "+iD);
		System.out.println("Now its time to delete the book!!!");
		deleteBook(iD);
	}
	
	@DataProvider(name="BooksData")
	public Object[][] getData()
	{
		return new Object[][] {{"qzw","969"},{"sed","745"},{"okn","314"}};
	}
	public void deleteBook(String id)
	{
		String dlt_response=given().log().all().header("Content-Type","application/json")
		.body("{\r\n"
				+ "\"ID\" : \""+id+"\"\r\n"
				+ "} \r\n"
				+ "").when().post("/Library/DeleteBook.php")
		.then().assertThat().log().all().statusCode(200)
		.extract().response().asString();
		
		JsonPath js = ReUsableMethods.rawToJson(dlt_response);
		String msg=js.get("msg");
		System.out.println("Message of the Delete Book API: "+msg);
	}

}
