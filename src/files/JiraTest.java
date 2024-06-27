package files;

import static io.restassured.RestAssured.given;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

public class JiraTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String userName="sayanbhowmik1995";
		String password = "San77@kol";
		SessionFilter session = new SessionFilter();
		RestAssured.baseURI="http://localhost:8080";
	//Login into Jira API and get session id	
		String response = given().log().all().relaxedHTTPSValidation().header("Content-Type","application/json")
		.body("{ \"username\": \""+userName+"\", \"password\": \""+password+"\" }").filter(session)
		.when().post("rest/auth/1/session")
		.then().assertThat().log().all().statusCode(200).extract().response().asString();
		
	//using session id and defect id add a comment in the issue	
		String comment="OTP screen loading but OTP not going";
		String comment_res=given().pathParam("key", "10004").log().all().header("Content-Type","application/json")
		.body("{\r\n"
				+ "    \"body\": \""+comment+"\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}").filter(session).when().post("rest/api/2/issue/{key}/comment")
		.then().log().all().assertThat().statusCode(201).extract().response().asString();
		JsonPath j = ReUsableMethods.rawToJson(comment_res);
		String commentId = j.get("id");
		System.out.println("Comment Id : "+commentId);
		
		
	//Add attachment using the session id
//		given().log().all().header("X-Atlassian-Token","no-check").filter(session).pathParam("key", "10004").header("Content-Type","multipart/form-data")
//		.multiPart("file",new File("JiraIssueAttachment.txt"))
//		.when().post("/rest/api/2/issue/{key}/attachments")
//		.then().log().all().assertThat().statusCode(200);
		
	//Get issue details using session id - verify if added comment and attchment exist using GetIssue API
		String issueDetails = given().log().all().filter(session).pathParam("key", "10004")
		.queryParam("fields", "comment").when().get("/rest/api/2/issue/{key}")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println(issueDetails);
		JsonPath j2 = ReUsableMethods.rawToJson(issueDetails);
		int commentCount = j2.get("fields.comment.comments.size()");
		for(int i= 0 ;i<commentCount;i++)
		{
			String commentIdIssue = j2.get("fields.comment.comments["+i+"].id").toString();
			if(commentIdIssue.equalsIgnoreCase(commentId))
			{
				String message = j2.get("fields.comment.comments["+i+"].body").toString();
				System.out.println(message);
				Assert.assertEquals(comment, message);
				break;
			}
		}
		
		

	}

}
