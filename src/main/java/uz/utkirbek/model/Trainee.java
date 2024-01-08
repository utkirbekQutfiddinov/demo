package uz.utkirbek.model;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Trainee extends BaseIdBean{
    private Integer userId;
    private String address;
    private Date birthdate;

    public Trainee() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public String toString() {
        return "Trainee{" +
                "userId=" + userId +
                ", address='" + address + '\'' +
                ", birthdate=" + birthdate +
                '}';
    }
}
