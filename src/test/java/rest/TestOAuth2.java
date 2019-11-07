package rest;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class TestOAuth2 {

	@DataProvider
	public Object[][] mockData() {
		return new Object[][]{
				{"client_credentials", 200, "token_type", "bearer"},
				{"", 400, "error", "invalid_request"},
		};
	}

    @Test(dataProvider="mockData")
	public void testAuth(String grant, int expectedCode, String key, String value) {

		given().auth().preemptive().basic("test", "abc123")
			.contentType("application/x-www-form-urlencoded")
			.formParam("grant_type", grant)
			.formParam("scope", "read:pets write:pets")
		.when()
			.post("http://petstore.swagger.io/oauth/token")
        .then()
		    .statusCode(expectedCode)
            .body(String.format("any { it.key == '%s' }", key), is(true))
			.body(key, equalTo(value));
	}
}
