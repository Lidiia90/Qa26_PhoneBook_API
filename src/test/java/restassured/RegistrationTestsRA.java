package restassured;

import dto.AuthRequestDTO;
import dto.AuthResponseDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class RegistrationTestsRA {

    String endpoint = "user/registration/usernamepassword";

    @BeforeMethod
    public void preCondition() {
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";
    }

    @Test
    public void registrationSuccess() {
        int i = (int) (System.currentTimeMillis() / 1000) % 3600;
        AuthRequestDTO authRequestDTO = AuthRequestDTO.builder()
                .username("dot" + i + "@gmail.com")
                .password("Dgjgh345$")
                .build();

        String token = given()
                .body(authRequestDTO)
                .contentType(ContentType.JSON)
                .when()
                .post(endpoint)
                .then()
                .assertThat().statusCode(200)
                .extract()
                .path("token");
        System.out.println(token);
    }

    @Test
    public void registrationWrongEmail() {
        AuthRequestDTO authRequestDTO = AuthRequestDTO.builder()
                .username("dogmail.com")
                .password("Dgjgh345$")
                .build();

        given()
                .body(authRequestDTO)
                .contentType(ContentType.JSON)
                .when()
                .post(endpoint)
                .then()
                .assertThat().statusCode(400)
                .assertThat().body("message.username", containsString("must be a well-formed email address"));

    }

    @Test
    public void registrationWrongPassword() {
        AuthRequestDTO authRequestDTO = AuthRequestDTO.builder()
                .username("do24@gmail.com")
                .password("345")
                .build();

        given()
                .body(authRequestDTO)
                .contentType(ContentType.JSON)
                .when()
                .post(endpoint)
                .then()
                .assertThat().statusCode(400)
                .assertThat().body("message.password", containsString("At least 8 characters; Must contain at least 1 uppercase letter, 1 lowercase letter, and 1 number"));
    }
    @Test
    public void registrationDuplicate(){
        AuthRequestDTO authRequestDTO = AuthRequestDTO.builder().username("kate24@gmail.com").password("kaT45#kit").build();

        given()
                .body(authRequestDTO)
                .contentType(ContentType.JSON)
                .when()
                .post(endpoint)
                .then()
                .assertThat().statusCode(409)
                .assertThat().body("message", containsString("User already exists"));

    }
}
