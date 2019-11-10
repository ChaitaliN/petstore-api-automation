package rest;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.PetStoreUtil;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class TestPetGet {
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
				{991985, 200, ContentType.JSON},
		};
	}

    @Test(dataProvider="successMockData")
	public void testPositive(int pid, int expectedCode, ContentType ctype)
	{
		given()
			.spec(defaultRequest)
			.pathParam("id", pid)
		.when()
			.get("/pet/{id}")
		.then()
            .log()
            .all()
			.statusCode(expectedCode)
            .contentType(ctype)
            .body("any { it.key == 'id' }", is(true))
			.body("id", equalTo(pid));
	}

    // Test failure cases
	@DataProvider
	public Object[][] failureMockData() {
		return new Object[][]{
				{ContentType.JSON, 0, 404},
				{ContentType.JSON, "string_id", 400},
				{ContentType.JSON, "%%##@@", 400},
				{ContentType.TEXT, 991985, 400}
		};
	}

    @Test(dataProvider="failureMockData")
	public void testNegative(ContentType ctype, Object pid, int expectedCode)
	{
		given()
			.spec(defaultRequest)
			.contentType(ctype)
			.pathParam("id", pid)
		.when()
			.get("/pet/{id}")
		.then()
            .log()
            .all()
			.statusCode(expectedCode);
	}
}
