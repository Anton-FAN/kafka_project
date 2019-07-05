package second;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "BORROWER")
public class Borrower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "borrower_id")
    private Long id;
    private String name;
    private String surname;
    private String secondName;
    private Date dateOfBirth;
    private String gender;
    private Integer incomePerMonth;
    private Integer expensesPerMonth;
    @OneToOne
    @JoinColumn(name = "employer_id")
    private Employer employer;

    public Borrower() {
    }

    public Borrower(String name, String surname, String secondName, Date dateOfBirth, String gender, Integer incomePerMonth, Integer expensesPerMonth, Employer employer) {
        this.name = name;
        this.surname = surname;
        this.secondName = secondName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.employer = employer;
        this.incomePerMonth = incomePerMonth;
        this.expensesPerMonth = expensesPerMonth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setIncomePerMonth(Integer incomePerMonth) {
        this.incomePerMonth = incomePerMonth;
    }

    public void setExpensesPerMonth(Integer expensesPerMonth) {
        this.expensesPerMonth = expensesPerMonth;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getOtchevstvo() {
        return secondName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public Employer getEmployer() {
        return employer;
    }

    public Integer getIncomePerMonth() {
        return incomePerMonth;
    }

    public Integer getExpensesPerMonth() {
        return expensesPerMonth;
    }
}
