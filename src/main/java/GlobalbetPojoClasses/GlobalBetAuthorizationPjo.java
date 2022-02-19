package GlobalbetPojoClasses;

public class GlobalBetAuthorizationPjo {
    private String systemId;
    private String systemPassword;

    public GlobalBetAuthorizationPjo(){

    }
    public GlobalBetAuthorizationPjo(String systemId, String systemPassword){

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
