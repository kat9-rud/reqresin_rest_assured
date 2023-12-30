package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;

public class SingleUserNotFoundSpec extends UsersListSpec {
    public RequestSpecification singleUserNotFoundRequestSpec;
    public ResponseSpecification singleUserNotFoundResponseSpec;

    public SingleUserNotFoundSpec(){
        singleUserNotFoundRequestSpec = super.usersListRequestSpec;
        singleUserNotFoundResponseSpec = new ResponseSpecBuilder()
                .log(STATUS)
                .log(BODY)
                .expectStatusCode(404)
                .build();
    }
}
