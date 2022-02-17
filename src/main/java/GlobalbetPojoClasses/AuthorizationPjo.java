package GlobalbetPojoClasses;

public class AuthorizationPjo {
    private String systemId;
    private String systemPassword;

    public AuthorizationPjo(){

    }
    public AuthorizationPjo(String systemId, String systemPassword){

        this.systemId = systemId;
        this.systemPassword = systemPassword;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getSystemPassword() {
        return systemPassword;
    }

    public void setSystemPassword(String systemPassword) {
        this.systemPassword = systemPassword;
    }
}
