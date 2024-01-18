package uz.utkirbek.model;

import org.springframework.stereotype.Component;

@Component
public class TrainingType extends BaseIdBean {
    private String name;

    public TrainingType() {
    }

    public TrainingType(int id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TrainingType{" +
                "name='" + name + '\'' +
                '}';
    }
}
