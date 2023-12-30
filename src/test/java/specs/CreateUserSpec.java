package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;

public class CreateUserSpec extends UsersListSpec {
    public RequestSpecification createUserRequestSpec;
    public ResponseSpecification createUserResponseSpec;

    public CreateUserSpec(){
        createUserRequestSpec = super.usersListRequestSpec;
        createUserResponseSpec = new ResponseSpecBuilder()
                .log(STATUS)
                .log(BODY)
                .expectStatusCode(201)
                .build();
    }
}
