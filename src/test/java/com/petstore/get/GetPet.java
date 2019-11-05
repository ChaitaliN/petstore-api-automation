package com.petstore.get;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


public class GetPet {

    @Test(dataProvider="mockData")
	public void getPetDetails(int pid, int expectedCode)
	{
		given()
			.contentType(ContentType.JSON)
			.pathParam("id", pid)
		.when()
			.get("https://petstore.swagger.io/v2/pet/{id}")
		.then()
			.statusCode(expectedCode);
	}

	@DataProvider
	public Object[][] mockData() {
		return new Object[][]{{1, 200}, {0, 404}};
	}

	@DataProvider(name="petStoreData")
	public String[][] petStoreData()
	{
		String[][] data= {{"301","Lucy","available"},{"302","Tommy","available"},{"18121990","Sejal", "sold"},};
		return data;
	}
	
	
	@Test(dataProvider="petStoreData",priority=2)
	public void get_pet_statusCode(String pid)
	{	
		given()
			.contentType("application/json")
			.pathParam("id", pid)
		.when()
			.get("https://petstore.swagger.io/v2/pet/{id}")
		.then()
			.statusCode(200)
			.statusLine("HTTP/1.1 200 OK")
			.log().all();
	 }
	
	
	@Test(dataProvider="petStoreData",priority=3)
	public void verify_pet_details(String pid,String pname,String pstatus)
	{	
		 
		given().when()
			.pathParam("id", pid)
			.get("https://petstore.swagger.io/v2/pet/{id}")
		.then()
			.assertThat().body("id", equalTo(pid))
			.and()
			.assertThat().body("name", equalTo(pname))
			.and()
			.assertThat().body("status", equalTo(pstatus));
		
	}

}
