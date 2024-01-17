import static io.restassured.RestAssured.*;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

public class API_Test_Automation {

    @Test
    public void ListUsers() {
        when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200)
                .time(lessThan(1000L));
    }

    @Test
    public void SingleUser() {
        Response response = get("https://reqres.in/api/users/2");

        System.out.println("Response :" + response.asString());
        System.out.println("Status Code :" + response.getStatusCode());
        System.out.println("Body :" + response.getBody().asString());
        System.out.println("Time taken :" + response.getTime());
        System.out.println("Header :" + response.getHeader("content-type"));

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
    }

    @Test
    public void ListResource() {
        when().
                get("https://reqres.in/api/unknown").
                then()
                .statusCode(200)
                .body("data.name", equalTo(Arrays.asList("cerulean", "fuchsia rose", "true red", "aqua sky", "tigerlily", "blue turquoise")))
                .time(lessThan(1000L));

    }

    @Test
    public void Create() {
        baseURI = "https://reqres.in/api";

        Map<String, Object> postData = new HashMap<String, Object>();
        postData.put("name", "ResatKavci");
        postData.put("job", "APITestAutomation");

        JSONObject request = new JSONObject(postData);

                given().
                        body(request.toJSONString()).
                when().
                        post("/users").
                then().
                        statusCode(201).
                        log().all();
        System.out.println(request.toJSONString());

    }

}
