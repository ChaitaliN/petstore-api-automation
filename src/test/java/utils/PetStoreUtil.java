package utils;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;


public class PetStoreUtil {
    public PetStoreUtil(){
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

	public Response getAccessToken() {
		Response response = given().auth().preemptive().basic("test", "abc123")
				.contentType("application/x-www-form-urlencoded")
				.formParam("grant_type", "client_credentials")
				.formParam("scope", "read:pets write:pets")
				.when()
				.post("http://petstore.swagger.io/oauth/token");

        return response;
	}

    public RequestSpecification requestSpec() {

        // extract oauth2 token
		Response response = getAccessToken();
		JsonPath jp = new JsonPath(response.getBody().asString());
		String token = jp.get("access_token");

        RequestSpecBuilder builder = new RequestSpecBuilder();
		builder.addHeader("Authorization", String.format("Bearer %s", token));
        builder.addHeader("Content-Type", "application/json");
        return builder.build();
    }
}
