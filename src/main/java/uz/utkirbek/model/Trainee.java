package uz.utkirbek.model;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "trainees")
public class Trainee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer userId;
    private String address;
    private Date birthdate;

    public Trainee() {
    }

    public Trainee(int id, Integer userId, String address, Date birthdate) {
        this.id=id;
        this.userId = userId;
        this.address = address;
        this.birthdate = birthdate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
