package GlobalbetPojoClasses;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
//here I just order the xml body
@XmlType(name = "", propOrder = {"Login","Code","Authorization"})
//here I just identify the root element of the xml body
@XmlRootElement(name = "AuthenticateByCodeRequest")
public class AuthenticateByCodeRequest {

    @XmlElement(name = "Login", required = true)
    private String Login;
    @XmlElement(name = "Code", required = true)
    private String Code;
    @XmlElement(name = "Authorization", required = true)
    private GlobalBetAuthorizationPjo Authorization;

    public AuthenticateByCodeRequest(){

    }

    public AuthenticateByCodeRequest(String Login, String Code, GlobalBetAuthorizationPjo Authorization){

        this.Login = Login;
        this.Code = Code;
        this.Authorization = Authorization;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public GlobalBetAuthorizationPjo getAuthorization() {
        return Authorization;
    }

    public void setAuthorization(GlobalBetAuthorizationPjo authorization) {
        Authorization = authorization;
    }
}

