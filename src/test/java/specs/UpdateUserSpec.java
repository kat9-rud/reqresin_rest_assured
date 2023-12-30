package specs;

import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class UpdateUserSpec extends UsersListSpec {
    public RequestSpecification updateUserRequestSpec;
    public ResponseSpecification updateUserResponseSpec;

    public UpdateUserSpec(){
        updateUserRequestSpec = super.usersListRequestSpec;
        updateUserResponseSpec = super.usersListResponseSpec;
    }
}
