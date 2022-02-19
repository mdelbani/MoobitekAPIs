package GlobalbetPojoClasses;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
//here I just order the xml body
@XmlType(name = "", propOrder = {"PlayerId","SessionToken","Authorization"})
//here I just identify the root element of the xml body
@XmlRootElement(name = "GetBalanceRequest")
public class GetPlayerBalance {

    @XmlElement(name = "PlayerId", required = true)
    private String PlayerId;
    @XmlElement(name = "SessionToken", required = true)
    private String SessionToken;
    @XmlElement(name = "Authorization", required = true)
    private GlobalBetAuthorizationPjo Authorization;

    public GetPlayerBalance(){

    }

    public GetPlayerBalance(String PlayerId, String SessionToken, GlobalBetAuthorizationPjo Authorization){

        this.PlayerId = PlayerId;
        this.SessionToken = SessionToken;
        this.Authorization = Authorization;
    }

    public String getPlayerId() {
        return PlayerId;
    }

    public void setPlayerId(String playerId) {
        PlayerId = String.valueOf(Integer.parseInt(playerId));
    }

    public String getSessionToken() {
        return SessionToken;
    }

    public void setSessionToken(String sessionToken) {
        SessionToken = sessionToken;
    }

    public GlobalBetAuthorizationPjo getAuthorization() {
        return Authorization;
    }

    public void setAuthorization(GlobalBetAuthorizationPjo authorization) {
        Authorization = authorization;
    }

}
