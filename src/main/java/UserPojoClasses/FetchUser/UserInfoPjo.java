package UserPojoClasses.FetchUser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfoPjo {

    public UserInfoPjo(){

    }
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String city;
    private String gender;
    private String dateOfBirth;
    private String virtualId;
    private String nationality;
    private String nationalityId;
    private String passportNumber;
    private String loginType;
    private String title;
    private UserMobileNumberPjo mobileNumber;
    private UserEmailInfoPjo email;
    private String nationalityIdExpiry;
    private String country;
    private String postalCode;
    private String maximumAmountPlayable;
    private List<UserEmailsInfoPjo> emails;
    private List<UserMobileNumbersPjo> mobileNumbers;
}
