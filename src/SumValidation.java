import org.testng.Assert;
import org.testng.annotations.Test;

import files.PayLoad;
import io.restassured.path.json.JsonPath;
public class SumValidation {

	
	@Test
	public void sumOfCourses()
	{
		JsonPath js = new JsonPath(PayLoad.coursePrice());
		//6. Verify if Sum of all Course prices matches with Purchase Amount
				int sum=0;
				int count = js.getInt("courses.size()");
				int amount = js.getInt("dashboard.purchaseAmount");
				for(int i=0;i<count;i++)
				{
					int price = js.get("courses["+i+"].price");
					int copy=js.get("courses["+i+"].copies");
					
					sum=sum+price*copy;
					
				}
				System.out.println(sum);
//				if(amount==sum) {
//					System.out.println("Sum of all Course prices matches with Purchase Amount.");
//				} else {
//					System.out.println("Sum of all Course prices not matches with Purchase Amount.");
//				}
				Assert.assertEquals(amount, sum);
	}
	

}
