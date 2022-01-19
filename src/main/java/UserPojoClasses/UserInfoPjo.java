package UserPojoClasses;

import java.util.List;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getVirtualId() {
        return virtualId;
    }

    public void setVirtualId(String virtualId) {
        this.virtualId = virtualId;
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

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UserMobileNumberPjo getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(UserMobileNumberPjo mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public UserEmailInfoPjo getEmail() {
        return email;
    }

    public void setEmail(UserEmailInfoPjo email) {
        this.email = email;
    }

    public String getNationalityIdExpiry() {
        return nationalityIdExpiry;
    }

    public void setNationalityIdExpiry(String nationalityIdExpiry) {
        this.nationalityIdExpiry = nationalityIdExpiry;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getMaximumAmountPlayable() {
        return maximumAmountPlayable;
    }

    public void setMaximumAmountPlayable(String maximumAmountPlayable) {
        this.maximumAmountPlayable = maximumAmountPlayable;
    }

    public List<UserEmailsInfoPjo> getEmails() {
        return emails;
    }

    public void setEmails(List<UserEmailsInfoPjo> emails) {
        this.emails = emails;
    }

    public List<UserMobileNumbersPjo> getMobileNumbers() {
        return mobileNumbers;
    }

    public void setMobileNumbers(List<UserMobileNumbersPjo> mobileNumbers) {
        this.mobileNumbers = mobileNumbers;
    }

}
