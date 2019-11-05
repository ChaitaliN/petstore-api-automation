package com.petstore.post;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class CreatePet {

	JSONObject requestParams = new JSONObject();
	
	@SuppressWarnings("unchecked")
	@BeforeClass()
	public void postData()
	{
		
		requestParams.put("id",RestUtils.getId());
		System.out.println(RestUtils.getName());
		requestParams.put("name", RestUtils.getName());
	}
	
	public ValidatableResponse res;
	public String json;
	public JsonPath jp;
	
	//@BeforeMethod
	@Test(priority=1)
	public void createPet()
	{
		res=given()
			.contentType("application/json")
			.body(requestParams.toJSONString())
		.when()
			.post("https://petstore.swagger.io/v2/pet/")
		.then()
			.statusCode(200);
		json=res.toString();
		jp = new JsonPath(json);
		
		if(jp.get("name").equals(RestUtils.getName()))
		 {
			System.out.println("Id is matched");
		 }
		
	}
		
	/*//@SuppressWarnings("deprecation")
	@Test(priority=1)
	public void verifyInvalidEnpoints()
	{
		 if(jp.get("id").equals(RestUtils.getId()))
		 {
			System.out.println("Id is matched");
		 }
	}*/
		
}
