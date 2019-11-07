package rest;

import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.PetStoreUtil;
import java.util.HashMap;
import static io.restassured.RestAssured.given;

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
				{200},
				{201}, // create status code should be 201
		};
	}

    @Test(dataProvider="successMockData")
	public void testSuccess(int code) {

        // define payload
        HashMap<String, Object> payload = new HashMap();
        payload.put("id", "991985");
        payload.put("name", "Milo");

		given()
			.spec(defaultRequest)
            .body(payload)
		.when()
			.post("/pet")
		.then()
			.statusCode(code);
	}

    // Test fail cases
	@DataProvider
	public Object[][] failureMockData() {

        // define payload
        HashMap<String, Object> payload = new HashMap();
        payload.put("id", "!@#");

		return new Object[][]{
				{payload, 405},
				{"random", 400}, // create status code should be 201
		};
	}

    @Test(dataProvider="failureMockData")
	public void testFailure(Object payload, int code) {

		given()
			.spec(defaultRequest)
            .body(payload)
		.when()
			.post("/pet")
		.then()
			.statusCode(code);
	}
}
