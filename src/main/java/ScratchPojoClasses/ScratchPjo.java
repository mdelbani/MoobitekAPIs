package ScratchPojoClasses;

import com.fasterxml.jackson.annotation.JsonInclude;


public class ScratchPjo {

    private int numberOfTickets;
    private int type;
    private String currency;

    public ScratchPjo(){

    }
    public ScratchPjo(int numberOfTickets, int type, String currency){

        this.numberOfTickets = numberOfTickets;
        this.type = type;
        this.currency = currency;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
