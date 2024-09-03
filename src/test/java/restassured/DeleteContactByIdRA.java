package restassured;

import dto.ContactDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DeleteContactByIdRA {

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoia2F0ZTI0QGdtYWlsLmNvbSIsImlzcyI6IlJlZ3VsYWl0IiwiZXhwIjoxNzI1OTgwODc2LCJpYXQiOjE3MjUzODA4NzZ9.qe8O_ys9ycGgdohpSuiRcomArVQaZOgRBNYFfGB5yJs";
    String id;

    @BeforeMethod
    public void preCondition() {
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";
        int i = new Random().nextInt(1000)+1000;

        ContactDTO contactDTO = ContactDTO.builder()
                .name("Donna")
                .lastName("Down")
                .email("donna" +i+"@gmail.com")
                .phone("6766777777")
                .address("Tel Aviv")
                .description("Donna")
                .build();

       String message = given()
                .body(contactDTO)
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .post("contacts")
                .then()
                .assertThat().statusCode(200)
                .extract()
                .path("message");

        String[] all = message.split(": ");
        id = all[1];
    }

    @Test
    public void deleteContactById(){
        given()
                .header("Authorization", token)
                .when()
                .delete("contacts/"+id)
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("message", equalTo("Contact was deleted!"));
    }
    @Test
    public void deleteContactByIdWrongToken(){
        given()
                .header("Authorization", "ghghghg")
                .when()
                .delete("contacts/"+id)
                .then()
                .assertThat().statusCode(401);
    }
    @Test
    public void deleteContactByIdAnyFormatError(){
        given()
                .header("Authorization", token)
                .when()
                .delete("contacts/123")
                .then()
                .assertThat().statusCode(400)
                .assertThat().body("message", equalTo("Contact with id: 123 not found in your contacts!"));
    }
}


