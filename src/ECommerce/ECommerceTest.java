package ECommerce;
import static io.restassured.RestAssured.given;

import java.io.File;

import files.ReUsableMethods;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import pojo.LoginRequest;
import pojo.LoginResponse;

public class ECommerceTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON)
		.build();
	//Login call to generate auth token
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUserEmail("write@gmail.com");
		loginRequest.setUserPassword("San77@kol");
		
		RequestSpecification relogin= given().relaxedHTTPSValidation().spec(req).body(loginRequest);
		LoginResponse loginResponse =relogin.when().post("api/ecom/auth/login").then().extract().response().as(LoginResponse.class);
		String token = loginResponse.getToken();
		System.out.println(token);
		String userId= loginResponse.getUserId();
		System.out.println(userId);
		System.out.println(loginResponse.getMessage());
	
	//Add product
		RequestSpecification addProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", token)
				.build();
		RequestSpecification reqAddProduct =given().log().all().spec(addProductBaseReq).param("productName", "Laptop").param("productAddedBy", userId).param("productCategory", "fashion")
		.param("productSubCategory", "shirts").param("productPrice", "11500").param("productDescription", "Addias Originals").param("productFor", "men")
		.multiPart("productImage",new File("laptop.jpg"));
		
		String addProductResponse = reqAddProduct.when().post("api/ecom/product/add-product").then().extract().response().asString();
		System.out.println(addProductResponse);
		JsonPath js = ReUsableMethods.rawToJson(addProductResponse);
		String productId=js.get("productId");
		System.out.println("ProductId: "+productId);
	//create order
//		System.out.println("Creating order");
//		RequestSpecification createOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", token)
//				.setContentType(ContentType.JSON).build();
//		
//		OrderDetails orderDetails = new OrderDetails();
//		orderDetails.setCountry("India");
//		orderDetails.setProductOrderedId(productId);
//		List<OrderDetails> orderDetailsList=new ArrayList<>();
//		orderDetailsList.add(orderDetails);
//		Orders order=new Orders();
//		order.setOrders(orderDetailsList);
//		
//		RequestSpecification  createOrderReq = given().log().all().spec(createOrderBaseReq).body(order);
//		String responseAddOrder = createOrderReq.when().post("api/ecom/order/create-order").then().log().all().extract().response().asString();
//		System.out.println(responseAddOrder);
//		JsonPath js1 = ReUsableMethods.rawToJson(responseAddOrder);
//		String orderId = js1.get("orders[0]");
//		System.out.println("Newly created OderId:- "+orderId);
//		
//	//View order details	
//		RequestSpecification viewOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", token)
//				.setContentType(ContentType.JSON).build();
//		
//		RequestSpecification  viewOrderReq = given().log().all().queryParam("id", orderId) .spec(viewOrderBaseReq);
//		String responseviewOrder = viewOrderReq.when().get("/api/ecom/order/get-orders-details").then().log().all().extract().response().asString();
//		System.out.println(responseviewOrder);
		
	//Delete the newly added product
		RequestSpecification deleteProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", token)
				.build();
		RequestSpecification deleteProductReq = given().log().all().spec(deleteProductBaseReq).pathParam("productId", productId);
		String deleteResponse = deleteProductReq.when().delete("api/ecom/product/delete-product/{productId}").then().log().all().extract().response().asString();
		System.out.println(deleteResponse);
		
		
	}

}
