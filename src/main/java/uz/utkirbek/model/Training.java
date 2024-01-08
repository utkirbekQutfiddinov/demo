package uz.utkirbek.model;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Training extends BaseIdBean{
    private Integer trainerId;
    private Integer traineeId;
    private String name;
    private Integer trainingTypeId;
    private Date date;
    private Integer duration;


    public Training() {
    }

    public Integer getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Integer trainerId) {
        this.trainerId = trainerId;
    }

    public Integer getTraineeId() {
        return traineeId;
    }

    public void setTraineeId(Integer traineeId) {
        this.traineeId = traineeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTrainingTypeId() {
        return trainingTypeId;
    }

    public void setTrainingTypeId(Integer trainingTypeId) {
        this.trainingTypeId = trainingTypeId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }


    @Override
    public String toString() {
        return "Training{" +
                "trainerId=" + trainerId +
                ", traineeId=" + traineeId +
                ", name='" + name + '\'' +
                ", trainingTypeId=" + trainingTypeId +
                ", date=" + date +
                ", duration=" + duration +
                '}';
    }
}
