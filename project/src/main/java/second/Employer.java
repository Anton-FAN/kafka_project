package second;

import javax.persistence.*;

@Entity
@Table(name = "Employer",schema = "myschema")
public class Employer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "employer_id")
    private Long id;
    private Integer INN;
    private String name;
    private Integer phoneNumber;

    public Employer() {
    }

    public Employer(Integer INN, String name, Integer phoneNumber) {
        this.INN = INN;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public void setINN(Integer INN) {
        this.INN = INN;
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

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getINN() {
        return INN;
    }

    public String getName() {
        return name;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;

    }
}
