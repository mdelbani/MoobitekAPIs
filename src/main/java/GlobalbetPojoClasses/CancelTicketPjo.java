package GlobalbetPojoClasses;

import javax.xml.bind.annotation.*;
@XmlAccessorType(XmlAccessType.FIELD)
//here I just order the xml body
@XmlType(name = "", propOrder = {"PlayerId","SessionToken","TicketNumber","Stake","CurrencyCode","Authorization"})
//here I just identify the root element of the xml body
@XmlRootElement(name = "CancelTicketRequest")
public class CancelTicketPjo {

        @XmlElement(name = "PlayerId", required = true)
        private String PlayerId;
        @XmlElement(name = "SessionToken", required = true)
        private String SessionToken;
        @XmlElement(name = "TicketNumber", required = true)
        private long TicketNumber;
        @XmlElement(name = "Stake", required = true)
        private float Stake;
        @XmlElement(name = "CurrencyCode", required = true)
        private String CurrencyCode;
        @XmlElement(name = "Authorization", required = true)
        private GlobalBetAuthorizationPjo Authorization;

        public CancelTicketPjo(){

        }

        public CancelTicketPjo(String PlayerId, String SessionToken, long TicketNumber, float Stake, String CurrencyCode, GlobalBetAuthorizationPjo Authorization){

            this.PlayerId = PlayerId;
            this.SessionToken = SessionToken;
            this.TicketNumber = TicketNumber;
            this.Stake = Stake;
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
