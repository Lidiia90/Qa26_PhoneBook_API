package restassured;

import dto.ContactDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class UpdateExistsContactRA {
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoia2F0ZTI0QGdtYWlsLmNvbSIsImlzcyI6IlJlZ3VsYWl0IiwiZXhwIjoxNzI1OTgwODc2LCJpYXQiOjE3MjUzODA4NzZ9.qe8O_ys9ycGgdohpSuiRcomArVQaZOgRBNYFfGB5yJs";
    String id;

    ContactDTO contactDTO = ContactDTO.builder()
            .name("Donna")
            .lastName("Down")
            .email("donna@gmail.com")
            .phone("6766777777")
            .address("Tel Aviv")
            .description("Donna")
            .build();

    @BeforeMethod
    public void preCondition() {
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";

      String message = given()
                .header("Authorization", token)
                .body(contactDTO)
                .contentType(ContentType.JSON)
                        .post("contacts")
                        .then()
                .assertThat().statusCode(200)
        .extract()
                .path("message");
        System.out.println(message);
String[]all = message.split(": ");
id = all[1];

    }
    @Test
    public void updateExistsContactSuccess(){
        String name = contactDTO.getName();
        contactDTO.setId(id);
        contactDTO.setName("fgfgfgfg");
        given()
                .body(contactDTO)
                .header("Authorization", token)
                .contentType(ContentType.JSON)
                .when()
                .put("contacts")
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("message", containsString("Contact was updated"));
    }
}
