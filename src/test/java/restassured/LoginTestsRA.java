package restassured;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import dto.AuthRequestDTO;
import dto.AuthResponseDTO;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class LoginTestsRA {

    String endpoint = "user/login/usernamepassword";


    @BeforeMethod
    public void preCondition(){
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";
    }

    @Test
    public void loginSuccess(){
        AuthRequestDTO authRequestDTO = AuthRequestDTO.builder()
                .username("kate24@gmail.com")
                .password("kaT45#kit")
                .build();

        AuthResponseDTO responseDTO = given()
                .body(authRequestDTO)
                .contentType("application/json")
                .when()
                .post(endpoint)
                .then()
                .assertThat().statusCode(200)
                .extract()
                .response()
                .as(AuthResponseDTO.class);
        System.out.println(responseDTO.getToken());
    }

}