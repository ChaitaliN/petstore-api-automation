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
	
	@Test()
	public JsonPath createPet()
	{
		res=given()
			.contentType("application/json")
			.body(requestParams.toJSONString())
		.when()
			.post("https://petstore.swagger.io/v2/pet/")
		.then()
			.statusCode(201);
		json=res.toString();
		JsonPath jp = new JsonPath(json);
		return jp;
	}
	
	@Test()
	public void verifyPetId(JsonPath jp)
	{
		//when().then()
		//	.post()
		//	.assertThat("RestUtils.getId()",jp.get("id"));
	}
		
}
