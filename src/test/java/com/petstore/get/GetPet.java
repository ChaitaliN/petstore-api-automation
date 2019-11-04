package com.petstore.get;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


public class GetPet {
	
	
	@Test()
	public void get_pet_details()
	{
		Response res = given()
				.contentType(ContentType.JSON)
				.pathParam("id",18121990)
				.when()
					.get("https://petstore.swagger.io/v2/pet/{id}");
		System.out.println(res.asString());
		String json = res.asString();
		JsonPath jp = new JsonPath(json);
		assertEquals(18121990, jp.get("id"));
		assertEquals("Yedo",jp.get("name"));
		assertEquals("Available",jp.get("status"));
		
	}
	/*
	@Test(priority=1)
	public void get_pet_details()
	{
		given()
			.contentType("application/json")
		.when()
			.get("https://petstore.swagger.io/v2/pet/18121990")
		.then()
			.statusCode(200)
			.statusLine("HTTP/1.1 200 OK")
			.log().all();
	 }
	@Test(priority=2)
	public void verify_pet_details()
	{	
		 
		given().when()
			.get("https://petstore.swagger.io/v2/pet/18121990")
		.then()
			.assertThat().body("id", equalTo(18121990))
			.assertThat().body("name", equalTo("Yedo"))
			.assertThat().body("status", equalTo("Available"));
		
	}*/

}
