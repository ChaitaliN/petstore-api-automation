package com.petstore.get;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


public class GetPet {

	public String getAccessToken() {

		Response response = given().auth().preemptive().basic("test", "abc123")
				.contentType("application/x-www-form-urlencoded")
				.formParam("grant_type", "client_credentials")
				.formParam("scope", "read:pets write:pets")
				.when()
				.post("http://petstore.swagger.io/oauth/token");

		JsonPath jp = new JsonPath(response.getBody().asString());
		String accessToken = jp.get("access_token");
		return accessToken;
	}

	public RequestSpecification requestSpec() {
		final String token = getAccessToken();
		RequestSpecBuilder builder = new RequestSpecBuilder();
		builder.addHeader("Authorization", String.format("Bearer %s", token));
		return builder.build();
	}

    @Test(dataProvider="mockData")
	public void getPetDetails(ContentType cType, String pid, int expectedCode)
	{
		given()
			.spec(requestSpec())
			.contentType(cType)
			.pathParam("id", pid)
		.when()
			.get("https://petstore.swagger.io/v2/pet/{id}")
		.then()
			.statusCode(expectedCode)
			.body("id", equalTo(Integer.parseInt(pid)));
	}

	@DataProvider
	public Object[][] mockData() {
		return new Object[][]{
				{ContentType.JSON, "18121990", 200},
				{ContentType.JSON, "0", 404},
				{ContentType.JSON, "notNumber", 404},
				{ContentType.JSON, "%%##@@", 404},
				{ContentType.TEXT, "18121990", 400},
		};
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
