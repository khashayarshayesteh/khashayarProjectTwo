package public_Api_tasks;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utilities.ConfigurationReader;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PublicApiTaskTest {
    static Response response;
    static PublicPojo publicPojo;

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = ConfigurationReader.get("public_api_url");
        response = given().accept(ContentType.JSON)
                .when().get(baseURI);
        publicPojo = response.body().as(PublicPojo.class);
    }

    @DisplayName("Verify status and content type")
    @Test
    void statusContentType() {
        assertEquals(200, response.statusCode(), "status code is not as expected");
        assertEquals("application/json", response.contentType(), "content type is not application/json");
    }

    @DisplayName("Get all data from the list where category = “Animals”  and contain link = “github”")
    @Test
    void getData() {
        System.out.println("\u001B[34m" + "Getting all data from the list where category = “Animals”  and contain link = “github”:" + "\u001B[0m");
        publicPojo.getEntries().stream()
                .filter(e -> e.getCategory().equalsIgnoreCase("Animals") && e.getLink().contains("github"))
                .forEach(System.out::println);
    }

    @DisplayName("Get all distinct cors value")
    @Test
    void getDistinctCors() {
        System.out.println("\u001B[34m" + "Getting all distinct cors value:" + "\u001B[0m");
        publicPojo.getEntries().stream()
                .map(PublicPojo.Entry::getCors)
                .distinct()
                .forEach(System.out::println);
    }

    @DisplayName("Get number/counts of apis belong to Animal category")
    @Test
    void getNumberOfAnimalCategory() {
        System.out.print("\u001B[34m" + "Getting number/counts of apis belong to Animal category:" + "\u001B[0m");
        long number = publicPojo.getEntries().stream()
                .filter(e -> e.getCategory().equalsIgnoreCase("Animals"))
                .count();

        System.out.println("\t" + number);
    }

    @DisplayName("Get first 3 Category which has most entries")
    @Test
    void getListOfCategory() {
        System.out.print("\u001B[34m" + "Getting first 3 Category which has most entries: " + "\u001B[0m");
        publicPojo.getEntries().stream()
                .collect(Collectors.groupingBy(PublicPojo.Entry::getCategory, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Comparator.comparing((Map.Entry<String, Long> e) -> e.getValue()).reversed())
                .limit(3)
                .forEach(e -> System.out.print(e.getKey() + ", "));
    }

    @DisplayName("List all the entries which HTTPS field does not match with link")
    @Test
    void verifyHTTPSField() {
        System.out.println("\u001B[34m" + "Listing all the entries which HTTPS field does not match with link: " + "\u001B[0m");
        System.out.format("| %-45s | %-5s | %-50s %n", "API", "HTTPS", "link");
        System.out.printf("----------------------------------------------------------------------------------------------------------%n");
        publicPojo.getEntries().stream()
                .filter(e -> e.getLink().contains("https") != e.isHttps())
                .forEach(e -> System.out.format("| %-45s | %-5s | %-50s %n", e.getApi(), e.isHttps(), e.getLink()));
    }

}
