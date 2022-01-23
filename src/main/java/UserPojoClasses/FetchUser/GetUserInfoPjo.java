package UserPojoClasses.FetchUser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetUserInfoPjo {

    public GetUserInfoPjo(){

    }
    private UserInfoPjo user;
    private String message;
    private String status;
    private String token;
    private String errorMessages;
    private String redirectUrl;
}
