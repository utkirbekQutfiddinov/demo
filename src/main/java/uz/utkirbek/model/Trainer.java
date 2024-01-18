package uz.utkirbek.model;

import org.springframework.stereotype.Component;

@Component
public class Trainer extends BaseIdBean {
    private Integer userId;
    private String specialization;

    public Trainer() {
    }

    public Trainer(int userId, String spec) {
        super(userId);
        this.specialization = spec;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "userId=" + userId +
                ", specialization='" + specialization + '\'' +
                '}';
    }
}
