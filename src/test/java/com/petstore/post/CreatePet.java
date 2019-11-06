package com.petstore.post;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.gson.Gson;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class CreatePet {

	
	@DataProvider
	public Object[][] mockData() {
		return new Object[][]{{18121990,"Yedo","Available", 200}, {0,"","", 404}};
	}
	
	public RequestSpecification requestSpec()
	{
	        RequestSpecBuilder builder = new RequestSpecBuilder();
	        builder.addHeader("Content-Type", "application/json");
	        return builder.build();
	 }
	
	Gson gson = new Gson();
	
	@Test(dataProvider="mockData",priority=1)
	public void createPet(int expectedCode)
	{
		given()
			//.pathParam("id",pid)
			.spec(requestSpec())
			.body(gson.toJson(mockData))
		.when()
			.post("https://petstore.swagger.io/v2/pet/")
		.then()
			.statusCode(expectedCode);
		/*json=res.toString();
		jp = new JsonPath(json);
		
		if(jp.get("name").equals(RestUtils.getName()))
		 {
			System.out.println("Id is matched");
		 }*/
		
	}
		
	
		
}
