package models.lombok;

import lombok.Data;

@Data
public class CreateNewUserResponseModel {
    private String name;
    private String job;
    private int id;
    private String createdAt;
}
