package apilesson.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiLessonTest {
    @DisplayName("Проверка поиска несуществующего пользователя")
    @Test
    void userNotFoundTest() {
        given()
                .log().uri()
                .log().body()
                .when()
                .get("https://reqres.in/api/users/23")
                .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }

    @DisplayName("Проверка поиска существующего пользователя")
    @Test
    void userFoundTest() {
        given()
                .log().all()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .body("data.first_name", is("Janet"));
    }

    @DisplayName("Проверка запроса списка пользователей")
    @Test
    void usersListTest() {
        String expectedUsersList = "{\"page\":2,\"per_page\":6,\"total\":12,\"total_pages\":2,\"data\":[{\"id\":7,\"email\":\"michael.lawson@reqres.in\",\"first_name\":\"Michael\",\"last_name\":\"Lawson\",\"avatar\":\"https://reqres.in/img/faces/7-image.jpg\"},{\"id\":8,\"email\":\"lindsay.ferguson@reqres.in\",\"first_name\":\"Lindsay\",\"last_name\":\"Ferguson\",\"avatar\":\"https://reqres.in/img/faces/8-image.jpg\"},{\"id\":9,\"email\":\"tobias.funke@reqres.in\",\"first_name\":\"Tobias\",\"last_name\":\"Funke\",\"avatar\":\"https://reqres.in/img/faces/9-image.jpg\"},{\"id\":10,\"email\":\"byron.fields@reqres.in\",\"first_name\":\"Byron\",\"last_name\":\"Fields\",\"avatar\":\"https://reqres.in/img/faces/10-image.jpg\"},{\"id\":11,\"email\":\"george.edwards@reqres.in\",\"first_name\":\"George\",\"last_name\":\"Edwards\",\"avatar\":\"https://reqres.in/img/faces/11-image.jpg\"},{\"id\":12,\"email\":\"rachel.howell@reqres.in\",\"first_name\":\"Rachel\",\"last_name\":\"Howell\",\"avatar\":\"https://reqres.in/img/faces/12-image.jpg\"}],\"support\":{\"url\":\"https://reqres.in/#support-heading\",\"text\":\"To keep ReqRes free, contributions towards server costs are appreciated!\"}}";

        String acualUsersList =  given()
                .log().all()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200)
                .extract().asString();
        assertEquals(expectedUsersList, acualUsersList);
    }

    @DisplayName("Проверка редактирования существующего пользователя")
    @Test
    void userUpadateTest() {
        String requestBody = "{ \"name\": \"morpheus\", \"job\": \"zion resident\" }";

        given()
                .log().all()
                .contentType(JSON)
                .body(requestBody)
                .when()
                .patch("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));
    }

   @DisplayName("Проверка создания нового пользователя")
    @Test
    void userCreateTest() {
        String requestBody = "{ \"name\": \"ramil\", \"job\": \"best-qa\" }";

        given()
                .log().all()
                .contentType(JSON)
                .body(requestBody)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201)
                .body("name", is("ramil"))
                .body("job", is("best-qa"));
    }
}
