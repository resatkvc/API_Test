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

    // BDD stratejiyle Given,When,Then
    @Test
    public void ListUsers() {
        when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200)
                .time(lessThan(1000L));
    }

    //Kodsal olarak API test süreci
    @Test
    public void SingleUser() {
        //RestAssured kütüphanesini kullanarak URL'e bir GET isteği gönderir ve yanıtı response değişkenine atar.
        Response response = get("https://reqres.in/api/users/2");

        // HTTP yanıtını bir dize olarak yazdırır.
        System.out.println("Response :" + response.asString());
        //HTTP yanıtının durum kodunu yazdırır.
        System.out.println("Status Code :" + response.getStatusCode());
        //HTTP yanıtının gövdesini bir dize olarak yazdırır.
        System.out.println("Body :" + response.getBody().asString());
        //İsteğin tamamlanma süresini (milisaniye cinsinden) yazdırır.
        System.out.println("Time taken :" + response.getTime());
        //content-type" adlı HTTP başlığının değerini yazdırır.
        System.out.println("Header :" + response.getHeader("content-type"));

        //HTTP yanıtının durum kodunu bir değişkene atar.
        int statusCode = response.getStatusCode();
        //HTTP yanıtının durum kodunun 200 olup olmadığını kontrol eder.Başarılı bir HTTP GET isteğini temsil eder.
        // 200 den farklı bir expected  değer girildiğinde konsola Expected ve Actual(beklenen/gerçek) response bilgis döner
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
        //API endpoint'inin base URI'sini belirler. post isteğinde baseURL belirttiğimiz için sonrasında ki endpointi yazmamız yeterli olacaktır
        baseURI = "https://reqres.in/api";

        //Map, anahtar (key) ve değer (value) çiftlerini içeren bir koleksiyon tipidir. Kalıp bir ifadedir
        Map<String, Object> postData = new HashMap<String, Object>();
        //Gönderilecek veriyi belirttik
        postData.put("name", "ResatKavci");
        postData.put("job", "APITestAutomation");

        //JSON nesnesi olan request oluşturulur. Bu, POST isteğinin gövdesini temsil eder.
        JSONObject request = new JSONObject(postData);

                given().
                        body(request.toJSONString()).
                when().
                        post("/users").
                then().
                        statusCode(201). //Yanıt kontrolü yapılır. statusCode(201) ile yanıtın durum kodunun 201 (Created) olup olmadığı kontrol edilir. log().all() ile yanıtın tamamı konsola yazdırılır.
                        log().all();

                //SON isteği konsola yazdırılır.
        System.out.println(request.toJSONString());

    }

}
