package GlobalbetPojoClasses;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
//here I just order the xml body
@XmlType(name = "", propOrder = {"SubjectSessionId","Subject","Authorization"})
//here I just identify the root element of the xml body
@XmlRootElement(name = "GetAgentBalanceRequest")
public class GetAgentBalance {

    @XmlElement(name = "SubjectSessionId", required = true)
    private String SubjectSessionId;
    @XmlElement(name = "Subject", required = true)
    private String Subject;
    @XmlElement(name = "Authorization", required = true)
    private GlobalBetAuthorizationPjo Authorization;

    public GetAgentBalance(String SubjectSessionId, String Subject, GlobalBetAuthorizationPjo Authorization){

        this.SubjectSessionId = SubjectSessionId;
        this.Subject = Subject;
        this.Authorization = Authorization;
    }

    public GetAgentBalance() {
    }

    public GetAgentBalance(String subjectSessionId, String subject) {
    }

    public String getSubjectSessionId() {
        return SubjectSessionId;
    }

    public void setSubjectSessionId(String subjectSessionId) {
        SubjectSessionId = subjectSessionId;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public GlobalBetAuthorizationPjo getAuthorization() {
        return Authorization;
    }

    public void setAuthorization(GlobalBetAuthorizationPjo authorization) {
        Authorization = authorization;
    }
}
