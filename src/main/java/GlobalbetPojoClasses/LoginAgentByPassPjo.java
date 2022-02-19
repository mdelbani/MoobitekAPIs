package GlobalbetPojoClasses;

import javax.xml.bind.annotation.*;
@XmlAccessorType(XmlAccessType.FIELD)
//here I just order the xml body
@XmlType(name = "", propOrder = {"Login","Password","Authorization"})
//here I just identify the root element of the xml body
@XmlRootElement(name = "LoginAgentByPasswordRequest")
public class LoginAgentByPassPjo {

    @XmlElement(name = "Login", required = true)
    private String Login;
    @XmlElement(name = "Password", required = true)
    private String Password;
    @XmlElement(name = "Authorization", required = true)
    private GlobalBetAuthorizationPjo Authorization;

    public LoginAgentByPassPjo(){

    }

    public LoginAgentByPassPjo(String Login, String Password, GlobalBetAuthorizationPjo Authorization){

        this.Login = Login;
        this.Password = Password;
        this.Authorization = Authorization;

    }

    public GlobalBetAuthorizationPjo getAuthorization() {
        return Authorization;
    }

    public void setAuthorization(GlobalBetAuthorizationPjo authorization) {
        Authorization = authorization;
    }
    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

}
