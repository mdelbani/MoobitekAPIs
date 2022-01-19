package UserPojoClasses;

import java.util.List;

public class GetUserInfoPjo {

    public GetUserInfoPjo(){

    }
    private UserInfoPjo user;
    private String message;
    private String status;

    public UserInfoPjo getUser() {
        return user;
    }

    public void setUser(UserInfoPjo user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
