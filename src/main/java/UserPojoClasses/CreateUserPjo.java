package UserPojoClasses;

import com.fasterxml.jackson.annotation.JsonInclude;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNationalityId() {
        return nationalityId;
    }

    public void setNationalityId(String nationalityId) {
        this.nationalityId = nationalityId;
    }

    public String getNationalityIdExpiry() {
        return nationalityIdExpiry;
    }

    public void setNationalityIdExpiry(String getNationalityIdExpiry) {
        this.nationalityIdExpiry = getNationalityIdExpiry;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getRefugeeCardNumber() {
        return refugeeCardNumber;
    }

    public void setRefugeeCardNumber(String refugeeCardNumber) {
        this.refugeeCardNumber = refugeeCardNumber;
    }

    public int getMaximumAmountPlayable() {
        return maximumAmountPlayable;
    }

    public void setMaximumAmountPlayable(int maximumAmountPlayable) {
        this.maximumAmountPlayable = maximumAmountPlayable;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
