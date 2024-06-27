import files.PayLoad;
import io.restassured.path.json.JsonPath;
/*1. Print No of courses returned by API

2.Print Purchase Amount

3. Print Title of the first course
 */
public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JsonPath js = new JsonPath(PayLoad.coursePrice());
		
	//1. Print No of courses returned by API
		int count = js.getInt("courses.size()");
		System.out.println("Number of courses returned by API: "+count);
		
	//2.Print Purchase Amount
		int amount = js.getInt("dashboard.purchaseAmount");
		System.out.println("Purchase Amount: "+amount);
	//3. Print Title of the first course
		String title = js.get("courses.title[0]");
		System.out.println("Title of the first course: "+title);
	//4. Print All course titles and their respective Prices
		for(int i=0;i<count;i++)
		{
			String cTitle=js.get("courses["+i+"].title");
			String price = js.get("courses["+i+"].price").toString();
			System.out.println(cTitle+"->"+price);
		}
	//5. Print no of copies sold by RPA Course
		for(int i=0;i<count;i++)
		{
			String cTitle=js.get("courses["+i+"].title");
			if(cTitle.equalsIgnoreCase("RPA"))
			{
				String copy=js.get("courses["+i+"].copies").toString();
				System.out.println("Number of copies sold by RPA Course: "+copy);
				break;
			}
		}
	//6. Verify if Sum of all Course prices matches with Purchase Amount
		int sum=0;
		for(int i=0;i<count;i++)
		{
			int price = js.get("courses["+i+"].price");
			int copy=js.get("courses["+i+"].copies");
			
			sum=sum+price*copy;
			
		}
		System.out.println(sum);
		if(amount==sum) {
			System.out.println("Sum of all Course prices matches with Purchase Amount.");
		} else {
			System.out.println("Sum of all Course prices not matches with Purchase Amount.");
		}
		
		
		
		

	}

}
