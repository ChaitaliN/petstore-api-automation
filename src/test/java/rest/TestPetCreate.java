package rest;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.PetStoreUtil;
import java.util.HashMap;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;

public class TestPetCreate {
    protected static RequestSpecification defaultRequest;

    @BeforeTest
    public static void beforeClass() {
        PetStoreUtil configurator = new PetStoreUtil();
        defaultRequest = configurator.requestSpec();
    }

    // Test success cases
	@DataProvider
	public Object[][] successMockData() {
		return new Object[][]{
				{201, 991985, "Milo", ContentType.JSON},
		};
	}

    @Test(dataProvider="successMockData")
	public void testSuccess(int code, int petID, String petName, ContentType ctype) {

        // define payload
        HashMap<String, Object> payload = new HashMap();
        payload.put("id", petID);
        payload.put("name", petName);

		given()
			.spec(defaultRequest)
            .body(payload)
		.when()
			.post("/pet")
		.then()
			.statusCode(code)
            .contentType(ctype)
			.body("id", equalTo(petID))
			.body("name", equalTo(petName));
	}

    // Test fail cases
	@DataProvider
	public Object[][] failureMockData() {

        // define payload
        HashMap<String, Object> payload = new HashMap();
        payload.put("id", "string id");
        payload.put("name", "Milo");

		return new Object[][]{
				{payload, 400, "something bad happened"},
				{"raw string", 400, "bad input"},
		};
	}

    @Test(dataProvider="failureMockData")
	public void testFailure(Object payload, int code, String resMessage) {

		given()
			.spec(defaultRequest)
            .body(payload)
		.when()
			.post("/pet")
		.then()
			.statusCode(code)
			.body("message", equalTo(resMessage));
	}
}
