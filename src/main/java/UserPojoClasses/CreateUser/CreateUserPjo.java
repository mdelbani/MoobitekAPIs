package UserPojoClasses.CreateUser;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateUserPjo {
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private String operator;
    private String password;
    private String confirmPassword;
    private String dateOfBirth;
    private String city;
    private String country;
    private String gender;
    private String title;
    private String username;
    private String district;
    private String division;
    private String nationality;
    private String nationalityId;
    private String nationalityIdExpiry;
    private String passportNumber;
    private String refugeeCardNumber;
    private int maximumAmountPlayable;
    private String address;
    private String postalCode;
    private String token;
    private String message;
    private String code;
    private String type;
    private String status;
    private String user;

    public CreateUserPjo(){

    }

    public CreateUserPjo(String firstName,String lastName,String email,String mobileNumber,String operator,String password, String confirmPassword,String dateOfBirth,
                    String city,String country,String gender,String title,String username,String nationality,String nationalityId,String nationalityIdExpiry,
                    String passportNumber,int maximumAmountPlayable,String postalCode){

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.operator = operator;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.dateOfBirth = dateOfBirth;
        this.city = city;
        this.country = country;
        this.gender = gender;
        this.title = title;
        this.username = username;
        this.nationality = nationality;
        this.nationalityId = nationalityId;
        this.nationalityIdExpiry = nationalityIdExpiry;
        this.passportNumber = passportNumber;
        this.maximumAmountPlayable = maximumAmountPlayable;
        this.postalCode = postalCode;
    }
}
