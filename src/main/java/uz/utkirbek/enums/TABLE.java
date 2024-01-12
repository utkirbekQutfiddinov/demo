package uz.utkirbek.enums;

public enum TABLE {
    USERS("users"),
    TRAINEES("trainees"),
    TRAINERS("trainers"),
    TRAINING_TYPES("trainingTypes"),
    TRAININGS("trainings");


    private final String tableName;

    TABLE(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }
}
