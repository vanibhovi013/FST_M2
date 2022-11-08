package Project;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class Testing {
    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;
    String sshKey;
    int id;

    @BeforeClass
    public void beforeClass(){
        requestSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("Authorization","token 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")
                .setBaseUri("https://api.github.com")
                .build();
    }

    @Test(priority = 1)
    public void postRequest(){
        String reqbody = """
                    {
                    "title": "TestAPIKey",
                    "key": "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQDoSPzKooMUj0AM6WX4iveyCJhDfZElSAduK9rVkEZ43SawjRAqyjgFb3HP6TalpIQ+OmwXk5VNg17Pm++wfZzC/bSlpqS+OPhA498IDh8ELuINzdi54LRiZBWTV0l/rGG5XtVnWuV9mQb1j0vzPGDtWZb6GKB3Hg7zFU+GiCAIGzAppoLZ2LuSYnlQxpxs3HAlwsF8Q/FmVZerf6bte42qm/w1ceRtkZ8XIOdVV8silK/biE8N5jTjiqrzXnHO1ZGz2q3ZkItZ4dJS0NkhRM/HWGV3/v8/CLlP44IY6m4DmBVqgdXnwkkzHoUi/NN3swiTDo7MpZEEcu/yjiTaDUtogBQm5T1vm0k/q/81UkzShQEBnzDtq8Rb9wY1/g2807BsIciD8R9f5nRVVizyd9f3oIeCAIL97gPAlaafo1VXZLL9SCWtJAC5cmfkYOptvVcHIaJwi3x7ikGqtDSXmzmyqQJfkSA6iClZsmMIG/tJ3o0YyNiHLkgwW7QzF+9LN+0= gmx\\\\00010y744@DESKTOP-DCF5JHO"
                     }
                """;
        Response response = given().spec(requestSpec).body(reqbody).when().post("/user/keys");
        System.out.println(response.asPrettyString());
        id = response.path("id");
        responseSpec = new ResponseSpecBuilder().expectStatusCode(201).build();
    }

    @Test(priority = 2)
    public void getRequest() {
        Response response =
                given().spec(requestSpec)
                        .when().get();
        String body = response.getBody().asPrettyString();
        System.out.println(body);
        responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
    }

    @Test(priority = 3)
    public void deleteRequest()
    {
        Response response = given().spec(requestSpec).when().pathParam("keyID",id).delete("/{keyID}");
        System.out.println(response.asPrettyString());
        responseSpec = new ResponseSpecBuilder().expectStatusCode(204).build();
    }

}
