package GlobalbetPojoClasses;

import org.checkerframework.checker.units.qual.C;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
//here I just order the xml body
@XmlType(name = "", propOrder = {"PlayerId","SessionToken","TicketNumber","Stake","Payout","Jackpot","TotalNettoPayout","Commission","CurrencyCode","Authorization"})
//here I just identify the root element of the xml body
@XmlRootElement(name = "PayoutTicketRequest")
public class PayoutTicketPjo {
    @XmlElement(name = "PlayerId", required = true)
    private String PlayerId;
    @XmlElement(name = "SessionToken", required = true)
    private String SessionToken;
    @XmlElement(name = "TicketNumber", required = true)
    private long TicketNumber;
    @XmlElement(name = "Stake", required = true)
    private float Stake;
    @XmlElement(name = "Payout", required = true)
    private float Payout;
    @XmlElement(name = "Jackpot", required = true)
    private float Jackpot;
    @XmlElement(name = "TotalNettoPayout", required = true)
    private float TotalNettoPayout;
    @XmlElement(name = "Commission", required = true)
    private float Commission;
    @XmlElement(name = "CurrencyCode", required = true)
    private String CurrencyCode;
    @XmlElement(name = "Authorization", required = true)
    private GlobalBetAuthorizationPjo Authorization;

    public PayoutTicketPjo(){

    }

    public PayoutTicketPjo(String PlayerId,String SessionToken,long TicketNumber,float Stake,float Jackpot,
                           float TotalNettoPayout,float Commission,String CurrencyCode, GlobalBetAuthorizationPjo Authorization){

        this.PlayerId = PlayerId;
        this.SessionToken = SessionToken;
        this.TicketNumber = TicketNumber;
        this.Stake = Stake;
        this.Jackpot = Jackpot;
        this.TotalNettoPayout = TotalNettoPayout;
        this.Commission = Commission;
        this.CurrencyCode = CurrencyCode;
        this.Authorization = Authorization;

    }

    public String getPlayerId() {
        return PlayerId;
    }

    public void setPlayerId(String playerId) {
        PlayerId = playerId;
    }

    public String getSessionToken() {
        return SessionToken;
    }

    public void setSessionToken(String sessionToken) {
        SessionToken = sessionToken;
    }

    public long getTicketNumber() {
        return TicketNumber;
    }

    public void setTicketNumber(long ticketNumber) {
        TicketNumber = ticketNumber;
    }

    public float getStake() {
        return Stake;
    }

    public void setStake(float stake) {
        Stake = stake;
    }

    public float getPayout() {
        return Payout;
    }

    public void setPayout(float payout) {
        Payout = payout;
    }

    public float getJackpot() {
        return Jackpot;
    }

    public void setJackpot(float jackpot) {
        Jackpot = jackpot;
    }

    public float getTotalNettoPayout() {
        return TotalNettoPayout;
    }

    public void setTotalNettoPayout(float totalNettoPayout) {
        TotalNettoPayout = totalNettoPayout;
    }

    public float getCommission() {
        return Commission;
    }

    public void setCommission(float Commission) {
        this.Commission = Commission;
    }

    public String getCurrencyCode() {
        return CurrencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        CurrencyCode = currencyCode;
    }

    public GlobalBetAuthorizationPjo getAuthorization() {
        return Authorization;
    }

    public void setAuthorization(GlobalBetAuthorizationPjo authorization) {
        Authorization = authorization;
    }

}
