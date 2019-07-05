package second;

import java.util.Date;

public class Request {
    private Long id;
    private Date date;
    private Integer amount;
    private Integer months;
    private Borrower borrower;
    private String decision;

    public Request(Date date, Integer amount, Integer months, Borrower borrower) {
        this.date = date;
        this.amount = amount;
        this.months = months;
        this.borrower = borrower;
    }

    public String getDecision() {
        return decision;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setMonths(Integer months) {
        this.months = months;
    }

    public void setBorrower(Borrower borrower) {
        this.borrower = borrower;
    }

    public Request() {
    }

    public Date getDate() {
        return date;
    }

    public Integer getAmount() {
        return amount;
    }

    public Integer getMonths() {
        return months;
    }

    public Borrower getBorrower() {
        return borrower;
    }
}
