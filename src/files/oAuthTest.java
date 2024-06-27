package files;
import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;

import pojo.API;
import pojo.Courses;
import pojo.GetCourse;
import pojo.Mobile;
import pojo.WebAutomation;
public class oAuthTest {
	
	public static void getPriceOfSOAPCourse(List<API> list)
	{
		
		Map<String, String> map = new HashMap<>();
		for(API a : list)
		{
			map.put(a.getCourseTitle(), a.getPrice());
		}
		if(map.containsKey("SoapUI Webservices testing"))
		{
			System.out.println("Price of SoapUI course: "+map.get("SoapUI Webservices testing")); 
		}
	}
	public static void getAllCoursesOfWebAutomation(List<WebAutomation> webAutomation) {
		// TODO Auto-generated method stub
		String courseTitle[]= {"Selenium Webdriver Java","Cypress","Protractor"};
		ArrayList<String> al=new ArrayList<>();
		System.out.print("Courses of WebAutomation: ");
		for(WebAutomation we : webAutomation)
		{
			al.add(we.getCourseTitle());
		}
		System.out.println(al);
		Assert.assertEquals(courseTitle, al.toArray(),"Content of courseTitle and al not matching...");
		
	}
	public static void getTotalPriceOfAllCourses(Courses courses) {
		int totalPrice=0;
		for(WebAutomation w : courses.getWebAutomation())
		{
			totalPrice = totalPrice + Integer.parseInt(w.getPrice());
		}
		for(API ap  : courses.getApi())
		{
			totalPrice = totalPrice + Integer.parseInt(ap.getPrice());
		}
		for(Mobile m  : courses.getMobile())
		{
			totalPrice = totalPrice + Integer.parseInt(m.getPrice());
		}
		System.out.println("Total Price of All Courses: "+totalPrice);
		
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	//Create Access token from Authorization server
		String response = given().log().all()
		.formParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.formParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
		.formParams("grant_type","client_credentials")
		.formParams("scope","trust").when().log().all()
		.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();
		
		//System.out.println(response);
		String access_token = ReUsableMethods.rawToJson(response).get("access_token");
		System.out.println("Accesstoken: "+access_token);
	//Get course details using received Access token from authorization server	
//		String respose2=given().queryParams("access_token", access_token)
//		.when().log().all().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").asString();
//		System.out.println(respose2);
	//Get JSON values with POJO mechanism 
		GetCourse gc =given().queryParams("access_token", access_token)
				.when().log().all().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(GetCourse.class);
		System.out.println("====================================================================================");
		System.out.println("Instructor: "+gc.getInstructor());
		System.out.println("URL: "+gc.getUrl());
		System.out.println("Services: "+gc.getServices());
		System.out.println("Expertise: "+gc.getExpertise());
		System.out.println("LinkedIn: "+gc.getLinkedIn());
		getPriceOfSOAPCourse(gc.getCourses().getApi());
		System.out.println("====================================================================================");
		getAllCoursesOfWebAutomation(gc.getCourses().getWebAutomation());
		System.out.println("====================================================================================");
		getTotalPriceOfAllCourses(gc.getCourses());
		System.out.println("====================================================================================");
	}
	

	

}
