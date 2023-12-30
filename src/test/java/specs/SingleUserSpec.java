package specs;

import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SingleUserSpec extends UsersListSpec {
    public RequestSpecification singleUserRequestSpec;
    public ResponseSpecification singleUserResponseSpec;

    public SingleUserSpec(){
        singleUserRequestSpec = super.usersListRequestSpec;
        singleUserResponseSpec = super.usersListResponseSpec;
    }
}
